package enumeration;

public enum CardSuit {

	//VALUE  --------------------------------
	HEARTS(CardColor.RED, "♥"), CLUBS(CardColor.BLACK, "♧"),
	SPADES(CardColor.BLACK, "♤"), DIAMONDS(CardColor.RED, "♢");
	
	//CONSTRUCTOR  ---------------------------
	private CardSuit(final CardColor cardColor, final String abbreviation) {
		this.cardColor=cardColor;
		this.abbreviation=abbreviation;
	}
	
	//GETTER AND SETTER  ---------------------
	private CardColor cardColor;
	public CardColor getColor() {
		return cardColor;
	}
	
	private String abbreviation;
	public String getAbbreviation() {
		return abbreviation;
	}
	//----------------------------------------
}