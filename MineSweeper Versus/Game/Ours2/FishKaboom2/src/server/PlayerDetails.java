package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.GameConstants;
import util.Player;
import util.PlayerSocket;


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
	
	public void addPlayer(String id, Socket socket) 
			throws IOException {
		
		this.nrPlayers++;
		
		PlayerSocket playerSocket = new PlayerSocket(id, GameConstants.SOCKET_SENDER, socket);
		playerSocket.getPlayer().setColor(nrPlayers-1);		
		
		this.playersList.add(playerSocket);
		this.playersMap.put(id, playerSocket);
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
			this.playersList.get(i).sendMsg(msg);
		}
	}
}
