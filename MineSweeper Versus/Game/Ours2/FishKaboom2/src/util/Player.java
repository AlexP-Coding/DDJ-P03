package util;

public class Player {
	private String name;
	private int minesFound;
	int i=0;
	
	public Player (String username) {
		name = username;
		minesFound = 0;
		
	}
	
	public String getUsername() {
		return name;
	}
	
	public void setUsername(String username) {
		name = username;
	}
	
	public int getMinesFound() {
		return minesFound;
	}
	
	public void setMinesFound(int minesFoundUpdate) {
		minesFound = minesFoundUpdate;
	}
	
	
}
