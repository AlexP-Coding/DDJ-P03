package server;

import java.awt.Color;
import java.io.*;
import java.net.*;
import java.util.ArrayDeque;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import util.Board;
import util.GameConstants;
import util.MineButton;
import util.SocketInfo;
import server.GameState.Status;

public class GameServer {
	private ServerSocket serversocket; // awaits connections
	private GameState gameState;
	private Queue<String> msgQueue;
	private PlayerDetails playerDetails;
	
	public GameServer() {
		System.out.println("---------GAME SERVER---------");
		
		try {
			this.serversocket = new ServerSocket(GameConstants.PORT_DEFAULT);
			this.msgQueue = new ConcurrentLinkedQueue<String>();
		} catch (IOException e) {
			System.out.println("IOException from GameServer Constructor");
		}
	}
	
	public void acceptConnections() {
		Thread boardThread = new Thread(new MsgQueueManager(this.msgQueue, this.playerDetails, this.gameState));
		
		
		try {
			System.out.println("Waiting for connections...");
			while (playerDetails.getNrPlayers() < 2 || gameState.getStatus().equals(Status.STARTED)) {
				Socket socket = serversocket.accept();
				Thread playerThread = new Thread(new PlayerMsgManager(socket, this.msgQueue, this.playerDetails));
				playerThread.start();
			}
			System.out.println("We now have 2 players. No longer accepting connections. Thank you, try next time!");
		} catch (IOException e) {
			System.out.println("IOException from acceptConnections()");
		}
	}
}
