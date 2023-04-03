package interfaces;

import card_entity.Card;
import card_entity.CardCoord;
import card_list.SubCardList;

public interface BackendHandler {
	public int getSizeColumnN(int column);
	public Card getColumnCard(int column, int indexInColumn);
	public Card getPileCard(int indexPile);
	public SubCardList getSubCardList(CardCoord base);

	public void nextCardInDeck();
	public boolean isEmplyDeckPile();
	public Card getDeckCard();
	public Card getPreviousDeckCard();

	public void operateMove(CardCoord start, CardCoord destination);
	public int getSelectedNum(CardCoord start);

	public boolean hasWon();

}
