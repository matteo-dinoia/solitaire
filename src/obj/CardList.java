package obj;

import java.util.ArrayList;

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
	protected abstract void moveToColumn(CardListColumn destination, int numSelected);
	protected abstract void moveToPile(CardListPile destination);
	public void moveTo(CardList destination, CardCoord destinationC,int numSelected) {
		if(destinationC.isColumn()) this.moveToColumn((CardListColumn) destination, numSelected);
		else if(destinationC.isPiles()) this.moveToPile((CardListPile)destination);
	}
	
	public abstract boolean canStack(Card baseOfTop);
	protected void move(CardList destination) {
		if(destination.canStack(getTopCard())) {
			destination.add(getTopCard());
			this.removeTopCard();
		}
	}

	
	//-----------------------------------------------
}
