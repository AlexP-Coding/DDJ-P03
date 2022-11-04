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
	private int maxPlayers;
	private List<PlayerSocket> playersList;
	private Map<String, PlayerSocket> playersMap;
	
	public PlayerDetails() {
		this.nrPlayers = 0;
		this.maxPlayers = 1;
		this.playersList = new ArrayList<PlayerSocket>();
		this.playersMap = new HashMap<String, PlayerSocket>();
	}
	
	public int getNrPlayers() {
		return this.nrPlayers;
	}
	
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	public List<PlayerSocket> getPlayers() {
		return this.playersList;
	}
	
	// Returns player color Id
	public int addPlayer(String id, Socket socket) 
			throws IOException {
		
		this.nrPlayers++;
		
		PlayerSocket playerSocket = new PlayerSocket(id, GameConstants.SOCKET_SENDER, socket);
		playerSocket.getPlayer().setColor(nrPlayers-1);		
		
		this.playersList.add(playerSocket);
		this.playersMap.put(id, playerSocket);
		
		return playerSocket.getPlayer().getColorId();
	}
	
	public PlayerSocket getPlayerSocket(String id) {
		return this.playersMap.get(id);
	}
	
	public Player getPlayer(String id) {
		PlayerSocket playerSocket = this.getPlayerSocket(id);
		if (playerSocket != null)
			return this.playersMap.get(id).getPlayer();
		else
			return null;
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
