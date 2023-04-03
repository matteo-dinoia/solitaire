package card_list;

import card_entity.Card;

public class CardListDeck extends CardList{

	protected CardListDeck(){
		super(true);
	}

	@Override
	protected boolean canStack(Card toStack) {
		return false;
	}
}
