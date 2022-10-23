import javax.swing.*;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.*;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GUI extends JFrame {
	
	int spacing = 5;
	
	public int mx = -100;
	public int my = -100;
	
	public GUI() { 
		this.setTitle("Fish-Kaboom!");
		this.setSize(1286,829);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true); //whenever the window is created, is visible to the user
		this.setResizable(false); //it can be used or not
		
		Board board = new Board();
		this.setContentPane(board);
		
		Move move = new Move();
		this.addMouseMotionListener(move);
		
		Click click = new Click();
		this.addMouseListener(click);
	}
	
	public class Board extends JPanel {
		public void paintComponent(Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0,0,1280, 800);
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.gray);
			Image img1 = Toolkit.getDefaultToolkit().getImage("minesweeper battle/src/assets/TileUnknown.png");
			for(int i=0; i<16; i++) {
				for(int j=0; j<9; j++) {
					g.fillRect(spacing+i*80,spacing+j*80+80,80-2*spacing, 80-2*spacing);
					g2.drawImage(img1, spacing+i*80, spacing+j*80+80, this);
				}
			}
		}
	}
	
	public class Move implements MouseMotionListener  {

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println("The mouse was moved!");
			mx = e.getX();
			my = e.getY();
			System.out.println("");
		}
		
	}
	
	public class Click implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			System.out.println("The mouse was clicked!");
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
