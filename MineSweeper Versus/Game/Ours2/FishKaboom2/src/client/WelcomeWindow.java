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
import java.util.ArrayList;

public class WelcomeWindow extends JFrame{
	
	/** Text field for username */
	private JTextField textf_username;
	
	/** Text field for server IP */
	private JTextField textf_serverIp;
	
	/** Button labels */
	private JLabel usernameLabel, serverIpLabel;
	
	private JButton login;
	
	public WelcomeWindow() throws IOException {
		
		//Under the frame - setup
		JPanel panel = new JPanel();
		
		//Player name
		panel.add(usernameLabel = new JLabel("Username: "));
		panel.add(textf_username = new JTextField(16));
		panel.add(serverIpLabel = new JLabel("Server Public IP: "));
		
		//server to witch player will connect to play
		panel.add(serverIpLabel = new JLabel("Server IP:"));
		panel.add(textf_serverIp = new JTextField(20));
		
		this.usernameLabel.setForeground(Color.white);
		this.serverIpLabel.setForeground(Color.white);
		
		panel.add(login = new JButton("LOGIN"));
		
		//Backgraound Image
		JLabel pic = new JLabel(new ImageIcon("assets/fish-kaboom2.png"));
		panel.add(pic);
		
		 
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
		
		actionlogin(); // setup login
	}
	
	public void actionlogin() {
		login.addActionListener( new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//System.out.println("Button clicked");
				String playerId = textf_username.getText();
				String serverIp = textf_serverIp.getText();
				
				try {
					// Starts up a client view if button clicked
					ClientView cv = new ClientView(playerId, serverIp);
					dispose();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	
}
