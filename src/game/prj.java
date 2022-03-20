package game;

import javax.swing.JFrame;

import ui.game;

public class prj {

	public static void main(String[] args) {
		JFrame fla = new JFrame();
		fla.setVisible(true);
		fla.setSize(500, 700);
		fla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game can = game.getinstance();
		fla.add(can);
		can.requestFocus();
		fla.validate();
	}

}
