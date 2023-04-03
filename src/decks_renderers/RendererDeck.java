package decks_renderers;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import backend.Settings;
import card_entity.Card;
import card_entity.CardSuit;
import card_entity.CardValue;

public abstract class RendererDeck {
	protected String basePath;
	protected HashMap<Card, Image> imagesFront = new HashMap<>();
	protected Image imageBack = null;

	private static RendererDeck renderer = new RendererDeckDanimaccari();// = new RendererDeckMeunier();
	public static RendererDeck get(){
		return renderer;
	}

	protected abstract String getBackName();
	protected abstract String getFrontName(Card card);

	//IMAGE
	public RendererDeck(String basePath){
		this.basePath = Settings.IMG_PATH + basePath;
	}

	public final Image getFrontImage(Card card){
		if(!imagesFront.containsKey(card))
			imagesFront.put(card, getImage(card));
		return imagesFront.get(card);
	}

	public final Image getBackImage(){
		if(imageBack == null)
			imageBack = getImage(null);
		return imageBack;
	}

	protected Image getImage(Card card){
		String cardName;
		if(card == null) cardName = getBackName();
		else cardName = getFrontName(card);

		try {
			return ImageIO.read(new File(basePath + cardName));
		} catch (IOException e) {}

		return null;
	}

	protected void preload(){
		for(CardValue value : CardValue.values()){
			for(CardSuit suit : CardSuit.values()){
				Card card = new Card(value, suit);
				imagesFront.put(card, getFrontImage(card));
			}
		}

		imageBack = getBackImage();
	}
}
