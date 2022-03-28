package obj;

import java.util.Collections;

import enumeration.CardSuit;
import enumeration.CardValue;

public class CardListDeck extends CardList{

	//TOP CARD  --------------------------------------
	@Override public Card getTopCard() {
		Card ris=null;
		
		try { 
			ris=get(indexCard);
		}catch(IndexOutOfBoundsException er){}
		
		return ris; 
	}
	@Override public void removeTopCard() {
		try { 
			remove(indexCard);
			priviousDeckCard();
		}catch(IndexOutOfBoundsException er){}
		
	}
	
	
	//ALL CARDS  -------------------------------------
 	private static final CardValue[] ALL_VALUE={CardValue.ONE, CardValue.TWO, CardValue.THREE, CardValue.FOUR, 
			CardValue.FIVE, CardValue.SIX, CardValue.SEVEN, CardValue.EIGHT, CardValue.NINE, CardValue.TEN, 
			CardValue.JACK, CardValue.QUEEN, CardValue.KING};
	/** Return a list of all cards */
	public static CardListDeck getFullDeck() {
		CardListDeck ris = new CardListDeck();
		
		for(int i=0; i<ALL_VALUE.length; i++) {
			ris.add(new Card(ALL_VALUE[i], CardSuit.CLUBS));
			ris.add(new Card(ALL_VALUE[i], CardSuit.HEARTS));
			ris.add(new Card(ALL_VALUE[i], CardSuit.DIAMONDS));
			ris.add(new Card(ALL_VALUE[i], CardSuit.SPADES));
		}
		

		return ris;
	}
	/** Return a list of all cards shuffled */
	public static CardListDeck getFullDeckShuffled() {
		CardListDeck ris = getFullDeck();
		
		ris.shuffle();
		return ris;
	}
	private void shuffle() {
		Collections.shuffle(list);
	}
	
	
	//INDEX MANAGEMENT  AND VOID ----------------------
	private int indexCard=0;
	public void nextDeckCard() { 
		indexCard++;
		if(indexCard>=size()) {
			indexCard=-1;
		}
		 
	}
	private void priviousDeckCard() {
		indexCard--;
		if(indexCard<-1) {
			indexCard=size()-1;
		}
		 
	}

	public boolean isEmptyDeckPile() {
		return (size()-1) <= indexCard;
	}

	
	//MOVE AND STACK  ---------------------------------
	@Override protected void moveToColumn(CardListColumn destination, int numSelected) {
		move(destination);
	}
	@Override protected void moveToPile(CardListPile destination) {
		move(destination);
	}
	
	@Override public boolean canStack(Card baseOfTop) {
		return false;
	}

	
	//-------------------------------------------------

	
}
