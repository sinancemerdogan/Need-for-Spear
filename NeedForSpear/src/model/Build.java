package model;

import java.util.Iterator;
import java.util.Random;

import model.factory.FactoryObstacle;
import model.obstacle.Obstacle;
import java.awt.geom.*;

public class Build {

	private static boolean isMoving = false;
	private static int width = 1124 - 45;
	private static int height = 768 - 400;
	private static int randX = 0;
	private static int randY = 0;
	private static Obstacle newObstacle;
	public static boolean noOverlap = false;
	private static Random rand = new Random();
	private static boolean maxObstacles = false;

	/***
	 * @REQUIRES Obstacle list in the GameMain class cannot be null
	 * @MODIFIES GameMain.obstacleList
	 * @EFFECTS Creates and adds obstacles to the obstacle list in the GameMain
	 *          class according to the given numbers: numberOfSimple, numberOfFirm,
	 *          numberOfExplosive, numberOfGift.
	 */
	public static void addObstacles(int numberOfSimple, int numberOfFirm, int numberOfExplosive, int numberOfGift) {
		if (!maxObstacles) {
			for (int i = 0; i < numberOfSimple; i++) {
				isMoving = rand.nextFloat() >= 0.2 ? false : true;
				randX = rand.nextInt(width);
				randY = rand.nextInt(height) + 50;
				canAdd(randX, randY);
				int bcount = 0;
				while (!noOverlap) {
					bcount++;
					randX = rand.nextInt(width);
					randY = rand.nextInt(height) + 50;
					canAdd(randX, randY);
					if (bcount >= 10000) {
						System.out.println("Max obstacle limit reached, no place to put obstacles");
						maxObstacles = true;
						break;
					}
				}
				if (bcount >= 10000) {
					break;
				}
				bcount = 0;
				newObstacle = FactoryObstacle.getInstance().generateObstacle("SimpleObstacle", randX, randY, 1,
						isMoving);
				GameMain.obstacleList.add(newObstacle);

			}

			for (int i = 0; i < numberOfFirm; i++) {
				isMoving = rand.nextFloat() >= 0.2 ? false : true;
				randX = rand.nextInt(width);
				randY = rand.nextInt(height) + 50;
				canAdd(randX, randY);
				int bcount = 0;
				while (!noOverlap) {
					bcount++;
					randX = rand.nextInt(width);
					randY = rand.nextInt(height) + 50;
					canAdd(randX, randY);
					if (bcount >= 10000) {
						System.out.println("Max obstacle limit reached, no place to put obstacles");
						maxObstacles = true;
						break;
					}
				}
				if (bcount >= 10000) {
					break;
				}
				bcount = 0;
				newObstacle = FactoryObstacle.getInstance().generateObstacle("FirmObstacle", randX, randY, 1,
						rand.nextInt(8) + 1, isMoving);
				GameMain.obstacleList.add(newObstacle);

			}

			for (int i = 0; i < numberOfExplosive; i++) {
				isMoving = rand.nextFloat() >= 0.2 ? false : true;
				randX = rand.nextInt(width);
				randY = rand.nextInt(height) + 50;
				canAdd(randX, randY);
				int bcount = 0;
				while (!noOverlap) {
					bcount++;
					randX = rand.nextInt(width);
					randY = rand.nextInt(height) + 50;
					canAdd(randX, randY);
					if (bcount >= 10000) {
						System.out.println("Max obstacle limit reached, no place to put obstacles");
						maxObstacles = true;
						break;
					}
				}
				if (bcount >= 10000) {
					break;
				}
				bcount = 0;
				newObstacle = FactoryObstacle.getInstance().generateObstacle("ExplosiveObstacle", randX, randY, 2, 1, isMoving, Constants.EXPLOSIVE_RADIUS);
				GameMain.obstacleList.add(newObstacle);

			}

			for (int i = 0; i < numberOfGift; i++) {
				isMoving = rand.nextFloat() >= 0.2 ? false : true;
				randX = rand.nextInt(width);
				randY = rand.nextInt(height) + 50;
				canAdd(randX, randY);
				int bcount = 0;
				while (!noOverlap) {
					bcount++;
					randX = rand.nextInt(width);
					randY = rand.nextInt(height) + 50;
					canAdd(randX, randY);
					if (bcount >= 10000) {
						System.out.println("Max obstacle limit reached, no place to put obstacles");
						maxObstacles = true;
						break;
					}
				}
				if (bcount >= 10000) {
					break;
				}
				bcount = 0;
				newObstacle = FactoryObstacle.getInstance().generateObstacle("GiftObstacle", randX, randY, 1, isMoving);
				GameMain.obstacleList.add(newObstacle);

			}
			
			
			/*
			addOneTypeOfObstacle("SimpleObstacle", numberOfSimple);
			addOneTypeOfObstacle("FirmObstacle", numberOfFirm);
			addOneTypeOfObstacle("ExplosiveObstacle", numberOfExplosive);
			addOneTypeOfObstacle("GiftObstacle", numberOfGift);
			*/
			
		}
		else {
			System.out.println("Max obstacle limit reached, no place to put obstacles");
		}
	}

	public static void addSingleObstacle(String type, double posx, double posy) {
		isMoving = rand.nextFloat() >= 0.2 ? false : true;
		canAdd(posx, posy);
		if (noOverlap) {
			if (type.equals("FirmObstacle")) {
				newObstacle = FactoryObstacle.getInstance().generateObstacle(type, posx, posy, 1, rand.nextInt(8) + 1,
						isMoving);

			} else if (type.equals("ExplosiveObstacle")) {
				newObstacle = FactoryObstacle.getInstance().generateObstacle(type, posx, posy, 2, 1, isMoving, Constants.EXPLOSIVE_RADIUS);

			} else {
				newObstacle = FactoryObstacle.getInstance().generateObstacle(type, posx, posy, 1, isMoving);
			}

			GameMain.obstacleList.add(newObstacle);
		}
	}

	public static void removeObstacle(int obsIndex) {
		GameMain.obstacleList.remove(obsIndex);
	}

	public static void canAdd(double x, double y) {
		noOverlap = false;
		boolean hasOverlap = false;
		Rectangle2D obs1, obs2;
		obs1 = new Rectangle2D.Double(x, y, Constants.OBSTACLE_WIDTH + 2, Constants.OBSTACLE_HEIGHT + 1);
		Iterator<Obstacle> it = GameMain.obstacleList.iterator();
		while (it.hasNext()) {
			Obstacle i = it.next();
			obs2 = new Rectangle2D.Double(i.getPosx(), i.getPosy(), Constants.OBSTACLE_WIDTH + 2, Constants.OBSTACLE_HEIGHT + 1);
			if (obs1.intersects(obs2)) {
				hasOverlap = true;
			}

		}
		if (hasOverlap == false) {
			noOverlap = true;
		}
	}
	
	public static boolean getMaxObstacles() {
		return maxObstacles;
	}
	
	public static void setMaxObstacles(boolean b) {
		maxObstacles = b;
	}
	
	public static void addOneTypeOfObstacle(String obstacleType, int obstacleAmount) {
		for (int i = 0; i < obstacleAmount; i++) {
			isMoving = rand.nextFloat() >= 0.2 ? false : true;
			randX = rand.nextInt(width);
			randY = rand.nextInt(height) + 50;
			canAdd(randX, randY);
			int bcount = 0;
			while (!noOverlap) {
				bcount++;
				randX = rand.nextInt(width);
				randY = rand.nextInt(height) + 50;
				canAdd(randX, randY);
				if (bcount >= 10000) {
					System.out.println("Max obstacle limit reached, no place to put obstacles");
					maxObstacles = true;
					break;
				}
			}
			if (bcount >= 10000) {
				break;
			}
			bcount = 0;
			newObstacle = FactoryObstacle.getInstance().generateObstacle(obstacleType, randX, randY, 1,
					isMoving);
			GameMain.obstacleList.add(newObstacle);

		}
	}
}
