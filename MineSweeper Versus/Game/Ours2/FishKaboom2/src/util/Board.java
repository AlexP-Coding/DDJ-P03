package util;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JPanel;

import client.ClientView;

public class Board extends JPanel {
	
	public static MineButton[][] grid;
	private int totalBombs=0;
	private int maxBombs = ClientView.BOMB_COUNT;
	private int bombsFound = 0;
	private int bombLimit = 5;
	private int safeSpotsLeft;
	private boolean isFirstClick = true;

	public Board(Handler handler) throws IOException {		
		super();
		Board.grid = new MineButton[ClientView.X][ClientView.Y];
		this.makeNewBoard(handler);
		// FOR PLAYER, WHO NEEDS CLICKS ACKNOWLEDGED
		// BUT NOT BOARD FILLED
		if (handler == null) { 
			this.placeMines();
			this.placeNumbersandSpaces();
		}
        this.setBounds(0, 0, ClientView.Y * ClientView.WIDTH, ClientView.X * ClientView.HEIGHT);
        this.setLayout(new GridLayout(ClientView.X, ClientView.Y));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		this.safeSpotsLeft = ClientView.X * ClientView.Y - ClientView.BOMB_COUNT;
		
	}
	
	public boolean isFirstClick() {
		return this.isFirstClick;
	}

	public void setIsFirstClick(boolean isFC) {
		this.isFirstClick = isFC;
	}
	
	public void findBomb() {
		this.bombsFound++;
	}
	
	public int getBombsFound() {
		return this.bombsFound;
	}
	
	public int getBombLimit() {
		return this.bombLimit;
	}
	
	public void findSafeSpot() {
		this.safeSpotsLeft--;
	}
	
	public int getSafeSpotsLeft() {
		return this.safeSpotsLeft;
	}

	//Iniciates a grid of undiscovered mines
	public void makeNewBoard(Handler handler) throws IOException {
		try {
		for(int i=0; i< ClientView.X; i++) { // ROWS
			for(int j=0; j< ClientView.Y; j++) { // COLUMNS
				MineButton button = new MineButton(i,j, handler);
				GridBagConstraints constraint = new GridBagConstraints();
				constraint.gridx = i;
				constraint.gridy = j;
				this.add(button, constraint);
				
				this.add(button);
				Board.grid[i][j] = button;
			}
		}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void placeMines() {
		int x, y;
		while(totalBombs < maxBombs) {
			
			x = (int)(Math.random() *ClientView.X);
			y = (int)(Math.random()*ClientView.Y);
			System.out.println("Placing mine at [" + x + ", " + y + "]");
			if((Board.grid[x][y]).hasBomb() == false) {
				grid[x][y].setBomb();
				totalBombs ++ ;
			}
		}
		System.out.println("totalbombas:" + totalBombs);

	}
	
	public void placeNumbersandSpaces() {
		
		for(int i=0; i< ClientView.X; i++) {
			for(int j=0; j< ClientView.Y; j++) {
				int numberBombs = 0;
				MineButton mineButton = grid[i][j];
				if(!mineButton.hasBomb()) {
					
					if (mineButton.hasBombAdjacent(i, j, null, MineButton.Location.LEFT))
						numberBombs++;
					
					if (mineButton.hasBombAdjacent(i, j, null, MineButton.Location.RIGHT))
						numberBombs++;
					
					if (mineButton.hasBombAdjacent(i, j, MineButton.Location.TOP, null))
						numberBombs++;
					
					if (mineButton.hasBombAdjacent(i, j, MineButton.Location.BOTTOM, null))
						numberBombs++;
					
					if (mineButton.hasBombAdjacent(i, j, MineButton.Location.TOP, MineButton.Location.LEFT))
						numberBombs++;
					
					if (mineButton.hasBombAdjacent(i, j, MineButton.Location.BOTTOM, MineButton.Location.LEFT))
						numberBombs++;
					
					if (mineButton.hasBombAdjacent(i, j, MineButton.Location.TOP, MineButton.Location.RIGHT))
						numberBombs++;
					
					if (mineButton.hasBombAdjacent(i, j, MineButton.Location.BOTTOM, MineButton.Location.RIGHT))
						numberBombs++;
				}
				mineButton.setBombNearby(numberBombs);
				System.out.println("[x, y] = [" + i + ", " + j + "], #bombsAdjacent = " + numberBombs);
			}
		}
	}
	
	public static MineButton getMineButton(int x, int y) {
		return Board.grid[x][y];
	}
	
	public MineButton[][] getAllMineButton() {
		return Board.grid;
	}
	
}
