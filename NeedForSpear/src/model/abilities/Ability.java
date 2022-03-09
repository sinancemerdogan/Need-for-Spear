package model.abilities;

import java.io.Serializable;

import model.GameMain;

public class Ability implements Serializable {
	private static final long serialVersionUID = 6L;
	
	// By design when we are done with the ability we remove it from the abilityList
	/*
	 * isBeingUsed helps us to indicate if user can use this ability or 
	 * it is already been used but waiting in the abilityList to perform their revertFunction
	 */
	protected boolean isBeingUsed = false;
	/*
	 * abilityUsedSec allows us to know when we have used the ability 
	 * so that we can run the revert function of the ability after it's duration
	 */
	protected int abilityUsedSec;
	/*
	 * some abilities have duration some not
	 */
	protected boolean hasDuration;
	/*
	 * These durations are 15, 30 sec indicated as in the project description
	 */
	protected int duration;
	protected boolean isUsed;
	
	/*
	 * When an ability gets created by the Ability Factory it automatically gets added to the abilityList (also known as the inventory)
	 */
	public Ability() {
		GameMain.abilityList.add(this);
	}
	
	/*
	 *  Abilities have some common part when we want to use an ability and some differentiations.
	 *  For each ability we should update abilityUsedSec and update isBeingUsed.
	 *  But depending on the ability type we should also perform different things, which are handled by the performSpecificAbilityAction()
	 *  So this function is the general function to activate an ability, assuming that performSpecificAbilityAction() function has been 
	 *  implemented specifically for the ability
	 */
	public void useAbility() {
		abilityUsedSec = GameMain.time;
		isBeingUsed = true;
		performSpecificAbilityAction();
	}
	
	/*
	 * This function differs for each ability type and should be overwritten in each ability class
	 * This is where we write abilities actual function
	 */
	public void performSpecificAbilityAction() {
		
	}
	
	/*
	 * After using an ability we should remove it from the abilityList at some time.
	 * Some abilities, such as NoblePhantasmExpansion, have specific actions when getting removed.
	 * So this function is the general function to remove an ability from the list 
	 * and allow different abilities to perform specific revertion function on the way.
	 */
	public void removeAbilityFromList() {
		revertSpecificAbilityAction();
		GameMain.abilityList.remove(this);
	}

	/*
	 * Some abilities, such as NoblePhantasmExpansion, has a specific effect we should run when we delete them from the abilityList.
	 */
	public void revertSpecificAbilityAction() {
		
	}

	public boolean isHasDuration() {
		return hasDuration;
	}

	public void setHasDuration(boolean hasDuration) {
		this.hasDuration = hasDuration;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getAbilityUsedSec() {
		return abilityUsedSec;
	}

	public void setAbilityUsedSec(int abilityUsedSec) {
		this.abilityUsedSec = abilityUsedSec;
	}

	public boolean isBeingUsed() {
		return isBeingUsed;
	}

	public void setBeingUsed(boolean isBeingUsed) {
		this.isBeingUsed = isBeingUsed;
	}
}
