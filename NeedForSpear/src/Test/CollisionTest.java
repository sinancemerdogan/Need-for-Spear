package Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.GameMain;
import model.NoblePhantasm;
import model.controller.GameController;
import model.factory.FactoryObstacle;
import model.factory.FactoryParticle;
import model.EnchantedSphere;
import model.CollisionHandler;
import model.GameMain;
import model.obstacle.Obstacle;
import model.obstacle.Particle;

class CollisionTest {
	static int topleftX1, topleftY1, topleftX2, topleftY2, ballX, ballY;
	static double speedX, speedY;
	
	static Obstacle simpleObstacle;
	static Obstacle giftObstacle;
	static Obstacle firmObstacle;
	static Obstacle explosiveObstacle;
	
	static Particle giftParticle;
    static Particle explosiveParticle;
    
    static ArrayList<Particle> particleList;

    
    
    
	@BeforeAll
	public static void setUp() {
		// two obstacles to check edge collision test and corner collision test
		topleftX1 = 100;
		topleftY1 = 100;
		topleftX2 = 200;
		topleftY2 = 100;
		ballX = 0;
		ballY = 0;
		
		particleList = new ArrayList<Particle>();

	}

	@BeforeEach
	void beforeEach() {
		assertTrue(CollisionHandler.repOk());
		CollisionHandler.particleList.clear();
		topleftX1 = 100;
		topleftY1 = 100;
		topleftX2 = 200;
		topleftY2 = 100;
		ballX = 0;
		ballY = 0;

	}

	@AfterEach
	void afterEach() {

	}

	@Test
	void collisionEdgesTest() {
		/*
		 * let ball be in location (120, 110),then it should collide with the top edge
		 * of the first obstacle, and nothing else.
		 */
		ballX = 120;
		ballY = 90;
		speedX = 1;
		speedY = 1;
		// first obstacle
		assertTrue(CollisionHandler.collideCheckTop(topleftX1, topleftY1, ballX, ballY, speedY));
		assertFalse(CollisionHandler.collideCheckBot(topleftX1, topleftY1, ballX, ballY, speedY));
		assertFalse(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		assertFalse(CollisionHandler.collideCheckRight(topleftX1, topleftY1, ballX, ballY, speedX));

		// second obstacle
		assertFalse(CollisionHandler.collideCheckTop(topleftX2, topleftY2, ballX, ballY, speedY));
		assertFalse(CollisionHandler.collideCheckBot(topleftX2, topleftY2, ballX, ballY, speedY));
		assertFalse(CollisionHandler.collideCheckLeft(topleftX2, topleftY2, ballX, ballY, speedX));
		assertFalse(CollisionHandler.collideCheckRight(topleftX2, topleftY2, ballX, ballY, speedX));
		
		/*
		 * to also check sides, let ball be at (90,100). It should only collide with the left edge of the first
		 * obstacle. 
		 */
		ballX = 90;
		ballY = 100;
		assertFalse(CollisionHandler.collideCheckTop(topleftX1, topleftY1, ballX, ballY, speedY));
		assertFalse(CollisionHandler.collideCheckBot(topleftX1, topleftY1, ballX, ballY, speedY));
		assertTrue(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		assertFalse(CollisionHandler.collideCheckRight(topleftX1, topleftY1, ballX, ballY, speedX));
		
		//second obstacle
		
		assertFalse(CollisionHandler.collideCheckTop(topleftX2, topleftY2, ballX, ballY, speedY));
		assertFalse(CollisionHandler.collideCheckBot(topleftX2, topleftY2, ballX, ballY, speedY));
		assertFalse(CollisionHandler.collideCheckLeft(topleftX2, topleftY2, ballX, ballY, speedX));
		assertFalse(CollisionHandler.collideCheckRight(topleftX2, topleftY2, ballX, ballY, speedX));
		
		/*
		 * Now, to limit test collision, we will be decrementing the x coordinate of the ball one by one,
		 * and observe the results.
		 */
		
		ballX--;
		assertTrue(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		ballX--;
		assertTrue(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		ballX--;
		assertTrue(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		ballX--;
		assertTrue(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		ballX--;
		assertTrue(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		
		/*
		 * Since the radius of the ball is 15px, now when topleftX1 - ballX = 15, it should not collide.
		 */
		ballX--;
		assertFalse(CollisionHandler.collideCheckLeft(topleftX1, topleftY1, ballX, ballY, speedX));
		
		
	}

	@Test
	void collisionCornersTest() {
		/*
		 * Test the corner collision function. Function returns 1 for top left, 2 for top right, 
		 * 3 for bottom left and 4 for bottom right corner.
		 */
		
		//top-left
		ballX = 90;
		ballY = 90;
		assertEquals(1, CollisionHandler.collideCheckCorner(topleftX1, topleftY1, ballX, ballY));
		
		//top-right
		ballX = 125;
		ballY = 90;
		assertEquals(2, CollisionHandler.collideCheckCorner(topleftX1, topleftY1, ballX, ballY));
		
		//bottom-left
		ballX = 90;
		ballY = 105;
		assertEquals(3, CollisionHandler.collideCheckCorner(topleftX1, topleftY1, ballX, ballY));
		
		//bottom-right
		ballX = 125;
		ballY = 105; 
		assertEquals(4, CollisionHandler.collideCheckCorner(topleftX1, topleftY1, ballX, ballY));
	
	}

	// Helper function to clear the obstacleList, add a new simpleObstacle and adjust EnchantedSphere such that it would collide with the simpleObstacle from top
	void collisionHandlerTestHelperFunctionToSetTopCollision(String obstacleType) {
		// While making this test we assume that collideCheckTop works as expected.
		EnchantedSphere es = GameMain.getEs();
		// Clear the obstacle list to make sure other obstacle somehow effect the current test.
		GameMain.obstacleList.clear();
		simpleObstacle = FactoryObstacle.getInstance().generateObstacle(obstacleType, 110, topleftY1, 0, false);
		GameMain.obstacleList.add(simpleObstacle);
		// Those coordinates imply that enchantedSphere will collide with the obstacle from the top. 
		es.setPosX(120.0);
		es.setPosY(90.0);
		es.setSphereXdir(1.0);
		es.setSphereYdir(1.0);
		es.setNormal();
	}
	
	@Test
	void collisionHandlerTest() {
		//** Test Enchanted Sphere's unstoppable feature, does it actually work as expected. Check with change of directions of the enchantedSphere
		
		// NORMAL ENCHANTED SPHERE
		EnchantedSphere es = GameMain.getEs();
		this.collisionHandlerTestHelperFunctionToSetTopCollision("SimpleObstacle");
		CollisionHandler.collisionHandler();
		// Since Enchanted Sphere collides with obstacle from top, it's sphereYdir get the negative value of the when es is normal (not unstoppable).
		assertEquals(-1.0, es.getSphereYdir());
		
		// UNSTOPPABLE ENCHANTED SPHERE
		this.collisionHandlerTestHelperFunctionToSetTopCollision("SimpleObstacle");
		es.setUnstoppable();
		CollisionHandler.collisionHandler();
		// Since Enchanted Sphere is unstoppable it's directions should not change
		assertEquals(1.0, es.getSphereYdir());
		
		//** Test if in the case of obstacle destruction score increases by 100
		this.collisionHandlerTestHelperFunctionToSetTopCollision("SimpleObstacle");
		int prevScore =  GameMain.score;
		CollisionHandler.collisionHandler();
		// Since the helper function set EnchantedSpehere in such a way that they collide from top, enchantedShere should hit simpleObstacle, therefore destory the obstacle
		//and gains user 100 score
		assertEquals(prevScore + 100, GameMain.score);
		
		//** Test if in the case of EnchantedSphere hits simpleObstacle, therefore destroys it. The obstacleList.size() gets decremented by 1
		this.collisionHandlerTestHelperFunctionToSetTopCollision("SimpleObstacle");
		int prevObstacleListSize = GameMain.obstacleList.size();
		CollisionHandler.collisionHandler();
		assertEquals(prevObstacleListSize - 1, GameMain.obstacleList.size());
		
		//** Test if in the case of EnchantedSphere hits giftObstacle, therefore destroys it. The particleList.size() gets increased by 1
		this.collisionHandlerTestHelperFunctionToSetTopCollision("GiftObstacle");
		int prevParticleListSize = CollisionHandler.particleList.size();
		CollisionHandler.collisionHandler();
		assertEquals(prevParticleListSize + 1, CollisionHandler.particleList.size());
		
		//** Test when two obstacles collide, therefore change direction
		simpleObstacle = FactoryObstacle.getInstance().generateObstacle("SimpleObstacle", 300, topleftY1, 0, true);
		Obstacle simpleObstacle2 = FactoryObstacle.getInstance().generateObstacle("SimpleObstacle", 330, topleftY1, 0, true);
		simpleObstacle2.changeDirection();
		GameMain.obstacleList.add(simpleObstacle);
		GameMain.obstacleList.add(simpleObstacle2);
		CollisionHandler.collisionHandler();
		// Since those coordinates indicate that obstacle would collide with each other 
		// simpleObstacle (which was going right, therefore xDir 1) changed to xDir -1
		// reverse for the simpleObstacle2
		assertEquals(-1, simpleObstacle.getXDir());
		assertEquals(1, simpleObstacle2.getXDir());
		
		//** Test EnchantedSpehere NoblePhantom collision
		es.setPosX(120);
		NoblePhantasm np = GameMain.getNp();
		np.setPosX(100);
		int paddleY = GameController.getFRAME_Y() - 100;
		es.setPosY(paddleY);
		es.setSphereYdir(1.0);
		double prevYdir = es.getSphereYdir();
		CollisionHandler.collisionHandler();
		// After EnchantedSphere collides with Paddle, EnchantedSphere's yDir should be negative of the prev
		assertEquals(-prevYdir, es.getSphereYdir());
	}

	@Test
	void addParticleTest() {

		
		simpleObstacle = FactoryObstacle.getInstance().generateObstacle("SimpleObstacle", 100, 100, 1, false);
		giftObstacle = FactoryObstacle.getInstance().generateObstacle("GiftObstacle", 100, 100, 1, false);
		firmObstacle = FactoryObstacle.getInstance().generateObstacle("FirmObstacle", 100, 100, 1, false);
		explosiveObstacle = FactoryObstacle.getInstance().generateObstacle("ExplosiveObstacle", 100, 100, 1, false);
		
		
		//if obstacle is a instance of SimpleOsbtacle
		CollisionHandler.addParticle(simpleObstacle);
		assertTrue(CollisionHandler.getParticles().isEmpty());
		
		//if obstacle is a instance of FirmObstacle
		CollisionHandler.addParticle(firmObstacle);
		assertTrue(CollisionHandler.getParticles().isEmpty());
		
		//if obstacle is a instance of ExplosiveObstacle
		CollisionHandler.addParticle(explosiveObstacle);
		assertEquals(CollisionHandler.getParticles().get(0).getType(), "ExplosiveParticle");
		
		//if obstacle is a instance of GiftObstacle
		CollisionHandler.addParticle(giftObstacle);
		assertEquals(CollisionHandler.getParticles().get(1).getType(), "GiftParticle");
		
		
		
	}

	@Test
	void getParticlesTest() {
		for(Particle i: CollisionHandler.getParticles()) {
			particleList.add(i);
		}
				
		//add instance of explosive obstacle
		explosiveObstacle = FactoryObstacle.getInstance().generateObstacle("ExplosiveObstacle", 400, 400, 2, 15, true);
		explosiveParticle = FactoryParticle.getInstance().generateParticle("ExplosiveParticle", 25, 10, explosiveObstacle.getPosx() + 15, explosiveObstacle.getPosy());
		CollisionHandler.addParticle(explosiveObstacle);
		particleList.add(explosiveParticle);

		for(int i = 0; i<CollisionHandler.getParticles().size(); i++) {
			assertEquals(CollisionHandler.getParticles().get(i).getType(), particleList.get(i).getType());
		}
		
		//add instance of gift obstacle
		giftObstacle = FactoryObstacle.getInstance().generateObstacle("GiftObstacle", 200, 200, 1, false);
		giftParticle = FactoryParticle.getInstance().generateParticle("GiftParticle", 25, 10, giftObstacle.getPosx() + 15, giftObstacle.getPosy());
		CollisionHandler.addParticle(giftObstacle);
		particleList.add(giftParticle);

		assertNotEquals(CollisionHandler.getParticles().get(CollisionHandler.getParticles().size()-1).getType(), explosiveParticle.getType());
		assertEquals(CollisionHandler.getParticles().get(CollisionHandler.getParticles().size()-1).getType(), giftParticle.getType());

		for(int i = 0; i<CollisionHandler.getParticles().size(); i++) {
			assertEquals(CollisionHandler.getParticles().get(i).getType(), particleList.get(i).getType());
		}
	}

}
