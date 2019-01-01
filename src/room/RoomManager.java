package room;

import java.awt.Graphics2D;

import main.Game;

/**
 * The {@code RoomManager} class handles
 * room initialization, updating, and drawing.
 * 
 * This class is only ever meant to be accessed
 * statically, so there is no constructor.
 * @see Room
 *
 */
public class RoomManager {

	private static Room room;
	private static boolean clearScreen = false;
	
	public static void setRoom(Room r) {
		room = r;
		clearScreen = true;
	}
	
	public static void tick() {
		room.tick();
	}
	
	public static void draw(Graphics2D g) {
		if(clearScreen) {
			g.clearRect(0, 0, Game.getWidth(true), Game.getHeight(true));
			clearScreen = false;
		}
		room.draw(g);
	}
}
