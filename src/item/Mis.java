package item;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import ui.game;

public class Mis {
	private Image img;
	private double x;
	private double y;
	private int speed;
	
	public Mis(double x, double y) {
		this.x = x-10;
		this.y = y-20;
		speed = 5;
		img = Toolkit.getDefaultToolkit().getImage("res/image/missile.png");
	}

	public void draw(Graphics g) {
		int x = (int)this.x;
		int y = (int)this.y;
		g.drawImage(img, x, y, game.getinstance());
	}

	public void update() {
		y -= speed;
		
	}

	public boolean outline() {
		return !((0<x && x<500)&&(0<y && y<700));
	}
}
