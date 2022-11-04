package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.Player;


public class PlayerDetails {
	
	private int nrPlayers;
	private List<PlayerSocket> playersList;
	private Map<String, PlayerSocket> playersMap;
	
	public PlayerDetails() {
		this.nrPlayers = 0;
		this.playersList = new ArrayList<PlayerSocket>();
	}
	
	public int getNrPlayers() {
		return this.nrPlayers;
	}
	
	public List<PlayerSocket> getPlayers() {
		return this.playersList;
	}
	
	public void addPlayer(String id, ServerSocket serverSocket) 
			throws IOException {
		
		this.nrPlayers++;
		Socket playerSocket = serverSocket.accept();
		
		PlayerSocket player = new PlayerSocket(id, playerSocket);
				
		this.playersList.add(player);
		this.playersMap.put(id, player);
	}
	
	public PlayerSocket getPlayerSocket(String id) {
		return this.playersMap.get(id);
	}
	
	public Player getPlayer(String id) {
		return this.playersMap.get(id).getPlayer();
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
