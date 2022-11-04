package util;

import java.io.IOException;
import java.util.ArrayList;

public class Handler {
	
	private PlayerSocket playerSocket;
	
	public Handler(PlayerSocket playerSocket) {
		this.playerSocket = playerSocket;
	}
    
	public void click(MineButton mine) throws IOException {
		int positionx = mine.getPosx();
		int positiony = mine.getPosy();
		
		if(!mine.isFlagged() && !mine.isCleared()) {
			System.out.println("CLIENT sending clear spot msg");
			playerSocket.sendClearSpotMsg(positionx, positiony);
		}
	}
	
	public void rightClick(MineButton mine) throws IOException {
		int positionx = mine.getPosx();
		int positiony = mine.getPosy();
		
		if (!mine.isCleared()) {
			System.out.println("CLIENT sending clear spot msg");			
			playerSocket.sendFlagMsg(positionx, positiony);
		}
			
	}
}
