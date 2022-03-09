package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.GameMain;
import model.abilities.*;
import model.factory.FactoryAbility;

class GenerateAbilityTest {
	static Ability chanceGiving, magicalHex, expansion, godmode, infVoid, doubleAccel, hollowPurple;

	@BeforeAll
	public static void setUp() {
		chanceGiving = FactoryAbility.getInstance().generateAbility("ChanceGiving");
		magicalHex = FactoryAbility.getInstance().generateAbility("MagicalHex");
		expansion = FactoryAbility.getInstance().generateAbility("NoblePhantasmExpansion");
		godmode = FactoryAbility.getInstance().generateAbility("UnstoppableEnchantedSphere");
		infVoid = FactoryAbility.getInstance().generateAbility("InfiniteVoid");
		doubleAccel = FactoryAbility.getInstance().generateAbility("DoubleAccel");
		hollowPurple = FactoryAbility.getInstance().generateAbility("HollowPurple");
	}

	@Test
	/*
	 * Test the creation of ability factory
	 */
	void getInstanceTest() {
		FactoryAbility obsFactory = FactoryAbility.getInstance();
		assertTrue(obsFactory instanceof FactoryAbility);
	}

	@Test
	/*
	 * Test creation of the different types of abilities
	 */
	void abilityGenerationTest() {
		assertTrue(chanceGiving instanceof ChanceGiving);
		assertTrue(magicalHex instanceof MagicalHex);
		assertTrue(expansion instanceof NoblePhantasmExpansion);
		assertTrue(godmode instanceof UnstoppableEnchantedSphere);
		assertTrue(infVoid instanceof InfiniteVoid);
		assertTrue(doubleAccel instanceof DoubleAccel);
		assertTrue(hollowPurple instanceof HollowPurple);
	}

	@Test
	/*
	 * Test abilities have the correct durations
	 */
	void createdAbilityDurationTest() {

		assertEquals(30, expansion.getDuration());
		assertEquals(30, magicalHex.getDuration());
		assertEquals(30, godmode.getDuration());
		assertEquals(15, infVoid.getDuration());
		assertEquals(15, doubleAccel.getDuration());

	}

	@Test
	/*
	 * Test expansion ability correctly expand the noblePhantasm
	 */
	void expansionTest() {
		int paddleW = GameMain.getNp().getWidth();
		expansion.performSpecificAbilityAction();
		int paddleWUpdated = GameMain.getNp().getWidth();
		assertEquals(2, paddleWUpdated / paddleW);
		expansion.revertSpecificAbilityAction();
		assertEquals(1, paddleW / GameMain.getNp().getWidth());
	}

}
