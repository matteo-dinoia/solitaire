package frontend;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import interfaces.BackendHandler;
import obj.Card;
import obj.CardCoord;

public class PanelCanvas extends JPanel implements MouseListener, MouseMotionListener{
	
	//COSTANT AND VARIABLE -------------------------------------------
	private static final long serialVersionUID = 883516271508061367L;
	private static final int WIDTH=100,     
			HEIGHT=(int)(1.6*WIDTH),
			PARTIAL_HEIGHT=(int)(0.2*HEIGHT),     
			SPACE_BETWEEN=10,
			LINE_THICKENESS = 4,
			ROUNDED_CORNER_RADIUS= 20;
	
	
	
	//CONSTRUCTOR AND UI PARAMETERS  ---------------------------------
	private BackendHandler backend;
	public PanelCanvas(BackendHandler backend) {
		
		//Listener
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		//backend and parameters
		this.backend=backend;
		setParameters();
		
	}
	private void setParameters() {
		int size=getXColumnN(7); //so that it can stay exacly 7 columns
		this.setPreferredSize(new Dimension(size, size));
		
	}
	
	
	//DRAWING  --------------------------------------------------------
	private Graphics2D currentGraphics=null;
	@Override
	protected void paintComponent(Graphics g) {
		currentGraphics=(Graphics2D) g;
		super.paintComponent(g);    //TODO Cremove in order to not reprint all
		
		paintGame();
		
		if(xMouse!=null && yMouse!=null) {
			for(int i=0; i<numCard; i++) {
				paintCardGhost(xMouse+offsetX, yMouse+offsetY + i*PARTIAL_HEIGHT, HEIGHT+(numCard-i-1)*PARTIAL_HEIGHT);
			}
		}
		
		g.dispose();
		currentGraphics=null;
		
		
		
	}
	private void paintGame() { 
		//FULL DECK
		if(!backend.isEmplyDeckPile())paintCardBackFull(getXColumnN(0), 10); 
		else paintCardFrontFull(getXColumnN(0), 10, null); 
		//DISCARDED CARD
		paintCardFrontFull(getXColumnN(1), 10, backend.getDeckCard()); 
		
		//PILES
		for(int i=0; i<4; i++) {
			paintCardFrontFull(getXColumnN(i+3), 10, backend.getPileCard(i));
		}
		
		//COLUMNS
		for(int i=0; i<7; i++) {
			for(int i2=0; i2<backend.getSizeColumnN(i) || i2==0; i2++) {
				Card card=backend.getColumnCard(i, i2);
				if(card!=null && card.isHidden()) {
					paintCardBackFull(getXColumnN(i), getYColumnElementN(i2));
				}else {
					paintCardFrontFull(getXColumnN(i), getYColumnElementN(i2), card);
				}
				
				
			}
		}
		
	}
	
	private void fillRoundRect(int x, int y, int width, int height, Color color) {
		currentGraphics.setColor(color);
		currentGraphics.fillRoundRect(x, y, width, height, ROUNDED_CORNER_RADIUS, ROUNDED_CORNER_RADIUS);
		
	}
	private void drawRoundRect(int x, int y, int width, int height, Color color) {
		currentGraphics.setColor(color);
		currentGraphics.drawRoundRect(x, y, width, height, ROUNDED_CORNER_RADIUS, ROUNDED_CORNER_RADIUS);
		
	}
	private void fillAndDrawRoundRect(int x, int y, int width, int height, Color drawColor, Color fillColor) {
		fillRoundRect(x, y, width, height, fillColor);
		drawRoundRect(x, y, width, height, drawColor);
	}
	
	/**From column 0 to 6*/
	private int getXColumnN(int numOfColumn) {
		return (numOfColumn+1)*SPACE_BETWEEN +  numOfColumn*WIDTH;
	}
	private int getYColumnElementN(int indexElementInColumn) {
		return (SPACE_BETWEEN*2+HEIGHT)  +  PARTIAL_HEIGHT*indexElementInColumn;
	}
	
	private void paintCardBackFull(int x, int y) {
		
		fillAndDrawRoundRect(x, y, WIDTH, HEIGHT, Color.black, Color.blue);
		fillAndDrawRoundRect(x+LINE_THICKENESS*2, y+LINE_THICKENESS*2, WIDTH-4*LINE_THICKENESS, HEIGHT-4*LINE_THICKENESS, Color.black, Color.CYAN);
		fillAndDrawRoundRect(x+LINE_THICKENESS*3, y+LINE_THICKENESS*3, WIDTH-6*LINE_THICKENESS, HEIGHT-6*LINE_THICKENESS, Color.black, Color.blue);
	}
	private void paintCardGhost(int x, int y, int height) {
		
		drawRoundRect(x, y, WIDTH, height, Color.black);
		drawRoundRect(x+1, y+1, WIDTH-2, height-2, Color.black);
		drawRoundRect(x+2, y+2, WIDTH-4, height-4, Color.black);
		drawRoundRect(x+3, y+3, WIDTH-6, height-6, Color.black);
		
	}
	private void paintCardFrontFull(int x, int y, Card cardToDisplay) {
		
		if(cardToDisplay==null) { //missing card
			fillAndDrawRoundRect(x, y, WIDTH, HEIGHT, Color.black, Color.gray);
		}
		else {
			//BOLD SHAPE
			fillRoundRect(x, y, WIDTH, HEIGHT, Color.white);
			drawRoundRect(x, y, WIDTH, HEIGHT, Color.black);
			
			Font fontBold=new Font("", Font.BOLD, 16);
			Font fontBoldInverted=new Font("", Font.BOLD, -16);
			
			
			currentGraphics.setColor(cardToDisplay.getCardColor().getColorUI());
			
			
			//DIPLAYING SIZE
			String txt=cardToDisplay.getAbbreviation();
			// get metrics from the graphics (like height and width of a string)
			FontMetrics metrics = currentGraphics.getFontMetrics(fontBold);
			int hgt = metrics.getAscent();
			int adv = metrics.stringWidth(txt);

			
			//PRINT TEXT
			currentGraphics.setFont(fontBold);
			currentGraphics.drawString(txt, x+WIDTH-10-adv, y+10+hgt);
			currentGraphics.setFont(fontBoldInverted);
			currentGraphics.drawString(txt, x+10+adv, y+HEIGHT-10-hgt);
		}
	}
	/*TODO add this feature (paint 20% of a card)
	private void paintCardBackPartial(int x, int y) {}
	private void paintCardFrontPartial(int x, int y, Card cardToDisplay) {}*/
	
	
	
	//MOUSE HANDLER  ---------------------------------------------------
	
	/**return -2 if not valid, else it return a column index (0 to 6)*/
	public int getColumnByMouseX(int x) {
		
		//IF not in spaces between
		int remaining = x%(WIDTH + SPACE_BETWEEN);
		if(remaining<SPACE_BETWEEN)  return CardCoord.NOT_VALID_POINT;
		
		//ELSE return value of column
		int col = x/(WIDTH + SPACE_BETWEEN);
		if(col<0||col>=7) return CardCoord.NOT_VALID_POINT;
		return col;
	}
	public int getRowByMouseY(int y, int col) {
		//DECK
		if(SPACE_BETWEEN<y && y<SPACE_BETWEEN+HEIGHT) 
			return CardCoord.DECK_ROW;
		else if(y<SPACE_BETWEEN*2+HEIGHT)
			return  CardCoord.NOT_VALID_POINT;
		
		
		
		//NORMAL
		int yRelative = y - (SPACE_BETWEEN*2 + HEIGHT);
		int sizeCol=backend.getSizeColumnN(col);
		int index = yRelative/PARTIAL_HEIGHT;
		
		if(index<sizeCol) return index;
		else if(sizeCol==0) {
			if(yRelative < HEIGHT) return 0;
		}
		else if(yRelative - PARTIAL_HEIGHT*(sizeCol-1) < HEIGHT) {
			return sizeCol-1;  //Last larger card
		}
		
		
		return CardCoord.NOT_VALID_POINT;

	}
	public CardCoord getCardCoordByMouseXY(int x, int y) {
		int col=getColumnByMouseX(x), row=CardCoord.NOT_VALID_POINT;
		if(col > CardCoord.NOT_VALID_POINT) row=getRowByMouseY(y, col);
		
		return CardCoord.getCardCoord(col, row);
	}
	
	private CardCoord oldCoord=null;
	private Integer xMouse=null, yMouse=null;
	private int offsetX=0, offsetY=0, numCard=0;
	@Override public void mousePressed(MouseEvent e) {
		oldCoord=getCardCoordByMouseXY(e.getX(), e.getY());
		
		if(oldCoord!=null) {
			xMouse=e.getX();
			yMouse=e.getY();
			offsetX= - (xMouse%(SPACE_BETWEEN+WIDTH)-SPACE_BETWEEN);
			if(oldCoord.getRow()==CardCoord.DECK_ROW) {
				offsetY= - (yMouse-SPACE_BETWEEN);
			}
			else {
				offsetY= - ((yMouse-SPACE_BETWEEN*2-HEIGHT) - oldCoord.getRow()*PARTIAL_HEIGHT);
			}
			numCard=backend.getSelectedNum(oldCoord);
		}
		
	}
	@Override public void mouseReleased(MouseEvent e) {
		
		CardCoord coord=getCardCoordByMouseXY(e.getX(), e.getY());
		if(coord!=null && oldCoord!=null) {
			backend.operateMoves(oldCoord, coord); 
			this.repaint(); //TODO Efficency repainto a pezzi
			
		}
		
		this.repaint();
		xMouse=null; yMouse=null;
		offsetX=0; offsetY=0;
		numCard=0;
	}
	@Override public void mouseDragged(MouseEvent e) {
		if(oldCoord!=null) {
			xMouse=e.getX();
			yMouse=e.getY();
			repaint();
		}
	}
	
	@Override public void mouseMoved(MouseEvent e) {}
	@Override public void mouseClicked(MouseEvent e) {}
	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
	
	
	
	
	//-------------------------------------------------------------------
}
