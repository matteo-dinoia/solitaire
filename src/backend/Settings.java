package backend;

public class Settings {
	public static final int NUM_COLS_TABLE=7,
		NUM_SLOT_PILE=4;
	public static final int WIDTH=100,
		HEIGHT=(int)(1.6*WIDTH),
		PARTIAL_HEIGHT=(int)(0.2*HEIGHT),
		SPACE_BETWEEN=10,
		LINE_THICKENESS = 4,
		CORNER_RADIUS= 20;

	//Directory
	public static final String IMG_PATH = "./../img/";

	//User related
	public static boolean onlyKing = false;
	public static boolean aceInsetad = true;
	public static String style = null;
}
