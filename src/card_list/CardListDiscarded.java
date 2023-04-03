package card_list;

import java.util.Collections;
import card_entity.*;

public class CardListDiscarded extends CardList {
	private CardListDeck deck=new CardListDeck();

	public CardListDiscarded() {
		super(true);
		addFullShuffledDeck();
	}

	@Override protected boolean canStack(Card toStack) {
		return false;
	}

	public void addFullShuffledDeck() {
		for(CardValue value : CardValue.values()){
			for(CardSuit suit : CardSuit.values()){
				deck.list.add(new Card(value, suit));
			}
		}

		Collections.shuffle(deck.list);
	}

	public void giveCardToColumns(CardListColumn cardListColumn, boolean isHidden) {
		if(this.deck.isEmpty()) return;
		Card card = deck.list.get(deck.list.size() - 1);

		card.setHidden(isHidden);
		cardListColumn.list.add(card);
		deck.list.remove(deck.list.size() - 1);
	}

	public Card getTopCard(){
		return list.isEmpty() ? null : list.get(list.size()-1);
	}

	public Card getPrevCard(){
		return list.size() < 2 ? null : list.get(list.size() - 2);
	}

	public void turnDeckCard(){
		if(!deck.isEmpty()){
			list.add(deck.list.get(deck.list.size() - 1));
			deck.list.remove(deck.list.size() - 1);
		}else if(!list.isEmpty()){
			for(int i = list.size() - 1; i >= 0; i--){
				deck.list.add(list.get(i));
				list.remove(i);
			}
		}
	}

	public boolean isEmptyDeckPile(){
		return deck.isEmpty();
	}

	public CardListDeck getCardListDeck(){
		return deck;
	}
}
