package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.Board;
import util.GameConstants;
import util.Handler;
import util.PlayerSocket;
import util.Player;

public class ClientView extends JFrame implements ActionListener{
	
	private static ClientView instance;
		
	//setup
	public static final int HEIGHT = 40;
	public static final int WIDTH = 40;
	public static final int X = 15;
	public static final int Y = 15;
	
	public int points, pointsOther, bombs = 0;
	
	//GAME ATRIBUTES
	public static final int BOMB_COUNT = 50;
	
	public Panel panel = new Panel();
	public JLabel label, label2, label3;
	public String username;
	public JButton button1 = new JButton("+100 points");
	public JButton button2 = new JButton("+100 points");
	public JButton button3 = new JButton("BOMB!");
	public JButton button4 = new JButton("BOMB!");
	public JButton button5 = new JButton("Remove bomb.");
	
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
		
		setUsername(username);
		
				
		this.setTitle("Hello " + username + "! Good Luck at Fish-Kaboom!");
		this.setSize(Y * WIDTH,X * HEIGHT );
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		panel.setBackground(Color.GRAY);
		this.add(panel, BorderLayout.SOUTH);
		
		label = new JLabel(username + " : " + points);
		
		panel.add(label);
		panel.add(button1);
		button1.addActionListener(this);
		panel.add(button3);
		button3.addActionListener(this);
		
		label2 = new JLabel("Other Fisherman: " + pointsOther);
		panel.add(label2);		
		panel.add(button2);
		
		button2.addActionListener(this);
		panel.add(button4);
		button4.addActionListener(this);
		
		label3 = new JLabel("Bombs Exploded: " + bombs + "/5");
		panel.add(label3);
		button5.addActionListener(this);
		panel.add(button5);
		
		// Socket communication
		playerSocket = new PlayerSocket(username, GameConstants.SOCKET_SENDER, host, GameConstants.PORT_DEFAULT);
		playerSocket.setIn();
		playerSocket.setOut();
		playerSocket.sendNewPlayerMsg();
		
		players = new HashMap<String,Player>();
		
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
	
	public void setUsername(String name) {
		username = name;
	}
	
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == button1 && bombs < 5) {
			points += 100;
			label.setText(username + " : " + points);
		} else if (obj == button2 && bombs < 5) {
			pointsOther += 100;
			label2.setText("Other Fisherman: " + pointsOther);
		} else if (obj == button3 && bombs < 5) {
			if (points <= 500)
				points = 0;
			else
				points -= 500;
			label.setText(username + " : " + points);

			bombs++;
			if (bombs < 5) {
				label3.setText("Bombs Exploded: " + bombs + "/5");
			} else {
				label3.setText("YOU LOST! Team Points: " + (points + pointsOther));
			}

		} else if (obj == button4 && bombs < 5) {
			if (pointsOther <= 500)
				pointsOther = 0;
			else
				pointsOther -= 500;
			label2.setText("Other Fisherman: " + pointsOther);

			bombs++;
			if (bombs < 5) {
				label3.setText("Bombs Exploded: " + bombs + "/5");
			} else {
				label3.setText("YOU LOST! Team Points: " + (points + pointsOther));
			}

		} else if (obj == button5) {
			if (bombs > 0 && bombs < 6) {
				bombs--;
				label3.setText("Bombs Exploded: " + bombs + "/5");
			}
		}
	}

}
