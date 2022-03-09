package model.abilities;

import model.Constants;
import model.GameMain;
import model.NoblePhantasm;
import java.util.Timer;
import java.util.TimerTask;

public class NoblePhantasmExpansion extends Ability {
	
	/*
	 * Since this ability gets used by the player whenever they want we do not call useAbilty or removeAbilityFromList
	 */
	public NoblePhantasmExpansion() {
		super();
		hasDuration = true;
		duration = Constants.NOBLE_PHANTOM_EXPANSION_DURATION;
	}
	
	@Override
	public void performSpecificAbilityAction() {
		NoblePhantasm np = GameMain.getNp();
		np.setWidth(np.getWidth() * 2);
	}
	
	@Override
	public void revertSpecificAbilityAction() {
		NoblePhantasm np = GameMain.getNp();
		np.setWidth(np.getWidth() / 2);
	}

}
