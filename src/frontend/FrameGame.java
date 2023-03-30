package frontend;

import java.awt.event.*;
import javax.swing.*;
import backend.TableOfCards;

public class FrameGame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 8167773692334768326L;

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
		PanelCanvas canvas = new PanelCanvas(new TableOfCards(), this);

		this.getContentPane().removeAll();
		this.getContentPane().add(new JScrollPane(canvas));
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
}
