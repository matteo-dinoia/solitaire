package card_list;

import java.util.ArrayList;

import card_entity.Card;

public abstract class CardList {
	private final static int MAX_MOVE = 13;

	protected ArrayList<Card> list=new ArrayList<Card>();
	private boolean canMoveOnlyOne;

	public CardList(boolean canMoveOnlyOne){ this.canMoveOnlyOne=canMoveOnlyOne; }

	private int maxSend(){ return Math.min(list.size(), maxReceive()); }
	private int maxReceive(){ return canMoveOnlyOne ? 1 : MAX_MOVE; }

	public boolean moveTo(CardList dest){ return moveTo(dest, 1); }
	public boolean moveTo(CardList dest, int numElem){
		if(numElem > this.maxSend() || numElem > dest.maxReceive() || numElem <= 0) return false;


		int bottomCard = list.size() - numElem;
		if(!dest.canStack(list.get(bottomCard))) return false;

		//Move truly
		for(int i=0; i<numElem; i++) {
			dest.list.add(this.list.get(bottomCard));
			this.list.remove(bottomCard);
		}

		//Change visibility of new top card
		if(bottomCard - 1 >= 0)
			this.list.get(bottomCard - 1).setHidden(false);

		return true;
	}

	protected abstract boolean canStack(Card toStack);

	public boolean isEmpty(){
		return list.isEmpty();
	}

	//TEMP
	public Card getTemp(int i){
		return list.get(i);
	}
	public Card getTopTemp(){
		return list.isEmpty() ? null : list.get(list.size() - 1);
	}
	public int getSizeTemp(){
		return list.size();
	}
}
