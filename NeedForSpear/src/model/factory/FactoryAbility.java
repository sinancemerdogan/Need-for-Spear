package model.factory;

import model.abilities.*;

public class FactoryAbility {

	// implementing singleton obstacle factory

	private FactoryAbility() {
		// empty constructor
	}
	
	private static volatile FactoryAbility instance = null;

	public static FactoryAbility getInstance() {
		if (instance == null) {
			synchronized (FactoryAbility.class) {
				if (instance == null) {
					instance = new FactoryAbility();
				}
			}
		}
		return instance;
	}
	
	public Ability generateAbility(String abilityType) {
		Ability newAbility = null;
		if (abilityType.equals("ChanceGiving")) {
			newAbility = new ChanceGiving();
		} else if (abilityType.equals("MagicalHex")) {
			newAbility = new MagicalHex();
		} else if (abilityType.equals("NoblePhantasmExpansion")) {
			newAbility = new NoblePhantasmExpansion();
		} else if (abilityType.equals("UnstoppableEnchantedSphere")) {
			newAbility = new UnstoppableEnchantedSphere();
		} else if (abilityType.equals("InfiniteVoid")) {
			newAbility = new InfiniteVoid();
		} else if (abilityType.equals("DoubleAccel")) {
			newAbility = new DoubleAccel();
		} else if (abilityType.equals("HollowPurple")) {
			newAbility = new HollowPurple();
		}
		return newAbility;
	}	
}
