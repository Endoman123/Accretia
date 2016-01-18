package room;

import java.awt.Graphics2D;

/**
 * The {@code Room} abstract class contains
 * the bare bones each room uses.
 * 
 * @see RoomManager
 *
 */
public abstract class Room {

	/**
	 * <pre>
	 * public Room()
	 * </pre>
	 * <p>
	 * Constructs a new {@code Room}.
	 */
	public Room() {
		
	}

	public abstract void tick();
	public abstract void draw(Graphics2D g);

}
