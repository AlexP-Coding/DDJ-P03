package util;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import client.ClientView;

public class MineButton extends JButton{
	private boolean bomb;
	private boolean flagged;
	private boolean cleared;
	private int bombNearby;
	private BufferedImage image;
	
	public MineButton(int x, int y) throws IOException{
		super();
		this.bomb = false;
		flagged = false;
		cleared = false;
		bombNearby =0;
		
		this.setGridStyleDefault();
		
//		Dimension minSize = new Dimension(200, 500);
//		Dimension prefSize = new Dimension(200, 500);
//		Dimension maxSize = new Dimension(200, 500);
//		this.add(new Box.Filler(minSize, prefSize, maxSize));
	}
	
//	public void setGridStyleDefault() throws IOException {
//		
//		BufferedImage facingDown = ImageIO.read(new File("assets/TileUnknownBlueFish2.png"));
//		image = ClientView.resizeImage(facingDown, 30, 30);
//	}
	
	public void setGridStyleDefault() {
		ImageIcon img = new ImageIcon("assets/TileUnknownBlueFish2.png");
		this.setIcon(img);
	}
	

	public boolean hasBomb() {
		return bomb;
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


	
}
