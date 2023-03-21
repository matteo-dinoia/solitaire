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
	@Override public boolean moveToColumn(CardListColumn destination, int numSelected) {
		return move(destination);
	}
	@Override public boolean moveToPile(CardListPile destination) {
		return move(destination);
	}

	@Override public boolean canStack(Card baseOfTop) {
		return Card.isCardNextInPiles(getTopCard(), baseOfTop);
	}
}
