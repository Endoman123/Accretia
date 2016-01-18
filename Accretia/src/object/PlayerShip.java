package object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import io.Input;
import physics.CollisionPolygon;
import room.GameRoom;
import sprite.Sprite;

public class PlayerShip extends GameObject{

	private static final int
		BLACK_SHIP = 1,
		WHITE_SHIP = 0;
	
	private Sprite sprite;
	private Planet planet;
	private int planetX, planetY;
	private double pathRadius, speed, fireRate = 0.07, fireTime;
	private CollisionPolygon mask;
	
	public PlayerShip(int startX, int startY, int w, int h, Planet p, int shipColor) {
		super(startX, startY, w, h);
		
		String ship = "";
		
		setDirection(270);
		
		planet = p;
		speed = 80;
		
		planetX = p.getOriginX();
		planetY = p.getOriginY();
		
		fireTime = 0;
		
		if (shipColor >= BLACK_SHIP)
			ship = "black";
		else if (shipColor <= WHITE_SHIP)
			ship = "white";
		
		sprite = new Sprite("default", "/Sprites/PlayerShip/" + ship +"_strip360.png/", 64, 64, 0, 360);
		mask = new CollisionPolygon(new int[3], new int[3], 3);
	}
	
	public CollisionPolygon getCollisionMask() {
		return mask;
	}
	
	public void tick() {
		super.tick();
		
		fireTime += fireRate;
		pathRadius = (planet.getWidth() / 2) + 16;

		// Don't need this to check
/*		if (Input.getPressedDown("Up"))
			speed += 10;
		if (Input.getPressedDown("Down"))
			speed -= 10;*/
		
		if (speed > 200)
			speed = 200;
		if (speed <= 0)
			speed = 10;
		
		double 
			rotSpeed = speed / pathRadius,
			rad = Math.toRadians(getDirection()),
			vX,
			vY;
		
		if (Input.getPressed("Right"))
			rotate(rotSpeed);
		
		if (Input.getPressed("Left"))
			rotate(-rotSpeed);

		vX = planetX + pathRadius * Math.cos(rad);
		vY = planetY + pathRadius * Math.sin(rad);
		
		setLocation(vX, vY);
		updateMask();
	}

	public boolean canFire() {
		return fireTime >= 1;
	}
	
	public void setFireTime(double t) {
		fireTime = t;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void setSpeed(double value) {
		speed = value;
	}
	
	private void updateMask() {
		double
			collisionRadius = getWidth() / 2,
			rotation,
			segment = 360 / 3;

		for(int p = 0; p < 3; p++) {
			rotation = Math.toRadians(segment * p + getDirection());
			
			mask.xpoints[p] = (int)(getOriginX() + collisionRadius * Math.cos(rotation));
			mask.ypoints[p] = (int)(getOriginY() + collisionRadius * Math.sin(rotation));
		}
		mask.tick();
	}

	@Override
	public void draw(Graphics2D g) {
		int frame = (int)getDirection() + 90;
		frame %= 360;
		
		BufferedImage spr = sprite.getFrame(frame);
		
		g.drawImage(spr, getX(), getY(), getWidth(), getHeight(), null);
		
		if (GameRoom.getDebugState()) {
			mask.draw(g);
			
			/*g.setColor(Color.WHITE);
			g.setFont(new Font("Console", Font.PLAIN, 12));
			
			g.drawString("Speed: " + speed, 10, 40);*/
		}
	}


}
