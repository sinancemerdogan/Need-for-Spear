package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Arrays;

import model.abilities.Ability;
import model.factory.FactoryAbility;

/*
 * Class for Phase 2 to implement Ymir functionality
 */
public class Ymir {
	// List of abilities that could be created by Ymir.
	private List<String> abilityList = Constants.YMIR_ABILITY_NAMES;
	private Random rand = new Random();


	// Function that will be run every 30 seconds
	public static void perform() {
		Ymir ymir = new Ymir();
		boolean coinFlipResult = ymir.flipCoin();
		if (coinFlipResult) {
			String randomAbilityType = ymir.getRandomAbilityType();
			FactoryAbility.getInstance().generateAbility(randomAbilityType);
		}
	}
	
	private Ymir() {
	}
	
	// Function to give Ymir 50% chance to create an ability
	private boolean flipCoin() {
		double randDouble = this.rand.nextDouble();
		if (randDouble > 0.5) {
			return true;
		}
		return false;
	}
	
	// Function to get a specific type of ability from Ymir specific abilities
	private String getRandomAbilityType() {
		int abilityIndex = this.rand.nextInt(abilityList.size());
		return abilityList.get(abilityIndex);
	}
}
