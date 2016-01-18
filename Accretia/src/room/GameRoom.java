package room;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gui.*;
import io.Input;
import main.Game;
import map.Background;
import object.*;

public class GameRoom extends Room {
	
	// Debug constants
	private final boolean DO_SPAWN = true;
	
	private final double 
		SPAWN_SPEED = 0.03,
		RESPAWN_SPEED = 0.005;
	
	private final int 
		SPAWN_DISTANCE = 325,
		MIN_RANDOM_SIZE = 8,
		MAX_RANDOM_SIZE = 15;
	
	// GameRoom Objects
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Explosion> explosions;
    private ArrayList<Missile> missiles;
	private Planet planet;
	private PlayerShip ship;
	private int shipColor;
	
	// Spawn Timers
	private double spawnTimer, respawnTimer;

	// Background
	private Background bg;
	
	// GUI
	private HealthBar shipHealth;
	private LivesCounter shipLives;
	private ScoreCounter score;
	
	// For debug purposes only
	private static boolean debugState;
	
	public GameRoom(int color) {
		bg = new Background("/Backgrounds/Space.png/", 0);
		planet = new Planet(Game.getWidth(false) / 2, Game.getHeight(false) / 2, 30, 30);
		
		shipColor = color;
		shipHealth = new HealthBar(10, Game.getHeight(false) - 40, 100, 10, true);
		shipLives = new LivesCounter(10, Game.getHeight(false) - 20, 12, 12, shipColor);
		score = new ScoreCounter(Game.getWidth(false) - 100, Game.getHeight(false) - 20);
		shipLives.setLives(3);
	
		debugState = false;
		
		respawnTimer = 0;
		
		respawn();
	}

	public static boolean getDebugState() {
		return debugState;
	}
	
	public void tick() {
		// TODO: Add a powerup for speed.
		
		if (planet != null)
			planet.tick();
		
		if (shipHealth.getHealth() > 0 && ship != null) {
			ship.tick();
		
			if (Input.getPressedDown("Fire") && ship.canFire()) {
				missiles.add(new Missile(ship.getOriginX(), ship.getOriginY(), 16, 16, 2, ship.getDirection()));
				ship.setFireTime(0);
			}
			
			updateMissiles();
			updateAsteroids();	
		}
		else {
			respawnTimer += RESPAWN_SPEED;
			if (respawnTimer >= 1) {
				respawnTimer = 0;
				if (shipLives.getLives() > 0) {
					respawn();
				}
				else {
					System.out.println("Game Over!");
					System.exit(0);
				}
			}
		}
		
		if(Input.getPressedDown("Select"))
			debugState = !debugState;
		
		shipHealth.tick();
		updateExplosions();
	}
	
	private void updateAsteroids() {
		double 
			maxSpawnSpeed = SPAWN_SPEED * ((double)planet.getWidth() / 10) + 0.1,
			minSpawnSpeed = SPAWN_SPEED * ((double)planet.getWidth() / 10) - 0.1;
				
		spawnTimer += Math.random() * (maxSpawnSpeed - minSpawnSpeed + 1) + minSpawnSpeed;
		
		for(int a = 0; a < asteroids.size(); a++) {
			Asteroid asteroid = asteroids.get(a);
			asteroid.tick();
			
			if (asteroid.getCollisionMask().isColliding(planet.getCollisionMask())) {
				
				if (planet.getWidth() < 200)
					planet.accrete(asteroid.getWidth() / 2);
				
				destroyAsteroid(a);
			}
			
			if (ship != null) {
				if (asteroid.getCollisionMask().isColliding(ship.getCollisionMask())) {
					int factor = asteroid.getWidth();
					
					shipHealth.addHealth(-factor);
					
					destroyAsteroid(a);
					
					if (shipHealth.getHealth() <= 0)
						destroyShip();
				}
			}
			
			for(int m = 0; m < missiles.size(); m++) {
				if (missiles.get(m) != null) {
					Missile missile = missiles.get(m);
					
					if (asteroid.getCollisionMask().isColliding(missile.getCollisionMask())) {
						missiles.remove(m);
						destroyAsteroid(a);
						score.setScore(score.getScore() + (asteroid.getWidth() * 10));
					}
				}
			}
		}
		
		if (spawnTimer >= 100 && DO_SPAWN) {
			// Setting up spawning of asteroids on a point on a circle.
			double
				theta = Math.random() * Math.PI * 2,
				spawnX = Math.cos(theta) * SPAWN_DISTANCE + (Game.getWidth(false) / 2),
				spawnY = Math.sin(theta) * SPAWN_DISTANCE + (Game.getHeight(false) / 2);
				
			// Random scale and speed for asteroids to add some variety
			int scale = (int)(Math.random() * (MAX_RANDOM_SIZE - MIN_RANDOM_SIZE + 1)) + MIN_RANDOM_SIZE;
			double speed = Math.random() + 0.4;
			
			// Create a random point outside of the window and spawn a new asteroid there.
			asteroids.add(new Asteroid(spawnX, spawnY, scale, scale, speed, planet));
			
			spawnTimer = 0;
		}
	}
	
	private void destroyAsteroid(int a) {
		Asteroid asteroid = asteroids.get(a);
		explosions.add(new Explosion(asteroid.getOriginX(), asteroid.getOriginY(), asteroid.getWidth() + 10, asteroid.getHeight() + 10, 0.1, false));

		asteroids.remove(a);
	}
	
	private void updateExplosions() {
		for (int e = 0; e < explosions.size(); e++) {
			Explosion exp = explosions.get(e);
			
			if (exp.destroy())
				explosions.remove(e);
		}
	}
	
	private void updateMissiles() {
		for (int m = 0; m < missiles.size(); m++) {
			Missile missile = missiles.get(m);
			missile.tick();
			
			if (missile.getX() <= -Game.getWidth(false)|| missile.getX() >= Game.getWidth(false)||
				missile.getY() <= -Game.getHeight(false) || missile.getY() >= Game.getHeight(false)) {
				missiles.remove(m);
			}
		}
	}
	
	private void destroyShip() {
		explosions.add(new Explosion(ship.getOriginX(), ship.getOriginY(), ship.getWidth() + 10, ship.getHeight() + 10, 0.1, true));
		
		ship = null;
	}
	
	private void respawn() {
		ship = new PlayerShip(planet.getOriginX(), planet.getOriginY(), 24, 24, planet, shipColor);
		shipHealth.setHealth(100);
		shipLives.addLives(-1);
		
		asteroids = new ArrayList<Asteroid>();
		explosions = new ArrayList<Explosion>();
		missiles = new ArrayList<Missile>();
	}
	
	public void draw(Graphics2D g) {
		g.clearRect(0, 0, Game.getWidth(true), Game.getHeight(true));
		
		bg.draw(g);
		
		for(int a = 0; a < asteroids.size(); a++) {
			Asteroid asteroid = asteroids.get(a);
			asteroid.draw(g);
		}	

		for(int m = 0; m < missiles.size(); m++) {
			Missile missile = missiles.get(m);
			missile.draw(g);
		}
		
		planet.draw(g);
		if (shipHealth.getHealth() > 0 && ship != null)
			ship.draw(g);
		
		
		for(int x = 0; x < explosions.size(); x++) {
			Explosion exp = explosions.get(x);
			exp.draw(g);
		}	
		
		String debugText = debugState ? "OFF" : "ON";
		
		g.setColor(Color.WHITE);
		g.setFont(new Font("Console", Font.PLAIN, 12));

		g.drawString("Press \"Enter\" or \"E\" to turn debug mode " + debugText + ".", 10, 20);
		
		shipHealth.draw(g);
		shipLives.draw(g);
		score.draw(g);
	}
}
