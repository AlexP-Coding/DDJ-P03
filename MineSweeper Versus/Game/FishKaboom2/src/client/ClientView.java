package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import server.PlayerDetails;
import server.PlayerSocket;
import util.Board;
import util.Handler;

public class ClientView extends JFrame implements ActionListener{
	
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
	public int points, pointsOther = 0;
	
	public Panel panel = new Panel();
	public JLabel label, label2;
	public String username;
	public JButton button1 = new JButton("+100 points");
	public JButton button2 = new JButton("+100 points");
	public JButton button3 = new JButton("BOMB!");
	public JButton button4 = new JButton("BOMB!");
	
	
	//private JLabel scoreboardLabel; //
	public ClientView(String username) throws IOException  {		
		
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
		//PlayerDetails playerDetails = new PlayerDetails();
		//int nPlayers = playerDetails.getNrPlayers();
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 20));
		panel.setLayout(new GridLayout(0,3));
		panel.setBackground(Color.GRAY);
		this.add(panel, BorderLayout.SOUTH);
		
		label = new JLabel(username + " : " + points);
		label2 = new JLabel("Other Fisherman: " + pointsOther);
		panel.add(label);
		panel.add(button1);
		button1.addActionListener(this);
		panel.add(button3);
		button3.addActionListener(this);
		panel.add(label2);
		panel.add(button2);
		button2.addActionListener(this);
		panel.add(button4);
		button4.addActionListener(this);


		
		
		//button1.addActionListener((ActionListener) this);
		
		//button3.addActionListener((ActionListener) this);
		
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
	/*
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == button1) {
		 points+=100;
		 label.setText(username + " : " + points);
		} else if (obj == button2) {
		 pointsOther+=100;
		 label2.setText("Other Fisher: " + pointsOther);
		}else if (obj == button3) {
		 pointsOther-=500;
		 label2.setText(username + " : " + points);
		} else if (obj == button4) {
		 pointsOther-=500;
		 label2.setText("Other Fisher: " + pointsOther);

	}	
	*/
	public void actionPerformed(ActionEvent e) {

		
		Object obj = e.getSource();
		if (obj == button1) {
		 points+=100;
		 label.setText(username + " : " + points);
		} else if (obj == button2) {
		 pointsOther+=100;
		 label2.setText("Other Fisherman: " + pointsOther);
		} else if (obj == button3) {
			if (points <= 500) 
			points= 0;
			else points -=500;
			 label.setText(username + " : " + points);
		}else if (obj == button4) {
			if (pointsOther <= 500) 
			pointsOther= 0;
			else pointsOther -=500;
			 label2.setText("Other Fisherman: " + pointsOther);
		}
		}
	
}

