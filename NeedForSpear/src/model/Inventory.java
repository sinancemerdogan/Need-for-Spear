package model;

import java.util.ArrayList;

import model.abilities.Ability;

/*
 * Class for UI to interact and get data about ability inventory which is a arrayList in the GameMain class
 */
public class Inventory {
	
	/*
	 * Function for player to use an ability.
	 * Gets the first unused ability and runs useAbility() function of the ability instance.
	 */
	public static void playerUsesAbility(Class<?> abilityClass) {
		Ability ability = getFirstUnusedAbilityWithSameClassFromInventory(abilityClass);
		if (ability != null) {
			ability.useAbility();
		} else {
			System.out.println("Cannot use the ability " + abilityClass + " because it is not in the inventory.");
		}
	}
	
	/*
	 * Get first ability with correct type that is not beignUsed
	 */
	private static Ability getFirstUnusedAbilityWithSameClassFromInventory(Class<?> abilityClass) {
		for (Ability ability: GameMain.abilityList) {
			if (ability.getClass().equals(abilityClass)) {
				if (!ability.isBeingUsed()) {
					return ability;	
				}
			}
		}
		return null;
	}
	
	/*
	 * Given an abilityName get the amount of abilities that are not beingUsed.
	 */
	public static int getAbilityAmountForInventory(String abilityName) {
		int abilityAmount = 0;
		for (Ability ability: GameMain.abilityList) {
			if (ability.getClass().getSimpleName().equals(abilityName)) {
				if (!ability.isBeingUsed()) {
					abilityAmount++;
				}
			}
		}
		return abilityAmount;
	}
}
