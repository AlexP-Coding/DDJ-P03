package util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketInfo {
	private Socket socket;
	private int type; // listening, receiving, both
	private String address;
	private int port;
	private BufferedReader in;
	private DataOutputStream out;
	
	public SocketInfo(int type, Socket socket) throws IOException {
		this.socket = socket;
		this.address = socket.getInetAddress().getHostAddress();
		this.port = socket.getPort();
	}

	public Socket getSocket() {
		return socket;
	}

	public String getAddress() {
		return address;
	}

	public int getPort() {
		return port;
	}

	public BufferedReader getIn() {
		return in;
	}

	public DataOutputStream getOut() {
		return out;
	}
	
	// All messages to end in "\n"
	public void sendMsg(String msg) throws IOException {
		this.out.writeBytes(msg + "\n");
	}
	
	public String readMsg() throws IOException {
		return this.in.readLine();
	}
	
	public void setIn(BufferedReader in) {
		this.in = in;
	}
	
	public void setIn() throws IOException {
		this.setIn(new BufferedReader(new InputStreamReader(this.socket.getInputStream())));
	}
	
	public void setOut(DataOutputStream out) {
		this.out = out;
	}
	
	public void setOut() throws IOException {
		this.setOut(new DataOutputStream(this.socket.getOutputStream()));
		
	}
	
}
