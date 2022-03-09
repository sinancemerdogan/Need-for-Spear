package ui;

import javax.swing.*;

import model.controller.GameController;

import java.awt.event.*;
import java.awt.*;

public class MainMenuPanel extends JPanel implements KeyListener, ActionListener{
	
	private JButton newGame = new JButton("New Game");
	private JButton load = new JButton("Load");
	private JButton exit = new JButton("Exit");
	private GameController gameController = new GameController();
	
	
	public MainMenuPanel() {
		addKeyListener(this);
		setFocusable(true);
		setLayout(new GridBagLayout());
		setBackground(Color.GRAY);
		
		newGame.addActionListener(this);
		exit.addActionListener(this);
		load.addActionListener(this); 
		
		Dimension buttonDim = new Dimension(200,50);
		newGame.setPreferredSize(buttonDim);
		exit.setPreferredSize(buttonDim);
		load.setPreferredSize(buttonDim);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
		add(newGame, gbc);
		add(load, gbc);
		add(exit, gbc);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newGame) {
			gameController.resetGame();
			GameUI.showBuildMode();
			
		} else if(e.getSource() == load) {
			GameUI.showSavedGames();
			
		} else if(e.getSource() == exit) {
			System.exit(0);
			
		} 
	}
}
