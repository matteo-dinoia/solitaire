package card_list;

import card_entity.Card;

public class CardListColumn extends CardList{

	//TOP CARD  -------------------------------------------
	@Override public void removeTopCard() {
		if(size()>0) list.remove(list.size()-1);

	}
	@Override public Card getTopCard() {
		if(size()>0) return list.get(list.size()-1);
		else return null;
	}


	//MOVE AND STACK  -------------------------------------
	@Override public boolean moveToColumn(CardListColumn destination, int numSelected) {
		//Check if stackable
		int baseInt=size()-numSelected;
		if(!destination.canStack(get(baseInt))|| numSelected<1) return false;

		//Move truly
		for(int i=0; i<numSelected; i++) {
			destination.add(this.get(baseInt));
			this.remove(baseInt);
		}

		//set visibility
		setVisibleTopCard();
		return true;
	}
	@Override public boolean moveToPile(CardListPile destination) {
		if(move(destination)){
			setVisibleTopCard();
			return true;
		}

		return false;
	}

	@Override public boolean canStack(Card baseOfTop) {
		return Card.isCardStackableOnTop(getTopCard(), baseOfTop);
	}
	private void setVisibleTopCard() {
		Card top=getTopCard();
		if(top!=null) top.setHidden(false);
	}
}
