package backend;

import frontend.FrameGame;

/**Class just to start the game*/
public class App {
	
	public static void main(String args[]) {
		new FrameGame(new TableOfCards());
	}
}
