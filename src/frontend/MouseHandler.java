package frontend;

import java.awt.event.*;

import interfaces.*;
import backend.Settings;
import card_entity.CardCoord;
import card_list.SubCardList;

public class MouseHandler implements MouseListener, MouseMotionListener{
	private CardCoord oldCoord=null;
	private MoveListener moveListener;
	private BackendHandler backend;
	private SubCardList selectedCards = null;
	private int xMouse=0, yMouse=0, offsetX=0, offsetY=0;


	public MouseHandler(MoveListener moveListener, BackendHandler backend){
		this.moveListener = moveListener;
		this.backend = backend;
	}

	public Integer getX(){return selectedCards == null ? null : xMouse + offsetX;}
	public Integer getY(){return selectedCards == null ? null : yMouse + offsetY;}


	/**@return -2 if not valid, else it return a column index (0 to 6)*/
	public int getColumnByMouseX(int x) {

		//IF not in spaces between
		int remaining = x%(Settings.WIDTH + Settings.SPACE_BETWEEN);
		if(remaining<Settings.SPACE_BETWEEN)  return CardCoord.NOT_VALID_POINT;

		//ELSE return value of column
		int col = x/(Settings.WIDTH + Settings.SPACE_BETWEEN);
		if(col<0||col>=7) return CardCoord.NOT_VALID_POINT;
		return col;
	}

	public int getRowByMouseY(int y, int col) {
		//DECK
		if(Settings.SPACE_BETWEEN<y && y<Settings.SPACE_BETWEEN+Settings.HEIGHT)
			return CardCoord.DECK_ROW;
		else if(y<Settings.SPACE_BETWEEN*2+Settings.HEIGHT)
			return  CardCoord.NOT_VALID_POINT;

		//NORMAL
		int yRelative = y - (Settings.SPACE_BETWEEN*2 + Settings.HEIGHT);
		int sizeCol=backend.getSizeColumnN(col);
		int index = yRelative/Settings.PARTIAL_HEIGHT;

		if(index<sizeCol) return index;
		else if(sizeCol==0) {
			if(yRelative < Settings.HEIGHT) return 0;
		}
		else if(yRelative - Settings.PARTIAL_HEIGHT*(sizeCol-1) < Settings.HEIGHT) {
			return sizeCol-1;  //Last larger card
		}

		return CardCoord.NOT_VALID_POINT;
	}

	public CardCoord getCardCoordByMouseXY(int x, int y) {
		int col=getColumnByMouseX(x), row=CardCoord.NOT_VALID_POINT;
		if(col > CardCoord.NOT_VALID_POINT) row=getRowByMouseY(y, col);

		return CardCoord.getCardCoord(col, row);
	}


	@Override public void mousePressed(MouseEvent e) {
		oldCoord=getCardCoordByMouseXY(e.getX(), e.getY());
		if(oldCoord==null) return;

		xMouse=e.getX();
		yMouse=e.getY();
		offsetX= - (xMouse%(Settings.SPACE_BETWEEN+Settings.WIDTH)-Settings.SPACE_BETWEEN);
		if(oldCoord.getRow()==CardCoord.DECK_ROW)
			offsetY= - (yMouse-Settings.SPACE_BETWEEN);
		else
			offsetY= - ((yMouse-Settings.SPACE_BETWEEN*2-Settings.HEIGHT) - oldCoord.getRow()*Settings.PARTIAL_HEIGHT);
		selectedCards = backend.getSubCardList(oldCoord);
		if(selectedCards != null) selectedCards.setVisibilityCards(false);
	}

	@Override public void mouseReleased(MouseEvent e) {
		if(selectedCards == null) return;

		selectedCards.setVisibilityCards(true);
		selectedCards = null;
		moveListener.makeMove(oldCoord, getCardCoordByMouseXY(e.getX(), e.getY()));
	}

	@Override public void mouseDragged(MouseEvent e) {
		if(oldCoord==null) return;

		xMouse=e.getX();
		yMouse=e.getY();
		moveListener.updateGraphics();
	}

	@Override public void mouseMoved(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}

	public SubCardList getSelectedCard(){
		return selectedCards;
	}
}
