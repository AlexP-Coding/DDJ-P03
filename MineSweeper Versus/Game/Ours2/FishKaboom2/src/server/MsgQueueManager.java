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
import server.GameState.Status;

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
			try {
				String msg = "";
				msg = this.msgQueue.poll();
				if (msg != null) 
					interpretMsg(msg);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void interpretMsg(String msg) throws IOException {
		GameCommand cmd = GameCommand.createCommand(msg);
		
		// NEW_PLAYER is the only command that must also be taken care of
		// in the PlayerMsgManager
		if (cmd.getType().equals(CommandType.NEW_PLAYER))
			interpretNewPlayer(cmd);
	
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
		String playerId = cmd.getPlayerId();
		int colorId = playerDetails.getPlayer(playerId).getColorId();
		
		String newMsg = GameCommand.createMsgNewPlayer(playerId, colorId);
		this.playerDetails.sendMsgToAllPlayers(newMsg);	
	}
	
	/*
	 * Readies result of clear msg to send to all players
	 */
	public void interpretClear(GameCommand cmd) throws IOException {
		List<String> tokens = cmd.getTokens();
		String playerId = cmd.getPlayerId();
		
		int x = Integer.parseInt(tokens.get(0));
		int y = Integer.parseInt(tokens.get(1));
		
		Board board = this.gameState.getBoard();
		MineButton spot = board.getMineButton(x, y);

		// Do nothing if spot is cleared or flagged already
		if (spot.isCleared() || spot.isFlagged())
			return;
		
		if (spot.hasBomb()) {
			
			spot.setCleared(true);
			board.findBomb(x, y);
			String newMsg = GameCommand.createMsgClearSpace(playerId, x, y, GameConstants.BOMB_PENALTY);
			this.playerDetails.sendMsgToAllPlayers(newMsg);
			
			if (board.getBombsFound() >= board.getBombLimit() && 
					!this.gameState.getStatus().equals(Status.COUNTDOWN_ON)) {
				newMsg = GameCommand.createMsgBombMax(GameConstants.SERVER_ID);
				this.gameState.startCountdown(); // sets status to countdown on
				this.playerDetails.sendMsgToAllPlayers(newMsg);
			}
				
			return;
		}
		
		int nrBombsAdjacent = spot.getBombNearby();
		
		if (nrBombsAdjacent > 0) {
			spot.setCleared(true);
			board.findSafeSpot(x, y);
			String newMsg = GameCommand.createMsgClearSpace(playerId, x, y, nrBombsAdjacent);
			this.playerDetails.sendMsgToAllPlayers(newMsg);
			return;
		}
		
		// !! TO-DO, Keep adding spots and values as they are cleared
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
		
		Board board = this.gameState.getBoard();
		MineButton spot = board.getMineButton(x, y);
		
		spot.setFlagged(!spot.isFlagged());
		
		String newMsg = GameCommand.createMsgFlagSpace(playerId, x, y);
		this.playerDetails.sendMsgToAllPlayers(newMsg);
	}
	
	

}
