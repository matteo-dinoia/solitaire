package obj;

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
	@Override protected void moveToColumn(CardListColumn destination, int numSelected) {
		//Check if stackable
		int baseInt=size()-numSelected;
		if(!destination.canStack(get(baseInt))|| numSelected<1)return; 
		
		//Move truly
		for(int i=0; i<numSelected; i++) {
			destination.add(this.get(baseInt));
			this.remove(baseInt);
		}
		
		
		//set visibility
		setVisibleTopCard();
	}
	@Override protected void moveToPile(CardListPile destination) {
		move(destination);
		
		setVisibleTopCard();
	}
	
	@Override public boolean canStack(Card baseOfTop) {
		return Card.isCardStackableOnTop(getTopCard(), baseOfTop);
	}
	private void setVisibleTopCard() {
		Card top=getTopCard();
		if(top!=null) top.setHidden(false);
	}

	
	//-----------------------------------------------------
	

	
}
