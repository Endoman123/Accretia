package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import io.Input;
import room.MenuRoom;
import room.RoomManager;

@SuppressWarnings("serial")
public class Game extends JPanel implements Runnable{

	// Dimensions
	private final int DEF_WIDTH = 400, DEF_HEIGHT = 400, SCALE = 2;
	private static int width, height, scale;
	
	// Game Thread
	private Thread thread;
	private final int DEF_FPS = 60;
	private int fps;
	private long targetTime;
	private boolean running;
	
	private BufferedImage bimg;
	private Graphics2D g2d;
	
	public Game() {
		super();
		
		width = DEF_WIDTH;
		height = DEF_HEIGHT;
		scale = SCALE;
		fps = DEF_FPS;
		
		setPreferredSize(new Dimension(width * scale, height * scale));
		setFocusable(true);
		requestFocus();
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.setName("Game-Accretia");
			addKeyListener(new Input());
			thread.start();
		}
	}
	
	public static int getWidth(boolean scaled) {
		if (scaled)
			return width * scale;
		else
			return width;
	}
	
	public static int getHeight(boolean scaled) {
		if (scaled)
			return height * scale;
		else
			return height;
	}
	
	public static int getScale() {
		return scale;
	}
	
	public static void setWidth(int w) {
		width = w;
	}
	
	public static void setHeight(int h) {
		height = h;
	}
	
	public static void setScale(int s) {
		scale = s;
	}
	
	public void init() {
		bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		g2d = (Graphics2D)bimg.getGraphics();
		RoomManager.setRoom(new MenuRoom());
		running = true;
	}
	
	public void run() {
		init();
		targetTime = 1000 / fps;
		long start, elapsed, sleep;
		while(running) {
			start = System.nanoTime();
			
			tick();
			draw();
			drawToScreen();
			
			elapsed = System.nanoTime() - start;
			sleep = targetTime - elapsed / 1000000;
			if (sleep > 0)
				try {
					Thread.sleep(sleep);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	private void tick() {
		RoomManager.tick();
		if (Input.getPressed("Quit"))
			System.exit(0);
	}
	
	private void draw() {
		RoomManager.draw(g2d);
	}
	
	private void drawToScreen() {
		Graphics g = getGraphics();
		g.drawImage(bimg, 0, 0, width * scale, height * scale, null);
		g.dispose();
	}
	
	public static void main(String[] args) {
		JFrame gF = new JFrame("Accretia");
		Game gP = new Game();
		
		gF.setContentPane(gP);
		gF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gF.setResizable(false);
		gF.pack();

		gF.setLocationRelativeTo(null);
		gF.setVisible(true);
	}
}
