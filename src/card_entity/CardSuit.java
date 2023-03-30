package card_entity;

import java.awt.Color;

public enum CardSuit {

	//VALUE  --------------------------------
	HEARTS(Color.RED, "♥"), CLUBS(Color.black, "♧"),
	SPADES(Color.black, "♤"), DIAMONDS(Color.RED, "♢");


	//CONSTRUCTOR  ---------------------------
	private CardSuit(final Color cardColor, final String abbreviation) {
		this.cardColor=cardColor;
		this.abbreviation=abbreviation;
	}

	//GETTER AND SETTER  ---------------------
	private Color cardColor;
	public Color getColor() { return cardColor; }

	private String abbreviation;
	public String getAbbreviation() { return abbreviation; }

}