package frontend;

import java.awt.*;
import javax.swing.JPanel;
import interfaces.*;
import backend.App;
import card_entity.Card;
import card_entity.CardCoord;
import card_entity.CardValue;
import card_list.SubCardList;


/*TODO partial print-> backPartial(x, y) / frontPartial(x, y, cardToDisplay)*/
public class PanelCanvas extends JPanel implements MoveListener{
	private static final long serialVersionUID = 883516271508061367L;
	private MouseHandler mouse;
	private BackendHandler backend;
	private FrameGame frame;
	private Graphics2D currentGraphics=null;

	public PanelCanvas(BackendHandler backend, FrameGame frame) {
		this.backend = backend;
		this.frame = frame;

		mouse=new MouseHandler(this, backend);
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);

		setParameters();
		this.updateGraphics();
	}
	private void setParameters() {
		int size=getXColumnN(7); //so that it can stay exacly 7 columns
		this.setPreferredSize(new Dimension(size, size));
	}

	private int getXColumnN(int numOfColumn) {
		return (numOfColumn + 1) * App.SPACE_BETWEEN + numOfColumn * App.WIDTH;
	}

	private int getYColumnElementN(int indexElementInColumn) {
		return (App.SPACE_BETWEEN * 2 + App.HEIGHT) + App.PARTIAL_HEIGHT * indexElementInColumn;
	}


	@Override protected void paintComponent(Graphics g) {
		currentGraphics=(Graphics2D) g;

		super.paintComponent(g);
		paintGame();

		g.dispose();
	}

	private void paintGame() {
		//Temp
		paintCardFrontFull(getXColumnN(1), 10, backend.getPreviousDeckCard(), false); //TODO REMOVE
		//FULL DECK
		if(!backend.isEmplyDeckPile())paintCardBackFull(getXColumnN(0), 10);
		else paintCardFrontFull(getXColumnN(0), 10, null, false);
		//DISCARDED CARD
		paintCardFrontFull(getXColumnN(1), 10, backend.getDeckCard(), false);

		//PILES
		for(int i=0; i<App.NUM_SLOT_PILE; i++){
			Card c = backend.getPileCard(i);

			if(c != null){
				CardValue prevVal = c.getCardValue().getPrevCardValue();

				if(prevVal != null){
					Card prev = new Card(prevVal, c.getCardSuit());
					paintCardFrontFull(getXColumnN(i+3), 10, prev, false);
				}else{
					paintCardFrontFull(getXColumnN(i+3), 10, null, false);
				}
			}

			paintCardFrontFull(getXColumnN(i+3), 10, c, false);
		}


		//COLUMNS
		for(int i=0; i<App.NUM_COLS_TABLE; i++) {
			paintCardFrontFull(getXColumnN(i), getYColumnElementN(0), null, false); //TODO REMOVE

			for(int i2=0; i2<backend.getSizeColumnN(i); i2++) {
				Card card=backend.getColumnCard(i, i2);
				if(card != null && card.isHidden())
					paintCardBackFull(getXColumnN(i), getYColumnElementN(i2));
				else
					paintCardFrontFull(getXColumnN(i), getYColumnElementN(i2), card, false);
			}
		}

		//GHOST
		SubCardList selected = mouse.getSelectedCard();
		if(selected != null && !selected.isEmpty()) {
			for(int i = 0; i < selected.size(); i++){
				Card card = selected.get(i);

				if(card != null && card.isHidden())
					paintCardBackFull(mouse.getX(), mouse.getY() + i * App.PARTIAL_HEIGHT);
				else
					paintCardFrontFull(mouse.getX(), mouse.getY() + i * App.PARTIAL_HEIGHT, card, true);
			}
		}
	}

	private void fillRoundRect(int x, int y, int width, int height, Color color) {
		currentGraphics.setColor(color);
		currentGraphics.fillRoundRect(x, y, width, height, App.CORNER_RADIUS, App.CORNER_RADIUS);

	}

	private void drawRoundRect(int x, int y, int width, int height, Color color) {
		currentGraphics.setColor(color);
		currentGraphics.drawRoundRect(x, y, width, height, App.CORNER_RADIUS, App.CORNER_RADIUS);

	}

	private void fillAndDrawRoundRect(int x, int y, int width, int height, Color drawColor, Color fillColor) {
		fillRoundRect(x, y, width, height, fillColor);
		drawRoundRect(x, y, width, height, drawColor);
	}


	private void paintCardBackFull(int x, int y) {
		fillAndDrawRoundRect(x, y, App.WIDTH, App.HEIGHT, Color.black, Color.blue);
		fillAndDrawRoundRect(x+App.LINE_THICKENESS*2, y+App.LINE_THICKENESS*2, App.WIDTH-4*App.LINE_THICKENESS, App.HEIGHT-4*App.LINE_THICKENESS, Color.black, Color.CYAN);
		fillAndDrawRoundRect(x+App.LINE_THICKENESS*3, y+App.LINE_THICKENESS*3, App.WIDTH-6*App.LINE_THICKENESS, App.HEIGHT-6*App.LINE_THICKENESS, Color.black, Color.blue);
	}

	/*private void paintCardGhost(int x, int y, int height) {
		drawRoundRect(x, y, App.WIDTH, height, Color.black);
		drawRoundRect(x+1, y+1, App.WIDTH-2, height-2, Color.black);
		drawRoundRect(x+2, y+2, App.WIDTH-4, height-4, Color.black);
		drawRoundRect(x+3, y+3, App.WIDTH-6, height-6, Color.black);
	}*/

	private void paintCardFrontFull(int x, int y, Card cardToDisplay, boolean forceDisplay) {
		if(cardToDisplay == null) { //missing card
			fillAndDrawRoundRect(x, y, App.WIDTH, App.HEIGHT, Color.black, Color.gray);
			return;
		}else if(!cardToDisplay.isVisible() && !forceDisplay) return;

		//BOLD SHAPE
		fillRoundRect(x, y, App.WIDTH, App.HEIGHT, Color.white);
		drawRoundRect(x, y, App.WIDTH, App.HEIGHT, Color.black);

		Font fontBold=new Font("", Font.BOLD, 16);
		Font fontBoldInverted=new Font("", Font.BOLD, -16);
		currentGraphics.setColor(cardToDisplay.getCardColor());

		//DIPLAYING SIZE
		String txt=cardToDisplay.getAbbreviation();
		// get metrics from the graphics (like height and width of a string)
		FontMetrics metrics = currentGraphics.getFontMetrics(fontBold);
		int hgt = metrics.getAscent();
		int adv = metrics.stringWidth(txt);

		//PRINT TEXT
		currentGraphics.setFont(fontBold);
		currentGraphics.drawString(txt, x+App.WIDTH-10-adv, y+10+hgt);
		currentGraphics.setFont(fontBoldInverted);
		currentGraphics.drawString(txt, x+10+adv, y+App.HEIGHT-10-hgt);
	}

	@Override public void updateGraphics(){
		this.repaint();
	}

	@Override public void makeMove(CardCoord oldCoord, CardCoord coord){
		if(oldCoord == null || coord == null){
			this.updateGraphics();
			return;
		}

		backend.operateMove(oldCoord, coord);
		this.updateGraphics();
		if(backend.hasWon()) frame.showWonPopup();
	}
	@Override public Card[] getPileSelected(CardCoord coord) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getPileSelected'");
	}
}
