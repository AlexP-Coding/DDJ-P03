package util;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientView;

public class Handler {
	
	//private MineButton[][] current = Board.grid ;
	private ArrayList<Integer> posx = new ArrayList<Integer>();
    private ArrayList<Integer> posy = new ArrayList<Integer>();
    private ArrayList<String> click = new ArrayList<String>();
	
	public void click(MineButton mine) throws IOException {
		int positionx = mine.getPosx();
		int positiony = mine.getPosy();
		int bombsNear = mine.getBombNearby();
		
		mine.setCleared(true);
		if(mine.getBombNearby() == 0) {
			
			if(positionx-1 >= 0 && Board.grid[positionx-1][positiony]!= null) {
				if(Board.grid[positionx-1][positiony].getBombNearby()== 0) {
					
					posx.add(positionx-1);
					Board.grid[positionx-1][positiony].setClicked(true);
//					current[positionx-1][positiony].setCleared(true);
//					current[positionx-1][positiony].setClicked(true);
					Board.grid[positionx-1][positiony].clickButton();
				}
				else {
					Board.grid[positionx-1][positiony].setCleared(true);
//					current[positionx-1][positiony].setCleared(true);
				}
			}
			if(positionx+1 < 16 && Board.grid[positionx+1][positiony] != null) {
				if(Board.grid[positionx+1][positiony].getBombNearby()== 0) {
					Board.grid[positionx+1][positiony].setCleared(true);
					Board.grid[positionx+1][positiony].setClicked(true);
//					current[positionx+1][positiony].setCleared(true);
//					current[positionx+1][positiony].setClicked(true);
					//Board.grid[positionx+1][positiony].clickButton();
				}
				else {
					Board.grid[positionx+1][positiony].setCleared(true);
//					current[positionx+1][positiony].setCleared(true);
				}
			}
			if(positiony-1 >= 0 && Board.grid[positionx][positiony-1] != null) {
				if(Board.grid[positionx][positiony-1].getBombNearby()== 0) {
					Board.grid[positionx][positiony-1].setCleared(true);
					Board.grid[positionx][positiony-1].setClicked(true);
//					current[positionx][positiony-1].setCleared(true);
//					current[positionx][positiony-1].setClicked(true);
					//Board.grid[positionx][positiony-1].clickButton();
				}
				else {
					Board.grid[positionx][positiony-1].setCleared(true);
//					current[positionx][positiony-1].setCleared(true);
				}
			}
			
			
			if(positiony+1 < 30 && Board.grid[positionx][positiony+1] != null) {
				if(Board.grid[positionx][positiony+1].getBombNearby()== 0) {
					Board.grid[positionx][positiony+1].setCleared(true);
					Board.grid[positionx][positiony+1].setClicked(true);
//					current[positionx][positiony+1].setCleared(true);
//					current[positionx][positiony+1].setClicked(true);
					//Board.grid[positionx][positiony+1].clickButton();
				}
				else {
					Board.grid[positionx][positiony+1].setCleared(true);
//					current[positionx][positiony+1].setCleared(true);
				}
			}
			
			if((positiony-1 >= 0) && (positionx-1 >= 0) && Board.grid[positionx-1][positiony-1] != null) {
				if(Board.grid[positionx-1][positiony-1].getBombNearby()== 0) {
					Board.grid[positionx-1][positiony-1].setCleared(true);
					Board.grid[positionx-1][positiony-1].setClicked(true);
//					current[positionx-1][positiony-1].setCleared(true);
//					current[positionx-1][positiony-1].setClicked(true);
					//Board.grid[positionx-1][positiony-1].clickButton();
				}
				else {
					Board.grid[positionx-1][positiony-1].setCleared(true);
//					current[positionx-1][positiony-1].setCleared(true);
				}
			}
			if((positionx-1 >= 0) && (positiony+1 < 30) && Board.grid[positionx-1][positiony+1] !=  null) {
				if(Board.grid[positionx-1][positiony+1].getBombNearby()== 0) {
					Board.grid[positionx-1][positiony+1].setCleared(true);
					Board.grid[positionx-1][positiony+1].setClicked(true);
//					current[positionx-1][positiony+1].setCleared(true);
//					current[positionx-1][positiony+1].setClicked(true);
					//Board.grid[positionx-1][positiony+1].clickButton();
				}else {
					Board.grid[positionx-1][positiony+1].setCleared(true);
//					current[positionx-1][positiony+1].setCleared(true);
				}
			}
			if((positionx+1 < 16) && (positiony-1 >= 0) && Board.grid[positionx+1][positiony-1] != null) {
				if(Board.grid[positionx+1][positiony-1].getBombNearby()== 0) {
					Board.grid[positionx+1][positiony-1].setCleared(true);
					Board.grid[positionx+1][positiony-1].setClicked(true);
//					current[positionx+1][positiony-1].setCleared(true);
//					current[positionx+1][positiony-1].setClicked(true);
					//Board.grid[positionx+1][positiony-1].clickButton();
				}
				else {
					Board.grid[positionx+1][positiony-1].setCleared(true);
//					current[positionx+1][positiony-1].setCleared(true);
				}
			}
			if(positiony+1 < 30 && positionx+1<16 && Board.grid[positionx+1][positiony+1] != null) {
				if(Board.grid[positionx+1][positiony+1].getBombNearby()== 0) {
					Board.grid[positionx+1][positiony+1].setCleared(true);
					Board.grid[positionx+1][positiony+1].setClicked(true);
//					current[positionx+1][positiony+1].setCleared(true);
//					current[positionx+1][positiony+1].setClicked(true);
					//Board.grid[positionx+1][positiony+1].clickButton();
				}
				else if(Board.grid[positionx+1][positiony+1].hasBomb()== false){
					Board.grid[positionx+1][positiony+1].setCleared(true);
//					current[positionx+1][positiony+1].setCleared(true);
				}
			}
			
			
		}
		
//		for(int i=0; i<ClientView.X; i++) {
//			for(int j=0; j<ClientView.Y; j++) {
//				if(Board.grid[i][j].isCleared() == true) {
//					if (Board.grid[i][j].getClicked() == true) {
//						Board.grid[i][j].clickButton();
//					}else {
//						Board.grid[i][j].setImageByNumbersOfBomb();
//					}
//				}
//			}
//		}
		mine.setImageByNumbersOfBomb();
		
	}
	
	public void rightClick(MineButton mine) throws IOException {
		if(mine.isCleared() == false) {
			if(mine.isFlagged() == true) {
				mine.setGridStyleDefault();
				System.out.println("is is flaged");
			}
			else {
				mine.setFlagged(true);
				mine.setImageFlagged();
				System.out.println(" flaged");
			}
		}
	}
}
