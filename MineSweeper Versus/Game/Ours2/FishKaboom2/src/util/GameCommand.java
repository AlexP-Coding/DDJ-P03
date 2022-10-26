package util;

import java.util.ArrayList;
import java.util.List;

public class GameCommand {
	
	// Command received
	private String fullMsg;
	
	private String type;
	
	private List<String> tokens;
	
	// Tokens included
	private int nrTokens;
	
	// Player/Server who sent command
	private String senderID;
	

	public GameCommand (String msg) {
		this.fullMsg = msg;
		String[] tokensMsg =  msg.split(CommandConstants.SPLITTER);
		this.type = tokensMsg[0];
		this.senderID = tokensMsg[1];
		this.tokens = new ArrayList<String>();
		for (int i = 2; i < tokensMsg.length; i++)
			this.tokens.add(tokensMsg[i]);
		this.nrTokens = this.tokens.size();
	}
	
	public GameCommand() {}
	
	public static String createCommandMsg(String type, String senderId, List<String> cmdTokens) {
		StringBuilder msgBuilder = new StringBuilder();
		msgBuilder.append(type);
		msgBuilder.append(" ");
		msgBuilder.append(senderId);
		
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
	
	public static GameCommand createCommand(String type, String senderId, List<String> tokens) {
		String fullMsg = createCommandMsg(type, senderId, tokens);
		return new GameCommand(fullMsg);
	}

	public String getFullMsg() {
		return fullMsg;
	}


	public String getType() {
		return type;
	}


	public List<String> getTokens() {
		return tokens;
	}


	public int getNrTokens() {
		return nrTokens;
	}


	public String getSenderID() {
		return senderID;
	}
	
	public String getToken(int index) {
		return this.tokens.get(index);
	}
	
}
