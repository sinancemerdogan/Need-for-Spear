package model.factory;

import model.obstacle.*;

public class FactoryObstacle {
	
	/***
	 * Overview: FactoryObstacle class is a singleton class that we use to create 
	 * different types of obstacles corresponding to the given obstacle type
	 */

	// implementing singleton obstacle factory

	private FactoryObstacle() {
		// empty constructor
	}

	private static volatile FactoryObstacle instance = null;

	public static FactoryObstacle getInstance() {
		if (instance == null) {
			synchronized (FactoryObstacle.class) {
				if (instance == null) {
					instance = new FactoryObstacle();
				}
			}
		}
		return instance;
	}
	
	//first method
	public Obstacle generateObstacle(String obstacletype, double posx, double posy, int shape, int remainingHit,
			boolean isMoving) {
		/***
		* @EFFECTS returns an object with type obstacletype that is created with parameters posx, posy, shape, remainingHit and isMoving.
		*/
		Obstacle newObstacle = null;
		if (obstacletype.equals("SimpleObstacle")) {
			newObstacle = new SimpleObstacle(posx, posy, shape, isMoving);
		}

		else if (obstacletype.equals("FirmObstacle")) {
			newObstacle = new FirmObstacle(posx, posy, shape, remainingHit, isMoving);
		}

		else if (obstacletype.equals("GiftObstacle")) {
			newObstacle = new GiftObstacle(posx, posy, shape, isMoving);

		}

		else if (obstacletype.equals("ExplosiveObstacle")) {
			newObstacle = new ExplosiveObstacle(posx, posy, shape, 1, isMoving);
		}

		return newObstacle;

	}
	
	//second method with alternative variables
	public Obstacle generateObstacle(String obstacletype, double posx, double posy, int shape, int remainingHit,
			boolean isMoving, double explosiveRadius) {
		/***
		* @EFFECTS returns an object with type obstacletype that is created with parameters posx, posy, shape, remainingHit, 
		* isMoving and explosiveRadius.
		*/
		Obstacle newObstacle = null;
		if (obstacletype.equals("SimpleObstacle")) {
			newObstacle = new SimpleObstacle(posx, posy, shape, isMoving);
		}

		else if (obstacletype.equals("FirmObstacle")) {
			newObstacle = new FirmObstacle(posx, posy, shape, remainingHit, isMoving);
		}

		else if (obstacletype.equals("GiftObstacle")) {
			newObstacle = new GiftObstacle(posx, posy, shape, isMoving);

		}

		else if (obstacletype.equals("ExplosiveObstacle")) {
			newObstacle = new ExplosiveObstacle(posx, posy, shape, explosiveRadius, isMoving);
		}

		return newObstacle;

	}
	
	//third method with alternative variables
	public Obstacle generateObstacle(String obstacletype, double posx, double posy, int shape,
			boolean isMoving) {
		/***
		* @EFFECTS returns an object with type obstacletype that is created with parameters posx, posy, shape and 
		* isMoving.
		*/
		Obstacle newObstacle = null;
		if (obstacletype.equals("SimpleObstacle")) {
			newObstacle = new SimpleObstacle(posx, posy, shape, isMoving);
		}

		else if (obstacletype.equals("GiftObstacle")) {
			newObstacle = new GiftObstacle(posx, posy, shape, isMoving);

		}
		
		else if (obstacletype.equals("ExplosiveObstacle")) {
			newObstacle = new ExplosiveObstacle(posx, posy, shape, 1, isMoving);
		}
		
		else if (obstacletype.equals("HollowPurpleObstacle")) {
			newObstacle = new HollowPurpleObstacle(posx, posy, shape, isMoving);
		}

		return newObstacle;

	}

}
