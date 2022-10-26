package util;

import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JPanel;

import client.ClientView;

public class Board extends JPanel {
	private MineButton[][] grid;
	
	public Board() throws IOException {		
		super();
		this.grid = new MineButton[ClientView.Y][ClientView.X];
		this.makeNewBoard();
        this.setBounds(0, 0, 480, 420);
        this.setLayout(new GridLayout(ClientView.Y, ClientView.X));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		
	}
	
	//Iniciates a grid of undiscovered mines
	public void makeNewBoard() throws IOException {
		MineButton button;
		for(int i=0; i< ClientView.Y; i++) {
			for(int j=0; j< ClientView.X; j++) {
				button = new MineButton(i,j);
				this.add(button);
				this.grid[i][j] = button;
			}
		}
	}
	
//	public void setGridFromTab(Cell[][] cells) {
//		
//	}
	
	public MineButton getMineButton(int x, int y) {
		return this.grid[x][y];
	}
	
}