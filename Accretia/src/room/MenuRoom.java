package room;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import io.Input;
import map.Background;

public class MenuRoom extends Room {

	private final int MARGIN = 25;
	
	private Background bg;
	
	private String[] options = {"Start", "Help", "Quit"};
	private int option;
	
	private Color unfocused, focused, title;
	
	private Font titleFont, optionFont;
	
	public MenuRoom() {
		option = 0;
		
		title = new Color(255, 255, 150);
		titleFont = new Font("Harlow Solid Italic", Font.BOLD, 48);
		
		unfocused = new Color(200, 200, 200);
		focused = new Color(150, 150, 255);
		optionFont = new Font("System", Font.PLAIN, 22);
		
		bg = new Background ("/Backgrounds/Space.png/", 1);
		bg.setVector(0, 0);
	}

	@Override
	public void tick() {
		bg.tick();
		
		if (Input.getPressedDown("Up")) {
			if (option == 0)
				option = 2;
			else
				option--;
		}
		
		if (Input.getPressedDown("Down")) {
			if (option == 2)
				option = 0;
			else
				option++;
		}
		
		if (Input.getPressedDown("Select"))
			switch(option) {
				case 0:
					RoomManager.setRoom(new SelectShipRoom());			
					break;
				case 1:
					
					break;
				default:
					System.exit(0);
					break;
			}
	}

	@Override
	public void draw(Graphics2D g) {
		bg.draw(g);
		
		g.setColor(title);
		g.setFont(titleFont);
		g.drawString("Accretia", 90, 90);
		
		g.setFont(optionFont);
		for(int o = 0;o < options.length; o++) {
			if (o == option)
				g.setColor(focused);
			else
				g.setColor(unfocused);
			
			g.drawString(options[o], 170, 200 + (MARGIN * o));
		}
	}

}
