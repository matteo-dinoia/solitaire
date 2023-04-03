package frontend;

import javax.swing.*;
import backend.TableOfCards;

public class FrameGame extends JFrame{
	private static final long serialVersionUID = 8167773692334768326L;


	public FrameGame() {
		setNewUI();
		this.setJMenuBar(new Menus(this));
		setParameters();

		this.setVisible(true);
	}

	private void setParameters() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Solitaire");
		this.pack();
	}

	public void restart(){
		setNewUI();
		refreshScreen();
	}

	public void refreshScreen(){
		this.repaint();
		this.setVisible(true);
	}

	private void setNewUI() {
		PanelCanvas canvas = new PanelCanvas(new TableOfCards(), this);

		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(canvas));
	}

	//POPUP  --------------------------------------------------------
	public void showWonPopup(){
		int response = JOptionPane.showConfirmDialog(this,
			"Would you like to play another game?",
			"You Won",
			JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

		if(response == JOptionPane.YES_OPTION){
			restart();
		}else if(response == JOptionPane.NO_OPTION){
			System.exit(0);
		}
	}
}
