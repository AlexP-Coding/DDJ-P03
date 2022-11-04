package util;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.ClientView;
// import util.Board.Click;
// import util.Board.Move;

public class MineButton extends JButton{
	public int posx, posy;
	public int mx, my;
	private boolean bomb;
	private boolean flagged;
	private boolean cleared;
	private int bombNearby;
	private BufferedImage image;
	private PlayerSocket playerSocket;
	
	public MineButton(int x, int y) throws IOException{
		super();
		this.posx=x;
		this.posy=y;
		this.bomb = false;
		flagged = false;
		cleared = false;
		bombNearby =0;
		
		
		this.setGridStyleDefault();
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click = new Click();
		this.addMouseListener(click);
		//this.addMouseListener(new MouseListener(x, y));
//		Dimension minSize = new Dimension(200, 500);
//		Dimension prefSize = new Dimension(200, 500);
//		Dimension maxSize = new Dimension(200, 500);
//		this.add(new Box.Filler(minSize, prefSize, maxSize));
	}
	
	public MineButton(int x, int y, PlayerSocket playerSocket) {
		this.posx=x;
		this.posy=y;
		this.bomb = false;
		flagged = false;
		cleared = false;
		bombNearby =0;
		this.playerSocket = playerSocket;
	}
	
//	public void setGridStyleDefault() throws IOException {
//		
//		BufferedImage facingDown = ImageIO.read(new File("assets/TileUnknownBlueFish2.png"));
//		image = ClientView.resizeImage(facingDown, 30, 30);
//	}
	
	public void setGridStyleDefault() {
		ImageIcon img = new ImageIcon("assets/TileUnknownBlueFish3.png");
		this.setIcon(img);
	}
	
	public void setPlayerSocket(PlayerSocket playerSocket) {
		this.playerSocket = playerSocket;
	}
	
	public boolean hasBomb() {
		return bomb;
	}
	
	public void setBomb() {
		this.bomb = true;
	}

	public boolean isFlagged() {
		return flagged;
	}

	public void setFlagged(boolean f) {
		this.flagged = f;
	}

	public boolean isCleared() {
		return cleared;
	}
	
	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}

	public int getBombNearby() {
		return bombNearby;
	}

	public void setBombNearby(int b) {
		this.bombNearby = b;
	}

	public BufferedImage getImage() {
		return image;
	}
	
	public void setGridImage(String Fileimagename) throws IOException {
		ImageIcon img = new ImageIcon(Fileimagename);
		this.setIcon(img);
	}

//	public void setImage(String Fileimagename) throws IOException {
//		BufferedImage temp = ImageIO.read(new File(Fileimagename));
//		image = ClientView.resizeImage(temp, ClientView.WIDHT, ClientView.HEIGHT);
//	}
	
	public int getPosx() {
		return posx;
	}
	
	public int getPosy() {
		return posy;
	}
	
	public void setImageWhenClicked() throws IOException {
		if(!isCleared()) {
			this.playerSocket.sendClearSpotMsg(posx, posy);
		}
	}
	
	public class Move implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent arg0) {
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("The mouse was moved");
			//System.out.println("x:" + posx + " y:" + posy);
		}
	}
	
	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("The mouse clicked");
			System.out.println("x:" + posx + " y:" + posy + " numb:" + getBombNearby() + " hasbomb:" + hasBomb());
			
			
			try {
				setImageWhenClicked();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
