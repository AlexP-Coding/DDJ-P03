package util;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;

import client.ClientView;

public class Board {
	private JPanel panel;
	private JLabel u;
	private Space[][] board = new Space[ClientView.Y][ClientView.X]	;
	
	public Board() {
		int remainder = ClientView.BOMB_COUNT;
		
		double probability = (double) ClientView.BOMB_COUNT / (ClientView.X * ClientView.Y);
		
		try {
			System.out.println("entre try");
			for(int i=0; i < ClientView.Y; i++) {
				for(int j=0; j < ClientView.X; j++) {
					panel.add(u = new JLabel("HELOOO: "));
					System.out.println("pass 2 for");
					//if(Math.random() < probability && remainder > 0) {
						//System.out.println("enter if");
						board[i][j] = new Space(true);
						//remainder--;
					//} else {
						//board[i][j] = new Space(false);
					//}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
