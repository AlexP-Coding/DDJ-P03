package util;

import java.io.IOException;
import java.util.ArrayList;

import client.ClientView;

public class Handler {
	
	//private MineButton[][] current = Board.grid ;
	private ArrayList<Integer> posx = new ArrayList<Integer>();
    private ArrayList<Integer> posy = new ArrayList<Integer>();
    private ArrayList<MineButton> adjacentes;
    private ArrayList<MineButton> adjacentesclickeble = new ArrayList<MineButton>();
	
	public void click(MineButton mine) throws IOException {
	
		int positionx = mine.getPosx();
		int positiony = mine.getPosy();
		boolean v = mine.hasBomb();
		
		System.out.println("x: " + positionx + " y: " + positiony + " -- " + v);
		int bombsNear = mine.getBombNearby();
		
		mine.setCleared(true);
		
		if( mine.getBombNearby() == 0 && mine.hasBomb()==false) {
			System.out.println("EMPTY");
			adjacentes = mine.listofadjacenteswithoutBomb();
			//System.out.println("lista: " + adjacentes.get(0).getBombNearby());
			if (!adjacentes.isEmpty()) {
				for(int i=0; i<adjacentes.size(); i++) {
					adjacentesclickeble.add(adjacentes.get(i));
				}
			}
		}
		System.out.println("ITS BOMB OU NUMBER");
		mine.setImageWhenClicked();
		while(!adjacentesclickeble.isEmpty()) {
			MineButton temp = adjacentesclickeble.get(0);
			adjacentesclickeble.remove(0);
            temp.clickButton();
		}
		
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
