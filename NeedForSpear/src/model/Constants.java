package model;

import java.util.ArrayList;
import java.util.Arrays;

import model.controller.GameController;

public class Constants {

	public static final int OBSTACLE_WIDTH = 30;
	public static final int OBSTACLE_HEIGHT = 10;
	public static final int CIRCLE_OBSTACLE_WIDTH = 15;
	
	public static final int ONE_SEC_IN_MS = 1000;
	public static final int YMIR_DELAY_IN_MS = 5000;
	public static final int YMIR_PERIOD_IN_MS = 30000;
	public static final int YMIR_PERIOD_IN_S = YMIR_PERIOD_IN_MS / ONE_SEC_IN_MS;

	public static final int NOBLE_PHANTOM_EXPANSION_DURATION = 30;
	public static final int MAGICAL_HEX_DURATION = 30;
	public static final int UNSTOPPABLE_ENCHANTED_SPHERE_DURATION = 30;
	public static final int INFINITE_VOID_DURATION = 15;
	public static final int DOUBLE_ACCEL_DURATION = 15;
	
	public static final int FRAME_X = 1124;
	public static final int FRAME_Y = 768;
	
	public static final double EXPLOSIVE_RADIUS = 50;

	public static final String HollowPurpleObstacleClassName = "HollowPurpleObstacle";

	
	public static final ArrayList<String> YMIR_ABILITY_NAMES = new ArrayList<String>(			
			Arrays.asList("InfiniteVoid",
            "DoubleAccel",
            "HollowPurple") 
			); 

	public static final String[] abilityTypes = {
		 "ChanceGiving",
		 "MagicalHex",
		 "NoblePhantasmExpansion",
		 "UnstoppableEnchantedSphere",
		 "InfiniteVoid",
		 "DoubleAccel",
		 "HollowPurple"
	};
	public static final String[] nonYmirAbilityTypes = {
			 "ChanceGiving",
			 "MagicalHex",
			 "NoblePhantasmExpansion",
			 "UnstoppableEnchantedSphere"
		};
	
}
