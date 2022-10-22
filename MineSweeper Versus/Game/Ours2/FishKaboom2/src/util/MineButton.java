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
	
	public MineButton() throws IOException{
		super();
		this.bomb = false;
		flagged = false;
		cleared = false;
		bombNearby =0;
		
		this.setGridStyleDefault();
		
		Dimension minSize = new Dimension(200, 500);
		Dimension prefSize = new Dimension(200, 500);
		Dimension maxSize = new Dimension(200, 500);
		this.add(new Box.Filler(minSize, prefSize, maxSize));
	}
	
//	public void setGridStyleDefault() throws IOException {
//		
//		BufferedImage facingDown = ImageIO.read(new File("assets/TileUnknownBlueFish.png"));
//		System.out.println(facingDown);
//		image = ClientView.resizeImage(facingDown, ClientView.WIDHT, ClientView.HEIGHT);
//	}
	
	public void setGridStyleDefault() {
		ImageIcon img = new ImageIcon("assets/TileUnknownBlueFish.png");
		//image = ClientView.resizeImage(img, ClientView.WIDHT, ClientView.HEIGHT);
		this.setIcon(img);
//		String imagePath = "TileUnknownBlueFish.png";
//		this.setIcon((new ImageIcon(((new ImageIcon(getClass().getClassLoader().getResource(imagePath))).getImage()).getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH))));
//		((ImageIcon) this.getIcon()).setDescription(imagePath);
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

//	public void setImage(String Fileimagename) throws IOException {
//		BufferedImage temp = ImageIO.read(new File(Fileimagename));
//		image = ClientView.resizeImage(temp, ClientView.WIDHT, ClientView.HEIGHT);
//	}


	
}
