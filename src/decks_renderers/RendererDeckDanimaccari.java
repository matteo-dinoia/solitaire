package decks_renderers;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

import card_entity.Card;

public class RendererDeckDanimaccari extends RendererDeck {
	private Image fullImage = null;

	public RendererDeckDanimaccari() {
		super("cute_danimaccari/splitted/");
		//preload();
	}

	@Override
	protected String getBackName() {
		return "row-4-column-15.png";
	}

	@Override
	protected String getFrontName(Card card) {
		String name = "row-";

		switch(card.getCardSuit()){
			case DIAMONDS:
				name += "2";
				break;
			case CLUBS:
				name += "1";
				break;
			case HEARTS:
				name += "4";
				break;
			case SPADES:
				name += "3";
				break;
		}

		return name + "-column-" + card.getCardValue().getIntValue() + ".png";
	}
/*
	@Override protected Image getImage(String cardName) {
		if(fullImage == null)
			fullImage = ImageIO.read(new File(basePath + ""));
		return super.getImage(cardName);
	}*/
}
