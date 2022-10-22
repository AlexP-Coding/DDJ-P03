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
import util.Space;

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
	private int score_board_width = 100;
	private boolean end = false;
	private boolean win = false;
	private int flags;
	
	//ARGUMENTOS DO BOARD TODO
	private JPanel panel;
	private JLabel u;
	private BufferedImage image;
	private Space[][] board = new Space[ClientView.Y][ClientView.X]	;
	
	
	public ClientView(String username) {
		
		int remainder = ClientView.BOMB_COUNT;
		
		double probability = (double) ClientView.BOMB_COUNT / (ClientView.X * ClientView.Y);
		
		panel = new JPanel();
		try {
			System.out.println("entre try");
			for(int i=0; i < ClientView.Y; i++) {
				for(int j=0; j < ClientView.X; j++) {
					//panel.add(u = new JLabel("HELOOO: "));
					//System.out.println("pass 2 for");
					if(Math.random() < probability && remainder > 0) {
						//System.out.println("enter if");
						panel.add(u = new JLabel("HELOOO: "));
						BufferedImage facingDown = ImageIO.read(new File("assets/TileUnknownBlueFish.png"));
						image = ClientView.resizeImage(facingDown, ClientView.WIDHT, ClientView.HEIGHT);
						//panel.add(new Space(true));
						remainder--;
					} else {
						board[i][j] = new Space(false);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.add(panel, BorderLayout.CENTER);
		//layout = new Board();
		ClientView.setInstance(this);
		
		//JPanel panel = new JPanel();
		
		flags = BOMB_COUNT;
		
		this.setTitle("Hello " + username + "! Good Luck at Fish-Kaboom!");
		this.setSize(X * WIDHT,Y * HEIGHT );
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
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
