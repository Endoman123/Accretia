package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import physics.*;
import room.GameRoom;
import sprite.Sprite;

public class Missile extends GameObject {

	private Sprite sprite;
	private CollisionPolygon mask;
	private double radDir;
	
	public Missile(double startX, double startY, double w, double h, double speed, int direction) {
		super(startX, startY, w, h);
		double deltaX, deltaY;
		
		setDirection(direction);
		radDir = Math.toRadians(getDirection());
		
		initMask();
		sprite = new Sprite("default", "/Sprites/Missle/missle1_strip360.png/", 78, 78, 0, 360);

		deltaX = Math.cos(radDir);
		deltaY = Math.sin(radDir);
		
		setVector(deltaX, deltaY, speed);
	}
	
	public CollisionPolygon getCollisionMask() {
		return mask;
	}

	public void tick() {
		updateMask();
		super.tick();
	}
	private void initMask() {
		double
			width = getWidth() / 8,
			height = getHeight() / 2;
		
		int[] 
			tempX = new int[4], 
			tempY = new int[4],
			xpoints = new int[4],
			ypoints = new int[4];

		tempX[0] = tempX[3] = (int)(-height);
		tempX[1] = tempX[2] = (int)(height);
		
		tempY[0] = tempY[1] = (int)(-width);
		tempY[2] = tempY[3] = (int)(width);
		
		for (int p = 0; p < 4; p++) {
			xpoints[p] = (int)(tempX[p] * Math.cos(radDir) - tempY[p] * Math.sin(radDir));
			ypoints[p] = (int)(tempX[p] * Math.sin(radDir) + tempY[p] * Math.cos(radDir));
			
			xpoints[p] += getOriginX();
			ypoints[p] += getOriginY();
		}
		
		mask = new CollisionPolygon(xpoints, ypoints, 4);
	}
	
	private void updateMask() {
		double 
			originX = (mask.xpoints[0] + mask.xpoints[1]) / 2,
			originY = (mask.ypoints[0] + mask.ypoints[2]) / 2,
			deltaX = getOriginX() - originX,
			deltaY = getOriginY() - originY;
		
		mask.tick();
		
		for (int p = 0; p < 4; p++) {
			mask.xpoints[p] += deltaX;
			mask.ypoints[p] += deltaY;
		}
	}
	
	public void draw(Graphics2D g) {
		int frame = (int)getDirection() + 90;
		frame %= 360;
		
		BufferedImage spr = sprite.getFrame(frame);
		g.drawImage(spr, getX(), getY(), getWidth(), getHeight(), null);
		
		if (GameRoom.getDebugState())
			mask.draw(g);
	}
}
