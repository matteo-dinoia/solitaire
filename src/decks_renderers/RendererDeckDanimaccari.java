package decks_renderers;

import card_entity.Card;

public class RendererDeckDanimaccari extends RendererDeck {

	public RendererDeckDanimaccari() {
		super("cute_danimaccari/");
		imageFull = ImgAdvanced.getFromFile(basePath + "CuteCards_outline.png");
		imageFull.addGrid(15, 4);
	}

	@Override protected String getBackName() {return null;}
	@Override protected String getFrontName(Card card) {return null;}

	@Override protected ImgAdvanced getImage(Card card){
		return imageFull.getGridElement(getCol(card) - 1, getRow(card) - 1, 0, 0);
	}

	private int getCol(Card card){
		if(card == null) return 15;
		else return card.getCardValue().getIntValue();
	}

	private int getRow(Card card){
		if(card == null) return 4;

		switch(card.getCardSuit()){
			case CLUBS:
				return 3;
			case DIAMONDS:
				return 2;
			case HEARTS:
				return 4;
			case SPADES:
				return 1;
		}
		return 0;
	}
}
