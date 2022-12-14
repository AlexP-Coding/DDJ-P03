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
	
	public enum CommandType {
		NEW_PLAYER,
		START,
		CLEAR,
		FLAG,
		BOMB,
		MAXBOMBS;
	}
	
	public static String SPLITTER = " ";
	
	public GameCommand (String msg) {
		this.fullMsg = msg;
		System.out.println("FULL MESSAGE: " + fullMsg + ".");
		String[] tokensMsg =  msg.split(GameCommand.SPLITTER);
		System.out.println("Value of command: " + CommandType.valueOf(tokensMsg[0]) + ".");
		
		this.type = CommandType.valueOf(tokensMsg[0]);
		this.playerId = tokensMsg[1];
		//System.out.println("token nameid: " + tokensMsg[1]);
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
	
	public String getPlayerId() {
		return playerId;
	}
	
	public List<String> getTokens() {
		return tokens;
	}
	
	public int getNrTokens() {
		return nrTokens;
	}
	
	public String getToken(int index) {
		return this.tokens.get(index);
	}
	
	
	// STATIC CREATION OF A COMMAND

	public static GameCommand createCommand(String msg) {
		System.out.println("Creating a command (may not be to send): " + msg);
		return new GameCommand(msg);
	}
	
	public static GameCommand createCommand(CommandType type, String playerId, List<String> tokens) {
		String fullMsg = createCommandMsg(type, playerId, tokens);
		return GameCommand.createCommand(fullMsg);
	}
	
	
	// MSG CREATION
	
	public static String createCommandMsg(CommandType type, String playerId, List<String> cmdTokens) {
		StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(type.toString());
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

	public static String createMsgForPosition(CommandType type, String playerId, int x, int y, int value) {
		List<String> tokens = new ArrayList<String>();
		tokens.add("" + x);
		tokens.add("" + y);
		tokens.add("" + value);
		return GameCommand.createCommandMsg(type, playerId, tokens);
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

	// START playerJeny
	public static String createMsgGameStart(String playerId) {
		return GameCommand.createCommandMsg(CommandType.START, playerId, null);		
	}
	
	// BOMB playerJeny 3 4 20
	public static String createMsgBombFound(String playerId, int x, int y, int value){
		return GameCommand.createMsgForPosition(CommandType.BOMB, playerId, x, y, value);	
	}
	
	// CLEAR playerJeny 3 4
	public static String createMsgClearSpace(String playerId, int x, int y) {
		return GameCommand.createMsgForPosition(CommandType.CLEAR, playerId, x, y);	
	}
	
	// CLEAR playerJeny 3 4 20
	public static String createMsgClearSpace(String playerId, int x, int y, int value) {
		return GameCommand.createMsgForPosition(CommandType.CLEAR, playerId, x, y, value);
	}
	
	// FLAG playerJeny 3 4
	public static String createMsgFlagSpace(String playerId, int x, int y) {
		return GameCommand.createMsgForPosition(CommandType.FLAG, playerId, x, y);	
	}
}
