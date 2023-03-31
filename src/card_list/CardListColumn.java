package card_list;

import card_entity.Card;

public class CardListColumn extends CardList {

	public CardListColumn() { super(false); }

	@Override protected boolean canStack(Card toStack) {
		if(list.isEmpty()) return true;

		Card topCard = list.get(list.size() - 1);
		return toStack.isCardPreviousOf(topCard) && !toStack.isSameColor(topCard);
	}


}
