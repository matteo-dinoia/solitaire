package decks_renderers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
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
				renderer =new RendererDeckDefault("yewbi.png");
				break;
			case "blueeyedrat":
				renderer =new RendererDeckDefault("blueeyedrat.png");
				break;
			default:
				renderer = new RendererDeckDefault("default.png");

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

	protected final void saveToSaneFormat(int width, int height, String fileName){
		BufferedImage img = new BufferedImage(13 * width, 5 * height, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics2D g = (Graphics2D) img.getGraphics();
		g.setBackground(new Color(0, 0, 0, 0));

		int x = 0, y = 0;
		for(CardValue val : CardValue.values()){
			g.drawImage(getFrontImage(new Card(val, CardSuit.SPADES)), x * width, 0 * height, width, height, null, null);
			g.drawImage(getFrontImage(new Card(val, CardSuit.DIAMONDS)), x * width, 1 * height, width, height, null, null);
			g.drawImage(getFrontImage(new Card(val, CardSuit.CLUBS)), x * width, 2 * height, width, height, null, null);
			g.drawImage(getFrontImage(new Card(val, CardSuit.HEARTS)), x * width, 3 * height, width, height, null, null);
			x++;
		}

		g.drawImage(getBackImage(), 0 * width, 4 * height, width, height, null, null);
		g.drawImage(getEmptyImage(), 1 * width, 4 * height, width, height, null, null);
		g.setColor(Color.RED);
		g.drawRoundRect(12 * width, 4 * height, width - 1, height - 1, 20, 20);
		g.setColor(Color.BLACK);

		g.dispose();
		new ImgAdvanced(img).writeOnFile("../img/converted/" + fileName);
	}
}
