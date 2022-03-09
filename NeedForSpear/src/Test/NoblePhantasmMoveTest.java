/**
 * 
 */
package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.NoblePhantasm;
import model.controller.GameController;

/**
 * @author Sinan Cem Erdo�an
 *
 */
class NoblePhantasmMoveTest {

	private static NoblePhantasm noblePhantasm;
	private static int posX;
	private static double angle;



	@BeforeAll
	static void setUp() throws Exception {

		noblePhantasm = new NoblePhantasm();

	}

	@Test
	/*
	 * Test of the move left action 
	 */
	void moveLeftTest() {

		//posX = 20
		noblePhantasm.setPosX(20);
		posX = noblePhantasm.getPosX();
		noblePhantasm.moveLeft();
		assertEquals(posX,noblePhantasm.getPosX());

		//posX < 20
		noblePhantasm.setPosX(15);
		posX = noblePhantasm.getPosX();
		noblePhantasm.moveLeft();
		assertEquals(posX,noblePhantasm.getPosX());


		//posX > 20
		noblePhantasm.setPosX(30);
		posX = noblePhantasm.getPosX();
		noblePhantasm.moveLeft();
		assertEquals((posX - 20) ,noblePhantasm.getPosX());

	}

	@Test
	/*
	 * Test of the right left action 
	 */
	void moveRightTest() {

		//posX = frame
		noblePhantasm.setPosX(GameController.getFRAME_X()*9/10);
		posX = noblePhantasm.getPosX();
		noblePhantasm.moveRight();
		assertEquals(posX,noblePhantasm.getPosX());

		//posX > frame
		noblePhantasm.setPosX((GameController.getFRAME_X()*9/10) + 5);
		posX = noblePhantasm.getPosX();
		noblePhantasm.moveRight();
		assertEquals(posX,noblePhantasm.getPosX());

		//posX < frame
		noblePhantasm.setPosX((GameController.getFRAME_X()*9/10) - 5);
		posX = noblePhantasm.getPosX();
		noblePhantasm.moveRight();
		assertEquals((posX + 20) ,noblePhantasm.getPosX());

	}


	@Test
	/*
	 * Test of the rotate left action 
	 */
	void rotateLeftTest() {

		//angle - 0.85 = -45 
		noblePhantasm.setAngle(-44.15);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateLeft();
		assertEquals(angle - 0.85, noblePhantasm.getAngle());

		// -45 < angle - 0.85 < 45 
		noblePhantasm.setAngle(0.85);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateLeft();
		assertEquals(angle - 0.85, noblePhantasm.getAngle());

		//angle - 0.85 = 45 
		noblePhantasm.setAngle(45.85);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateLeft();
		assertEquals(angle - 0.85, noblePhantasm.getAngle());

	}

	@Test
	/*
	 * Test of the rotate right action 
	 */
	void rotateRightTest() {

		//angle + 0.85 = -45 
		noblePhantasm.setAngle(-45.85);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateRight();
		assertEquals(angle + 0.85, noblePhantasm.getAngle());

		// -45 < angle + 0.85 < 45 
		noblePhantasm.setAngle(-0.85);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateRight();
		assertEquals(angle + 0.85, noblePhantasm.getAngle());

		//angle + 0.85 = 45 
		noblePhantasm.setAngle(44.15);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateRight();
		assertEquals(angle + 0.85, noblePhantasm.getAngle());
	}

	@Test
	/*
	 * Test of the rotate back action 
	 */
	void rotateBackTest() {


		//angle - 1.91 > 0
		noblePhantasm.setAngle(2);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateBack();
		assertEquals(angle - 1.91, noblePhantasm.getAngle());

		//angle - 1.91 < 0 and this.angle > 0
		noblePhantasm.setAngle(1.50);
		noblePhantasm.rotateBack();
		assertEquals(0, noblePhantasm.getAngle());

		//angle + 1.91 < 0
		noblePhantasm.setAngle(-2);
		angle = noblePhantasm.getAngle();
		noblePhantasm.rotateBack();
		assertEquals(angle + 1.91, noblePhantasm.getAngle());

		//angle + 1.91 > 0 and this.angle < 0
		noblePhantasm.setAngle(-1.50);
		noblePhantasm.rotateBack();
		assertEquals(0, noblePhantasm.getAngle());
	}

}
