package obj;

import enumeration.*;

public class Card {

	//CONSTRUCTOR  --------------------------------

	public Card(CardValue cardNumber, CardSuit cardSuit) {
		this.cardValue = cardNumber;
		this.cardSuit = cardSuit;
	}


	//GETTER AND SETTER  --------------------------
	private CardValue cardValue;
	public CardValue getCardValue() {
		return cardValue;
	}

	private CardSuit cardSuit;
	public CardSuit getCardSuit() {
		return cardSuit;
	}
	public CardColor getCardColor() {
		return cardSuit.getColor();
	}

	private boolean hidden;
	public boolean isHidden() {
		return hidden;
	}
	public void setHidden(boolean hidden) {
		this.hidden= hidden;
	}

	//ABBREVIATION  -------------------------------
	public String getAbbreviation() {
		return getCardValue().getAbbreviation() + " " + getCardSuit().getAbbreviation();
	}


	//UTILITY METHODS  ----------------------------
	public static boolean isCardStackableOnTop(Card bottom, Card top) {
		if(top==null) return false;

		//IF nothing -> placable
		if(bottom==null) return true;


		//ELSE different color and under number
		return !bottom.isSameColor(top) && top.isCardNextNumber(bottom);
	}
	public static boolean isCardNextInPiles(Card bottom, Card top) {
		if(top==null) return false;

		//IF empty slot i can put it if ONE
		if(bottom==null) return top.getCardValue()==CardValue.ONE;

		//ELSE same suit and next number
		return bottom.isSameSuit(top) && bottom.isCardNextNumber(top);
	}

	public boolean isSameColor(Card card) {
		return card.getCardColor() == this.getCardColor();
	}
	public boolean isSameSuit(Card card) {
		return card.getCardSuit() == this.getCardSuit();
	}
	public boolean isSameNumber(Card card) {
		return card.getCardValue() == this.getCardValue();
	}
	public boolean isCardNextNumber(Card card) {
		return (this.getCardValue().getIntValue() + 1 == card.getCardValue().getIntValue());
	}
}
