package io;

/**
 * The {@code Bind} class represents the definition for bindings to use with the Input class.
 * It contains the binding's name, and the default and alternate key to activate it.
 * <p>
 * This class contains various methods to replace the KeyListener events in Java.
 * @see Input
 */
public class Bind {

	private String name;
	private int defaultKey, altKey;
	private boolean isPressed, wasPressed;
	
	/**
	 * <pre>
	 * public Bind(String n,
	 *             int k1,
	 *             int k2)
	 * </pre>
	 * <p>
	 * Creates a new key binding and initializes it to have 
	 * the specified binding name and default and alternative key. 
	 * Note that you should not set k1 and k2 to the same key.
	 * 
	 * @param n - The name to give the new binding.
	 * @param k1 - The default key to activate the binding.
	 * @param k2 - The alternate key to activate the binding.
	 * 
	 */
	public Bind(String n, int k1, int k2) {
		name = n;
		defaultKey = k1;
		altKey = k2;
	}
	
	public String getName() {
		return name;
	}
	
	public int getKey() {
		return defaultKey;
	}
	
	public int getAltKey() {
		return altKey;
	}

	public boolean getPressed() {
		return isPressed;
	}
	
	public boolean getPressedDown() {
		if (isPressed && !wasPressed) {
			wasPressed = isPressed;
			return true;
		}
		else if (isPressed && wasPressed) {
			return false;
		}
		else {
			wasPressed = false;
			return false;
		}
	}
	
	public void setKey(int k) {
		if (altKey != k)
			defaultKey = k;
	}
	
	public void setAltKey(int k) {
		if (defaultKey != k)
			altKey = k;
	}

	public void setPressed(boolean p) {
		isPressed = p;
	}
}
