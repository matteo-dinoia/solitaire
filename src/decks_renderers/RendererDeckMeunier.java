package decks_renderers;

import card_entity.Card;

public class RendererDeckMeunier extends RendererDeck{


	public RendererDeckMeunier() {
		super("victor_meunier/PNG/white-smaller/");
	}

	@Override protected String getBackName() {
		return "../black/back.png";
	}

	@Override protected String getFrontName(Card card) {
		String name = "";

		switch(card.getCardSuit()){
			case DIAMONDS:
				name += "Tiles_";
				break;
			case CLUBS:
				name += "Clovers_";
				break;
			case HEARTS:
				name += "Hearts_";
				break;
			case SPADES:
				name += "Pikes_";
				break;
		}

		switch(card.getCardValue()){
			case ONE:
				name += "A";
				break;
			case JACK:
				name += "Jack";
				break;
			case QUEEN:
				name += "Queen";
				break;
			case KING:
				name += "King";
				break;
			default:
				name += card.getCardValue().getIntValue();
				break;
		}

		return name + "_white.png";
	}
}