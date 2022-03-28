package obj;

public class CardCoord {
	
	//CONSTANT  ---------------------------------------------------
	public static final int NOT_VALID_POINT=-2;
	public static final int DECK_ROW=-1;
	
	
	//CONSTRUCTOR  -------------------------------------------------
	public static CardCoord getCardCoord(int col, int row) {
		CardCoord ris=null;
		
		try {
			ris=new CardCoord(col, row);
		}catch(Exception er) {
			ris=null;
		}
		
		return ris;
	}
	private CardCoord(int col, int row) throws Exception {
		//IF not valid -> exception
		if( (col<=NOT_VALID_POINT) ||  (row<=NOT_VALID_POINT)   //Out of limits
				||  (row==DECK_ROW && col==2) ) { //empty hole in UI
			raiseError();
		}
			
		
		//ELSE save point
		this.col=col;
		this.row=row;
	}
	private void raiseError() throws Exception {
		throw new Exception("Class not costructable");
	}
	
	
	//GETTER AND SETTER  -------------------------------------------
	private int col;
	public int getCol() {
		return col;
	}
	
	private int row;
	public int getRow() {
		return row;
	}
	
	
	//ISEQUAL  -----------------------------------------------------
	public boolean isEqual(CardCoord coord){
		return (this.getCol()==coord.getCol()) && (this.getRow()==coord.getRow());
	}
	
	
	//GET INFORMATION ABOUT  --------------------------------------
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
	//--------------------------------------------------------------

}
