package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import physics.CollisionCircle;
import room.GameRoom;
import sprite.Sprite;

public class Planet extends GameObject {

	private final int RANDOM_FRAME = (int)(Math.random() * 4);
	private Sprite sprite;
	private CollisionCircle mask;
	
	public Planet(double startX, double startY, int w, int h) {
		super(startX, startY, w, h);
		
		mask = new CollisionCircle(getOriginX(), getOriginY(), getWidth() / 2);
				
		sprite = new Sprite("default", "/Sprites/Planet/planets_strip4.png/", 72, 72, 0, 4);
	}

	public void accrete(int r) {
		double 
			lastX = getOriginX(), 
			lastY = getOriginY();
		
		setDimensions(getWidth() + r, getHeight() + r);
		setLocation(lastX, lastY);
	}
	
	public CollisionCircle getCollisionMask() {
		return mask;
	}
	
	public void tick() {
		super.tick();
		mask.setRadius(getWidth() / 2);
	}
	
	@Override
	public void draw(Graphics2D g) {
		BufferedImage spr = sprite.getFrame(RANDOM_FRAME);
		g.drawImage(spr, getX(), getY(), getWidth(), getHeight(), null);
		
		if (GameRoom.getDebugState())
			mask.draw(g);
	}

}
