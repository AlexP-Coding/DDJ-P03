package server;

import java.awt.Color;
import java.io.IOException;

import util.Board;

public class GameState {

	public enum Status {
		STANDBY,
		STARTED,
		COUNTDOWN_ON,
		GAMEOVER;
	}
	
	private Status status;
	private Board board;
	
	public GameState() throws IOException {
		this.status = Status.STANDBY;
		
		// Sets up board
		this.board = new Board();
		
		// Makes board invisible
		this.board.setBackground(new Color(0, 0, 0, 0)); // for transparency purposes
		this.board.setOpaque(false);
	}
	

	public Board getBoard() {
		return this.board;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public void startGame() {
		this.setStatus(Status.STARTED);
	}
	
	public void startCountdown() {
		this.setStatus(Status.COUNTDOWN_ON);
	}

	public void endGame() {
		this.setStatus(Status.GAMEOVER);
	}
}
