package item;

import java.awt.Color;
import java.awt.Graphics;

public class Button {
	private int x;
	private int y;
	private Color bg;

	public Button(int x, int y) {
		this.x = x;
		this.y = y;
		bg = Color.BLUE;
	}

	public void draw(Graphics g) {
		g.setColor(bg);
		g.fillArc(x, y, 100, 100, 0, 180);
		g.setColor(bg);
	}

	public boolean point(int x, int y) {
		return ((this.x<x && x<this.x+100) && (this.y<y && y<this.y+50));
	}

	public void change(boolean b) {
		if(b)
			bg = Color.GREEN;
		else 
			bg = Color.BLUE;
	}

}
