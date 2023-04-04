package frontend;

import java.awt.Checkbox;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import backend.Settings;

public class Menus extends JMenuBar implements ActionListener, ItemListener{
	private FrameGame frame;
	private JCheckBoxMenuItem checkOnlyKing = new JCheckBoxMenuItem("Only king in empty space");
	private JCheckBoxMenuItem checkAceInstead = new JCheckBoxMenuItem("Use ace symbol instead");
	private ButtonGroup group = new ButtonGroup();

	public Menus(FrameGame frame){
		this.frame = frame;

		addFileMenu();
		addSettingsMenu();
	}

	private void addFileMenu(){
		JMenu file=new JMenu("File");
		this.add(file);

		JMenuItem newGame = new JMenuItem("New game");
		newGame.addActionListener(this);
		newGame.setActionCommand("new-game");
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		file.add(newGame);

		JMenuItem close = new JMenuItem("Close");
		close.addActionListener(this);
		close.setActionCommand("close");
		file.add(close);
	}

	private void addSettingsMenu(){
		JMenu file=new JMenu("Settings");
		this.add(file);

		checkOnlyKing.addItemListener(this);
		checkOnlyKing.setSelected(true);
		file.add(checkOnlyKing);

		checkAceInstead.addItemListener(this);
		checkAceInstead.setSelected(true);
		checkAceInstead.setEnabled(false);
		file.add(checkAceInstead);

		//Radio btn for style
		file.add(new JSeparator());

		JRadioButtonMenuItem radioStyleDani = new JRadioButtonMenuItem("Style Dani Macarri");
		radioStyleDani.setActionCommand("dani");
		radioStyleDani.addItemListener(this);
		group.add(radioStyleDani);
		file.add(radioStyleDani);

		JRadioButtonMenuItem radioStyleVictor = new JRadioButtonMenuItem("Style Victror Meunier");
		radioStyleVictor.setActionCommand("victor");
		radioStyleVictor.addItemListener(this);
		group.add(radioStyleVictor);
		file.add(radioStyleVictor);

		JRadioButtonMenuItem radioStyleYewbi = new JRadioButtonMenuItem("Style Yewbi");
		radioStyleYewbi.setActionCommand("yewbi");
		radioStyleYewbi.addItemListener(this);
		group.add(radioStyleYewbi);
		file.add(radioStyleYewbi);

		//Setting default
		group.setSelected(radioStyleYewbi.getModel(), true);
		Settings.style = group.getSelection().getActionCommand();
	}



	@Override public void actionPerformed(ActionEvent event) {
		switch(event.getActionCommand()){
			case "new-game":
				frame.restart();
				this.setVisible(true);
				break;
			case "close":
				System.exit(0);
				break;
		}
	}

	@Override public void itemStateChanged(ItemEvent event) {
		Settings.onlyKing = checkOnlyKing.isSelected();
		Settings.aceInsetad = checkAceInstead.isSelected();

		if(group.getSelection() != null)
			Settings.style = group.getSelection().getActionCommand();
		frame.refreshScreen();
	}


}
