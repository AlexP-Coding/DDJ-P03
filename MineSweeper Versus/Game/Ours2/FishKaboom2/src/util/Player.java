package util;

public class Player {
	
	int code;
	String id;
	int score;
	
	public Player(String id) {
		this.code = -1;
		this.id = id;
		this.score = 0;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	
}
