package util;

public class CommandConstants {

	enum Color {
        BLUE,
        RED,
        YELLOW,
        GREEN;
    }
	
	enum Command {
		NEW_PLAYER,
		START,
		CLEAR,
		FLAG,
		BOMB,
		MAXBOMBS;
	}
	
	enum Result {
		OK,
		NOK;
	}
	
	// Command types (format bellow)
	//public static String CMD_ = "";
	public static String CMD_NEWPLAYER = "NEW_PLAYER";
	public static String CMD_START = "START";
	public static String CMD_CLEAR = "CLEAR";
	public static String CMD_FLAG = "FLAG";
	public static String CMD_BOMB = "BOMB";
	public static String CMD_BOMBMAX = "MAXBOMBS";
	
	// Command responses/results
	public static String RES_OK = "OK";
	public static String RES_NOK = "NOK";
	
	public static String SPLITTER = " ";
}
