package card_list;

import card_entity.Card;

public class SubCardList {
	private CardList list;
	private int base;
	private boolean emptySelection = false;

	public static SubCardList getSubCardList(CardList list, int base){
		if(list == null) return null;

		if(list.isEmpty() ||  list instanceof CardListDeck)
			return new SubCardList(list);

		int bmod = (base + list.getSizeTemp()) % list.getSizeTemp();
		if(list.getTemp(bmod).isHidden()) return null;
		return new SubCardList(list, bmod);
	}

	private SubCardList(CardList list, int base){
		this.list = list;
		this.base = base;
	}

	private SubCardList(CardList list){
		this.list = list;
		emptySelection = true;
	}

	public boolean isEmpty(){
		return size() <= 0;
	}

	public int size(){
		if(emptySelection) return 0;
		return list.getSizeTemp() - base;
	}

	public Card get(int i){
		if(emptySelection) return null;
		return list.getTemp(base + i);
	}

	public void setVisibilityCards(boolean visibility){
		if(emptySelection) return;
		for(int i = 0; i < size(); i++){
			get(i).setVisible(visibility);
		}
	}
}
