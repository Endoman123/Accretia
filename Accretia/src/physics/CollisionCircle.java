package physics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * The {@code CollisionCircle} class calculates collisions using a circle mask
 * as the check area. This class contains methods to calculate collisions with 
 * other {@code CollisionCircles} and {@code CollisionRegularPolygons}.
 * <p>
 * This class uses an origin and a radius to calculate collisions within a
 * circle. This class is also the only "Collision" class to handle 
 * collision calculations.
 *
 * @see CollisionPolygon 
 * @see PhysicsLine
 * @see PhysicsPoint
 */

public class CollisionCircle {

	private double originX, originY, radius;
	private PhysicsPoint origin;
	
	/**
	 * <pre>
	 * public CollisionCicle(double x, 
	 *                       double y, 
	 *                       double r)
	 * </pre>
	 * 
	 * Constructs a new {@code CollisionCircle} at the coordinate (x, y) a set radius.
	 * 
	 * @param originX - The x-coordinate to start at.
	 * @param originY - The y-coordinate to start at.
	 * @param r - The distance each point is from the center.
	 */
	public CollisionCircle(double x, double y, double r) {
		originX = x;
		originY = y;
		
		origin = new PhysicsPoint(originX, originY);
		radius = r;
	}
	
	public void setLocation(int x, int y) {
		originX = x;
		originY = y;
		
		origin.setLocation(originX, originY);
	}
	
	public void setRadius(int r) {
		radius = r;
	}
	
	public PhysicsPoint getOrigin() {
		return origin;
	}
	
	public int getOriginX() {
		return (int)originX;
	}
	
	public int getOriginY() {
		return (int)originY;
	}
	
	public int getRadius() {
		return (int)radius;
	}
	
	public boolean isColliding(CollisionCircle c) {
		double
			dX = c.getOriginX() - originX,
			dY = c.getOriginY() - originY;
			
		int 
			radiusSquared = (int) Math.pow(radius + c.getRadius(), 2),
			distanceSquared = (int) (Math.pow(dX, 2) + Math.pow(dY ,2));
		
		return distanceSquared < radiusSquared;
	}
	
	public boolean isColliding(CollisionPolygon c) {
		// If the polygon already contains the origin.
		if (c.contains(origin))
			return true;
		
		// Check if points collide
		for (int p = 0; p < c.npoints; p++) {
			double distance = Point.distance(getOriginX(), getOriginY(), c.xpoints[p], c.ypoints[p]);

			if (distance < getRadius())
				return true;	
		}
		
		// Check if sides collide
		for(int s = 0; s < c.npoints; s++) {
			PhysicsLine side = c.getSide(s);
			
			double segDist = side.ptSegDist(origin);
			
			if (segDist <= radius)
				return true;
		}
		
		return false;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.RED);
		g.drawOval((int)(originX - radius), (int)(originY - radius), (int)(radius * 2), (int)(radius * 2));
	}
}
