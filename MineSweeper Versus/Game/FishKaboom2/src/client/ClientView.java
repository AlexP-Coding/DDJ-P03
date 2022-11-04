package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import server.PlayerDetails;
import server.PlayerSocket;
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
	//
	private int points = 0;
	
	public Panel panel = new Panel();
	public JLabel label;
	public String username;
	
	
	private JLabel scoreboardLabel; //
	public ClientView(String username) throws IOException {		
		
		ClientView.setInstance(this);
		setUsername (username);
		
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
		
		//*//
		PlayerDetails playerDetails = new PlayerDetails();
		int nPlayers = playerDetails.getNrPlayers();
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		panel.add(scoreboardLabel = new JLabel("SCOREBOARD"));
		panel.setBackground(Color.GRAY);
		this.add(panel, BorderLayout.SOUTH);
		
		JLabel label = new JLabel(username + " : " + points);
		panel.add(label);
		
		//List<PlayerSocket> allPlayers = playerDetails.getPlayers();
		
		//System.out.println(nPlayers); //0
		
		/*
		for (int i = 0; i < nPlayers; i++) {
			System.out.println(allPlayers.get(i));
			JLabel label = new JLabel(allPlayers.get(i) + " : " + "points");
			panel.add(label);
		}
		*/
	}
	
	public static void setInstance(ClientView instance) {
		ClientView.instance = instance;
	}
	
	public void setUsername (String name) {
		username = name;
	}
	
	
	//
	public void actionPerformed(ActionEvent e) {
		label.setText(username + " : " + points);
		
	}	
	
	
}
