package server;

public class StartGameServer {
	
	public static void main(String[] args) {
		GameServer server = new GameServer();
		server.acceptConnections();
	}
}
