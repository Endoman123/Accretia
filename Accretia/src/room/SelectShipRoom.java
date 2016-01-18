package room;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import io.Input;
import main.Game;

public class SelectShipRoom extends Room {

	private final int 
		SHIPS = 2,
		OFFSET_X = 120,
		OFFSET_Y = 200,
		MARGIN = 100;
	
	private final Color 
		FOCUSED = new Color(32, 32, 32),
		TITLE = new Color(255, 255, 150);
	
	private final Font TITLE_FONT = new Font("Harlow Solid Italic", Font.BOLD, 40);
	
	private BufferedImage[] ship;
	
	private int currentShip;
	
	public SelectShipRoom() {
		try {
			ship = new BufferedImage[SHIPS];
			ship[0] = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerShip/white_single.png/"));
			ship[1] = ImageIO.read(getClass().getResourceAsStream("/Sprites/PlayerShip/black_single.png/"));
		} catch (Exception e) {
			// Do nothing; just assume that the path is wrong.
		}
	}

	@Override
	public void tick() {
		if (Input.getPressedDown("Left")) {
			if (currentShip == 0)
				currentShip = 1;
			else
				currentShip--;
		}
		if (Input.getPressedDown("Right")) {
			if (currentShip == 1)
				currentShip = 0;
			else
				currentShip++;
		}
		if (Input.getPressedDown("Select"))
			RoomManager.setRoom(new GameRoom(currentShip));
	}

	@Override
	public void draw(Graphics2D g) {
		g.clearRect(0, 0, Game.getWidth(false), Game.getHeight(false));
		
		g.setColor(TITLE);
		g.setFont(TITLE_FONT);
		g.drawString("Choose your ship:", 50, 90);
		
		for(int s = 0; s < SHIPS; s++) {
			if (s == currentShip) {
				g.setColor(FOCUSED);
				g.fillRect(s * MARGIN + OFFSET_X, OFFSET_Y, 64, 64);
			}
			
			g.drawImage(ship[s], s * MARGIN + OFFSET_X, OFFSET_Y, 64, 64, null);
		}

	}

}
