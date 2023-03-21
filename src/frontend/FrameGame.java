package frontend;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;

import backend.TableOfCards;

public class FrameGame extends JFrame implements ActionListener{
	//COSTANTS  -----------------------------------------------------
	private static final long serialVersionUID = 8167773692334768326L;

	//CONSTRUCTOR AND UI/PARAMETERS  --------------------------------
	public FrameGame() {
		setNewUI();
		setMenu();
		setParameters();

		this.setVisible(true);
	}
	private void setParameters() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Solitaire");
		this.pack();
	}
	private void setNewUI() {
		final Container base = this.getContentPane();
		TableOfCards backend = new TableOfCards(this);

		base.removeAll();
		base.add(new JScrollPane(new PanelCanvas(backend)));
	}

	//MENU  -----------------------------------------------------------
	private void setMenu(){
		JMenuBar bar=new JMenuBar();

		//File
		JMenu file=new JMenu("File");
		bar.add(file);
		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(this);
		newGame.setActionCommand("new-game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		file.add(newGame);

		this.setJMenuBar(bar);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch(event.getActionCommand()){
			case "new-game":
				setNewUI();
				this.setVisible(true);
				break;
		}
	}

	//POPUP  --------------------------------------------------------
	public void showWonPopup(){
		int response = JOptionPane.showConfirmDialog(this,
			"Would you like to play another game?",
			"You Won",
			JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);

		if(response == JOptionPane.YES_OPTION){
			setNewUI();
			this.setVisible(true);
		}else if(response == JOptionPane.NO_OPTION){
			System.exit(0);
		}
	}

	//---------------------------------------------------------------
}
