package sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * The {@code Animator} class contains methods for handling sprite states and animating them.
 * If a sprite has an animation, it is best to create an {@code Animator} to handle the animations,
 * rather than re-code the animations into whatever you plan to implement the sprite(s) to.
 * 
 * @see Sprite
 *
 */
public class Animator {

	public static final int
		LOOP = 0,	
		ONE_SHOT = 1;
	
	private ArrayList<Sprite> states;
	private int currentFrame, currentState;
	private double animSpeed, animTimer;
	private boolean played;
	
	/**
	 * <pre>
	 * public Animator(double as)
	 * </pre>
	 * 
	 * Constructs a new {@code Animator} with a set animation speed.
	 * 
	 * @param as - The animation speed to use when animating states.
	 */
	public Animator(double as) {
		states = new ArrayList<Sprite>();
		animSpeed = as;
		animTimer = 0;
		currentFrame = 0;
		currentState = 0;
		played = false;
	}
	
	public boolean getPlayed() {
		return played;
	}
	
	public BufferedImage getFrame() {
		return states.get(currentState).getFrame(currentFrame);
	}

	public void setAnimationSpeed(double speed) {
		animSpeed = speed;
	}

	public void setState(String name) {
		currentState = findStateIndex(name);
		played = false;
	}
	
	public void setCurrentFrame(int f) {
		currentFrame = f;
	}
	
	public void addSprite(Sprite s) {
		states.add(s);
	}
	
	public void tick() {

	}
	
	public void playAnimation(int playMethod) {
		Sprite curSprite = states.get(currentState);
		animTimer += animSpeed;
		if (animTimer >= 1) {
			animTimer = 0;
			if (currentFrame < curSprite.getTotalFrames() - 1)
				currentFrame++;	
			else {
				if (playMethod == LOOP)
					currentFrame = 0;
				played = true;
			}
		}
	}

	private int findStateIndex(String name) {
		for(int s = 0; s < states.size(); s++) {
			Sprite curSprite = states.get(s);
			if (curSprite.getName() == name)
				return s;
		}
		return 0;
	}
}
