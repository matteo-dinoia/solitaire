package decks_renderers;

import java.awt.Image;
import java.util.HashMap;
import backend.Settings;
import card_entity.*;

public abstract class RendererDeck {
	protected String basePath;
	protected HashMap<Card, ImgAdvanced> imagesFront = new HashMap<>();
	protected ImgAdvanced imageBack = null;
	protected ImgAdvanced imageFull = null;
	protected ImgAdvanced imageEmpty = null;


	private static RendererDeck renderer;
	private static String oldStyle = null;
	public static RendererDeck get(){
		if(oldStyle == Settings.style && renderer != null)
			return renderer;

		switch(Settings.style){
			case "dani":
				renderer = new RendererDeckDanimaccari();
				break;
			case "victor":
				renderer = new RendererDeckMeunier();
				break;
			case "yewbi":
				renderer = new RendererDeckYewbi();
				break;
			default:
				renderer = new RendererDeckDefault();
		}

		return renderer;
	}

	protected abstract String getBackName();
	protected abstract String getFrontName(Card card);

	//IMAGE
	public RendererDeck(String basePath){
		this.basePath = Settings.IMG_PATH + basePath;
	}

	public final Image getEmptyImage() {return imageEmpty == null ? null : imageEmpty.getImage();}

	public final Image getFrontImage(Card card){
		if(!imagesFront.containsKey(card))
			imagesFront.put(card, getImage(card));

		ImgAdvanced img = imagesFront.get(card);
		if(img == null) return null;
		return img.getImage();
	}

	public final Image getBackImage(){
		if(imageBack == null)
			imageBack = getImage(null);
		return imageBack.getImage();
	}

	protected ImgAdvanced getImage(Card card){
		String cardName;
		if(card == null) cardName = getBackName();
		else cardName = getFrontName(card);

		return ImgAdvanced.getFromFile(basePath + cardName);
	}

	protected void preload(){
		for(CardValue value : CardValue.values()){
			for(CardSuit suit : CardSuit.values()){
				Card card = new Card(value, suit);
				imagesFront.put(card, getImage(card));
			}
		}

		imageBack = getImage(null);
	}
}
