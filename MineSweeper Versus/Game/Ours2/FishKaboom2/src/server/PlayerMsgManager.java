package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Queue;

import util.GameCommand;
import util.GameCommand.CommandType;
import util.PlayerSocket;

public class PlayerMsgManager implements Runnable {
	private PlayerSocket playerSocket;
	private Socket socket;
	private Queue<String> msgQueue;
	private PlayerDetails playerDetails;
	private boolean isRunning;
	
	public PlayerMsgManager(Socket socket, Queue<String> msgQueue, PlayerDetails playerDetails) {
		this.playerSocket = null;
		this.socket = socket;
		this.msgQueue = msgQueue;
		this.playerDetails = playerDetails;
		this.isRunning = false;
	}
	
	public void setIsRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public void run() {
		this.isRunning = true;
		
		// first awaits new game msg
		awaitNewPlayerMsg();  

		// Then continuously reads msgs to queue
		readMsgsToQueue();
	}
	
	private void readMsgsToQueue() {
		
		while (this.isRunning) {
			String msg = "";
			try {
				msg = this.playerSocket.readMsg();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.msgQueue.add(msg);
		}
	}
	
	private void awaitNewPlayerMsg() {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (true) {
			String msg = "";
			try {
				msg = in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (msg != "") {
				GameCommand cmd = GameCommand.createCommand(msg);
				if (cmd.getType().equals(CommandType.NEW_PLAYER)) {
					String playerId = cmd.getPlayerId();
					System.out.println("NEW PLAYER MSG RECEIVED FROM " + playerId);
					try {
						playerDetails.addPlayer(playerId, this.socket);
						this.playerSocket = this.playerDetails.getPlayerSocket(playerId);
						playerDetails.getPlayerSocket(playerId).setIn(in);
						playerDetails.getPlayerSocket(playerId).setOut();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					msgQueue.add(msg);
					break;
				}
			}
		}
	}
}
