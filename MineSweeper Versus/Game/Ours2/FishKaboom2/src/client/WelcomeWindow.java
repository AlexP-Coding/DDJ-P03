package client;

import javax.imageio.ImageIO;
import javax.swing.*;

import util.ImagePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class WelcomeWindow extends JFrame{
	
	/** Text field for username */
	private JTextField textf_username;
	
	/** Text field for server IP */
	private JTextField textf_serverIp;
	
	/** Button labels */
	private JLabel usernameLabel, serverIpLabel;
	
	private JPanel panel;
	
	private Image background;
	
	private ImagePanel picturePanel;
	
	private JButton login;
	
	private ClientSideConnection csc;
	
	private int playerID;
	private int otherPlayer;

	public WelcomeWindow() throws IOException {
		
		//Under the frame - setup
		JPanel panel = new JPanel();
		
		//Player name
		panel.add(usernameLabel = new JLabel("Username: "));
		panel.add(textf_username = new JTextField(16));
		
		//server to witch player will connect to play
		//panel.add(serverIpLabel = new JLabel("Server IP:"));
		//panel.add(textf_serverIp = new JTextField(16));
		
		this.usernameLabel.setForeground(Color.white);
		//this.serverIpLabel.setForeground(Color.white);
		
		panel.add(login = new JButton("LOGIN"));
		
		//Backgraound Image
		JLabel pic = new JLabel(new ImageIcon("assets/fish-kaboom2.png"));
		panel.add(pic);
		
		actionlogin();
		 
		//Backgound Color
		panel.setBackground(Color.BLACK);
		this.add(panel, BorderLayout.CENTER);
		
		//Frame set Up
		this.setTitle("Welcome to the Fish-Kaboom Game...");
		this.setSize(1150,800);
		this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setResizable(true);
		
		this.setVisible(true);
		
	}
	
	public void actionlogin() {
		login.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("Button clicked");
				String name = textf_username.getText();
				try {
					ClientView cv = new ClientView(name);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//String ip = textf_serverIp.getText();
			}
			
		});
	}
	
	public void connectToServer() {
		csc = new ClientSideConnection();
	}
	
	//Client Connection
	//class - all the things that a player need to connect to a server
	private class ClientSideConnection {
		private Socket socket;
		private DataInputStream dataIn;
		private DataOutputStream dataOut;
		
		public ClientSideConnection() {
			System.out.println("---------CLIENT-------");
			try {
				socket = new Socket("localhost", 5000);
				dataIn = new DataInputStream(socket.getInputStream());
				dataOut = new DataOutputStream(socket.getOutputStream());
				playerID = dataIn.readInt();
				System.out.println("Connection to server as Player #" + playerID + ".");
			} catch (IOException e) {
				System.out.println("IO Exception from csc contructor");
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		WelcomeWindow window = new WelcomeWindow();
		window.connectToServer();
	}
}
