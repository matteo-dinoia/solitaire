package obj;

public class CardListPile extends CardList{

	//TOP CARD  --------------------------------------
	@Override public void removeTopCard() {
		if(size()>0) list.remove(list.size()-1);
		
	}
	@Override public Card getTopCard() {
		if(size()>0) return list.get(list.size()-1);
		else return null;
	}

	
	//MOVE AND STACK  --------------------------------
	@Override protected void moveToColumn(CardListColumn destination, int numSelected) {
		move(destination);
	}
	@Override protected void moveToPile(CardListPile destination) {
		move(destination);
	}

	@Override public boolean canStack(Card baseOfTop) {
		return Card.isCardNextInPiles(getTopCard(), baseOfTop);
	}


	//-------------------------------------------------

}
