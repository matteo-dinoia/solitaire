package interfaces;

import obj.CardCoord;

public interface MoveListener {
	public void updateGraphics();
	public void makeMove(CardCoord oldCoord, CardCoord coord);
}
