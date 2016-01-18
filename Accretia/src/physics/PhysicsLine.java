package physics;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * The {@code PhysicsLine} class stores a line in an (x, y) coordinate
 * to use for collision calculations.
 *
 * @see CollisionCircle
 * @see CollisionPolygon
 * @see PhysicsPoint
 */
public class PhysicsLine extends Line2D {

	private double x1, y1, x2, y2;
	private Point2D p1, p2;
	
	/**
	 * <pre>
	 * public PhysicsLine(double x1, 
	 *                    double y1, 
	 *                    double x2, 
	 *                    double y2) 
	 * </pre>
	 * 
	 * Constructs a new {@code PhysicsLine} from (x1, y1) to (x2, y2).
	 * 
	 * @param x1 - The x-coordinate for point 1.
	 * @param y1 - The y-coordinate for point 1.
	 * @param x2 - The x-coordinate for point 2.
	 * @param y2 - The y-coordinate for point 2.
	 */
	public PhysicsLine(double x1, double y1, double x2, double y2) {
		super();
		setLine(x1, y1, x2, y2);
	}


	// Probably will never use this
	public Rectangle2D getBounds2D() {
		return null;
	}

	public Point2D getP1() {
		return p1;
	}

	public Point2D getP2() {
		return p2;
	}

	public double getX1() {
		return x1;
	}

	public double getX2() {
		return x2;
	}

	public double getY1() {
		return y1;
	}

	public double getY2() {
		return y2;
	}

	public void setLine(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		
		p1 = new PhysicsPoint(this.x1, this.y1);
		p2 = new PhysicsPoint(this.x2, this.y2);
	}

	/**
	 * Calculates the slope of the line.
	 * 
	 * @return The slope in a double array in the order (y, x).
	 */
	public double[] getSlope() {
		double[] slope = new double[2];
		double
			deltaX = x2 - x1,
			deltaY = y2 - y1;

		slope[0] = deltaY;
		slope[1] = deltaX;
		
		return slope;	
	}
}
