package util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import client.ClientView;

public class MineButton extends JButton{
	
	public enum Location {
		TOP,
		BOTTOM,
		LEFT,
		RIGHT;
	}
	
	public int posx, posy;
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
				if (handler != null) {
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
		img = new ImageIcon("assets/TileUnknownBlueFish3.png");
		this.setIcon(img);
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
			if(isFlagged())
				setImageFlagged();

			else if(hasBomb())
				setGridImage("assets/Tilemine.png");
			
			else {
				setImageByNumbersOfBomb();
			}
			
	}
	
	public void setImageFlagged() throws IOException {
		setGridImage("assets/TileFlag.png");
	}
	
	public void setImageByNumbersOfBomb() throws IOException {
		int nrBombsNearby = getBombNearby();
		
		if(nrBombsNearby == 0) 
			setGridImage("assets/TileEmpty.png");
		
		else if (1 <= nrBombsNearby && nrBombsNearby <= 8)
			setGridImage("assets/Tile" + nrBombsNearby + ".png");
	}
	
	public boolean noBombsAdjacent(int x, int y, Location relativeX, Location relativeY) {
		List<Integer> adjacent = getAdjacentAt(x, y, relativeX, relativeY);
		int checkX = adjacent.get(0);
		int checkY = adjacent.get(1);
		
		if (spotExists(checkX, checkY)) {
			if(Board.grid[checkX][checkY].hasBomb() == true || Board.grid[checkX][checkY].isCleared() ==true)
				return false;
			else
				return true;
		}
		
		return false;
	}
	
	public boolean hasBombAdjacent(int x, int y, Location relativeX, Location relativeY) {
		List<Integer> adjacent = getAdjacentAt(x, y, relativeX, relativeY);
		int checkX = adjacent.get(0);
		int checkY = adjacent.get(1);
		
		if (spotExists(checkX, checkY))
			return Board.grid[checkX][checkY].hasBomb();
		
		return false;
	}
	
	public boolean spotExists(int checkX, int checkY) {
		return (checkX >= 0) && (checkX < ClientView.X) &&
				(checkY >= 0) && (checkY < ClientView.Y);
	}
	
	public List<Integer> getAdjacentAt(int x, int y, Location relativeX, Location relativeY) {
		List<Integer> adjacent = new ArrayList<Integer>();
		int checkX = -1, checkY = -1;
		
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
		
		adjacent.add(checkX);
		adjacent.add(checkY);
		
		return adjacent;
	}
	
	
	public List<MineButton> listofAdjacentsWithoutBomb() {
		int px = this.getPosx();
		int py = this.getPosy();
		
		if(noBombsAdjacent(px,py,null, Location.LEFT)) {
			adjacentes.add(Board.getMineButton(px, py-1));
		}
		
		if(noBombsAdjacent(px,py,null, Location.RIGHT)) {
			adjacentes.add(Board.getMineButton(px, py+1));
		}
		
		if(noBombsAdjacent(px,py,Location.TOP, null)) {
			adjacentes.add(Board.getMineButton(px-1,py));
		}
		
		if(noBombsAdjacent(px,py,Location.BOTTOM, null)) {
			adjacentes.add(Board.getMineButton(px+1, py));
		}
		
		if(noBombsAdjacent(px,py,Location.TOP, Location.LEFT)) {
			adjacentes.add(Board.getMineButton(px-1,py-1));
		}
		
		if(noBombsAdjacent(px,py,Location.BOTTOM, Location.LEFT)) {
			adjacentes.add(Board.getMineButton(px+1,py-1));
		}
		
		if(noBombsAdjacent(px,py,Location.TOP, Location.RIGHT)) {
			adjacentes.add(Board.getMineButton(px-1,py+1));
		}
		
		if(noBombsAdjacent(px,py,Location.BOTTOM, Location.RIGHT)) {
			adjacentes.add(Board.getMineButton(px+1,py+1));
		}
		
		return adjacentes;

	}

	
}
