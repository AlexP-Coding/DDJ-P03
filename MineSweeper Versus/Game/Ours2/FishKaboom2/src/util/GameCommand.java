package util;

import java.util.ArrayList;
import java.util.List;

public class GameCommand {
	
	// Command received
	private String fullMsg;
	
	private CommandType type;
	
	private List<String> tokens;
	
	// Tokens included
	private int nrTokens;
	
	// Player/Server who sent command
	private String playerId; // Can be a player or NULL (the server)
	
	// Command types (format bellow)
	public enum CommandType {
		NEW_PLAYER,
		START,
		CLEAR,
		FLAG,
		BOMB,
		MAXBOMBS;
	}
	
	// Command responses/results
	public enum Result {
		OK,
		NOK;
	}
	
	public static String SPLITTER = " ";
	
	public GameCommand (String msg) {
		this.fullMsg = msg;
		String[] tokensMsg =  msg.split(this.SPLITTER);
		this.type = CommandType.valueOf(tokensMsg[0]);
		this.playerId = tokensMsg[1];
		this.tokens = new ArrayList<String>();
		for (int i = 2; i < tokensMsg.length; i++)
			this.tokens.add(tokensMsg[i]);
		this.nrTokens = this.tokens.size();
	}
	
	public GameCommand() {}
	
	
	// GETTERS
	
	public String getFullMsg() {
		return fullMsg;
	}
	
	public CommandType getType() {
		return type;
	}
	
	public List<String> getTokens() {
		return tokens;
	}
	
	public int getNrTokens() {
		return nrTokens;
	}
	
	public String getPlayerId() {
		return playerId;
	}
	
	public String getToken(int index) {
		return this.tokens.get(index);
	}
	
	
	// STATIC CREATION OF A COMMAND

	public static GameCommand createCommand(String msg) {
		return new GameCommand(msg);
	}
	
	public static GameCommand createCommand(CommandType type, String playerId, List<String> tokens) {
		String fullMsg = createCommandMsg(type, playerId, tokens);
		return new GameCommand(fullMsg);
	}
	
	
	// MSG CREATION
	
	public static String createCommandMsg(CommandType type, String playerId, List<String> cmdTokens) {
		StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(type);
		msgBuilder.append(" ");
		msgBuilder.append(playerId);
		
		if (cmdTokens != null) {
			int nrCmdTokens = cmdTokens.size();
			for (int i = 0; i < nrCmdTokens; i++) {
				msgBuilder.append(" ");
				msgBuilder.append(cmdTokens.get(i));
			}			
		}
		msgBuilder.append("\n");
		
		String cmdMsg = msgBuilder.toString();
		
		return cmdMsg;
	}

	public static String createMsgForPosition(CommandType type, String playerId, int x, int y) {
		List<String> tokens = new ArrayList<String>();
		tokens.add("" + x);
		tokens.add("" + y);
		return GameCommand.createCommandMsg(type, playerId, tokens);
	}
	
	public static String createMsgNewPlayer(String playerId, int colorId) {
		List<String> tokens = new ArrayList<String>();
		tokens.add("" + colorId);
		return GameCommand.createCommandMsg(CommandType.NEW_PLAYER, playerId, tokens);		
	}

	public static String createMsgGameStart(String playerId) {
		return GameCommand.createCommandMsg(CommandType.START, playerId, null);		
	}
	
	public static String createMsgBombFound(String playerId, int x, int y) {
		return GameCommand.createCommandMsg(CommandType.BOMB, playerId, null);	
	}
	
	public static String createMsgUpdateScore(String playerId, int value) {
		List<String> tokens = new ArrayList<String>();
		tokens.add("" + value);
		return GameCommand.createCommandMsg(CommandType.BOMB, playerId, tokens);	
	}
	
	public static String createMsgClearSpace(String playerId, int x, int y) {
		return GameCommand.createMsgForPosition(CommandType.CLEAR, playerId, x, y);	
	}
	
	public static String createMsgFlagSpace(String playerId, int x, int y) {
		return GameCommand.createMsgForPosition(CommandType.FLAG, playerId, x, y);	
	}
	
	public static String createMsgBombMax(String playerId) {
		return GameCommand.createCommandMsg(CommandType.MAXBOMBS, playerId, null);
	}

}
