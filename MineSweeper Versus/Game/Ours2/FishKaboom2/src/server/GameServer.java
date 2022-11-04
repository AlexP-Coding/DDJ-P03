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
			this.gameState = new GameState();
			this.playerDetails = new PlayerDetails();
		} catch (IOException e) {
			System.out.println("IOException from GameServer Constructor");
		}
	}
	
	public void acceptConnections() {
		Thread boardThread = new Thread(new MsgQueueManager(this.msgQueue, this.playerDetails, this.gameState));
		boardThread.start();
		
		try {
			System.out.println("Waiting for connections...");
			while (playerDetails.getNrPlayers() < playerDetails.getMaxPlayers() && !gameState.getStatus().equals(Status.STARTED)) {
				Socket socket = serversocket.accept();
				System.out.println("ACCEPTED CONN");
				Thread playerThread = new Thread(new PlayerMsgManager(socket, this.msgQueue, this.playerDetails));
				playerThread.start();
			}
			gameState.setStatus(Status.STARTED);
			System.out.println("We now have" + playerDetails.getNrPlayers() + "player(s). No longer accepting connections.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
