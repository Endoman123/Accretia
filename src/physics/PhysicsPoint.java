package physics;

import java.awt.geom.Point2D;

/**
 * The {@code PhysicsPoint} class stores a point in an (x, y) coordinate
 * to use for collision calculations.
 * 
 * @see CollisionCircle
 * @see CollisionPolygon
 * @see PhysicsLine
 */
public class PhysicsPoint extends Point2D{

	private double x, y;
	
	/**
	 * <pre>
	 * public PhysicsPoint(double x,
	 *                     double y) 
	 * </pre>
	 * 
	 * Constructs a new {@code PhysicsPoint} and initializes it to the specified x and y.
	 * 
	 * @param x - The starting x-coordinate.
	 * @param y - The starting y-coordinate.
	 */
	public PhysicsPoint(double x, double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}

	@Override
	public void setLocation(double x, double y) {
		this.x = x;
		this.y = y;		
	}

}
