package util;

public class GameConstants {

	// BOARD CONSTANTS
	public static final int CELL_HEIGHT = 40;
	public static final int CELL_WIDTH = 40;
	public static final int NR_ROWS = 15;
	public static final int NR_COLS = 15;
	
	public static final int NR_BOMBS = 50;
	
	public static final int BOMB_PENALTY = -5;
	public static final int FISH_BONUS = 100;
	public static final int SCORE_NONE = -30;
	
	// COMMUNICATION CONSTANTS
	public static final int PORT_DEFAULT = 5000;
	public static final int SOCKET_SENDER = 3;
	public static final int SOCKET_RECEIVER = 5;
	public static final int SOCKET_MULTITASKER = 5;
	public static final String SERVER_ID = "SERVER";
	
	
	
	// COMMAND CONSTANTS
		
	enum Color {
        BLUE,
        RED,
        YELLOW,
        GREEN;
    }
}
