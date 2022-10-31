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
import util.MineButton.Click;
import util.MineButton.Move;

public class Board extends JPanel {
	private enum Location {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
	}
	
	public static MineButton[][] grid;
	private int totalBombs=0;
	private int maxBombs = ClientView.BOMB_COUNT;
	
	public Board(Handler handler) throws IOException {		
		super();
		Board.grid = new MineButton[ClientView.X][ClientView.Y];
		this.makeNewBoard(handler);
		this.placeMines();
		this.placeNumbersandSpaces();
        this.setBounds(0, 0, ClientView.Y * ClientView.WIDTH, ClientView.X * ClientView.HEIGHT);
        this.setLayout(new GridLayout(ClientView.X, ClientView.Y));
		this.setOpaque(true);
		this.setBackground(Color.BLACK);
		
		
		
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
	
	public void setImageWhenClicked(int x, int y) throws IOException {
		if(!grid[x][y].isCleared()) {
			if(grid[x][y].isFlagged())
				grid[x][y].setGridImage("assets/TileFlag.png");

			else if(grid[x][y].hasBomb())
				grid[x][y].setGridImage("assets/Tilemine.png");
		
			else if(grid[x][y].getBombNearby() == 0) 
					grid[x][y].setGridImage("assets/TileEmpty.png");
			
			else if(grid[x][y].getBombNearby() == 1) 
				grid[x][y].setGridImage("assets/Tile1.png");
			
			else if(grid[x][y].getBombNearby() == 2) 
				grid[x][y].setGridImage("assets/Tile2.png");
			
			else if(grid[x][y].getBombNearby() == 3) 
				grid[x][y].setGridImage("assets/Tile3.png");
			
			else if(grid[x][y].getBombNearby() == 4) 
				grid[x][y].setGridImage("assets/Til4.png");
			
			else if(grid[x][y].getBombNearby() == 5) 
				grid[x][y].setGridImage("assets/Tile5.png");
			
			else if(grid[x][y].getBombNearby() == 6) 
				grid[x][y].setGridImage("assets/Tile6.png");
			
			else if(grid[x][y].getBombNearby() == 7) 
				grid[x][y].setGridImage("assets/Tile7.png");
			
			else if(grid[x][y].getBombNearby() == 8) 
				grid[x][y].setGridImage("assets/Tile8.png");
		}
	}
	
	
	public MineButton getMineButton(int x, int y) {
		return Board.grid[x][y];
	}
	
	public MineButton[][] getAllMineButton() {
		return Board.grid;
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
