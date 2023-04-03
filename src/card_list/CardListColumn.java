package card_list;

import backend.Settings;
import card_entity.Card;
import card_entity.CardValue;

public class CardListColumn extends CardList {

	public CardListColumn() { super(false); }

	@Override protected boolean canStack(Card toStack) {
		if(toStack.isHidden()) return false;
		if(list.isEmpty()) return !Settings.onlyKing || toStack.getCardValue() == CardValue.KING;

		Card topCard = list.get(list.size() - 1);
		return toStack.isCardPreviousOf(topCard) && !toStack.isSameColor(topCard) && !toStack.isHidden();
	}


}
