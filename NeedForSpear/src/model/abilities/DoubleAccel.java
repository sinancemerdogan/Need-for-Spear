package model.abilities;

import java.util.Timer;
import java.util.TimerTask;

import model.Constants;
import model.EnchantedSphere;
import model.GameMain;

public class DoubleAccel extends Ability {
	
	/*
	 * Since Ymir uses the ability upon creation we call useAbility inside the constructor
	 */
    public DoubleAccel() {
    	super();
    	hasDuration = true;
    	duration = Constants.DOUBLE_ACCEL_DURATION;
		useAbility();
	}
	
    
	@Override
	public void performSpecificAbilityAction() {
		EnchantedSphere es = GameMain.getEs();
		double xdir = es.getSphereXdir();
		double ydir = es.getSphereYdir();
		double[] newSpeed = {xdir / 2.0, ydir / 2.0};
		es.setSpeed(newSpeed);
	}
	
	
	@Override
	public void revertSpecificAbilityAction() {
		EnchantedSphere es = GameMain.getEs();
		double xdir = es.getSphereXdir();
		double ydir = es.getSphereYdir();
		double[] newSpeed = { xdir * 2.0, ydir * 2.0};
		es.setSpeed(newSpeed);
	}


}
