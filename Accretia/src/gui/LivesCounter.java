package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import sprite.Sprite;

/**
 * The {@code LivesCounter} class is a GUI element in Accretia that contains 
 * data to count and display lives on the screen.
 * 
 * @author Jared
 *
 */
public class LivesCounter {
	
	private static final int MARGIN = 5;
	
	private int lives, shipColor;
	private Sprite lifeSprite;
	
	private double x, y, width, height;

	public LivesCounter(double x, double y, double w, double h, int color) {
		this.x = x;
		this.y = y;
		width = w;
		height = h;
		
		lives = 3;
		lifeSprite = new Sprite("default", "/Sprites/PlayerShip/lives_strip2.png/", 24, 24, 0, 2);
		shipColor = color;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void setLives(int val) {
		lives = val;
	}
	
	public void addLives(int val) {
		lives += val;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics2D g) {
		BufferedImage img = lifeSprite.getFrame(shipColor);
		if (lives > 5) {
			g.setFont(new Font("Console", Font.PLAIN, 12));
			g.setColor(Color.WHITE);
			g.drawImage(img, (int)x, (int)y, (int)width, (int)height, null);
			g.drawString("- " + lives, (int)(x + width + MARGIN), (int)(y + 10));
		} else {
			for (int l = 0; l < lives; l++) {
				int xOffset = (int)((l * (width + MARGIN)));
				g.drawImage(img, (int)(x + xOffset),(int) y, (int)width, (int)height, null);
			}
		}
	}

}
