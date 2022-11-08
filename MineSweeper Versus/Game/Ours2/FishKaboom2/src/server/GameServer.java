package server;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import util.Board;
import util.GameConstants;

public class GameServer {
	private ServerSocket serversocket;
	private Queue<String> msgQueue;
	private PlayerDetails playerDetails;
	private Board board;
	
	public GameServer() {
		System.out.println("---------GAME SERVER---------");
		
		try {
			serversocket = new ServerSocket(GameConstants.PORT_DEFAULT);
			this.playerDetails = new PlayerDetails();
			this.msgQueue = new ConcurrentLinkedQueue<String>();
			this.board = new Board(null);
		} catch (IOException e) {
			System.out.println("IOException from GameServer Constructor");
		}
	}
	
	public void acceptConnections() {
		try {
			System.out.println("Starting board thread");
			
			Thread boardThread = new Thread(new MsgQueueManager(this.msgQueue, this.playerDetails, this.board));
			boardThread.start();
			
			System.out.println("Waiting for connections...");
			
			while (playerDetails.getNrPlayers() < playerDetails.getMaxPlayers()) {
				Socket socket = serversocket.accept();
				System.out.println("ACCEPTED CONN");
				Thread playerThread = new Thread(new PlayerMsgManager(socket, this.msgQueue, this.playerDetails));
				playerThread.start();
			}
			System.out.println("We now have" + playerDetails.getNrPlayers() + "player(s). No longer accepting connections.");			

		} catch (IOException e) {
			System.out.println("IOException from acceptConnections()");
		}
	}
	
}
