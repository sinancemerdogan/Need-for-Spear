package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.awt.geom.*;

import model.abilities.Ability;
import model.controller.EnchantedSphereController;
import model.controller.GameController;
import model.controller.NoblePhantasmController;
import model.factory.FactoryAbility;
import model.factory.FactoryParticle;
import model.obstacle.ExplosiveObstacle;
import model.obstacle.GiftObstacle;
import model.obstacle.HollowPurpleObstacle;
import model.obstacle.Obstacle;
import model.obstacle.Particle;

public class CollisionHandler {
	/*
	 * OVERVIEW: Collision class handles the all type of collision; paddle-ball,
	 * ball-obstacles, ball-particle, obstacle-obstacle collision
	 */

	private static GameController gameController = new GameController();
	private static EnchantedSphereController enchantedSphereController = gameController.esController();
	private static NoblePhantasmController noblePhantasmController = gameController.npController();
	public static ArrayList<Particle> particleList = new ArrayList<Particle>();
	public static FactoryParticle factoryParticle = FactoryParticle.getInstance();

	private static int paddleX = noblePhantasmController.getPosX();
	private static int paddleY = GameController.getFRAME_Y() - 100;
	private static int paddleWidth = noblePhantasmController.getWidth();
	private static int ballX = (int) enchantedSphereController.getPosX();
	private static int ballY = (int) enchantedSphereController.getPosY();
	private int ballRadius = 15;
	private static double ballSpeedX = enchantedSphereController.getSphereXdir();
	private static double ballSpeedY = enchantedSphereController.getSphereYdir();
	private static EnchantedSphere sphere;
	private static long cooldown = 0;

	/*
	 * The abstraction function:
	 * 
	 */

	/*
	 * The representation invariant is 0 <= paddleX <= FRAME_X && 0 <= ballX <=
	 * FRAME_X && 0 <= ballY <= FRAME_Y && obstacleList not null && particleList not
	 * null && noblePhantasmController not null && enchantedSphereController not
	 * null && gameController not null &&
	 */
	public static boolean paddleBallCollision(int ballX, int ballY, double paddleX, double paddleY, double angle,
			int paddleWidth) {
		double p1X = paddleX + paddleWidth + 15;
		double p1Y = paddleY + Math.tan(Math.toRadians(angle)) * (paddleWidth / 2 + 15);
		double p2X = paddleX - 15;
		double p2Y = paddleY - Math.tan(Math.toRadians(angle)) * (paddleWidth / 2 + 15);
		Line2D paddleLine = new Line2D.Double(p1X, p1Y, p2X, p2Y);
		Rectangle2D ballFrame = new Rectangle2D.Double(ballX, ballY, 14, 14);
		if (ballFrame.intersectsLine(paddleLine)) {
			return true;
		}
		return false;

	}

	public static void collisionHandler() {
		/*
		 * MODIFIES: obstacleList, particleList, direction of sphere (Xdir and Ydir),
		 * score EFFECTS: Detects the collision of sphere with other objects and changes
		 * these objects according to the effects of collision on them and changes
		 * sphere's direction. Creates a particle object if sphere collides with
		 * giftObstacle or explosiveObstacle
		 */
		sphere = GameMain.getEs();
		paddleX = noblePhantasmController.getPosX();
		ballX = (int) enchantedSphereController.getPosX();
		ballY = (int) enchantedSphereController.getPosY();
		ballSpeedX = enchantedSphereController.getSphereXdir();
		ballSpeedY = enchantedSphereController.getSphereYdir();
		paddleWidth = noblePhantasmController.getWidth();

		// paddle - ball collision
		double angle = noblePhantasmController.getAngle();
		// + is clockwise

		if (ballX + 15 <= paddleX + paddleWidth && paddleX - 10 <= ballX - 15) {
			if (paddleBallCollision(ballX, ballY, paddleX, paddleY, angle, paddleWidth)
					&& enchantedSphereController.getSphereYdir() >= 0) {
				if (System.currentTimeMillis() > cooldown + 100) {
					double atanball = (Math.atan2(ballSpeedY, ballSpeedX) * 180 / Math.PI);
					double diff = atanball - angle;
					atanball = atanball - (2 * diff);
					updateSpeed(ballSpeedX, ballSpeedY, atanball);
					cooldown = System.currentTimeMillis();
				}

			}
		}

		// obstacle - ball collision
		double atanball;
		double diff;
		double length;
		Iterator<Obstacle> it = GameMain.obstacleList.iterator();
		while (it.hasNext()) {
			Obstacle i = it.next();
			if (i == null)
				break;
			Rectangle2D obs1 = i.getShape() == 1
					? new Rectangle2D.Double(i.getPosx(), i.getPosy(), Constants.OBSTACLE_WIDTH,
							Constants.OBSTACLE_HEIGHT)
					: new Rectangle2D.Double(i.getPosx(), i.getPosy(), Constants.CIRCLE_OBSTACLE_WIDTH,
							Constants.CIRCLE_OBSTACLE_WIDTH);
			// hex-obstacle collision
			for (Iterator<Particle> iterator = NoblePhantasm.hexList.iterator(); iterator.hasNext();) {
				Particle particle = iterator.next();
				double px = particle.getPosx();
				double py = particle.getPosy();
				int pw = particle.getWidth();
				int ph = particle.getHeight();

				Rectangle2D hex = new Rectangle2D.Double(px, py, pw, ph);
				Rectangle2D obs = new Rectangle2D.Double(i.getPosx(), i.getPosy(), Constants.OBSTACLE_WIDTH,
						Constants.OBSTACLE_HEIGHT);

				if (obs.intersects(hex)) {
					if (!i.isFrozen()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
					iterator.remove();
				}
			}

			// obstacle-obstacle collision
			if (i.isMoving()) {
				Iterator<Obstacle> iter = GameMain.obstacleList.iterator();
				while (iter.hasNext()) {
					Obstacle o = iter.next();
					Rectangle2D obs2 = o.getShape() == 1
							? new Rectangle2D.Double(o.getPosx(), o.getPosy(), Constants.OBSTACLE_WIDTH,
									Constants.OBSTACLE_HEIGHT)
							: new Rectangle2D.Double(o.getPosx(), o.getPosy(), Constants.CIRCLE_OBSTACLE_WIDTH,
									Constants.CIRCLE_OBSTACLE_WIDTH);
					if (obs1.intersects(obs2)) {
						i.changeDirection();
						o.changeDirection();
					}
				}
			}

			int corner = collideCheckCorner(i.getPosx(), i.getPosy(), ballX, ballY);
			if (corner != 0) {
				if (System.currentTimeMillis() > cooldown + 10) {
					cooldown = System.currentTimeMillis();
				} else {
					corner = 0;
				}
			}
			switch (corner) {
			case 0:
				break;
			case 1:
				// topleft corner
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					atanball = (Math.atan2(ballSpeedY, ballSpeedX) * 180 / Math.PI);
					diff = atanball + 45;
					atanball = atanball - (2 * diff);
					updateSpeed(ballSpeedX, ballSpeedY, atanball);
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {
					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);
					}

				}

				break;
			case 2:
				// topright corner
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					atanball = (Math.atan2(ballSpeedY, ballSpeedX) * 180 / Math.PI);
					diff = atanball + 135;
					atanball = atanball - (2 * diff);
					updateSpeed(ballSpeedX, ballSpeedY, atanball);
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {

					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);
					}
				}

				break;
			case 3:
				// botleft corner
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					atanball = (Math.atan2(ballSpeedY, ballSpeedX) * 180 / Math.PI);
					diff = atanball - 45;
					atanball = atanball - (2 * diff);
					updateSpeed(ballSpeedX, ballSpeedY, atanball);
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {
					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);
					}
				}

				break;
			case 4:
				// botright corner
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					atanball = (Math.atan2(ballSpeedY, ballSpeedX) * 180 / Math.PI);
					diff = atanball - 135;
					atanball = atanball - (2 * diff);
					updateSpeed(ballSpeedX, ballSpeedY, atanball);
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {
					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);
					}
				}

				break;

			}

			if (collideCheckBot(i.getPosx(), i.getPosy(), ballX, ballY, enchantedSphereController.getSphereYdir())) {
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					enchantedSphereController.setSphereYdir(-(enchantedSphereController.getSphereYdir()));
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {
					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);
					}
				}
			}

			else if (collideCheckTop(i.getPosx(), i.getPosy(), ballX, ballY,
					enchantedSphereController.getSphereYdir())) {
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					enchantedSphereController.setSphereYdir(-(enchantedSphereController.getSphereYdir()));
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {
					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);
					}
				}
			}

			else if (collideCheckLeft(i.getPosx(), i.getPosy(), ballX, ballY,
					enchantedSphereController.getSphereXdir())) {
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					enchantedSphereController.setSphereXdir(-(enchantedSphereController.getSphereXdir()));
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {
					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);

					}
				}
			}

			else if (collideCheckRight(i.getPosx(), i.getPosy(), ballX, ballY,
					enchantedSphereController.getSphereXdir())) {
				if (!sphere.getUnstoppableStatus() || (sphere.getUnstoppableStatus() && i.isFrozen())) {
					enchantedSphereController.setSphereXdir(-(enchantedSphereController.getSphereXdir()));
					if (!i.isFrozen() || sphere.getUnstoppableStatus()) {
						i.setRemainingHit(i.getRemainingHit() - 1);
						if (i.getRemainingHit() <= 0) {
							addParticle(i);
							it.remove();
							if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
								GameMain.score += 300 / (GameMain.time);
							}
						}
					}
				} else {
					addParticle(i);
					it.remove();
					if (!i.getClass().getSimpleName().equals(Constants.HollowPurpleObstacleClassName)) {
						GameMain.score += 300 / (GameMain.time);

					}
				}
			}
		}

		// particle - paddle collision
		particleCollision(angle);
	}

	private static void updateSpeed(double ballSpeedX, double ballSpeedY, double atanball) {
		double length = Math.sqrt(ballSpeedX * ballSpeedX + ballSpeedY * ballSpeedY);
		enchantedSphereController.setSphereXdir(length * Math.cos(Math.toRadians(atanball)));
		enchantedSphereController.setSphereYdir(length * Math.sin(Math.toRadians(atanball)));
	}

	public static boolean collideCheckBot(double topLeftX, double topLeftY, int ballX, int ballY, double speedY) {

		/*
		 * EFFECTS: Returns true if the sphere collide with the bottom of the obstacle,
		 * otherwise returns false
		 */
		if (ballX + 6 <= topLeftX + Constants.OBSTACLE_WIDTH && topLeftX <= ballX + 9) {
			if (ballY <= topLeftY + Constants.OBSTACLE_HEIGHT && speedY <= 0 && ballY >= topLeftY) {
				return true;
			}
		}
		return false;
	}

	public static boolean collideCheckTop(double topLeftX, double topLeftY, int ballX, int ballY, double speedY) {
		/*
		 * EFFECTS: Returns true if the sphere collide with the top of the obstacle,
		 * otherwise returns false
		 */
		if (ballX + 6 <= topLeftX + Constants.OBSTACLE_WIDTH && topLeftX <= ballX + 9) {
			if (ballY <= topLeftY && speedY > 0 && ballY + 15 >= topLeftY) {
				return true;
			}
		}
		return false;
	}

	public static boolean collideCheckLeft(double topLeftX, double topLeftY, int ballX, int ballY, double speedX) {
		/*
		 * EFFECTS: Returns true if the sphere collide with the left of the obstacle,
		 * otherwise returns false
		 */
		if (ballY + 8 >= topLeftY + 6 && ballY + 8 <= topLeftY + 9) {
			if (ballX + 15 <= topLeftX + Constants.OBSTACLE_WIDTH && topLeftX <= ballX + 15 && speedX > 0) {
				return true;
			}
		}
		return false;
	}

	public static boolean collideCheckRight(double topLeftX, double topLeftY, int ballX, int ballY, double speedX) {
		/*
		 * EFFECTS: Returns true if the sphere collide with the right of the obstacle,
		 * otherwise returns false
		 */
		if (ballY + 8 >= topLeftY + 6 && ballY + 8 <= topLeftY + 9) {
			if (ballX <= topLeftX + Constants.OBSTACLE_WIDTH && topLeftX + Constants.OBSTACLE_WIDTH <= ballX + 15
					&& speedX < 0) {
				return true;
			}
		}
		return false;
	}

	public static int collideCheckCorner(double topLeftX, double topLeftY, int ballX, int ballY) {
		/*
		 * EFFECTS: Returns 1 if the sphere collide with the top left corner of the
		 * obstacle, Returns 2 if the sphere collide with the top right corner of the
		 * obstacle, Returns 3 if the sphere collide with the bottom left corner of the
		 * obstacle, Returns 4 if the sphere collide with the bottom right corner of the
		 * obstacle,
		 * 
		 */
		double speed = 3.5 * Math.sqrt(2); // Math.sqrt(Math.pow(enchantedSphere.getSphereXdir(),2) +
											// Math.pow(enchantedSphere.getSphereYdir(),2));
		double euclideanDistance1 = Math
				.sqrt(Math.pow(topLeftX - (ballX + 7), 2) + Math.pow(topLeftY - (ballY + 7), 2));
		if (speed > euclideanDistance1) {
			return 1;
		}

		else {
			double euclideanDistance2 = Math.sqrt(Math.pow(topLeftX + Constants.OBSTACLE_WIDTH - (ballX + 7), 2)
					+ Math.pow(topLeftY - (ballY + 7), 2));
			if (speed > euclideanDistance2) {
				return 2;
			} else {
				double euclideanDistance3 = Math.sqrt(Math.pow(topLeftX - (ballX + 7), 2)
						+ Math.pow(topLeftY + Constants.OBSTACLE_HEIGHT - (ballY + 7), 2));
				if (speed > euclideanDistance3) {
					return 3;
				} else {
					double euclideanDistance4 = Math.sqrt(Math.pow(topLeftX + Constants.OBSTACLE_WIDTH - (ballX + 7), 2)
							+ Math.pow(topLeftY + Constants.OBSTACLE_HEIGHT - (ballY + 7), 2));
					if (speed > euclideanDistance4) {
						return 4;
					}

					else {
						return 0;
					}
				}
			}
		}
	}

	public static void addParticle(Obstacle i) {
		/*
		 * MODIFIES: particleList EFFECTS: Adds a particle to the particleList
		 */
		if (i instanceof ExplosiveObstacle) {
			particleList
					.add(factoryParticle.generateParticle("ExplosiveParticle", 25, 10, i.getPosx() + 15, i.getPosy()));
		}

		if (i instanceof GiftObstacle) {
			particleList.add(factoryParticle.generateParticle("GiftParticle", 25, 10, i.getPosx() + 15, i.getPosy()));
		}
	}

	public static void particleCollision(double angle) {

		Iterator<Particle> it = particleList.iterator();
		while (it.hasNext()) {

			Particle particle = it.next();
			double px = particle.getPosx();
			double py = particle.getPosy();
			int pw = particle.getWidth();
			int ph = particle.getHeight();

			Rectangle2D rect = new Rectangle2D.Double(px, py, pw, ph);

			double p1X = paddleX + paddleWidth + 15;
			double p1Y = paddleY + Math.tan(Math.toRadians(angle)) * (paddleWidth / 2 + 15);
			double p2X = paddleX - 15;
			double p2Y = paddleY - Math.tan(Math.toRadians(angle)) * (paddleWidth / 2 + 15);
			Line2D paddleLine = new Line2D.Double(p1X, p1Y, p2X, p2Y);

			if (rect.intersectsLine(paddleLine)) {
				if (particle.getType().equals("GiftParticle")) {
					Random rand = new Random();
					int randomType = rand.nextInt(Constants.nonYmirAbilityTypes.length);
					String abilityType = Constants.nonYmirAbilityTypes[randomType];
					Ability ability = FactoryAbility.getInstance().generateAbility(abilityType);
				}

				if (particle.getType().equals("ExplosiveParticle")) {
					enchantedSphereController.setChanceLost(true);
					GameMain.getEs().setPosX(GameMain.getNp().getPosX() + (GameMain.getNp().getWidth() / 2) - 8);
					GameMain.getEs().setPosY(GameController.getFRAME_Y() - 115);
					GameMain.getEs().setSphereXdir(1);
					GameMain.getEs().setSphereXdir(-1);
					GameMain.remainingChances--;
				}

				it.remove();
			}
		}
	}

	public static ArrayList<Particle> getParticles() {
		/*
		 * EFFECTS: Returns the particleList
		 */
		return particleList;
	}

	public static boolean repOk() {
		/*
		 * EFFECTS: Returns true if the representation invariant holds for this;
		 * otherwise returns false.
		 */
		if (0 > paddleX || gameController.getFRAME_X() < paddleX)
			return false;
		if (0 > ballX || gameController.getFRAME_X() < ballX)
			return false;
		if (0 > ballY || gameController.getFRAME_Y() < ballY)
			return false;
		if (GameMain.obstacleList == null || particleList == null || noblePhantasmController == null
				|| enchantedSphereController == null || gameController == null)
			return false;
		return true;
	}

}
