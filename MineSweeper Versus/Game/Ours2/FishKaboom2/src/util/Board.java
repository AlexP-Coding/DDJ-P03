package util;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JPanel;

import client.ClientView;

public class Board extends JPanel {
	private enum Location {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
	}
	
	private MineButton[][] grid;
	private int totalBombs=0;
	private int maxBombs = ClientView.BOMB_COUNT;
	private int bombsFound = 0;
	private int bombLimit = 10;
	private int safeSpotsLeft;
	private PlayerSocket playerSocket = null;
	
	public Board() throws IOException {		
		super();
		this.grid = new MineButton[ClientView.X][ClientView.Y];
		this.makeNewBoard();
		this.placeMines();
		this.placeNumbersandSpaces();
        this.setBounds(0, 0, ClientView.Y * ClientView.WIDTH, ClientView.X * ClientView.HEIGHT);
        this.setLayout(new GridLayout(ClientView.X, ClientView.Y));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		this.safeSpotsLeft = ClientView.X * ClientView.Y - ClientView.BOMB_COUNT;
	}
	
	public Board(PlayerSocket playerSocket) throws IOException {		
		super();
		this.grid = new MineButton[ClientView.X][ClientView.Y];
		this.makeNewBoard();
        this.setBounds(0, 0, ClientView.Y * ClientView.WIDTH, ClientView.X * ClientView.HEIGHT);
        this.setLayout(new GridLayout(ClientView.X, ClientView.Y));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		this.safeSpotsLeft = ClientView.X * ClientView.Y - ClientView.BOMB_COUNT;
		this.playerSocket = playerSocket;
	}
	
	
	//Iniciates a grid of undiscovered mines
	public void makeNewBoard() throws IOException {
		try {
		for(int i=0; i< ClientView.X; i++) { // ROWS
			for(int j=0; j< ClientView.Y; j++) { // COLUMNS
				MineButton button;
				if (this.playerSocket == null)
					button = new MineButton(i,j);
				else
					button = new MineButton(i,j, this.playerSocket);
				
				GridBagConstraints constraint = new GridBagConstraints();
				constraint.gridx = i;
				constraint.gridy = j;
				this.add(button, constraint);
				
				this.add(button);
				this.grid[i][j] = button;
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
			if((this.grid[x][y]).hasBomb() == false) {
				grid[x][y].setBomb();
				totalBombs ++ ;
			}
		}
		System.out.println("totalbombas:" + totalBombs);

	}
	
	public void placeNumbersandSpaces() {
		System.out.println("PLACE NUMBERS");
		//int k,k1,k2,k3;
		for(int i=0; i< ClientView.X; i++) {
			for(int j=0; j< ClientView.Y; j++) {
				int numberBombs = 0;
				if(!grid[i][j].hasBomb()) {
					
					if (hasBombAdjacent(i, j, null, Location.LEFT))
						numberBombs++;
					
					if (hasBombAdjacent(i, j, null, Location.RIGHT))
						numberBombs++;
					
					if (hasBombAdjacent(i, j, Location.TOP, null))
						numberBombs++;
					
					if (hasBombAdjacent(i, j, Location.BOTTOM, null))
						numberBombs++;
					
					if (hasBombAdjacent(i, j, Location.TOP, Location.LEFT))
						numberBombs++;
					
					if (hasBombAdjacent(i, j, Location.BOTTOM, Location.LEFT))
						numberBombs++;
					
					if (hasBombAdjacent(i, j, Location.TOP, Location.RIGHT))
						numberBombs++;
					
					if (hasBombAdjacent(i, j, Location.BOTTOM, Location.RIGHT))
						numberBombs++;
				}
				grid[i][j].setBombNearby(numberBombs);
				System.out.println("[x, y] = [" + i + ", " + j + "], #bombsAdjacent = " + numberBombs);
			}
		}
	}
	
	public void findBomb(int x, int y) throws IOException {
		this.bombsFound++;
		setImageWhenClicked(x, y);
	}
	
	public int getBombsFound() {
		return this.bombsFound;
	}
	
	public int getBombLimit() {
		return this.bombLimit;
	}
	
	public void findSafeSpot (int x, int y) throws IOException {
		this.safeSpotsLeft--;
		setImageWhenClicked(x,y);
	}
	
	public int getSafeSpotsLeft() {
		return this.safeSpotsLeft;
	}
	
	public void setImageWhenClicked(int x, int y) throws IOException {
		if (this.playerSocket == null)
			return;
		
		if(!grid[x][y].isCleared()) {
			this.playerSocket.sendClearSpotMsg(x, y);
		}
	}
	
	
//	public void setGridFromTab(Cell[][] cells) {
//		for(int i=0; i< ClientView.Y; i++) {
//			for(int j=0; j< ClientView.X; j++) {
//				this.grid[i][j].set;
//			}
//		}
//	}
	
	public MineButton getMineButton(int x, int y) {
		return this.grid[x][y];
	}
	
	public boolean hasBombAdjacent(int x, int y, Location relativeX, Location relativeY) {
		int checkX = -1;
		int checkY = -1;
		
		if (relativeX == null)
			checkX = x;
		else if (relativeX.equals(Location.TOP) )
			checkX = x-1;
		else if (relativeX.equals(Location.BOTTOM))
			checkX = x+1;
		
		if (relativeY == null)
			checkY = y;
		else if (relativeY.equals(Location.LEFT) )
			checkY = y-1;
		else if (relativeY.equals(Location.RIGHT))
			checkY = y+1;
		
		if ((checkX >= 0) && 
			(checkX < ClientView.X) &&
			(checkY >= 0) &&
			(checkY < ClientView.Y) ) {
			
			return grid[checkX][checkY].hasBomb();			
		}
		
		return false;
	}
	
}
