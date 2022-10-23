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
import util.MineButton;

public class ClientView extends JFrame{
	
	private static ClientView instance;
	
	private Board layout;
	
	//setup
	public static final int WIDHT = 20;
	public static final int HEIGHT = 20;
	public static final int X = 30;
	public static final int Y = 16;
	
	//GAME ATRIBUTES
	public static final int BOMB_COUNT = 50;
	
	//ARGUMENTOS DO BOARD TODO
	private Board board;
	
	
	public ClientView(String username) throws IOException {		
		
		ClientView.setInstance(this);
				
		
		this.setTitle("Hello " + username + "! Good Luck at Fish-Kaboom!");
		this.setSize(X * WIDHT,Y * HEIGHT );
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		
		this.board = new Board();
		this.getContentPane().add(board, BorderLayout.CENTER);
		setResizable(false);
		this.setVisible(true);
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
