package server;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.TreeMap;

public class GameServer {
	private ServerSocket serversocket;
	private int numPlayers;
	private ServerSideConnection player1;
	private ServerSideConnection player2;
	Map<Integer, String> players = new TreeMap<Integer,String>();
	
	public GameServer() {
		System.out.println("---------GAME SERVER---------");
		numPlayers = 0;
		
		try {
			serversocket = new ServerSocket(5000);
		} catch (IOException e) {
			System.out.println("IOException from GameServer Constructor");
		}
	}
	
	public void acceptConnections() {
		try {
			System.out.println("Waiting for connections...");
			while ( numPlayers < 2) {
				Socket socket = serversocket.accept();
				numPlayers++;
				System.out.println("Player #" + numPlayers + " has connected.");
				ServerSideConnection ssc = new ServerSideConnection(socket,numPlayers);
				if (numPlayers == 1) {
					player1 = ssc;
				} else {
					player2 = ssc;
				}
				Thread t = new Thread(ssc);
				t.start();
			}
			System.out.println("We now have 2 players. No longer accepting connections. Thank you, try next time!");
		} catch (IOException e) {
			System.out.println("IOException from acceptConnections()");
		}
	}
	
	//we need to create a thread for each player
	//allow us to identify witch is for player 1 and witch is for player 2
	private class ServerSideConnection implements Runnable {
		private Socket s;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		private int playerID;
		
		public ServerSideConnection(Socket _s, int id) {
			s = _s;
			playerID = id;
			
			try {
				dataIn = new DataInputStream(s.getInputStream());
				dataOut = new DataOutputStream(s.getOutputStream());
			} catch (IOException e) {
				System.out.println("IOExecption from run() constructor");
			}
		}
		
		//instructions that we want to run in a thread
		public void run() {
			try {
				dataOut.writeInt(playerID);
				//ones the player connects the server immediately sends the player
				//the ID number they have
				dataOut.flush();
				
				while(true) {
					//allows the server to send and receiver more stuff
				}
			}catch (IOException e) {
				System.out.println("IOException from run() SSC");
			}
		}
	}
	
	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.acceptConnections();
	}
	
}
