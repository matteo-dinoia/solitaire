package backend;

public class Settings {
	public static final int NUM_COLS_TABLE = 7,
		NUM_SLOT_PILE = 4;

	//Graphics
	public static double HOW_MUCH_SHOWN = 0.20;
	public static int WIDTH = 130,
		HEIGHT = (int)(1.6 * WIDTH),
		PARTIAL_HEIGHT = (int)(HOW_MUCH_SHOWN * HEIGHT),
		SPACE_BETWEEN = WIDTH / 10;
	public static int WIN_W = WIDTH * NUM_COLS_TABLE + SPACE_BETWEEN * (NUM_COLS_TABLE + 1),
		WIN_H = 12 * PARTIAL_HEIGHT + 2 * HEIGHT + 3 * SPACE_BETWEEN;

	//Directory
	public static final String IMG_PATH = "../img/";

	//User related
	public static boolean onlyKing = false;
	public static String style = null;
}
