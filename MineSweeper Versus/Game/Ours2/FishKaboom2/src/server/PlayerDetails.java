package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PlayerDetails {
	
	private int nrPlayers;
	private List<Player> playersList;
	
	public PlayerDetails () {
		this.nrPlayers = 0;
		this.playersList = new ArrayList<Player>();
	}
	
	public int getNrPlayers() {
		return this.nrPlayers;
	}
	
	public List<Player> getPlayers() {
		return this.playersList;
	}
	
	public void addPlayer(String id, ServerSocket serverSocket) 
			throws IOException {
		
		this.nrPlayers++;
		Socket playerSocket = serverSocket.accept();
		
		Player player = new Player(id, playerSocket);
				
		this.playersList.add(player);
	}
	
	/*
	 * Sends a message to all players when world changes
	 * Should receive their respective OK or NOK messages
	 * eventually
	 */
	public void sendMsgToAllPlayers(String msg) 
			throws IOException {
		
		for (int i = 0; i < this.nrPlayers; i++) {
			this.playersList.get(i).sendMsgToPlayer(msg);
		}
	}
}
