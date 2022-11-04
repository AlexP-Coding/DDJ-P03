package client;

import java.io.IOException;
import java.util.List;

import server.GameState.Status;
import util.Board;
import util.GameCommand;
import util.GameConstants;
import util.MineButton;
import util.PlayerSocket;
import util.GameCommand.CommandType;

public class ServerMsgManager implements Runnable {
	private PlayerSocket playerSocket;
	private boolean isRunning;
	private Board board;
	
	public ServerMsgManager(PlayerSocket socket, Board board) {
		this.playerSocket = socket;
		this.board = board;
		this.isRunning = false;
	}
	
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void run() {
		this.isRunning = true;
		
		while (this.isRunning) {
			String msg = "";
			try {
				msg = this.playerSocket.readMsg();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				interpretMsg(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void interpretMsg(String msg) throws IOException {
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
		/*String playerId = cmd.getPlayerId();
		int colorId = playerDetails.getPlayer(playerId).getColorId();*/
	}
	
	/*
	 * Readies result of clear msg to send to all players
	 */
	public void interpretClear(GameCommand cmd) throws IOException {
		List<String> tokens = cmd.getTokens();
		String playerId = cmd.getPlayerId();
		
		int x = Integer.parseInt(tokens.get(0));
		int y = Integer.parseInt(tokens.get(1));
		int val = Integer.parseInt(tokens.get(2));
		
		MineButton spot = board.getMineButton(x, y);

		// Do nothing if spot is cleared or flagged already
		if (spot.isCleared() || spot.isFlagged())
			return;
		
		spot.setCleared(true);
		if (val < 0) {
			board.findBomb(x, y);
			spot.setGridImage("assets/Tilemine.png");
		}
		else if (val == 0)
			spot.setGridImage("assets/TileEmpty.png");
		else
			spot.setGridImage("assets/Tile" + val + ".png");
		
		// !! TO-DO, Keep adding spots and values as they are cleared
		return;
	}
	
	/*
	 * Sends flag msg to all players
	 */
	public void interpretFlag(GameCommand cmd) throws IOException {
		/*List<String> tokens = cmd.getTokens();
		String playerId = cmd.getPlayerId();
		
		int x = Integer.parseInt(tokens.get(0));
		int y = Integer.parseInt(tokens.get(1));
		
		Board board = this.gameState.getBoard();
		MineButton spot = board.getMineButton(x, y);
		
		spot.setFlagged(!spot.isFlagged());*/
	}
}