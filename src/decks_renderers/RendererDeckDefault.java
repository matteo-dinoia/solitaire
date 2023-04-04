package decks_renderers;

import card_entity.Card;

public class RendererDeckDefault extends RendererDeck {

	public RendererDeckDefault(String fileName) {
		super("converted/");

		imageFull = ImgAdvanced.getFromFile(basePath + fileName);
		imageFull.addGrid(13, 5);

		imageEmpty = imageFull.getGridElement(1, 4, 0, 0);
	}

	@Override protected String getBackName() {return null;}
	@Override protected String getFrontName(Card card) {return null;}

	@Override protected ImgAdvanced getImage(Card card){
		return imageFull.getGridElement(getCol(card), getRow(card), 0, 0);
	}

	private int getCol(Card card){
		if(card == null) return 0;
		else return card.getCardValue().getIntValue() - 1;
	}

	private int getRow(Card card){
		if(card == null) return 4;

		int offset = 0;
		switch(card.getCardSuit()){
			case CLUBS:
				offset = 2;
				break;
			case DIAMONDS:
				offset = 1;
				break;
			case HEARTS:
				offset = 3;
				break;
			case SPADES:
				offset = 0;
				break;
		}
		return offset;
	}
}
