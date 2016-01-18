package object;

import java.awt.Graphics2D;

import sprite.Animator;
import sprite.Sprite;

public class Explosion extends GameObject {

	private Animator animator;
	
	public Explosion(double startX, double startY, double w, double h, double speed, boolean isShip) {
		super(startX, startY, w, h);
		
		try {
			animator = new Animator(speed);
			
			if (!isShip)
				animator.addSprite(new Sprite("default", "/Sprites/FX/explosion_strip4.png/", 64, 64, 0, 4));
			else
				animator.addSprite(new Sprite("default", "/Sprites/FX/explosion_big_strip8.png/", 64, 64, 0, 8));
				
		} catch (Exception e) { } // Assume the path is screwed up.
	}

	public boolean destroy() {
		animator.playAnimation(Animator.ONE_SHOT);
		return animator.getPlayed();
	}
	
	@Override
	public void draw(Graphics2D g) {
		super.tick();
		g.drawImage(animator.getFrame(), getX(), getY(), getWidth(), getHeight(), null);

	}

}
