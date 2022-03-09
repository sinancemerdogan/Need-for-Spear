package model.controller;

import java.util.ArrayList;

import model.Build;
import model.CollisionHandler;
import model.GameMain;
import model.Inventory;
import model.Ymir;
import model.abilities.Ability;
import model.abilities.InfiniteVoid;
import model.abilities.MagicalHex;
import model.abilities.NoblePhantasmExpansion;
import model.abilities.UnstoppableEnchantedSphere;
import model.database.GameData;
import model.obstacle.Obstacle;
import model.obstacle.Particle;
import ui.GameUI;


public class GameController {
	
	public GameController() {
		
	}
	
	public void changeStatus() {
		GameMain.changeStatus();
	}
	
	public boolean getStatus() {
		return GameMain.getStatus();
	}
	
	public static ArrayList<Obstacle> getObstacles() {
		return GameMain.getObstacles();
	}
	
	public void setObstacles(ArrayList<Obstacle> obstacles) {
			GameMain.setObstacles(obstacles);
	}

	public static ArrayList<Particle> getParticles() {
		return CollisionHandler.getParticles();
	} 
	
	public ArrayList<Ability> getAbilities() {
		return GameMain.getAbilities();
	} 
	
	public void collisionHandler() {
		CollisionHandler.collisionHandler();
	}
	
	public EnchantedSphereController esController() {
		return GameMain.esController();
	}
	
	public NoblePhantasmController npController() {
		return GameMain.npController();
	}

	public int getScore() {
		return GameMain.score;
	}
	
	public int getNumberOfChances() {
		return GameMain.getRemainingChances();
	}
	
	public void changeMode() {
		GameMain.changeMode();
	}
	
	public String getMode() {
		return GameMain.getMode();
	}

	public static int getFRAME_X() {
		return GameUI.FRAME_X;
	}
	
	public static int getFRAME_Y() {
		return GameUI.FRAME_Y;
	}
	
	public boolean lose() {
		return GameMain.lose();
	}


	public void save(String saveName) {
		GameMain.saveGame(saveName);
	}
	
	public void load(String gameName) {
		GameMain.loadGame(gameName);
	}
	
	public ArrayList<String> getSavedNames() {
		return GameMain.getSavedGames();
	}
	
	public void startTimer() {
		GameMain.startTimer();
	}
	
	public void cancelTimer() {
		GameMain.cancelTimer();
	}
	//Build Mode Controllers
	
	public void addObstacles(int nOfSimple, int nOfFirm, int nOfExp, int nOfGift) {
		Build.addObstacles(nOfSimple, nOfFirm, nOfExp, nOfGift);
	}

	public void addSingleObstacle(String type, int posx, int posy) {
		Build.addSingleObstacle(type, posx, posy);
	}
	
	public void removeObstacle(int index) {
		Build.removeObstacle(index);
	}
	
	public void setMaxObstacles(boolean b) {
		Build.setMaxObstacles(b);
	}
	
	public boolean canAdd(double currentX, double currentY) {
		Build.canAdd(currentX, currentY);
		return Build.noOverlap;
	}
	public boolean getMaxObstacles() {
		return Build.getMaxObstacles();
	}
	
	public void resetGame() {
		GameMain.resetGameData();
	}
	
	public int getTime() {
		return GameMain.time;
	}
	
	//Ability Controllers
	
	public void useNoblePhantasmExpansion() {
		Inventory.playerUsesAbility(NoblePhantasmExpansion.class);
	}
	
	public void useMagicalHex() {
		Inventory.playerUsesAbility(MagicalHex.class);
	}
	
	public void useUnstoppable() {
		Inventory.playerUsesAbility(UnstoppableEnchantedSphere.class);
	}
	
	public int getSpecificUnusedAbiltyAmount(String abilityName) {
		return Inventory.getAbilityAmountForInventory(abilityName);
	}

}
