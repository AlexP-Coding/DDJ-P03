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
import util.Player;

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
		System.out.println("SERVER sending msg to prev players START: " + newMsg);
		this.playerDetails.sendMsgToAllPlayers(newMsg);	
		System.out.println("SERVER sending msg to prev players DONE: " + newMsg);
		List<PlayerSocket> players = playerDetails.getPlayers();
		for (int i = 0; i < playerDetails.getNrPlayers(); i++) {
			Player prevPlayer = players.get(i).getPlayer();
			if (prevPlayer.getId().equals(playerId))
				continue;
			newMsg = GameCommand.createMsgNewPlayer(prevPlayer.getId(), prevPlayer.getColorId());
			PlayerSocket newPlayerSocket = this.playerDetails.getPlayerSocket(playerId);
			newPlayerSocket.sendMsg(newMsg);
		}
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
			if (this.board.isFirstClick() == true) {
				this.board.setIsFirstClick(false);
				List<MineButton> adjacentsClickable = spot.listofAdjacentsWithoutBomb();
				System.out.println("Gathering clickable adjacents");
				int nrAdjacents = adjacentsClickable.size();
				System.out.println(nrAdjacents + " CLICKABLE ADJACENTS");
				for (int i = 0; i < nrAdjacents; i++) {
					MineButton temp = adjacentsClickable.get(i);
					System.out.println("New adjacent is at x:" + temp.posx + " y:" + temp.posy);
					String msg2 = GameCommand.createMsgClearSpace(playerId, temp.posx, temp.posy)
								.replaceAll("\n", "");
					System.out.println("Gathering new clickable adjacent: " + msg2);
					interpretMsg(msg2);					
				}
			}
			return;
		}
		else if (nrBombsAdjacent == 0) {
			board.findSafeSpot();
			spot.setCleared(true);

			List<MineButton> adjacentsClickable = spot.listofAdjacentsWithoutBomb();
			String newMsg = GameCommand.createMsgClearSpace(playerId, x, y, nrBombsAdjacent);
			System.out.println("Sending to all players: " + newMsg);
			this.playerDetails.sendMsgToAllPlayers(newMsg);
			
			System.out.println("Gathering clickable adjacents");
			int nrAdjacents = adjacentsClickable.size();
			System.out.println(nrAdjacents + " CLICKABLE ADJACENTS");
			for (int i = 0; i < nrAdjacents; i++) {
				MineButton temp = adjacentsClickable.get(i);
				System.out.println("New adjacent is at x:" + temp.posx + " y:" + temp.posy);
				String msg2 = GameCommand.createMsgClearSpace(playerId, temp.posx, temp.posy)
							.replaceAll("\n", "");
				System.out.println("Gathering new clickable adjacent: " + msg2);
				try {
					interpretMsg(msg2);					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
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
