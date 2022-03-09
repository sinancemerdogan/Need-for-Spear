package ui; 


import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import model.Constants;
import model.controller.GameController;
import ui.dialogs.SaveDialog;



public class GameUI extends JFrame{

	private static GameController gameController = new GameController();
	public static final int FRAME_X = 1124;
	public static final int FRAME_Y = 768;
	private static final CardLayout card = new CardLayout();	
	private final static JPanel mainPanel = new JPanel();
	private static PauseMenuPanel pausePanel = new PauseMenuPanel();
	private static MainMenuPanel menuPanel = new MainMenuPanel();
	private static MainGamePanel gamePanel = new MainGamePanel();
	private static BuildPanel buildPanel = new BuildPanel();
	private static SavedGamesPanel savedGamesPanel;
	
	//Dialogs
	private static SaveDialog saveDialog = new SaveDialog();
	 
	
	public GameUI () {	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(FRAME_X, FRAME_Y));
	    pack();
	    setLocationRelativeTo(null);
		setResizable(true);
		mainPanel.setLayout(card);
		mainPanel.add("Build Mode", buildPanel);
		mainPanel.add("Game Mode", gamePanel);
		mainPanel.add("Pause Menu", pausePanel);
		mainPanel.add("Main Menu", menuPanel);
		//Dialogs
		mainPanel.add("Save Dialog", saveDialog);
		add(mainPanel);
		showMainMenu();
		setVisible(true);
	}
	
	public static void showPlayMode() {
		card.show(mainPanel, "Game Mode");
		gamePanel.requestFocusInWindow();
	}
	
	public static void showPauseMenu() {
		card.show(mainPanel, "Pause Menu");
		pausePanel.requestFocusInWindow();
	}
	
	public static void showBuildMode() {
		buildPanel.validateObstacleNumber();
		card.show(mainPanel, "Build Mode");
		buildPanel.requestFocusInWindow();
	}
	
	public static void showMainMenu() {
		card.show(mainPanel, "Main Menu");
		menuPanel.requestFocusInWindow();
	}
	
	public static void showSavedGames() {
		savedGamesPanel = new SavedGamesPanel(gameController.getSavedNames());
		mainPanel.add("Saved Games", savedGamesPanel);
		card.show(mainPanel, "Saved Games");
		savedGamesPanel.requestFocusInWindow();
	}
	
	public static void showSaveDialog() {
		card.show(mainPanel, "Save Dialog");
		saveDialog.requestFocusInWindow();
	}
}