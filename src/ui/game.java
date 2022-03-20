package ui;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import item.Back;
import item.Button;
import item.Fig;
import item.Mis;

public class game extends Canvas implements Runnable{
	
	private Fig fig;
	private Back back;
	private Mis[] mis;
	private boolean isfire;
	private int misindex;
	private int mismax;
	private Button button;	
	private int dir;
	private Thread sub;		
	private int inter;
	
	private static game instance;
	public static game getinstance() {
		if(instance == null)
		instance = new game();
		return instance;		
	}
	private game() {		
		fig = new Fig(200,500);
		dir = 0;
		back = new Back();
		mis = new Mis[30];
		misindex = 0;
		mismax = 30;
		inter = 0;
		isfire = false;
		button = new Button(400-50,600-50);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if(button.point(x,y)) {
					isfire = true;
					button.change(true);
					return;
					}
				
				fig.move(x,y);				
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				if(button.point(x,y)) {
					isfire = false;
					button.change(false);
					return;
					}
			}
		});
		
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				
				fig.move(x,y);
			}
		});
		
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					dir |= Fig.UP;
					break;
				case KeyEvent.VK_RIGHT:
					dir |= Fig.RIGHT;
					break;
				case KeyEvent.VK_DOWN:
					dir |= Fig.DOWN;
					break;
				case KeyEvent.VK_LEFT:
					dir |= Fig.LEFT;
					break;		
				case KeyEvent.VK_SPACE:
					isfire = true;
					break;		
				}
				fig.moveby(dir);
			}			
			
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					dir &= ~Fig.UP;
					break;
				case KeyEvent.VK_RIGHT:
					dir &= ~Fig.RIGHT;
					break;
				case KeyEvent.VK_DOWN:
					dir &= ~Fig.DOWN;
					break;
				case KeyEvent.VK_LEFT:
					dir &= ~Fig.LEFT;
					break;
				case KeyEvent.VK_SPACE:
					isfire = false;
					break;	
				}
				
				fig.moveby(dir);
			}
	});
		
		sub = new Thread(this);
		sub.start();
	}
			
	
	public void update(Graphics g) {
		paint(g);
	}
	
	public void paint(Graphics g) {
		Image buf = createImage(getWidth(), getHeight());
		Graphics g2 = buf.getGraphics();	
		back.draw(g2);
		fig.draw(g2);		
		for(int i=0; i<misindex; i++)
			mis[i].draw(g2);
		button.draw(g2);
		g.drawImage(buf, 0, 0, this);
	}
	
	@Override
	public void run() {	
		while(true) {
			back.update();
			fig.update();	
			for(int i=0; i<misindex; i++)
				mis[i].update();
			
			for(int i=0; i<misindex; i++)
				if(mis[i].outline()) {
					for(int j=0; j<misindex-i-1; j++)
						mis[i+j]=mis[i+j+1];
						misindex--;
						break;
				}	
			
			
			//ÃÑ¾Ë½î±â
			if(isfire) {
				if(inter == 0) {
				Mis missile = fig.fire();
				if(misindex > mismax-1) {
					Mis[] temp = new Mis[misindex+mismax];
					for(int i=0; i<misindex; i++)
						temp[i]=mis[i];
					mis = temp;
				}
				mis[misindex] = missile;
				misindex++;
				}
				inter ++;
				inter %= 10;
			}
			
			System.out.println("½º·¹µå");
			System.out.printf("%d",misindex);
			repaint();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
