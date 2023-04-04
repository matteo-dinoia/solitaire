package decks_renderers;

import card_entity.Card;

public class RendererDeckYewbi extends RendererDeck {

	public RendererDeckYewbi() {
		super("yewbi/");
		imageFull = ImgAdvanced.getFromFile(basePath + "pixel_set_cropped.png");
		imageFull.addGrid(20, 7);
		imageEmpty = imageFull.getGridElement(2, 4, 0, 0);
	}

	@Override protected String getBackName() {return null;}
	@Override protected String getFrontName(Card card) {return null;}

	@Override protected ImgAdvanced getImage(Card card){
		return imageFull.getGridElement(getCol(card), getRow(card), 0, 0);
	}

	private int getCol(Card card){
		if(card == null) return 3;

		int base = ((card.getCardValue().getIntValue() - 1) / 3) * 4;
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
		return base + offset;
	}

	private int getRow(Card card){
		if(card == null) return 4;
		else return (card.getCardValue().getIntValue() - 1) % 3;
	}
}
