package card_entity;

public enum CardValue {

	//VALUE  --------------------------------
	ONE(1, "1"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"),
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
}
