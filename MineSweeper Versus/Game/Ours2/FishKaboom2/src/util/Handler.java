package util;

import java.io.IOException;

public class Handler {
	
	
	
	public void click(MineButton mine) throws IOException {
		int positionx = mine.getPosx();
		int positiony = mine.getPosy();
		mine.setCleared(true);
		if(mine.getBombNearby() == 0) {
			
			System.out.println("ZERO");
		}
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
