package server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Player {

	private String id;
	private Socket socket;
	private String address;
	private int port;
	private BufferedReader in;
	private DataOutputStream out;
	
	public Player(String id, Socket socket) throws IOException {
		this.id = id;
		this.socket = socket;
		this.address = socket.getInetAddress().getHostAddress();
		this.port = socket.getPort();
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new DataOutputStream(socket.getOutputStream());
	}
	
	public String getId() {
		return this.id;
	}
	public Socket getSocket() {
		return this.socket;
	}
	public String getAddress() {
		return this.address;
	}
	public int getPort() {
		return this.port;
	}
	
	/* Sends a message to a player
	 * Should return the player OK or NOK message eventually
	*/
	public void sendMsgToPlayer(String msg) throws IOException { 
		out.writeBytes(msg + "\n"); // All messages to end in "\n"
	}
	
	public String readMsgFromPlayer() throws IOException {
		return in.readLine();
	}
	
}
