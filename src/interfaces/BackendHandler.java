package interfaces;

import obj.*;

public interface BackendHandler {

	//COLUMNS AND PILES  --------------------------
	public int getSizeColumnN(int column);
	public Card getColumnCard(int column, int indexInColumn);
	public Card getPileCard(int indexPile);


	//DECK  ----------------------------------------
	public void nextCardInDeck();
	public boolean isEmplyDeckPile();
	public Card getDeckCard();


	//MOVEMENT  ------------------------------------
	public void operateMove(CardCoord start, CardCoord destination);
	public int getSelectedNum(CardCoord start);

	//OTHER  ---------------------------------------
	public boolean hasWon();

	//----------------------------------------------
}
