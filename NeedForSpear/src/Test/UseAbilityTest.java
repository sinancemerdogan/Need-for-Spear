package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import model.Constants;
import model.EnchantedSphere;
import model.GameMain;
import model.abilities.Ability;
import model.factory.FactoryAbility;
import model.NoblePhantasm;

class UseAbilityTest {
	static Ability chanceGivingAbility;
	static Ability doubleAccel;
	static Ability hollowPurple;
	static Ability infiniteVoid;
	static Ability magicalHex;
	static Ability noblePhantomExpansion;
	static Ability unstoppableEnchantedSphere;
	
	/*
	 * Create abilities before each test to make sure they are not changed by other tests
	 */
	@BeforeEach
	void refresh() {
		chanceGivingAbility = FactoryAbility.getInstance().generateAbility("ChanceGiving");
		doubleAccel = FactoryAbility.getInstance().generateAbility("DoubleAccel");
		hollowPurple = FactoryAbility.getInstance().generateAbility("HollowPurple");
		infiniteVoid = FactoryAbility.getInstance().generateAbility("InfiniteVoid");
		magicalHex = FactoryAbility.getInstance().generateAbility("MagicalHex");
		noblePhantomExpansion = FactoryAbility.getInstance().generateAbility("NoblePhantasmExpansion");
		unstoppableEnchantedSphere = FactoryAbility.getInstance().generateAbility("UnstoppableEnchantedSphere");
	}

	/* 
	 * Make sure that when useAbility() runs, current GameMain.time is the ability's abilityUsedSec
	 */
	@Test() 
	void abilityUsedSecTest() { 
		chanceGivingAbility.useAbility();
		assertEquals(GameMain.time, chanceGivingAbility.getAbilityUsedSec());
		doubleAccel.useAbility();
		assertEquals(GameMain.time, doubleAccel.getAbilityUsedSec());
		hollowPurple.useAbility();
		assertEquals(GameMain.time, hollowPurple.getAbilityUsedSec());
		infiniteVoid.useAbility();
		assertEquals(GameMain.time, infiniteVoid.getAbilityUsedSec());
		magicalHex.useAbility();
		assertEquals(GameMain.time, magicalHex.getAbilityUsedSec());
		noblePhantomExpansion.useAbility();
		assertEquals(GameMain.time, noblePhantomExpansion.getAbilityUsedSec());
		unstoppableEnchantedSphere.useAbility();
		assertEquals(GameMain.time, unstoppableEnchantedSphere.getAbilityUsedSec());
	}
	
	/*
	 * Some abilities (chanceGiving, doubleAccel, hollowPurple, infiniteVoid) ran the useAbility function when they are getting initialized
	 * they when we ran the beforeEach, those ones should have the isBeingUsed fields as true already. But others should have the field as 
	 * false because they are triggered abilities. When they get used their isBeingUsed field shoukld turn true.
	 */
	@Test() 
	void isBeingUsedTest() {
		// those 4 should already be true because they run the function 'useAbility' 
		// when they are being created while others do not run that function
		assertTrue(chanceGivingAbility.isBeingUsed());
		assertTrue(doubleAccel.isBeingUsed());
		assertTrue(hollowPurple.isBeingUsed());
		assertTrue(infiniteVoid.isBeingUsed());
		assertFalse(magicalHex.isBeingUsed());
		assertFalse(noblePhantomExpansion.isBeingUsed());
		assertFalse(unstoppableEnchantedSphere.isBeingUsed());
		chanceGivingAbility.useAbility();
		doubleAccel.useAbility();
		hollowPurple.useAbility();
		infiniteVoid.useAbility();
		magicalHex.useAbility();
		noblePhantomExpansion.useAbility();
		unstoppableEnchantedSphere.useAbility();
		assertTrue(chanceGivingAbility.isBeingUsed());
		assertTrue(doubleAccel.isBeingUsed());
		assertTrue(hollowPurple.isBeingUsed());
		assertTrue(infiniteVoid.isBeingUsed());
		assertTrue(magicalHex.isBeingUsed());
		assertTrue(noblePhantomExpansion.isBeingUsed());
		assertTrue(unstoppableEnchantedSphere.isBeingUsed());
	}
	
	/*
	 * Adding abilities to the GameMain.abilityList is not the responsibility of the useAbility function, 
	 * constructor of the Ability class is responsible for that task. We make sure that useAbility() does not change the size of the ArrayList
	 */
	@Test 
	void shouldNotAddDeleteFromListTest() {
		int size = GameMain.abilityList.size();
		chanceGivingAbility.useAbility();
		assertEquals(size, GameMain.abilityList.size());
		doubleAccel.useAbility();
		assertEquals(size, GameMain.abilityList.size());
		hollowPurple.useAbility();
		assertEquals(size, GameMain.abilityList.size());
		infiniteVoid.useAbility();
		assertEquals(size, GameMain.abilityList.size());
		magicalHex.useAbility();
		assertEquals(size, GameMain.abilityList.size());
		noblePhantomExpansion.useAbility();
		assertEquals(size, GameMain.abilityList.size());
		unstoppableEnchantedSphere.useAbility();
		assertEquals(size, GameMain.abilityList.size());
	}
	
	/*
	 * We test if the implemented 3 abilities work as expected, 
	 * 
	 * Ex: 
	 * 		* chanceGiving, increment's the player's chances
	 *  	* nobleExpansion, expands the noblePhantom
	 */
	@Test 
	void performsAbilitySpecificActionTest() {
		// chanceGivingAbility
		int remainingLives = GameMain.getRemainingChances();
		chanceGivingAbility.useAbility();
		assertEquals(remainingLives + 1, GameMain.getRemainingChances());
		
		
		// doubleAccel
		doubleAccel.useAbility();
		EnchantedSphere es = GameMain.getEs();
		double speedXAfter = es.getSphereXdir();
		double speedYAfter = es.getSphereYdir();
		//assertEquals(speedXAfter, Constants.ES_SLOW_SPEED[0]);
		//assertEquals(speedYAfter, Constants.ES_SLOW_SPEED[1]);
		
		// noblePhantamExpansion
		NoblePhantasm np = GameMain.getNp();
		int initWidth = np.getWidth();
		noblePhantomExpansion.useAbility();
		assertEquals(initWidth * 2, np.getWidth());
	}
	
	/*
	 * Some abilities (chanceGiving, doubleAccel, hollowPurple, infiniteVoid) ran the useAbility function when they are getting initialized
	 * we check if the cause of updating the abilityUsedSec property is updated after initializing these Abilities
	 */
	@Test
	void useAbiltiyRanWhenInitializingAbilityTest() {
		Ability newChanceGiving = FactoryAbility.getInstance().generateAbility("ChanceGiving");
		assertEquals(GameMain.time, newChanceGiving.getAbilityUsedSec());
		
		Ability newDoubleAccel = FactoryAbility.getInstance().generateAbility("DoubleAccel");
		assertEquals(GameMain.time, newDoubleAccel.getAbilityUsedSec());
		
		Ability newHollowPurple = FactoryAbility.getInstance().generateAbility("HollowPurple");
		assertEquals(GameMain.time, newHollowPurple.getAbilityUsedSec());
		
		Ability newInfiniteVoid = FactoryAbility.getInstance().generateAbility("InfiniteVoid");
		assertEquals(GameMain.time, newInfiniteVoid.getAbilityUsedSec());
	}


}
