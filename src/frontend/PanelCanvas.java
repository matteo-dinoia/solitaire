package frontend;

import java.awt.*;
import javax.swing.JPanel;
import interfaces.*;
import backend.Settings;
import card_entity.*;
import card_list.SubCardList;
import decks_renderers.RendererDeck;


/*TODO partial print-> backPartial(x, y) / frontPartial(x, y, cardToDisplay)*/
public class PanelCanvas extends JPanel implements MoveListener{
	private static final long serialVersionUID = 883516271508061367L;
	private MouseHandler mouse;
	private BackendHandler backend;
	private FrameGame frame;
	private Graphics2D currentGraphics = null;
	private RendererDeck currentRender = null;
	private boolean currentReprintAll = false;

	public PanelCanvas(BackendHandler backend, FrameGame frame) {
		this.backend = backend;
		this.frame = frame;

		mouse=new MouseHandler(this, backend);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);

		setParameters();
		this.updateGraphics(true);
	}
	private void setParameters() {
		int size=getXColumnN(7); //so that it can stay exacly 7 columns
		this.setPreferredSize(new Dimension(size, size));
	}

	private int getXColumnN(int numOfColumn) {
		return (numOfColumn + 1) * Settings.SPACE_BETWEEN + numOfColumn * Settings.WIDTH;
	}

	private int getYColumnElementN(int indexElementInColumn) {
		return (Settings.SPACE_BETWEEN * 2 + Settings.HEIGHT) + Settings.PARTIAL_HEIGHT * indexElementInColumn;
	}


	@Override protected void paintComponent(Graphics g) {
		currentGraphics=(Graphics2D) g;
		currentRender = RendererDeck.get();

		if(currentReprintAll == false){
			paintAllGhost();
		}else{
			super.paintComponent(g);
			paintGame();
		}

		g.dispose();
	}

	private void paintGame() {
		//Temp
		paintCard(getXColumnN(1), 10, backend.getPreviousDeckCard(), false); //TODO REMOVE
		//FULL DECK
		Card temp = new Card(CardValue.ONE, CardSuit.HEARTS);
		temp.setHidden(true);
		if(!backend.isEmplyDeckPile())paintCard(getXColumnN(0), 10, temp, false);
		else paintCard(getXColumnN(0), 10, null, false);
		//DISCARDED CARD
		paintCard(getXColumnN(1), 10, backend.getDeckCard(), false);

		//PILES
		for(int i=0; i<Settings.NUM_SLOT_PILE; i++){
			Card c = backend.getPileCard(i);

			if(c != null){
				CardValue prevVal = c.getCardValue().getPrevCardValue();
				Card prev = null;
				if(prevVal != null)
					prev = new Card(prevVal, c.getCardSuit());

				paintCard(getXColumnN(i+3), 10, prev, false);
			}

			paintCard(getXColumnN(i+3), 10, c, false);
		}


		//COLUMNS
		for(int i=0; i<Settings.NUM_COLS_TABLE; i++) {
			paintCard(getXColumnN(i), getYColumnElementN(0), null, false); //TODO REMOVE

			for(int i2=0; i2<backend.getSizeColumnN(i); i2++) {
				Card card=backend.getColumnCard(i, i2);
				paintCard(getXColumnN(i), getYColumnElementN(i2), card, false);
			}
		}

		//GHOST
		paintAllGhost();
	}

	private void paintAllGhost(){
		SubCardList selected = mouse.getSelectedCard();
		if(selected != null && !selected.isEmpty()) {
			for(int i = 0; i < selected.size(); i++){
				Card card = selected.get(i);

				paintCard(mouse.getX(), mouse.getY() + i * Settings.PARTIAL_HEIGHT, card, true);
			}
		}
	}

	private void fillRoundRect(int x, int y, int width, int height, Color color) {
		currentGraphics.setColor(color);
		currentGraphics.fillRoundRect(x, y, width, height, Settings.CORNER_RADIUS, Settings.CORNER_RADIUS);
	}

	private void drawRoundRect(int x, int y, int width, int height, Color color) {
		currentGraphics.setColor(color);
		currentGraphics.drawRoundRect(x, y, width, height, Settings.CORNER_RADIUS, Settings.CORNER_RADIUS);
	}

	private void fillAndDrawRoundRect(int x, int y, int width, int height, Color drawColor, Color fillColor) {
		fillRoundRect(x, y, width, height, fillColor);
		drawRoundRect(x, y, width, height, drawColor);
	}

	private void paintCard(int x, int y, Card cardToDisplay, boolean forceDisplay) {
		if(cardToDisplay == null) { //missing card
			Image img = currentRender.getEmptyImage();

			if(img == null)
				fillAndDrawRoundRect(x, y, Settings.WIDTH, Settings.HEIGHT, Color.black, Color.gray);
			else
				currentGraphics.drawImage(img, x, y, Settings.WIDTH, Settings.HEIGHT, null, null);
			return;
		}else if(!cardToDisplay.isVisible() && !forceDisplay){ //invisible card
			return;
		}

		Image img;
		if(cardToDisplay.isHidden()) //back
			img = currentRender.getBackImage();
		else //front
			img = currentRender.getFrontImage(cardToDisplay);

		currentGraphics.drawImage(img, x, y, Settings.WIDTH, Settings.HEIGHT, null, null);
	}

	//LISTENER HANDLER
	@Override public void updateGraphics(boolean reprintAll){
		currentReprintAll = true;
		if(currentReprintAll) this.repaint();
		else this.repaint(mouse.getX(), mouse.getY(), Settings.WIDTH, Settings.HEIGHT);
	}

	@Override public void makeMove(CardCoord oldCoord, CardCoord coord){
		if(oldCoord == null || coord == null){
			this.updateGraphics(false);
			return;
		}

		backend.operateMove(oldCoord, coord);
		this.updateGraphics(true);
		if(backend.hasWon()) frame.showWonPopup();
	}
	@Override public Card[] getPileSelected(CardCoord coord) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPileSelected'");
	}
}
