package io;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * 
 * The {@code Input} class is a KeyListener that has multiple methods to return key events
 * throughout the program. It contains a list of binds and axes to read from, 
 * and has static methods to return various info from this list.
 * @see Bind
 * @see Axis
 */
public class Input implements KeyListener {

	private static ArrayList<Bind> binds;
	private static ArrayList<Axis> axes;
	
	/**
	 * <pre>
	 * public Input()
	 * </pre>
	 * 
	 * Constructs a new {@code Input} object. Note that this
	 * is purely to initialize the class and to give reference
	 * so that this can act as a {@code KeyListener}. There is nothing to
	 * pass into the constructor; everything is initialized in the
	 * code.
	 */
	public Input() {
		binds = new ArrayList<Bind>();
		axes = new ArrayList<Axis>();
		
		addBind(new Bind("Up", KeyEvent.VK_UP, KeyEvent.VK_W));
		addBind(new Bind("Down", KeyEvent.VK_DOWN, KeyEvent.VK_S));
		addBind(new Bind("Left", KeyEvent.VK_LEFT, KeyEvent.VK_A));
		addBind(new Bind("Right", KeyEvent.VK_RIGHT, KeyEvent.VK_D));
		addBind(new Bind("Select", KeyEvent.VK_ENTER, KeyEvent.VK_E));
		addBind(new Bind("Fire", KeyEvent.VK_SPACE, KeyEvent.VK_SHIFT));
		addBind(new Bind("Quit", KeyEvent.VK_ESCAPE, KeyEvent.VK_Q));
		
		addAxis(new Axis("Horizontal", binds.get(1), binds.get(0), 0.3, 0.3, 0.01));
	}
	
	public void keyPressed(KeyEvent k) {
		int key = k.getKeyCode();
		try {
			findBind(key).setPressed(true);
		} catch(Exception e) { } // Not gonna print stack trace because the key probably doesn't exist if this is not working.
	}

	@Override
	public void keyReleased(KeyEvent k) {
		int key = k.getKeyCode();
		
		try {
			findBind(key).setPressed(false);
		} catch(Exception e){ } // Not gonna print stack trace because the key probably doesn't exist if this is not working.
	}

	@Override
	public void keyTyped(KeyEvent k) {
		// No need for this as of right now; 
		// we'll ignore it for the time being.
	}

	public static void addBind(Bind b) {
		binds.add(b);
	}
	
	public static void addAxis(Axis a) {
		axes.add(a);
	}
	
	public static boolean getPressed(String n) {
		return findBind(n).getPressed();
	}
	
	public static boolean getPressedDown(String n) {
		return findBind(n).getPressedDown();
	}

	public static double getAxis(String name) {
		Axis axis = findAxis(name);
		
		return axis.getAxis();
	}
	
	private static Bind findBind(int k) {
		for (int i = 0; i < binds.size(); i++) {
			Bind curBind = binds.get(i);
			if (curBind.getKey() == k || curBind.getAltKey() == k)
				return curBind;
		}
		return null;
	}
	
	private static Bind findBind(String n) {
		for (int i = 0; i < binds.size(); i++) {
			Bind curBind = binds.get(i);
			if (curBind.getName() == n)
				return curBind;
		}
		return null;
	}
	
	private static Axis findAxis(String n) {
		for (int i = 0; i < axes.size(); i++) {
			Axis curAxis = axes.get(i);
			if (curAxis.getName() == n)
				return curAxis;
		}
		return null;
	}
}
