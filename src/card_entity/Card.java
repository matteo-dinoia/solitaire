package card_entity;

import java.awt.Color;

public class Card {
	public Card(CardValue cardNumber, CardSuit cardSuit) {
		this.cardValue = cardNumber;
		this.cardSuit = cardSuit;
	}

	//GETTER AND SETTER  --------------------------
	private CardValue cardValue;
	public CardValue getCardValue() { return cardValue; }

	private CardSuit cardSuit;
	public CardSuit getCardSuit() { return cardSuit; }
	public Color getCardColor() { return cardSuit.getColor(); }

	private boolean hidden;
	public boolean isHidden() { return hidden; }
	public void setHidden(boolean hidden) { this.hidden= hidden; }

	//ABBREVIATION  -------------------------------
	public String getAbbreviation() {
		return getCardValue().getAbbreviation() + " " + getCardSuit().getAbbreviation();
	}


	//UTILITY METHODS  ----------------------------
	public boolean isSameColor(Card card) {
		return card.getCardColor() == this.getCardColor();
	}
	public boolean isSameSuit(Card card) {
		return card.getCardSuit() == this.getCardSuit();
	}
	public boolean isSameNumber(Card card) {
		return card.getCardValue() == this.getCardValue();
	}
	public boolean isCardPreviousOf(Card card) {
		return (this.getCardValue().getIntValue() + 1 == card.getCardValue().getIntValue());
	}
}
