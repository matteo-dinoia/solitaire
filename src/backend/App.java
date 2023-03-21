package backend;

import frontend.FrameGame;

/**Class just to start the game*/
public class App {
	public static final int NUM_COLS_TABLE=7,
			NUM_SLOT_PILE=4;
	public static final int WIDTH=100,
			HEIGHT=(int)(1.6*WIDTH),
			PARTIAL_HEIGHT=(int)(0.2*HEIGHT),
			SPACE_BETWEEN=10,
			LINE_THICKENESS = 4,
			ROUNDED_CORNER_RADIUS= 20;


	public static void main(String args[]) {
		new FrameGame();
	}
}
