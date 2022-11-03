package client;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;
import util.Board;
import util.Handler;

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
	
	private Handler handler = new Handler();
	
	public ClientView(String username) throws IOException {		
		
		ClientView.setInstance(this);
				
		
		this.setTitle("Hello " + username + "! Good Luck at Fish-Kaboom!");
		this.setSize(Y * WIDTH,X * HEIGHT );
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		this.board = new Board(handler);
		
		this.add(board, BorderLayout.CENTER);
		setResizable(false);
		this.setVisible(true);
	}
	
	public static void setInstance(ClientView instance) {
		ClientView.instance = instance;
	}
}
