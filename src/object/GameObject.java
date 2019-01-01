package object;

import java.awt.Graphics2D;

/**
 * 
 * The {@code GameObject} abstract class contains the definition for all objects that can be manipulated
 * by the game. Each has fields defining their transforms, along with methods to perform some
 * transformations. This is useful to handle almost every game object in one class, and 
 * not have to take up thousands of lines every time we make a new class.
 *
 */
public abstract class GameObject {
	
	private double 
		x, y, 
		width, height,
		originX, originY, 
		vX, vY,
		direction;

	/**
	 * <pre>
	 * public GameObject(double startX, 
	 *                   double startY, 
	 *                   double w, 
	 *                   double h)
	 * </pre>
	 * 
	 * Constructs a new {@code GameObject} and initializes it at the specified x and y
	 * with the specified width and height.
	 * 
	 * @param startX - The starting x-origin of the object.
	 * @param startY - The starting y-origin of the object.
	 * @param w 	 - The starting width of the object.
	 * @param h 	 - The starting height of the object.
	 */
	public GameObject(double startX, double startY, double w, double h) {
		originX = startX;
		originY = startY;
		
		width = w;
		height = h;
		
		vX = 0;
		vY = 0;
	}
	
	/**
	 * {@code public void tick()}
	 * <p>
	 * The update method for the {@code GameObject}.
	 */
	public void tick() {
		x = originX - (width / 2);
		y = originY - (height / 2);
		
		originX += vX;
		originY += vY;
		
		direction %= 360;
		if (direction < 0)
			direction = 360 + direction;
	}
	
	/**
	 * {@code public void moveTowards(int tX, int tY, double speed)}
	 * <p>
	 * Moves the {@code GameObject} towards a location.
	 * 
	 * @param tX    - The target x-coordinate.
	 * @param tY    - The target y-coordinate.
	 * @param speed - The speed to move towards the coordinate at
	 * 				  (Use negative speed to move away from coordinate).
	 */
	public void moveTowards(int tX, int tY, double speed) {
		double 
			deltaX = tX - getOriginX(),
			deltaY = tY - getOriginY(),
			direction;
		
		direction = Math.atan2(deltaY, deltaX);
		
		deltaX = Math.cos(direction);
		deltaY = Math.sin(direction);
		
		setVector(deltaX, deltaY, speed);
	}
	
	public int getOriginX() {
		return (int)originX;
	}
	
	public int getOriginY() {
		return (int)originY;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
	public int getWidth() {
		return (int)width;
	}
	
	public int getHeight() {
		return (int)height;
	}
	
	public int getDirection() {
		return (int) direction;
	}
	
	public void setDimensions(int w, int h) {
		width = w;
		height = h;
	}
	
	public void setVector(double x, double y, double v) {
		vX = x * v;
		vY = y * v;
	}
	
	public void setLocation(double x, double y) {
		originX = x;
		originY = y;
	}
	
	public void setDirection(double theta) {
		direction = theta;
	}
	
	public void rotate(double theta) {
		direction += theta;
	}
	
	public abstract void draw(Graphics2D g);
}
