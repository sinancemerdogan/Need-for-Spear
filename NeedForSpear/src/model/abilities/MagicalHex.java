package model.abilities;

import java.util.Timer;
import java.util.TimerTask;

import model.Constants;
import model.EnchantedSphere;
import model.GameMain;
import model.NoblePhantasm;

public class MagicalHex extends Ability {

	/*
	 * Since this ability gets used by the player whenever they want we do not call useAbilty or removeAbilityFromList
	 */
	public MagicalHex() {
		super();
		hasDuration = true;
		duration = Constants.MAGICAL_HEX_DURATION;
	}

	@Override
	public void performSpecificAbilityAction() {
		NoblePhantasm np = GameMain.getNp();
		np.addCannons();
	}
	
	@Override
	public void revertSpecificAbilityAction() {
		NoblePhantasm np = GameMain.getNp();
		np.removeCannons();
	}

}
