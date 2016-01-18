package map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

/**
 * The {@code Background} class contains methods to manipulate a {@code BufferedImage}
 * as a background.
 *
 */
public class Background {

	private BufferedImage img;
	private int moveScale;
	
	private int x, y, vX, vY;
	
	/**
	 * <pre>
	 * public Background(String path, 
	 *                   int ms)
	 * </pre>
	 * 
	 * Creates a new {@code Background}, using the file at the {@code path} and the move scale.
	 * 
	 * @param path - The location of the background image on the hard disk.
	 * @param ms   - The move scale of the background.
	 */
	public Background(String path, int ms) {
		try {
			img = ImageIO.read(getClass().getResourceAsStream(path));
			moveScale = ms;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setPosition (int x, int y) {
		this.x = (x * moveScale) % Game.getWidth(true);
		this.y = (y * moveScale) % Game.getHeight(true);
	}
	
	public void setVector(int x, int y) {
		vX = x;
		vY = y;
	}
	
	public void tick() {
		x += vX;
		y += vY;
	}
	
	public void draw(Graphics2D g) {
		int drawX, drawY = y;
		if (x > 0) {
			drawX = x - Game.getWidth(true);
		}
		else if (x < 0) {
			drawX = x + Game.getWidth(true);
		}
		else 
			drawX = x;
	
		g.drawImage(img, drawX, drawY, null);
	}
}
