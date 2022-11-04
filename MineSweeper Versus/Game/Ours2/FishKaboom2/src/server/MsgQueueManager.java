package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import util.Board;
import util.GameCommand;
import util.PlayerSocket;
import util.GameCommand.CommandType;
import util.GameConstants;
import util.MineButton;

class MsgQueueManager implements Runnable {
	
	private Queue<String> msgQueue;
	private PlayerDetails playerDetails;
	private boolean isRunning;
	private Board board;
	
	public MsgQueueManager(Queue<String> msgQueue, PlayerDetails playerDetails, Board board) {
		this.msgQueue = msgQueue;
		this.playerDetails = playerDetails;
		this.isRunning = false;
		this.board = board;
	}
	
	public void run() {
		this.isRunning = true;
		while (isRunning) {
			try {
				String msg = "";
				msg = this.msgQueue.poll();
				if (msg != null && msg != "") 
					interpretMsg(msg);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void interpretMsg(String msg) throws IOException {
		System.out.println("SERVER QueueManager retrieved: " + msg + ". Creating command to interpret...");
		GameCommand cmd = GameCommand.createCommand(msg);
		
		// NEW_PLAYER is the only command that must also be taken care of
		// in the PlayerMsgManager
		System.out.println("Type: " + cmd.getType().toString());
		if (cmd.getType().equals(CommandType.NEW_PLAYER)) {
			interpretNewPlayer(cmd);}
	
		else if (cmd.getType().equals(CommandType.CLEAR)) 
			interpretClear(cmd);
		
		else if (cmd.getType().equals(CommandType.FLAG)) 
			interpretFlag(cmd);
		
		else {
			System.out.println("SERVER received unusable command: " + msg);
			return;
		}
		
		return;
	}
	
	/*
	 * Sends new player msg to all players
	 */
	public void interpretNewPlayer(GameCommand cmd) throws IOException {
		System.out.println("SERVER Interpret New Player: " + cmd.getFullMsg());
		String playerId = cmd.getPlayerId();
		int colorId = playerDetails.getPlayer(playerId).getColorId();
		String newMsg = GameCommand.createMsgNewPlayer(playerId, colorId);
		System.out.println("SERVER sending msg START: " + newMsg);
		this.playerDetails.sendMsgToAllPlayers(newMsg);	
		System.out.println("SERVER sending msg DONE: " + newMsg);
	}
	
	/*
	 * Readies result of clear msg to send to all players
	 */
	public void interpretClear(GameCommand cmd) throws IOException {
		List<String> tokens = cmd.getTokens();
		String playerId = cmd.getPlayerId();
		
		int x = Integer.parseInt(tokens.get(0));
		int y = Integer.parseInt(tokens.get(1));
		
		MineButton spot = this.board.getMineButton(x, y);

		// Do nothing if spot is cleared or flagged already
		if (spot.isCleared() || spot.isFlagged())
			return;
		
		if (spot.hasBomb()) {
			
			spot.setCleared(true);
			board.findBomb();
			String newMsg = GameCommand.createMsgClearSpace(playerId, x, y, GameConstants.BOMB_PENALTY);
			this.playerDetails.sendMsgToAllPlayers(newMsg);
				
			return;
		}
		
		int nrBombsAdjacent = spot.getBombNearby();
		
		if (nrBombsAdjacent > 0) {
			spot.setCleared(true);
			board.findSafeSpot();
			String newMsg = GameCommand.createMsgClearSpace(playerId, x, y, nrBombsAdjacent);
			this.playerDetails.sendMsgToAllPlayers(newMsg);
			return;
		}
		else if (nrBombsAdjacent == 0) {
			List<String> spotAndValueTokens = new ArrayList<String>();

			List<MineButton> adjacentsClickable = new ArrayList<MineButton>();
			List<MineButton> adjacents = spot.listofAdjacentsWithoutBomb();
			
			if (!adjacents.isEmpty()) {
				for(int i=0; i<adjacents.size(); i++) {
					adjacentsClickable.add(adjacents.get(i));
				}
			}
			
			while (!adjacentsClickable.isEmpty()) {
				MineButton temp = adjacentsClickable.get(0);
				adjacentsClickable.remove(0);
			}
			
		}
		return;
	}
	
	/*
	 * Sends flag msg to all players
	 */
	public void interpretFlag(GameCommand cmd) throws IOException {
		List<String> tokens = cmd.getTokens();
		String playerId = cmd.getPlayerId();
		
		int x = Integer.parseInt(tokens.get(0));
		int y = Integer.parseInt(tokens.get(1));
		
		MineButton spot = board.getMineButton(x, y);
		
		spot.setFlagged(!spot.isFlagged());
		
		String newMsg = GameCommand.createMsgFlagSpace(playerId, x, y);
		this.playerDetails.sendMsgToAllPlayers(newMsg);
	}
	
	

}
