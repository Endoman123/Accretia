package gui;

import java.awt.Color;
import java.awt.Graphics2D;

import room.GameRoom;

/**
 * The {@code HealthBar} class is a GUI element in Accretia that contains data
 * to calculate and display health on the screen.
 *
 */
public class HealthBar {
	
	private double health, rectX, rectY, width, height;
	private static final Color
		DEFAULT = Color.GREEN,
		HALF = Color.YELLOW,
		CRITIICAL = Color.RED;
	
	private boolean drawBack;
	
	/**
	 * <pre>
	 * public HealthBar(int x, 
	 *                  int y, 
	 *                  int w, 
	 *                  int h, 
	 *                  boolean backed)
	 * </pre>
	 * 
	 * Constructs a new {@code HealthBar} and initializes the health to 100.
	 * It also initializes the GUI bar at the specified x and y coordinate with the specified width and height.
	 * It also takes in whether or not the GUI bar should be backed with another rectangle.
	 * 
	 * @param x - The x-coordinate of the GUI bar.
	 * @param y - The y-coordinate of the GUI bar.
	 * @param w - The width of the GUI bar.
	 * @param h - The height of the GUI bar.
	 * @param backed - Whether or not the GUI bar should be drawn
	 *                 backed with another rectangle.
	 */
	public HealthBar(double x, double y, double w, double h, boolean backed) {
		rectX = x;
		rectY = y;
		width = w;
		height = h;
		
		drawBack = backed;
		
		health = 100;
	}
	
	public int getHealth() {
		return (int)health;
	}
	
	public void setHealth(double val) {
		health = val;
	}
	
	public void addHealth(double val) {
		health += val;
	}
	
	public void tick() {
		if (health < 0)
			health = 0;
		
		if (health > 100)
			health = 100;
	}
	
	public void draw(Graphics2D g) {
		if (drawBack) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect((int)(rectX - 2), (int)(rectY - 2), (int)(width + 4), (int)(height + 4));
		}
		
		// Don't bother drawing empty health.
		if (health > 0) {
			int healthBarWidth = (int)(width * (health / 100));
			
			if (health <= 20)
				g.setColor(CRITIICAL);
			else if (health <= 50)
				g.setColor(HALF);
			else
				g.setColor(DEFAULT);

			g.fillRect((int)(rectX), (int)(rectY), healthBarWidth, (int)height);
		}
		
		if (GameRoom.getDebugState()) {
			g.setColor(Color.WHITE);
			g.drawString(Integer.toString((int)health), (int)(rectX + width + 5), (int)(rectY + height / 2 + 5));
		}
	}
}
