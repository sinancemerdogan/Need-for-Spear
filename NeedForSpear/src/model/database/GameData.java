package model.database;

import java.io.Serializable;
import java.util.ArrayList;

import model.EnchantedSphere;
import model.NoblePhantasm;
import model.abilities.Ability;
import model.obstacle.Obstacle;

/*
 * Class which indicates what we will be storing in the database for loading and saving a Game
 */
public class GameData implements Serializable {

	private String name;
	private ArrayList<Obstacle> obstacles;
	private NoblePhantasm noblePhantom;
	private EnchantedSphere enchantedSphere;
	private int score;
	private int remainingLives;
	private int time;
	private ArrayList<Ability> abilityList;
	private static final long serialVersionUID = 12L;

	public GameData(String name, ArrayList<Obstacle> obstacles, ArrayList<Ability> abilityList, NoblePhantasm noblePhantom, EnchantedSphere enchantedSphere,
			int score, int remainingLives, int time) {
		this.name = name;
		this.obstacles = obstacles;
		this.abilityList = abilityList;
		this.noblePhantom = noblePhantom;
		this.enchantedSphere = enchantedSphere;
		this.score = score;
		this.remainingLives = remainingLives;
		this.time = time;
	}

	public ArrayList<Obstacle> getObstacles() {
		return obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		this.obstacles = obstacles;
	}

	public NoblePhantasm getNoblePhantom() {
		return noblePhantom;
	}

	public void setNoblePhantom(NoblePhantasm noblePhantom) {
		this.noblePhantom = noblePhantom;
	}

	public EnchantedSphere getEnchantedSphere() {
		return enchantedSphere;
	}

	public void setEnchantedSphere(EnchantedSphere enchantedSphere) {
		this.enchantedSphere = enchantedSphere;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRemainingLives() {
		return remainingLives;
	}

	public void setRemainingLives(int remainingLives) {
		this.remainingLives = remainingLives;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Ability> getAbilityList() {
		return abilityList;
	}

	public void setAbilityList(ArrayList<Ability> abilityList) {
		this.abilityList = abilityList;
	}
	
}
