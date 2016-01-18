package sprite;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The {@code Sprite} class contains various methods and fields to create
 * sprites from BufferedImage inputs.
 * <p>
 * For the sake of simplicity, the {@code Sprites} are handled on a per-state, per-strip basis.
 * If a whole sprite sheet is to be used, each "state" or "strip" in the sheet needs to be 
 * separated into individual image files.
 */
public class Sprite {

	private BufferedImage[] frames;
	private String name;
	private int width, height, totalFrames, xOffset;
	
	/**
	 * <pre>
	 * public Sprite(String n, 
	 *               String path, 
	 *               int w, 
	 *               int h, 
	 *               int xOff, 
	 *               int f) 
	 * </pre>
	 * 
	 * Constructs a new {@code Sprite} object with the string of the image path,
	 * width and height of the individual frames, the x-offset between frames,
	 * and the total amount of frames. 
	 * 
	 * @param n    - The name of this sprite.
	 * @param path - The location of the image on the hard disk.
	 * @param w    - The width of the individual frames.
	 * @param h    - The height of the individual frames.
	 * @param xOff - The x-offset between frames.
	 * @param f    - The total amount of frames in this sprite.
	 * 			     Numbers less than or equal to 1 are treated as a one-frame sprite.
	 *
	 */
	public Sprite(String n, String path, int w, int h, int xOff, int f) {
		try {
			BufferedImage strip = ImageIO.read(getClass().getResourceAsStream(path));
			name = n;
			width = w;
			height = h;
			xOffset = xOff;
			totalFrames = (f > 1) ? f : 1;
			initFrames(strip);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initFrames(BufferedImage strip) {
		int x;
		frames = new BufferedImage[totalFrames];
		for(int f = 0; f < totalFrames; f++) {
			x = f * width + xOffset;
			frames[f] = strip.getSubimage(x, 0, width, height);
		}
	}

	public BufferedImage getFrame(int f) {
		return frames[f];
	}
	
	public BufferedImage[] getFrames() {
		return frames;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getTotalFrames() {
		return totalFrames;
	}
	
	public String getName() {
		return name;
	}
}
