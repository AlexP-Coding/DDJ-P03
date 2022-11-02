package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class PlayerSocket {

	private Player player;
	private SocketInfo socketInfo;
	
	public PlayerSocket(String id, int type, Socket socket) throws IOException {
		this.player = new Player(id);
		this.socketInfo = new SocketInfo(type, socket);
	}
	
	public PlayerSocket(String id, int type, String host, int port) throws IOException {
		this.player = new Player(id);
		Socket socket = new Socket(host, port);
		this.socketInfo = new SocketInfo(type, socket);
	}
	
	public String getId() {
		return this.getPlayer().getId();
	}
	public Player getPlayer() {
		return this.player;
	}
	
	public SocketInfo getSocketInfo() {
		return this.socketInfo;
	}
	
	public void setIn(BufferedReader in) {
		this.socketInfo.setIn(in);
	}
	
	public void setIn() throws IOException {
		this.socketInfo.setIn();
	}

	public void setOut(DataOutputStream out) {
		this.socketInfo.setOut(out);
	}
	
	public void setOut() throws IOException {
		this.socketInfo.setOut();
	}
	
	
	/* Sends a message to a player
	 * Should return the player OK or NOK message eventually
	*/
	public void sendMsg(String msg) throws IOException { 
		this.socketInfo.sendMsg(msg); 
	}
	
	public String readMsg() throws IOException {
		return this.socketInfo.readMsg();
	}
	
}
