package model.abilities;

import model.Build;
import model.Constants;
import model.GameMain;

public class HollowPurple extends Ability {

	/*
	 * Since Ymir uses the ability upon creation we call useAbility inside the constructor
	 * Since it does not have a duration and gets activated automatically we remove the ability from the list inside the constructor 
	 */
    public HollowPurple() {
    	super();
    	hasDuration = false;
		useAbility();
		removeAbilityFromList();
	}
	
	@Override
	public void performSpecificAbilityAction() {
		Build.addOneTypeOfObstacle("HollowPurpleObstacle", 8);
	}
	
}
