package card_entity;

public class CardCoord {
	public static final int NOT_VALID_POINT=-2;
	public static final int DECK_ROW=-1;

	public static CardCoord getCardCoord(int col, int row) {
		if(col <= NOT_VALID_POINT || row<=NOT_VALID_POINT //Out of limits
				|| (row == DECK_ROW && col == 2)) { //empty hole in UI
			return null;
		}

		return new CardCoord(col, row);
	}

	private CardCoord(int col, int row){
		this.col=col;
		this.row=row;
	}

	//GETTER AND SETTER  -------------------------------------------
	private int col;
	public int getCol() { return col; }

	private int row;
	public int getRow() { return row; }


	//OTHER  -----------------------------------------------------
	public boolean isEqual(CardCoord coord){
		return (this.getCol()==coord.getCol()) && (this.getRow()==coord.getRow());
	}

	public boolean isDeck() {
		return (col==0 && row==DECK_ROW);
	}

	public boolean isDiscardedPile() {
		return (col==1 && row==DECK_ROW);
	}

	public boolean isPiles() {
		return (col>=3 && col<7 && row==DECK_ROW);
	}

	public boolean isColumn() {
		return (row>DECK_ROW);
	}
}
