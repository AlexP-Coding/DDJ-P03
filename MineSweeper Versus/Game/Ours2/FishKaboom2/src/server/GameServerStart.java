package server;

public class GameServerStart {
	
	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.acceptConnections();
	}
}
