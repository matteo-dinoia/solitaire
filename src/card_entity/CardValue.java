package card_entity;

import backend.App;

public enum CardValue {

	//VALUE  --------------------------------
	ONE(1, App.ACE_INSTEAD_OF_1 ? "A" : "1"),
	TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"),
	SIX(6, "6"), SEVEN(7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"),
	JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");

	//CONSTRUCTOR  ---------------------------
	private CardValue(final int number, final String abbreviation) {
		this.value=number;
		this.abbreviation=abbreviation;
	}

	//GETTER AND SETTER  ---------------------
	private int value;
	public int getIntValue() { return value; }

	private String abbreviation;
	public String getAbbreviation() { return abbreviation; }

	//UTILS
	private CardValue getByInt(int val){
		switch(val){
			case 1: return ONE;
			case 2: return TWO;
			case 3: return THREE;
			case 4: return FOUR;
			case 5: return FIVE;
			case 6: return SIX;
			case 7: return SEVEN;
			case 8: return EIGHT;
			case 9: return NINE;
			case 10: return TEN;
			case 11: return JACK;
			case 12: return QUEEN;
			case 13: return KING;
		}
		return null;
	}
	public CardValue getPrevCardValue(){
		return getByInt(this.getIntValue() - 1);
	}
}
