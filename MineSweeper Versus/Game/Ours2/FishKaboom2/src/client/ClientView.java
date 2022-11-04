package client;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.JFrame;
import util.Board;
import util.GameConstants;
import util.Handler;
import util.PlayerSocket;
import util.Player;

public class ClientView extends JFrame{
	
	private static ClientView instance;
		
	//setup
	public static final int HEIGHT = 40;
	public static final int WIDTH = 40;
	public static final int X = 15;
	public static final int Y = 15;
	
	//GAME ATRIBUTES
	public static final int BOMB_COUNT = 50;
	
	//ARGUMENTOS DO BOARD TODO
	private Board board;
	
	private Handler handler;
	
	// SERVER COMMUNICATION
	private PlayerSocket playerSocket;
	
	private Map<String, Player> players;
	
	public ClientView(String username, String host) throws IOException {

		System.out.println("---------GAME CLIENT---------");
		
		// LAYOUT
		ClientView.setInstance(this);
				
		this.setTitle("Hello " + username + "! Good Luck at Fish-Kaboom!");
		this.setSize(Y * WIDTH,X * HEIGHT );
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		// Socket communication
		playerSocket = new PlayerSocket(username, GameConstants.SOCKET_SENDER, host, GameConstants.PORT_DEFAULT);
		playerSocket.setIn();
		playerSocket.setOut();
		playerSocket.sendNewPlayerMsg();
		
		Thread serverThread = new Thread(new ServerMsgManager(playerSocket, board, players));
		serverThread.start();
		
		handler = new Handler(playerSocket);
		
		this.board = new Board(handler);
		this.add(board, BorderLayout.CENTER);
		setResizable(false);
		this.setVisible(true);
		
	}
	
	public static void setInstance(ClientView instance) {
		ClientView.instance = instance;
	}
}
