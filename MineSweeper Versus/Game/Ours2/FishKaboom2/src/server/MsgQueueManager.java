package server;

import java.io.IOException;
import java.util.Queue;

import util.GameCommand;
import util.GameCommand.CommandType;

class MsgQueueManager implements Runnable {
	
	private Queue<String> msgQueue;
	private PlayerDetails playerDetails;
	private GameState gameState;
	private boolean isRunning;
	
	public MsgQueueManager(Queue<String> msgQueue, PlayerDetails playerDetails, GameState gameState) {
		this.msgQueue = msgQueue;
		this.playerDetails = playerDetails;
		this.gameState = gameState;
		this.isRunning = false;
	}
	
	public void run() {
		this.isRunning = true;
		while (isRunning) {
			String msg = "";
			msg = this.msgQueue.poll();
			if (msg != null)
				interpretMsg(msg);
		}
	}
	
	public void interpretMsg(String msg) {
		GameCommand cmd = GameCommand.createCommand(msg);
		
		if (cmd.getType().equals(CommandType.NEW_PLAYER)) {
			
		}
		
		else if (cmd.getType().equals(CommandType.CLEAR)) {
			
		}
		
		else if (cmd.getType().equals(CommandType.FLAG)) {
			
		}
		else
			System.out.println("SERVER received unusable command: " + msg);
	}
	
	

}
