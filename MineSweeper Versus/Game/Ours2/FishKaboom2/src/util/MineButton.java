package util;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.ClientView;
// import util.Board.Click;
// import util.Board.Move;

public class MineButton extends JButton{
	
	public enum Location {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
	}
	
	public int posx, posy;
	public int mx, my;
	private boolean bomb;
	private boolean flagged;
	private boolean cleared;
	private int bombNearby;
	private BufferedImage image;
	private boolean clicked;
	private Handler handler;
	
	private ArrayList<MineButton> adjacentes = new ArrayList<MineButton>();
	
	public MineButton(int x, int y, Handler h) throws IOException{
		super();
		this.posx=x;
		this.posy=y;
		this.bomb = false;
		flagged = false;
		cleared = false;
		bombNearby =0;
		clicked = false;
		this.handler = h;
		
		this.setGridStyleDefault();
		
		//Move move = new Move();
		this.addMouseListener( new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				try {
					if(SwingUtilities.isRightMouseButton(e)) {
						rightClickButton();
					} else {
						clickButton();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {	
			}
			@Override
			public void mouseExited(MouseEvent e) {}
			
			
		});
	}
	

	
	public void setGridStyleDefault() {
		ImageIcon img;
//		int randomimage = (int)(Math.random() *4);
//		System.out.println("random number: " + randomimage);
//		if (randomimage == 0) {
			img = new ImageIcon("assets/TileUnknownBlueFish3.png");
			this.setIcon(img);
//		}
//		else if (randomimage == 1) {
//			img = new ImageIcon("assets/TileUnkownFishRed3.png");
//			this.setIcon(img);
//		}
//		else if (randomimage == 2) {
//			img = new ImageIcon("assets/TileUnknownGreenFish3.png");
//			this.setIcon(img);
//		}
//		else if (randomimage == 3) {
//			img = new ImageIcon("assets/TileUnknownYellowFish3.png");
//			this.setIcon(img);
//		}
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
	
	public void setCleared(boolean iscleared) {
		cleared=iscleared;
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
	
	public int getPosx() {
		return posx;
	}
	
	public int getPosy() {
		return posy;
	}
	
	public boolean getClicked(){
		return clicked;
	}
	public void setClicked(boolean c) {
		clicked = c;
	}
	
	public void clickButton() throws IOException {
		handler.click(this);
	}
	
	public void rightClickButton() throws IOException {
		handler.rightClick(this);
	}
	
	public void setImageWhenClicked() throws IOException {
		//if(!isCleared()) {
			if(isFlagged())
				setImageFlagged();

			else if(hasBomb())
				setGridImage("assets/Tilemine.png");
			
			else {
				setImageByNumbersOfBomb();
			}
			
		//}
	}
	
	public void setImageFlagged() throws IOException {
		setGridImage("assets/TileFlag.png");
	}
	
	public void setImageByNumbersOfBomb() throws IOException {
		if(getBombNearby() == 0) 
			setGridImage("assets/TileEmpty.png");
	
		else if(getBombNearby() == 1) 
			setGridImage("assets/Tile1.png");
	
		else if(getBombNearby() == 2) 
			setGridImage("assets/Tile2.png");
		
		else if(getBombNearby() == 3) 
			setGridImage("assets/Tile3.png");
		
		else if(getBombNearby() == 4) 
			setGridImage("assets/Tile4.png");
		
		else if(getBombNearby() == 5) 
			setGridImage("assets/Tile5.png");
		
		else if(getBombNearby() == 6) 
			setGridImage("assets/Tile6.png");
		
		else if(getBombNearby() == 7) 
			setGridImage("assets/Tile7.png");
		
		else if(getBombNearby() == 8) 
			setGridImage("assets/Tile8.png");
	}
	
	public boolean verifyPosition(int x, int y, int positionx, int positiony) {
		System.out.println("VERIFICA");
		int checkX = -1;
		int checkY = -1;
		
		if (positionx == 0)
			checkX = x;
		else if (positionx == x - 1)
			checkX = x-1;
		else if (positionx == x+1)
			checkX = x+1;
		
		if (y == 0)
			checkY = y;
		else if (positiony == y-1 )
			checkY = y-1;
		else if (positiony == y+1)
			checkY = y+1;
		
		System.out.println("X: " + checkX + " Y: " + checkY);
		
		if ((checkX >= 0) && 
			(checkX < ClientView.X) &&
			(checkY >= 0) &&
			(checkY < ClientView.Y) ) {
			System.out.println("EXISTE");
			
			return true;			
		}
		
		return false;
	}
	
	public boolean verify2(int x, int y, Location relativeX, Location relativeY) {
		int checkX = -1;
		int checkY = -1;
		
		if (relativeX == null)
			checkX = x;
		else if (relativeX.equals(Location.TOP) )
			checkX = x-1;
		else if (relativeX.equals(Location.BOTTOM))
			checkX = x+1;
		
		if (relativeY == null)
			checkY = y;
		else if (relativeY.equals(Location.LEFT) )
			checkY = y-1;
		else if (relativeY.equals(Location.RIGHT))
			checkY = y+1;
		
		if ((checkX >= 0) && 
			(checkX < ClientView.X) &&
			(checkY >= 0) &&
			(checkY < ClientView.Y) ) {
			
			
			if(Board.grid[checkX][checkY].hasBomb() == true || Board.grid[checkX][checkY].isCleared() ==true) {
				return false;
			}
			else {
				return true;
			}
					
		}
		
		return false;
	}
	
	public ArrayList listofadjacenteswithoutBomb() {
		int px = this.getPosx();
		int py = this.getPosy();
		
		if(verify2(px,py,null, Location.LEFT)) {
//			System.out.println(Board.getMineButton(px, py-1).isCleared());
			Board.getMineButton(px, py-1).setCleared(true);
			adjacentes.add(Board.getMineButton(px, py-1));
		}
		if(verify2(px,py,null, Location.RIGHT)) {
			adjacentes.add(Board.getMineButton(px, py+1));
			Board.getMineButton(px, py+1).setCleared(true);
		}
		if(verify2(px,py,Location.TOP, null)) {
			adjacentes.add(Board.getMineButton(px-1,py));
			Board.getMineButton(px-1, py).setCleared(true);
		}
		if(verify2(px,py,Location.BOTTOM, null)) {
			adjacentes.add(Board.getMineButton(px+1, py));
			Board.getMineButton(px+1, py).setCleared(true);
		}
		if(verify2(px,py,Location.TOP, Location.LEFT)) {
			adjacentes.add(Board.getMineButton(px-1,py-1));
			Board.getMineButton(px-1,py-1).setCleared(true);
		}
		if(verify2(px,py,Location.BOTTOM, Location.LEFT)) {
			adjacentes.add(Board.getMineButton(px+1,py-1));
			Board.getMineButton(px+1,py-1).setCleared(true);
		}
		if(verify2(px,py,Location.TOP, Location.RIGHT)) {
			adjacentes.add(Board.getMineButton(px-1,py+1));
			Board.getMineButton(px-1,py+1).setCleared(true);
		}
		if(verify2(px,py,Location.BOTTOM, Location.RIGHT)) {
			adjacentes.add(Board.getMineButton(px+1,py+1));
			Board.getMineButton(px+1,py+1).setCleared(true);
		}
		
		System.out.println("ultimo adjacente: " + adjacentes.size());
		return adjacentes;

	}

	
}
