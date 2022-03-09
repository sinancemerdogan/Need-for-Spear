package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;


import ui.GameUI;
import model.abilities.Ability;
import model.controller.EnchantedSphereController;
import model.controller.GameController;
import model.controller.NoblePhantasmController;
import model.database.Database;
import model.database.GameData;
import model.obstacle.Obstacle;

public class GameMain {

	private static GameUI gameUI;
	private static EnchantedSphere enchantedSphere = new EnchantedSphere();
	private static NoblePhantasm noblePhantom = new NoblePhantasm();
	public static ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
	private static EnchantedSphereController enchantedSphereController = new EnchantedSphereController(enchantedSphere);
	private static NoblePhantasmController noblePhantasmController = new NoblePhantasmController(noblePhantom);
	private static GameController gameController = new GameController();
	public static int score = 0;
	public static int remainingChances = 3;
	public static int time = 1;
	public static boolean status = false;
	private static String mode = "Build";
	private static Database db;
	
	public static Timer timer = new Timer();
	public static long startTime;
	public static ArrayList<Ability> abilityList = new ArrayList<Ability>();
	

	public static void main(String[] args) throws Exception {
		db = Database.getInstance("mongoDB");
		db.connect();

		CreateAndShowGUI();
	}
	
	private static void CreateAndShowGUI() {
		gameUI = new GameUI();
		gameUI.setSize(500, 500);
		gameUI.setVisible(true);
		for (int i = 0; i < 1000; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void resetGameData() {
		enchantedSphere.setSphereXdir(1);
		enchantedSphere.setSphereYdir(-1);
		enchantedSphere.setPosX(Constants.FRAME_X/2 - 8);
		enchantedSphere.setPosY(Constants.FRAME_Y - 115);
		
		noblePhantom.setAngle(0);
		noblePhantom.setWidth(Constants.FRAME_X/10);
		noblePhantom.setPosX(Constants.FRAME_X/2 - (Constants.FRAME_X/10)/2);
		
		obstacleList.clear();
		abilityList.clear();
		setRemainingChances(3);
		setScore(0);
		setTime(1);
		setStatus(false);
		setMode("Build");
	}

	public static boolean lose() {
		if (remainingChances == 0) {
			return true;
		} else {
			return false;
		}
	}

	public static ArrayList<Obstacle> getObstacles() {
		return obstacleList;
	}

	public static void setObstacles(ArrayList<Obstacle> obstacles) {
		obstacleList = obstacles;
	}

	public static EnchantedSphere getEs() {
		return enchantedSphere;
	}

	public static void setEs(EnchantedSphere es) {
		enchantedSphere = es;
	}

	public static NoblePhantasm getNp() {
		return noblePhantom;
	}

	public static void setNp(NoblePhantasm np) {
		noblePhantom = np;
	}

	public static void changeStatus() {
		status = !status;
	}

	public static boolean getStatus() {
		return status;
	}
	
	public static void setStatus(boolean newStatus) {
		status = newStatus;
	}

	public static void changeMode() {
		mode = "Play";
	}

	public static String getMode() {
		return mode;
	}
	
	public static void setMode(String newMode) {
		mode = newMode;
	}

	public static EnchantedSphereController esController() {
		return enchantedSphereController;
	}
	

	public static NoblePhantasmController npController() {
		return noblePhantasmController;
	}

	public static int getScore() {
		return score;
	}

	public static void setScore(int newScore) {
		score = newScore;
	}

	public static void setRemainingChances(int chances) {
		remainingChances = chances;
	}
	
	public static int getRemainingChances() {
		return remainingChances;
	}

	public static int getTime() {
		return time;
	}

	public static void setTime(int newTime) {
		time = newTime;
	}

	/*
	 * Gets all of the names of Games saved on the db.
	 */
	public static ArrayList<String> getSavedGames() {
		ArrayList<String> gameNames = db.getGameNames();
		return gameNames;
	}
	
	public static ArrayList<Ability> getAbilities() {
		return abilityList;
	}
	
	public static void setAbilities(ArrayList<Ability> newList) {
		abilityList = newList;
	}

	/*
	 * Load a game which has the same name as gameName provided
	 */
	public static void loadGame(String gameName) {
		GameData gameData = db.loadGame(gameName);
			setObstacles(gameData.getObstacles());
			setAbilityList(gameData.getAbilityList());
			setEs(gameData.getEnchantedSphere());
			setNp(gameData.getNoblePhantom());
			setRemainingChances(gameData.getRemainingLives());
			setScore(gameData.getScore()); 
			setTime(gameData.getTime());
			new EnchantedSphereController(enchantedSphere);
			new NoblePhantasmController(noblePhantom);
			startTimer();
	}

	/*
	 * Create an GameData instance from the current game's state and save it to db.
	 */
	public static void saveGame(String gameName) {
		GameData gd = new GameData(gameName, getObstacles(), getAbilityList(), getNp(), getEs(), getScore(), getRemainingChances(),
				getTime());
		db.saveGame(gd);
	}

	/*
	 * This function gets ran every second. It's responsibility is to update the timer based on the status.
	 * And perform timer related functions. Such as checking if it is time to run Ymir and if it is time to remove an ability 
	 * because it's duration has been completed
	 */
	public static void startTimer() {
		timer = new Timer();
		TimerTask tt = new TimerTask() {
			@Override
			public void run() {
				if (status) {
					time += 1;
					// Run ymir's perform function if it is time
					if (time % Constants.YMIR_PERIOD_IN_S == 0) {
						Ymir.perform();
					}
					
					/*
					 *  Iterate the abilityList and remove abilities that hasDuration, isBeingUsed,
					 *  abilityUsedSec + duration is equal to time at the same time.
					 */
					
					Iterator<Ability> it = abilityList.iterator(); 
					// cannot remove from the list while iterating the list, therefore create a list to delete after the deletion
					ArrayList<Ability> toRemoveAbilities = new ArrayList<Ability>();
					while (it.hasNext()) { 
						Ability ability = it.next(); 
						if (ability.isHasDuration() && ability.isBeingUsed()) { 
							if (ability.getAbilityUsedSec() + ability.getDuration() == time) {
								toRemoveAbilities.add(ability);
							} 	 
						} 
					}
					
					for (Ability ability: toRemoveAbilities) {
						ability.removeAbilityFromList();
					}
				}
			};
		};
		timer.scheduleAtFixedRate(tt, 0, Constants.ONE_SEC_IN_MS);
	}
	
	public static void cancelTimer() {
		timer.cancel();
	}

	public static ArrayList<Ability> getAbilityList() {
		return abilityList;
	}

	public static void setAbilityList(ArrayList<Ability> abilityList) {
		GameMain.abilityList = abilityList;
	}
	
	public static void restartAndLoadGame(String gameName) {
		resetGameData();
		loadGame(gameName);
	}
	

}
