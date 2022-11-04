package client;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Board;
import util.GameConstants;
import util.MineButton;
import util.Player;
import util.PlayerSocket;
import util.SocketInfo;

public class ClientView extends JFrame{
	
	private static ClientView instance;
	
	private Board layout;
	
	//setup
	public static final int HEIGHT = 40;
	public static final int WIDTH = 40;
	public static final int X = 15;
	public static final int Y = 15;
	
	//GAME ATRIBUTES
	public static final int BOMB_COUNT = 50;
	
	//ARGUMENTOS DO BOARD TODO
	private Board board;
	
	private Player player;

	// Server communication
	private PlayerSocket playerSocket;
	private static final int PORT_DEFAULT = 5000;
	
	public ClientView(String username, String host) throws IOException {		
		
		ClientView.setInstance(this);
		
		playerSocket = new PlayerSocket(username, GameConstants.SOCKET_SENDER, host, PORT_DEFAULT);
		player = playerSocket.getPlayer();
				
		this.setTitle("Hello " + username + "! Good Luck at Fish-Kaboom!");
		this.setSize(Y * WIDTH,X * HEIGHT );
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		this.board = new Board(this.playerSocket);
		
		this.add(board, BorderLayout.CENTER);
		setResizable(false);
		this.setVisible(true);

		Thread serverThread = new Thread(new ServerMsgManager(playerSocket, board));
		serverThread.start();
	}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException {
		Image resultingImage = originalImage.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
		return outputImage;
	}
	
	public static void setInstance(ClientView instance) {
		ClientView.instance = instance;
	}
}
