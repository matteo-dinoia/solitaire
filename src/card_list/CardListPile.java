package card_list;

import card_entity.Card;
import card_entity.CardValue;

public class CardListPile extends CardList {

	public CardListPile() { super(true); }

	@Override
	protected boolean canStack(Card toStack) {
		if(toStack.isHidden()) return false;

		if(this.list.isEmpty())
			return toStack.getCardValue() == CardValue.ONE;

		Card topCard = list.get(list.size() - 1);
		return topCard.isCardPreviousOf(toStack) && toStack.isSameSuit(topCard);
	}

}
