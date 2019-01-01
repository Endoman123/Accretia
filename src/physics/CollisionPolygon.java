package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 * The {@code CollisionRegularPolygon} class calculates collisions using a polygon mask
 * as the check area. This class contains methods to calculate collisions with 
 * other {@code CollisionPolygons} and {@code CollisionCircles}.
 * <p>
 * @see CollisionCircle
 * @see PhysicsLine
 * @see PhysicsPoint
 */

@SuppressWarnings("serial")
public class CollisionPolygon extends Polygon {
	
	private double direction;
	private PhysicsLine[] sides;
	/**
	 * <pre>
	 * public CollisionRegularPolygon(int[] xP, 
	 *                                int[] yP, 
	 *                                int n)
	 * </pre>
	 * Constructs a new {@code CollisionNormalPolygon} and initializes with the specified points
	 * and number of points.
	 * 
	 * @param xP - The array of x-coordinates.
	 * @param yP - The array of y-coordinates.
	 * @param n  - The amounts of valid points this polygon has. 
	 * 			   Note that this must be greater than 0.
	 */
	public CollisionPolygon(int[] xP, int[] yP, int n) {
		npoints = n;
		
		xpoints = xP;
		ypoints = yP;
		
		sides = new PhysicsLine[npoints];
	}
	
	public void setPoint(int p, int x, int y) {
		xpoints[p] = x;
		ypoints[p] = y;
	}
	
	public PhysicsPoint getPoint(int i) {
		return new PhysicsPoint(xpoints[i], ypoints[i]);
	}
	
	public PhysicsLine getSide(int i) {
		return sides[i];
	}
	
	public boolean isColliding(CollisionCircle c) {
		return false;
	}

	public void tick() {
		for (int s = 0; s < sides.length; s++) {
			int s2 = s + 1;
			
			if (s2 == sides.length)
				s2 = 0;
			
			sides[s] = new PhysicsLine(xpoints[s], ypoints[s], xpoints[s2], ypoints[s2]);
		}
		
		direction %= 360;
		if (direction < 0)
			direction = 360 - direction;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.draw(this);
	}
}
