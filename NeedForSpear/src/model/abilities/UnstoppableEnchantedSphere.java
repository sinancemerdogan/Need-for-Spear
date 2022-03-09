package model.abilities;

import java.util.Timer;
import java.util.TimerTask;

import model.Constants;
import model.EnchantedSphere;
import model.GameMain;

public class UnstoppableEnchantedSphere extends Ability {
	
	/*
	 * Since this ability gets used by the player whenever they want we do not call useAbilty or removeAbilityFromList
	 */
	public UnstoppableEnchantedSphere() {
		super();
		hasDuration = true;
		duration = Constants.UNSTOPPABLE_ENCHANTED_SPHERE_DURATION;
	}
	
	@Override
	public void performSpecificAbilityAction() {
		EnchantedSphere sphere = GameMain.getEs();
		sphere.setUnstoppable();
	}
	
	@Override
	public void revertSpecificAbilityAction() {
		EnchantedSphere es = GameMain.getEs();
		es.setNormal();
	}


}
