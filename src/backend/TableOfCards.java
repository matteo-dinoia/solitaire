package backend;

import card_list.*;
import card_entity.Card;
import card_entity.CardCoord;
import interfaces.BackendHandler;

public class TableOfCards implements BackendHandler{
	public CardListColumn[] columns=new CardListColumn[App.NUM_COLS_TABLE];
	public CardListPile[] topPiles=new CardListPile[App.NUM_SLOT_PILE];
	public CardListDeck deck =new CardListDeck();

	public TableOfCards() {
		initializeArrays(); //For avoiding null-pointer
		distributeCards();
	}
	public void initializeArrays() {
		//Array columns
		for(int i=0; i<columns.length; i++) {
			columns[i]=new CardListColumn();
		}
		for(int i=0; i<topPiles.length; i++) {
			topPiles[i]=new CardListPile();
		}
	}
	public void distributeCards() {
		for(int col=0; col<7; col++) {
			for(int row=0; row<=col; row++) {
				deck.giveCardToColumns(columns[col], col!=row);
			}
		}
		deck.turnDeckCard();
	}

	@Override public void operateMove(CardCoord startC, CardCoord destinationC) {
		//NEW CARD
		if(startC.getRow()==CardCoord.DECK_ROW && startC.getCol()==0) {
			if(startC.isEqual(destinationC)) {
				nextCardInDeck();
				return;
			}
		}
		else { //MOVEMENTS

			CardList start=getCardList(startC);
			CardList destination=getCardList(destinationC);
			int numSelected=getSelectedNum(startC);

			if(start == destination){
				for(int i=0; i < App.NUM_SLOT_PILE; i++){
					if(start.moveTo(topPiles[i])) break;
				}
			}else if(destination != null){
				start.moveTo(destination, numSelected);
			}
		}
	}


	@Override public int getSelectedNum(CardCoord start) {
		if(start.isDeck()) return 0;
		else if(start.isPiles() && topPiles[start.getCol()-3].isEmpty()) return 0;
		else if(!start.isColumn()) return 1;

		return getSizeColumnN(start.getCol()) - start.getRow();

	}
	public CardList getCardList(CardCoord coord) {
		if(coord.isDiscardedPile()) return deck;
		else if(coord.isPiles()) return topPiles[coord.getCol()-3];
		else if(coord.isColumn()) return columns[coord.getCol()];
		else return null;
	}


	@Override public int getSizeColumnN(int col) { //TODO error handling
		return columns[col].getSizeTemp();
	}

	@Override public Card getColumnCard(int column, int indexInColumn) {
		Card ris=null;

		try {
			ris=columns[column].getTemp(indexInColumn);
		}catch(IndexOutOfBoundsException er){}

		return ris;
	}

	@Override public Card getPileCard(int indexPile) {
		Card ris=null;

		try {
			ris=topPiles[indexPile].getTopTemp();
		}catch(IndexOutOfBoundsException er){}

		return ris;
	}

	@Override public Card getDeckCard() {
		return deck.getTopCard();
	}

	@Override public void nextCardInDeck() {
		deck.turnDeckCard();
	}

	@Override public boolean isEmplyDeckPile() {
		return deck.isEmptyDeckPile();
	}

	@Override public boolean hasWon(){
		for(int i=0; i<App.NUM_COLS_TABLE; i++){
			if(!columns[i].isEmpty()) return false;
		}

		return deck.isEmpty();
	}
}
