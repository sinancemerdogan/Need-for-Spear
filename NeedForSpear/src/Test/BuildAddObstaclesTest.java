package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Build;
import model.GameMain;
import model.factory.FactoryObstacle;
import model.obstacle.ExplosiveObstacle;
import model.obstacle.FirmObstacle;
import model.obstacle.GiftObstacle;
import model.obstacle.Obstacle;
import model.obstacle.SimpleObstacle;

class BuildAddObstaclesTest {
	private Build buildHandler = new Build();
	private Obstacle obstacle;
	private int width = 1124 - 45;
	private int height = 768;
	
	@BeforeEach
	void refresh() {
		GameMain.obstacleList.clear();
	}

	@Test
	/*
	 * Test creation of the different types of obstacles in the addObstacles function 
	 */
	void obstacleTypeTest() {
		buildHandler.addObstacles(1, 0, 0, 0);
		obstacle =  GameMain.obstacleList.get(0); 
		assertTrue(obstacle instanceof SimpleObstacle);
		
		buildHandler.addObstacles(0, 1, 0, 0);
		obstacle =  GameMain.obstacleList.get(1); 
		assertTrue(obstacle instanceof FirmObstacle);
		
		buildHandler.addObstacles(0, 0, 1, 0);
		obstacle =  GameMain.obstacleList.get(2); 
		assertTrue(obstacle instanceof ExplosiveObstacle);
		
		buildHandler.addObstacles(0, 0, 0, 1);
		obstacle =  GameMain.obstacleList.get(3); 
		assertTrue(obstacle instanceof GiftObstacle);
		
	}
	
	@Test
	/*
	 * Test the positions of the created obstacles. 
	 * Obstacles' x and y position cannot be greater than the size of screen.
	 */
	void createdObstaclePositionTest() {
		buildHandler.addObstacles(1, 1, 1, 1);
		
		for (Obstacle o : GameMain.obstacleList) {
			assertTrue(0 <= o.getPosx() && o.getPosx() <= width);
			assertTrue(0 <= o.getPosy() && o.getPosy() <= height);
		}
	}
	
	@Test
	/*
	 * Test whether an obstacle already exists in the randomly selected location or not.
	 * If an obstacle exists, then choose a new location for obstacle. 
	 */
	void canAddTest() {
		obstacle = FactoryObstacle.getInstance().generateObstacle("SimpleObstacle", 0 , 0, 1, false);
		GameMain.obstacleList.add(obstacle);
		
		buildHandler.canAdd(0,0);
		assertFalse(buildHandler.noOverlap);
		
		buildHandler.canAdd(100,100);
		assertTrue(buildHandler.noOverlap);
		
	}
	
	@Test
	/*
	 * General obstacle addition test.
	 * Test the number of the added obstacles, types.
	 */
	void generalAddObstaclesTest() {
		buildHandler.addObstacles(2, 3, 4, 5);
		
		//Check size
		assertEquals(14, GameMain.obstacleList.size());
		
		//Check types and positions in the list
		for(int i = 0; i < GameMain.obstacleList.size(); i++) {
			if(i < 2) {
				assertTrue(GameMain.obstacleList.get(i) instanceof SimpleObstacle);
			}
			else if(i < 5) {
				assertTrue(GameMain.obstacleList.get(i) instanceof FirmObstacle);
			}
			else if(i < 9) {
				assertTrue(GameMain.obstacleList.get(i) instanceof ExplosiveObstacle);	
			}
			else {
				assertTrue(GameMain.obstacleList.get(i) instanceof GiftObstacle);
			}
		}
	}
}
