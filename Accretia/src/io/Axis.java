package io;

/**
 * The {@code Axis} class contains methods to return bind inputs
 * between -1 and 1. This is a copy of the Unity axis input
 * system.
 *
 * @see Input
 * @see Bind
 */
public class Axis {
	
	private String name;
	private Bind positive, negative;
	private double 
		delta,
		sensitivity,
		returnSensitivity,
		deadZone;
	
	/**
	 * <pre>
	 * public Axis(String n, 
	 *             Bind pos, 
	 *             Bind neg, 
	 *             double s, 
	 *             double rs, 
	 *             double dz) 
	 * </pre>
	 * 
	 * Constructs a new {@code Axis} and initializes it with a specified name, positive key, and negative key.
	 * The sensitivity, return sensitivity, and dead zone are also initialized with it.
	 * 
	 * @param n   - The name of the axis. 
	 * @param pos - The {@code Bind} that represents (1) in the axis.
	 *              This should not be the same value as {@code neg}.
	 * @param neg - The {@code Bind} that represents (-1) in the axis.
	 *              This should not be the same value as {@code pos}}
	 * @param s   - The speed that the delta of the axis moves from 0 to 1 or -1.
	 * @param rs  - The speed that the delta returns to 0.
	 * @param dz  - The threshold delta must be from 1, 0, or -1
	 *              that will be considered 1, 0, or -1.
	 */
	public Axis(String n, Bind pos, Bind neg, double s, double rs, double dz) {
		name = n;
		positive = pos;
		negative = neg;
		sensitivity = Math.abs(s);
		returnSensitivity = Math.abs(rs);
		deadZone = Math.abs(dz);
		
		delta = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public double getAxis() {
		if (positive.getPressed() && !negative.getPressed()) {
			if (delta < deadZone)
				delta += sensitivity;
			else
				delta = 1;
		}
		else if (!positive.getPressed() && negative.getPressed()) {
			if (delta > -deadZone)
				delta -= sensitivity;
			else
				delta = -1;
		}
		else {
			if (Math.abs(delta) > deadZone)
				delta -= Math.signum(delta) * returnSensitivity;
			else
				delta = 0;
		}
			
		return delta;
	}
	
	public int getAxisRaw() {
		if (positive.getPressed() && !negative.getPressed())
			return 1;
		else if (!positive.getPressed() && negative.getPressed())
			return -1;
		else
			return 0;
	}

}
