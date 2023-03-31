package card_list;

import java.util.ArrayList;
import java.util.Collections;
import card_entity.*;

public class CardListDeck extends CardList {
	private ArrayList<Card> listHidden=new ArrayList<Card>();

	public CardListDeck() {
		super(true);
		addFullShuffledDeck();
	}

	@Override protected boolean canStack(Card toStack) {
		return false;
	}

	public void addFullShuffledDeck() {
		for(CardValue value : CardValue.values()){
			for(CardSuit suit : CardSuit.values()){
				listHidden.add(new Card(value, suit));
			}
		}

		Collections.shuffle(listHidden);
	}

	public void giveCardToColumns(CardListColumn cardListColumn, boolean isHidden) {
		if(this.listHidden.isEmpty()) return;
		Card card = this.listHidden.get(this.listHidden.size() - 1);

		card.setHidden(isHidden);
		cardListColumn.list.add(card);
		this.listHidden.remove(this.listHidden.size() - 1);
	}

	public Card getTopCard(){
		return list.isEmpty() ? null : list.get(list.size()-1);
	}

	public void turnDeckCard(){
		if(!listHidden.isEmpty()){
			list.add(listHidden.get(listHidden.size() - 1));
			listHidden.remove(listHidden.size() - 1);
		}else if(!list.isEmpty()){
			for(int i = list.size() - 1; i > 0; i--){
				listHidden.add(list.get(i));
				list.remove(i);
			}
		}
	}

	public boolean isEmptyDeckPile(){
		return listHidden.isEmpty();
	}
}
