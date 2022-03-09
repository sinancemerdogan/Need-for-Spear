package ui;

import javax.swing.*;

import model.controller.GameController;

import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;

public class SavedGamesPanel extends JPanel implements KeyListener, ActionListener{
	
	
	private GameController gameController = new GameController();
	
	
	public SavedGamesPanel(ArrayList<String> games) {
		addKeyListener(this);
		setFocusable(true);
		setLayout(new GridBagLayout());
		setBackground(Color.GRAY);
		setBounds(0,0,100,100);
		Dimension buttonDim = new Dimension(200,50);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        for(String game : games) {
        	JButton savedGame = new JButton(game);
        	savedGame.addActionListener(this);
        	savedGame.setPreferredSize(buttonDim);
        	add(savedGame, gbc);
        }
	}
	
	

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Y || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameUI.showMainMenu();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String gameName = e.getActionCommand();
		gameController.load(gameName);
		GameUI.showPlayMode();
	}
}
