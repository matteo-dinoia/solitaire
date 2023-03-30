package card_list;

import java.util.ArrayList;

import card_entity.Card;
import card_entity.CardCoord;

public abstract class CardList {

	protected ArrayList<Card> list=new ArrayList<Card>();

	//WRAPPERS AND TOP CARD ------------------------
	public void remove(int index) {
		list.remove(index);
	}
	public void add(Card card) {
		list.add(card);
	}
	public Card get(int index) {
		if(index<0 || index>=size()) return null;
		return list.get(index);
	}
	public int size() {
		return list.size();
	}

	public abstract Card getTopCard();
	public abstract void removeTopCard();


	//MOVING AND STACK  -----------------------------
	public abstract boolean moveToColumn(CardListColumn destination, int numSelected);
	public abstract boolean moveToPile(CardListPile destination);
	public boolean moveTo(CardList destination, CardCoord destinationC,int numSelected) {
		if(destinationC.isColumn()) return this.moveToColumn((CardListColumn) destination, numSelected);
		else if(destinationC.isPiles()) return this.moveToPile((CardListPile)destination);
		else return false;
	}

	public abstract boolean canStack(Card baseOfTop);
	protected boolean move(CardList destination) {
		if(destination.canStack(getTopCard())) {
			destination.add(getTopCard());
			this.removeTopCard();
			return true;
		}

		return false;
	}
}
