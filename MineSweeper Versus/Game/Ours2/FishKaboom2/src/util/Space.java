package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import client.ClientView;

public class Space extends JPanel{
	private boolean bomb;
	private boolean flagged;
	private boolean cleared;
	private int bombNearby;
	private BufferedImage image;
	
	public Space(boolean bomb) throws IOException{
		this.bomb = bomb;
		flagged = false;
		cleared = false;
		bombNearby =0;
		
		BufferedImage facingDown = ImageIO.read(new File("assets/TileUnknownBlueFish.png"));
		image = ClientView.resizeImage(facingDown, ClientView.WIDTH, ClientView.HEIGHT);
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

	public void setImage(String Fileimagename) throws IOException {
		BufferedImage temp = ImageIO.read(new File(Fileimagename));
		image = ClientView.resizeImage(temp, ClientView.WIDTH, ClientView.HEIGHT);
	}


	
}
