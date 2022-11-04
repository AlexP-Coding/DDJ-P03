package util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	private Image img;

	/**
	 * Constructor
	 * @param img
	 */
	public ImagePanel(Image img) {
		this.img = img;
		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void setImg( Image img ){
		this.img = img;
		this.repaint();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(img, 0, 0, null);
	}
}
