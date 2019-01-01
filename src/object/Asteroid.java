package object;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import physics.CollisionCircle;
import room.GameRoom;
import sprite.Animator;
import sprite.Sprite;

public class Asteroid extends GameObject {
	
	private Animator animator;
	private CollisionCircle mask;
	private int planetX, planetY;
	private double velocity;
	private int lifeTime = 100;
	
	public Asteroid(double startX, double startY, int w, int h, double v, Planet p) {
		super(startX, startY, w, h);
		
		mask = new CollisionCircle(getOriginX(), getOriginY(), getWidth() / 2);
		
		planetX = p.getOriginX();
		planetY = p.getOriginY();
		
		velocity = v;
		
		animator = new Animator(0.075);
		
		animator.addSprite(new Sprite("default", "/Sprites/Asteroid/asteroid_large_strip4.png/", 32, 32, 0, 4));
		
		moveTowards(planetX, planetY, velocity);
	}

	public Animator getAnimator() {
		return animator;
	}
	
	public CollisionCircle getCollisionMask() {
		return mask;
	}
	
	public void tick() {
		super.tick();
		
		mask.setLocation(getOriginX(), getOriginY());
		animator.playAnimation(Animator.LOOP);
		lifeTime -= 1;
	}
	
	public int getLife() {
		return lifeTime;
	}
	
	@Override
	public void draw(Graphics2D g) {
		BufferedImage img = animator.getFrame();
		g.drawImage(img, getX(), getY(), getWidth(), getHeight(), null);
		
		if (GameRoom.getDebugState())
			mask.draw(g);
	}
}
