package frontend;

import javax.swing.*;

import interfaces.BackendHandler;

public class FrameGame extends JFrame{
	//COSTANTS  -----------------------------------------------------
	private static final long serialVersionUID = 8167773692334768326L;
	
	
	//CONSTRUCTOR AND UI/PARAMETERS  --------------------------------
	public FrameGame(BackendHandler backhand) {
		
		setUI(new PanelCanvas(backhand));
		setParameters();
		
		this.setVisible(true);
	}
	private void setParameters() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Solitaire");
		this.pack();
	}
	private void setUI(PanelCanvas panelCanvas) {
		this.getContentPane().add(new JScrollPane(panelCanvas));
	}
	
	
	//---------------------------------------------------------------

}
