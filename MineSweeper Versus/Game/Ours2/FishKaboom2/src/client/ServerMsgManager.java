package client;

import util.CommandConstants;
import util.GameCommand;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ClientMessageSender {

	String playerId;
	Socket socket;
	DataOutputStream out;
	BufferedReader in;
	
	public ClientMessageSender(String playerId, Socket socket, BufferedReader in, DataOutputStream out) {
		this.playerId = playerId;
		this.socket = socket;
		this.in = in;
		this.out = out;
		System.out.printf("Associated socket to server");
	}

	public void sendMsg(String msg) throws IOException {
		out.writeBytes(msg);
		System.out.printf("Sent msg " + msg);
	}
	
	public void sendMsg(GameCommand cmd) throws IOException {
		String msg = cmd.getFullMsg();
		sendMsg(msg);
	}
	
	public int sendNewPlayerMsg() throws IOException {
		String msg = GameCommand.createCommandMsg(CommandConstants.CMD_NEWPLAYER, this.playerId, null);
		sendMsg(msg);
		
		String response;
		while (true) {
			response = in.readLine();
			if (response != null) {
				System.out.printf("Player acknowleged with code %s %n", response);
				break;
			}
		}
		int playerCode = Integer.parseInt(response);
		return playerCode;
	}
	
	public void sendGameStartMsg() throws IOException {
		String msg = GameCommand.createCommandMsg(CommandConstants.CMD_START , playerId, null);
		sendMsg(msg);
	}
	
	public void sendClickedSpotMsg(String cmdType, int x, int y) throws IOException {
		List<String> tokens = 	new ArrayList<String>(
				Arrays.asList(
					"" + x, 
					"" + y)
			);

		String msg = GameCommand.createCommandMsg(cmdType, playerId, tokens);
		sendMsg(msg);
	}
	
	
	public void sendClearSpotMsg(int x, int y) throws IOException {
		sendClickedSpotMsg(CommandConstants.CMD_CLEAR, x, y);
	}
	
	
	public void sendFlagMsg(int x, int y) throws IOException {
		sendClickedSpotMsg(CommandConstants.CMD_FLAG, x, y);
	}
}