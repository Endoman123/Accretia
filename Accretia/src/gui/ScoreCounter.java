package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class ScoreCounter {

	private final int X, Y;
	private long score;
	private final Font FONT = new Font("Arial", Font.BOLD, 12);
	
	public ScoreCounter(int x, int y) {
		X = x;
		Y = y;
		
	}
	
	public long getScore() {
		return score;
	}
	
	public void setScore(long value) {
		score = value;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.setFont(FONT);
		g.drawString(String.format("%09d", score), X, Y);
	}

}
