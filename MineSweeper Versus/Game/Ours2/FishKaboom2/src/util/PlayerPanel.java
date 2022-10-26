package util;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel{
	private Player list;
	private JLabel label;
	
	public PlayerPanel(String username) {
		super(new BorderLayout());
		label = new JLabel(username);
		add(label,BorderLayout.CENTER);
		
	}
	
	
	
}
