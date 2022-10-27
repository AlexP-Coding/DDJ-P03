package util;

public class Player {
	
	enum Color {
        BLUE,
        RED,
        YELLOW,
        GREEN;
    }
	
	Color colorType;
	int colorId;
	String id;
	int score;
	int minesFound;
	int i = 0;
	
	public Player(String id) {
		this.colorId = -1;
		this.id = id;
		this.score = 0;
		this.minesFound = 0;
	}
	
	public Player() {}

	public int getColorId() {
		return this.colorId;
	}

	public void setColorId(int colorId) {
		this.colorId = colorId;
		this.colorType = Color.values()[colorId];
	}


	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public int updateScore(int value) {
		int newScore = this.score + value; // value may be negative
		this.score = Math.max(0, newScore); // player score may not be negative
		return this.score;
	}
	
	public int getMinesFound() {
		return this.minesFound;
	}
	
	public void setMinesFound(int minesFoundUpdate) {
		this.minesFound = minesFoundUpdate;
	}
}