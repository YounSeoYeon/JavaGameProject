package item;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import ui.game;

public class Fig {
	
	public static final int NONE = 0;
	public static final int UP = 0b0001;
	public static final int RIGHT = 0b0010; 
	public static final int DOWN = 0b0100;
	public static final int LEFT = 0b1000;
	private double x;
	private double y;
	private double dx;
	private double dy;
	private double vx;
	private double vy;
	private int w;
	private int h;
	private int index;
	private int speed;
	private int distance;
	private int direction;
	private Image img;
	
	public Fig(double x, double y) {
		this.x = x;
		this.y = y;
		dx = dy = vx = vy = direction = 0;
		speed = 5;
		distance = 1;
		index = 3;
		img = Toolkit.getDefaultToolkit().getImage("res/image/fighter.png");		
	}

	public void draw(Graphics g) {	
		Canvas can = game.getinstance();
		int x = (int)this.x;
		int y = (int)this.y;
		w = img.getWidth(can)/7;
		h = img.getHeight(can);
		int offx = w/2;
		int offy = h/2;
		int dx1 = x-offx;
		int dy1 = y-offy;
		int dx2 = dx1+w;
		int dy2 = dy1+h;
		int sx1 = w*index;
		int sy1 = 0;
		int sx2 = sx1+w;
		int sy2 = h;
		g.drawImage(img, 
				dx1, dy1, dx2, dy2, 
				sx1, sy1, sx2, sy2, game.getinstance());
		g.drawRect(x, y, 64, 64);
	}

	public void move(double x, double y) {		
		dx = x;
		dy = y;
		
		if((this.x-speed<dx && dx<this.x+speed) && (this.y-speed<dy && dy<this.y+speed))
			return;
		
		double w = dx-this.x;
		double h = dy-this.y;
		distance = (int)Math.sqrt(w*w+h*h);
		
		vx = w/distance*speed;
		vy = h/distance*speed;			
	}

	public void moveby(int dir) {
		direction = dir;
	}
	
	public void update() {		
		if((direction & UP) == UP)
			y -= speed;
		if((direction & RIGHT) == RIGHT)
			x += speed;
		if((direction & DOWN) == DOWN)
			y += speed;		
		if((direction & LEFT) == LEFT)
			x -= speed;
		
		x += vx;
		y += vy;
		
		if((dx-speed<x && x<dx+speed) && (dy-speed<y && y<dy+speed)) {
			vx = vy = 0;
			return;
			}		
		distance -= speed;	
		
		System.out.printf("%f,%f\n",x,y);
	}

	public Mis fire() {
		return new Mis(x,y-32);
	}

}
