package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.factory.FactoryObstacle;
import model.obstacle.ExplosiveObstacle;
import model.obstacle.FirmObstacle;
import model.obstacle.GiftObstacle;
import model.obstacle.Obstacle;
import model.obstacle.SimpleObstacle;

class GenerateObstacleTest {
	static Obstacle simpleObs;
	static Obstacle giftObs;
	static Obstacle firmObs;
	static Obstacle explosiveObs;
	
	//Author: Tolgay Dülger

	@BeforeAll
	public static void setUp() {
		simpleObs = FactoryObstacle.getInstance().generateObstacle("SimpleObstacle", 100, 100, 1, false);
		giftObs = FactoryObstacle.getInstance().generateObstacle("GiftObstacle", 200, 200, 1, false);
		firmObs = FactoryObstacle.getInstance().generateObstacle("FirmObstacle", 300, 300, 2, 5, true);
		explosiveObs = FactoryObstacle.getInstance().generateObstacle("ExplosiveObstacle", 400, 400, 2, 15, true);
	}
	
	@Test
	/*
	 * Test the creation of obstacle factory 
	 */
	void getInstanceTest() {
		FactoryObstacle obsFactory = FactoryObstacle.getInstance();
		assertTrue(obsFactory instanceof FactoryObstacle);
	}
	
	@Test
	/*
	 * Test creation of the different types of obstacles
	 */
	void simpleObstacleGenerationTest() {
		
		assertTrue(simpleObs instanceof SimpleObstacle);
		assertTrue(giftObs instanceof GiftObstacle);
		assertTrue(firmObs instanceof FirmObstacle);
		assertTrue(explosiveObs instanceof ExplosiveObstacle);
	}
	
	@Test 
	/*
	 * Test obstacles are created at the correct locations
	 */
	void createdObstacleLocationTest() {
		
		assertEquals(100, simpleObs.getPosx());
		assertEquals(100, simpleObs.getPosy());
		
		assertEquals(200, giftObs.getPosx());
		assertEquals(200, giftObs.getPosy());
		
		assertEquals(300, firmObs.getPosx());
		assertEquals(300, firmObs.getPosy());

	}
	
	@Test 
	/*
	 * Test obstacles are created in correct shapes
	 */
	void createdObstacleShapeTest() {
		
		assertEquals(1, simpleObs.getShape());
		assertEquals(1, giftObs.getShape());
		
		assertEquals(2, firmObs.getShape());
		assertEquals(2, explosiveObs.getShape());	
	}
	
	@Test
	/*
	 * Test obstacles are created with correct hit numbers
	 */
	void createdObstacleRemainingHitTest() {
		
		assertEquals(1, simpleObs.getRemainingHit());
		assertEquals(5, firmObs.getRemainingHit());
		
	}
	
	

}
