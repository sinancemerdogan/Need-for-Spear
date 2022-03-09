package model.abilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import model.Constants;
import model.GameMain;
import model.obstacle.Obstacle;

public class InfiniteVoid extends Ability {

	private static Random rand = new Random();
	private ArrayList<Obstacle> selectedObstacles = new ArrayList<Obstacle>();

	/*
	 * Since Ymir uses the ability upon creation we call useAbility inside the
	 * constructor
	 */
	public InfiniteVoid() {
		super();
		hasDuration = true;
		duration = Constants.INFINITE_VOID_DURATION;
		useAbility();
	}

	@Override
	public void performSpecificAbilityAction() {
		if (GameMain.obstacleList.size() <= 8) {
			Iterator<Obstacle> it = GameMain.obstacleList.iterator();
			while (it.hasNext()) {
				Obstacle o = it.next();
				selectedObstacles.add(o);
				o.setXDir(0);
			}

		} else {
			int counter = 0;
			while (selectedObstacles.size() < 8) {
				counter++;
				int selectedObsIndex = rand.nextInt(GameMain.obstacleList.size());
				Obstacle selectedObs = GameMain.obstacleList.get(selectedObsIndex);
				if (selectedObs != null) {
					if (!selectedObstacles.contains(selectedObs) && selectedObs.isMoving()) {
						selectedObstacles.add(selectedObs);
						selectedObs.setFrozen();
						selectedObs.setXDir(0);
					}
				}
				if (counter > 1000) break;
			}
			counter = 0;
		}
	}

	@Override
	public void revertSpecificAbilityAction() {
		Iterator<Obstacle> it = selectedObstacles.iterator();
		while (it.hasNext()) {
			Obstacle o = it.next();
			o.setFrozen();
			o.setXDir(o.getPrevDir());
		}
	}
}
