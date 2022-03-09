package ui;

import javax.swing.*;

import model.controller.GameController;

import java.awt.event.*;
import java.awt.*;

public class PauseMenuPanel extends JPanel implements KeyListener, ActionListener{
	
	private GameController gameController = new GameController();
	private JButton resume = new JButton("Resume");
	private JButton save = new JButton("Save Game");
	private JButton backToMain = new JButton("Back to Main Menu");
	
	public PauseMenuPanel() {
		addKeyListener(this);
		setFocusable(true);
		setLayout(new GridBagLayout());
		setBackground(Color.GRAY);
		setBounds(0,0,100,100);
		
		resume.addActionListener(this);
		save.addActionListener(this);
		backToMain.addActionListener(this);
		
		Dimension buttonDim = new Dimension(200,50);
		resume.setPreferredSize(buttonDim);
		save.setPreferredSize(buttonDim);
		backToMain.setPreferredSize(buttonDim);
		
		GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        add(resume, gbc);
		add(save, gbc);
		add(backToMain, gbc);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_Y || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			GameUI.showPlayMode();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == resume) {
			if(gameController.getMode() == "Build") {
				GameUI.showBuildMode();
			} else {
				GameUI.showPlayMode();
			}
			
			
		} else if(e.getSource() == save) {
			GameUI.showSaveDialog();
			
		} else if(e.getSource() == backToMain) {
			gameController.cancelTimer();
			GameUI.showMainMenu();
			
		} 
	}
}
