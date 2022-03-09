package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.EnchantedSphere;
import model.GameMain;
import model.controller.GameController;

class ESMoveTest {

	private static EnchantedSphere eS;	
	static double xDir;
	static double yDir;
	static int chances;
	static double posY, posX;
	
	@BeforeAll
	public static void setUp() {
		eS = new EnchantedSphere();
		xDir = eS.getSphereXdir();
		yDir = eS.getSphereYdir();
		chances = GameMain.getRemainingChances();
		posX = eS.getPosX();
		posY = eS.getPosY();
	}
	
	@AfterEach
	public void after() {
		xDir = eS.getSphereXdir();
		yDir = eS.getSphereYdir();
		chances = GameMain.getRemainingChances();
		eS.setPosX(10);
		eS.setPosY(60);
		posX = eS.getPosX();
		posY = eS.getPosY();

	}	
		
	@Test
	void leftWallTest() {
		//REQUIRES: sphere to hit the left wall (posX < 0)
		//MODIFIES: x direction of enchanted sphere
		//EFFECTS: enchanted sphere starts going right
			//x direction of enchanted sphere is multiplied by -1 
		eS.setPosX(-5);
		eS.move();
		assertEquals(-xDir, eS.getSphereXdir());
	}
	
	@Test
	void rightWallTest() {
		//REQUIRES: sphere to hit the right wall
		//MODIFIES: x direction of enchanted sphere
		//EFFECTS: enchanted sphere starts going left
			//x direction of enchanted sphere is multiplied by -1 
		eS.setPosX(GameController.getFRAME_X());
		eS.move();
		assertEquals(-xDir, eS.getSphereXdir());
	}
	
	@Test
	void topTest() {
		//REQUIRES: sphere to hit the top wall (posY < 50)
		//MODIFIES: y direction of enchanted sphere
		//EFFECTS: enchanted sphere starts going down
			//y direction of enchanted sphere is multiplied by -1 
		eS.setPosY(30);
		eS.move();
		assertEquals(-yDir, eS.getSphereYdir());
	}

	
	
	@Test
	void bottomTest() {
		//REQUIRES: sphere to hit the bottom
		//MODIFIES: position of enchanted sphere
		 		//remaining chances
		 		//x & y directions of enchanted sphere
		//EFFECTS: enchanted sphere gets positioned back to the beginning
			// x & y direction of enchanted sphere goes back to the beginning (x = 1, y = -1)
			// player loses 1 life
		eS.setPosY(GameController.getFRAME_Y()-60-eS.getSphereYdir());
		eS.move();

		assertTrue(eS.getChanceLost());
		assertEquals(GameMain.getNp().getPosX() + (GameMain.getNp().getWidth()/2) - 8, eS.getPosX());
		assertEquals(GameController.getFRAME_Y() - 115, eS.getPosY());
		assertEquals(1, eS.getSphereXdir());
		assertEquals(-1, eS.getSphereYdir());
		assertEquals(GameMain.getRemainingChances(), chances -1);
	}
	
	@Test
	void moveSeveralTest() {
		//REQUIRES: sphere won't hit anything for the next 5 moves
		//MODIFIES: position of enchanted sphere
		//EFFECTS: x & y positions of enchanted sphere changes by 5 move
				//xPos += 5* xDir	&&		yPos += 5*yDir
		eS.move();
		eS.move();
		eS.move();
		eS.move();
		eS.move();

		assertEquals(posX+5*xDir, eS.getPosX());
		assertEquals(posY+5*yDir, eS.getPosY());
	}

}
