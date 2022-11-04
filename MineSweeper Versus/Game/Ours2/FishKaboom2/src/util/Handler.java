package util;

import java.io.IOException;
import java.util.ArrayList;

public class Handler {
	
	public int totalFlags = 0;
	public int totalBombsFounded = 0;
    private ArrayList<MineButton> adjacentes;
    private ArrayList<MineButton> adjacentesclickeble = new ArrayList<MineButton>();
	
	public void click(MineButton mine) throws IOException {
	
		int positionx = mine.getPosx();
		int positiony = mine.getPosy();
		boolean v = mine.hasBomb();
		
		System.out.println("x: " + positionx + " y: " + positiony + " -- " + v);
		
		
		if(!mine.isFlagged()) {
			mine.setCleared(true);
			
			if( mine.getBombNearby() == 0 && mine.hasBomb()==false) {
				
				//System.out.println("EMPTY");
				adjacentes = mine.listofadjacenteswithoutBomb();
				if (!adjacentes.isEmpty()) {
					for(int i=0; i<adjacentes.size(); i++) {
						adjacentesclickeble.add(adjacentes.get(i));
					}
				}
			}
			
			if(mine.hasBomb() == true) {
				totalBombsFounded++;
			}
			
			//System.out.println("ITS BOMB OU NUMBER");
			mine.setImageWhenClicked();
			while(!adjacentesclickeble.isEmpty()) {
				MineButton temp = adjacentesclickeble.get(0);
				adjacentesclickeble.remove(0);
	            temp.clickButton();
			}
			
			
		}
		
	}
	
	public void rightClick(MineButton mine) throws IOException {
		if(mine.isCleared() == false) {
			if(mine.isFlagged() == true) {
				mine.setFlagged(false);
				mine.setGridStyleDefault();
				totalFlags--;
				System.out.println("is is flaged");
			}
			else {
				mine.setFlagged(true);
				mine.setImageFlagged();
				totalFlags++;
				System.out.println(" flaged");
			}
		}
	}
}
