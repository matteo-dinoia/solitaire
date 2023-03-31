package interfaces;

import card_entity.Card;
import card_entity.CardCoord;

public interface MoveListener {
	public void updateGraphics();
	public void makeMove(CardCoord oldCoord, CardCoord coord);
	public Card[] getPileSelected(CardCoord coord);
}
