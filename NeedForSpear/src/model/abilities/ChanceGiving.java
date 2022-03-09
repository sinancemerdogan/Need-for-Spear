package model.abilities;

import model.GameMain;

public class ChanceGiving extends Ability{

	/*
	 * Since we use the ability upon creation we call useAbility inside the constructor
	 * Since it does not have a duration and gets activated automatically we remove the ability from the list inside the constructor 
	 */
	public ChanceGiving() {
		super();
		hasDuration = false;
		useAbility();
		removeAbilityFromList();
	}
	
	/*
	 * Specific behaviour of the ability
	 */
	@Override
	public void performSpecificAbilityAction() {
		GameMain.setRemainingChances(GameMain.getRemainingChances() + 1);
	}
}
