package util;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

import javax.swing.JPanel;

import client.ClientView;

public class Board extends JPanel {
	private MineButton[][] grid;
	private int totalBombs=0;
	private int maxBombs = ClientView.BOMB_COUNT;
	
	public Board() throws IOException {		
		super();
		this.grid = new MineButton[ClientView.Y][ClientView.X];
		this.makeNewBoard();
		this.placeMines();
		this.placeNumbersandSpaces();
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
	
	public void placeMines() {
		int x, y;
		while(maxBombs >= totalBombs) {
			
			x = (int)(Math.random() *30);
			y = (int)(Math.random()*16);
			System.out.println("yuyuyu: " + x +" yuyuy:" +y);
			if((this.grid[y][x]).hasBomb() == false) {
				grid[y][x].setBomb();
				totalBombs ++ ;
			}
		}
		System.out.println("totalbombas:"+totalBombs);

	}
	
	public void placeNumbersandSpaces() {
		System.out.println("PLACE NUMBERS");
		int numberBombs = 0;
		int k,k1,k2,k3;
		for(int i=0; i< ClientView.Y; i++) {
			for(int j=0; j< ClientView.X; j++) {
				System.out.println("x e y:" + i + "hh " + j);
				if(!grid[i][j].hasBomb()) {
					//System.out.println(i + "-" +j);
					//left right side
					k=i-1;
					//System.out.println("left-" + k);
					/*left side of the grid*/
					if(k>0 && grid[k][j].hasBomb() == true ) {
						numberBombs++;
					}
					
					k1=i+1;
					System.out.println("right-" + k1);
					if(k1<ClientView.Y && grid[k1][j].hasBomb() == true) {
						numberBombs++;
					}
					
					
					//up down side
					k2=j-1;
					if(k2>=0) {
						if(grid[i][k2].hasBomb() == true ) {
							numberBombs++;
						}
					}
					k3=j+1;
					if(k3<ClientView.Y) {
						if(grid[i][k3].hasBomb() == true ) {
							numberBombs++;
						}
					}
					
					//diagonals
					if (k>=0 && k2>=0) {
						if(grid[k][k2].hasBomb() == true) {
							numberBombs++;
						}
					}
					if (k1<ClientView.Y && k3<ClientView.Y) {
						if(grid[k1][k3].hasBomb() == true) {
							numberBombs++;
						}
					}
					if (k>=0 && k3<ClientView.Y) {
						if(grid[k][k3].hasBomb() == true) {
							numberBombs++;
						}
					}
					if (k1<ClientView.Y && k3<ClientView.Y) {
						if(grid[k1][k3].hasBomb() == true) {
							numberBombs++;
						}
					}
				}
				grid[i][j].setBombNearby(numberBombs);
			}
		}
	}
	
	public void setImageWhenClicked(int x, int y) throws IOException {
		if(!grid[x][y].isCleared()) {
			if(grid[x][y].isFlagged()) {
				grid[x][y].setGridImage("assets/TileFlag.png");
			}
			if(grid[x][y].hasBomb()) {
				grid[x][y].setGridImage("assets/Tilemine.png");
			}
			else {
				if(grid[x][y].getBombNearby() == 0) {
					grid[x][y].setGridImage("assets/TileEmpty.png");
				}
				if(grid[x][y].getBombNearby() == 1) {
					grid[x][y].setGridImage("assets/Tile1.png");
				}
				if(grid[x][y].getBombNearby() == 2) {
					grid[x][y].setGridImage("assets/Tile2.png");
				}
				if(grid[x][y].getBombNearby() == 3) {
					grid[x][y].setGridImage("assets/Tile3.png");
				}
				if(grid[x][y].getBombNearby() == 4) {
					grid[x][y].setGridImage("assets/Tile4.png");
				}
				if(grid[x][y].getBombNearby() == 5) {
					grid[x][y].setGridImage("assets/Tile5.png");
				}
				if(grid[x][y].getBombNearby() == 6) {
					grid[x][y].setGridImage("assets/Tile6.png");
				}
				if(grid[x][y].getBombNearby() == 7) {
					grid[x][y].setGridImage("assets/Tile7.png");
				}
				if(grid[x][y].getBombNearby() == 8) {
					grid[x][y].setGridImage("assets/Tile8.png");
				}
				
			}
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
	
}
