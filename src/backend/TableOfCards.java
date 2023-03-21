package backend;

import frontend.FrameGame;
import interfaces.BackendHandler;
import obj.*;


public class TableOfCards implements BackendHandler{

	//VARIABLE AND CONSTANT  -----------------------------------
	public static final int NUM_COLS_TABLE=7;
	public static final int NUM_SLOT_PILE=4;

	private FrameGame frame;
	public CardListColumn[] columns=new CardListColumn[NUM_COLS_TABLE];
	public CardListPile[] topPiles=new CardListPile[NUM_SLOT_PILE];
	public CardListDeck deck =new CardListDeck();

	//CONSTRUCTOR AND GAME SETUP  ------------------------------
	public TableOfCards(FrameGame frame) {
		this.frame = frame;

		//For avoiding null-pointer
		initializeArrays();

		//Give card to deck and columns
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
		//fill the deck with all the cards
		deck=CardListDeck.getFullDeckShuffled();
		for(int col=0; col<7; col++) {
			for(int row=0; row<=col; row++) {
				Card card=deck.get(0);
				deck.remove(0);

				//visible only if top
				if(col!=row)card.setHidden(true);

				columns[col].add(card);
			}
		}
	}

	//MOVEMENT GESTION  ---------------------------------------
	private int DECK_ROW=CardCoord.DECK_ROW;

	@Override public void operateMoves(CardCoord startC, CardCoord destinationC) {
		//NEW CARD
		if(startC.getRow()==DECK_ROW && startC.getCol()==0) {
			if(startC.isEqual(destinationC)) {
				nextCardInDeck();
				return;
			}
		}
		else { //MOVEMENTS

			CardList start=getCardList(startC);
			CardList destination=getCardList(destinationC);
			int numSelected=getSelectedNum(startC);

			if(start==destination){
				start.moveToPile(topPiles[0]);
				start.moveToPile(topPiles[1]);
				start.moveToPile(topPiles[2]);
				start.moveToPile(topPiles[3]);
			}else{
				start.moveTo(destination, destinationC, numSelected);
			}
		}
	}


	@Override public int getSelectedNum(CardCoord start) {
		if(start.isDeck()) return 0;
		else if(start.isPiles() && topPiles[start.getCol()-3].size()==0) return 0;
		else if(!start.isColumn()) return 1;

		return getSizeColumnN(start.getCol()) - start.getRow();

	}
	public CardList getCardList(CardCoord coord) {
		if(coord.isDiscardedPile()) return deck;
		else if(coord.isPiles()) return topPiles[coord.getCol()-3];
		else if(coord.isColumn()) return columns[coord.getCol()];
		else return null;
	}


	//FRONTEND HANDLER  ---------------------------------------
	@Override
	public int getSizeColumnN(int col) { //TODO error handling
		return columns[col].size();
	}
	@Override
	public Card getColumnCard(int column, int indexInColumn) {
		Card ris=null;

		try {
			ris=columns[column].get(indexInColumn);
		}catch(IndexOutOfBoundsException er){}

		return ris;
	}
	@Override
	public Card getPileCard(int indexPile) {
		Card ris=null;

		try {
			ris=topPiles[indexPile].getTopCard();
		}catch(IndexOutOfBoundsException er){}

		return ris;
	}
	@Override
	public Card getDeckCard() {
		return deck.getTopCard();
	}

	@Override
	public void nextCardInDeck() {
		deck.nextDeckCard();
	}
	@Override
	public boolean isEmplyDeckPile() {
		return deck.isEmptyDeckPile();
	}

	//WINNING CHECK AND HANDLING  -----------------------------
	@Override public void checkWon(){
		if(hasWon())
			frame.showWonPopup();
	}

	private boolean hasWon(){
		for(int i=0; i<NUM_COLS_TABLE; i++){
			if(columns[i].size()!=0) return false;
		}

		if(deck.size()!=0) return false;

		return true;
	}


	//---------------------------------------------------------


}
