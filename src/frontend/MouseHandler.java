package frontend;

import java.awt.event.*;
import obj.CardCoord;
import interfaces.*;
import backend.App;

public class MouseHandler implements MouseListener, MouseMotionListener{
	private CardCoord oldCoord=null;
	private MoveListener moveListener;
	private BackendHandler backend;
	private int xMouse=0, yMouse=0, offsetX=0, offsetY=0, numCard=0;


	public MouseHandler(MoveListener moveListener, BackendHandler backend){
		this.moveListener = moveListener;
		this.backend = backend;
	}

	public Integer getX(){return numCard <= 0 ? null : xMouse + offsetX;}
	public Integer getY(){return numCard <= 0 ? null : yMouse + offsetY;}
	public int getNumSelected(){return numCard;}


	/**return -2 if not valid, else it return a column index (0 to 6)*/
	public int getColumnByMouseX(int x) {

		//IF not in spaces between
		int remaining = x%(App.WIDTH + App.SPACE_BETWEEN);
		if(remaining<App.SPACE_BETWEEN)  return CardCoord.NOT_VALID_POINT;

		//ELSE return value of column
		int col = x/(App.WIDTH + App.SPACE_BETWEEN);
		if(col<0||col>=7) return CardCoord.NOT_VALID_POINT;
		return col;
	}

	public int getRowByMouseY(int y, int col) {
		//DECK
		if(App.SPACE_BETWEEN<y && y<App.SPACE_BETWEEN+App.HEIGHT)
			return CardCoord.DECK_ROW;
		else if(y<App.SPACE_BETWEEN*2+App.HEIGHT)
			return  CardCoord.NOT_VALID_POINT;

		//NORMAL
		int yRelative = y - (App.SPACE_BETWEEN*2 + App.HEIGHT);
		int sizeCol=backend.getSizeColumnN(col);
		int index = yRelative/App.PARTIAL_HEIGHT;

		if(index<sizeCol) return index;
		else if(sizeCol==0) {
			if(yRelative < App.HEIGHT) return 0;
		}
		else if(yRelative - App.PARTIAL_HEIGHT*(sizeCol-1) < App.HEIGHT) {
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
		offsetX= - (xMouse%(App.SPACE_BETWEEN+App.WIDTH)-App.SPACE_BETWEEN);
		if(oldCoord.getRow()==CardCoord.DECK_ROW)
			offsetY= - (yMouse-App.SPACE_BETWEEN);
		else
			offsetY= - ((yMouse-App.SPACE_BETWEEN*2-App.HEIGHT) - oldCoord.getRow()*App.PARTIAL_HEIGHT);
		numCard = backend.getSelectedNum(oldCoord);
	}

	@Override public void mouseReleased(MouseEvent e) {
		numCard = 0;
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
}
