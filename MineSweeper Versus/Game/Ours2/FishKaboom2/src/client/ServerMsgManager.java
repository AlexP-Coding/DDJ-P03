package client;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//import server.GameState.Status;
import util.Board;
import util.GameCommand;
import util.GameConstants;
import util.MineButton;
import util.Player;
import util.PlayerSocket;
import util.GameCommand.CommandType;

public class ServerMsgManager implements Runnable {
	private PlayerSocket playerSocket;
	private boolean isRunning;
	private Board board;
	private Map<String, Player> players;
	
	public ServerMsgManager(PlayerSocket socket, Board board, Map<String, Player> players) {
		this.playerSocket = socket;
		this.board = board;
		this.isRunning = false;
		this.players = players;
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
				if (msg != null)
					interpretMsg(msg);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void interpretMsg(String msg) throws IOException {
		System.out.println("Msg received by client " + this.playerSocket.getId() + ": " + msg);
		
		GameCommand cmd = GameCommand.createCommand(msg);
		
		if (cmd.getType().equals(CommandType.NEW_PLAYER))
			interpretNewPlayer(cmd);
	
		else if (cmd.getType().equals(CommandType.CLEAR)) 
			interpretClear(cmd);
		
		else if (cmd.getType().equals(CommandType.FLAG)) 
			interpretFlag(cmd);
		
		else {
			System.out.println("CLIENT received unusable command: " + msg);
			return;
		}
		
		return;
	}
	

	public void interpretNewPlayer(GameCommand cmd) throws IOException {
		String playerId = cmd.getPlayerId();
		int colorId = Integer.parseInt(cmd.getToken(0));
		Player player = new Player(playerId);
		player.setColor(colorId);
		this.players.put(playerId, player);
	}
	
	
	public void interpretClear(GameCommand cmd) throws IOException {
		List<String> tokens = cmd.getTokens();
		String playerId = cmd.getPlayerId();
		Player player = this.players.get(playerId);
		
		int nrTokens = cmd.getNrTokens();
		
		for (int i = 0; i < nrTokens; ) {
			int x = Integer.parseInt(tokens.get(i));
			int y = Integer.parseInt(tokens.get(i+1));
			int val = Integer.parseInt(tokens.get(i+2));
			i += 3;
			
			MineButton spot = board.getMineButton(x, y);
			if (spot.isCleared() || spot.isFlagged())
				return;
			
			spot.setCleared(true);
			if (val < 0) {
				//board.findBomb(x, y);
				spot.setGridImage("assets/Tilemine.png");
			}	
			else if (val == 0)
				spot.setGridImage("assets/TileEmpty.png");
			else
				spot.setGridImage("assets/Tile" + val + ".png");
			
			player.updateScore(val);
		}
		return;
	}
	
	public void interpretFlag(GameCommand cmd) throws IOException {
		List<String> tokens = cmd.getTokens();
		String playerId = cmd.getPlayerId();
		
		int x = Integer.parseInt(tokens.get(0));
		int y = Integer.parseInt(tokens.get(1));
		
		MineButton spot = board.getMineButton(x, y);
		
		if (spot.isFlagged())
			spot.setImageFlagged();
		else
			spot.setGridStyleDefault();
		
		spot.setFlagged(!spot.isFlagged());
	}
}