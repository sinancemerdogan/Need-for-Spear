package model.obstacle;

import model.Constants;
import model.GameMain;

public class ExplosiveObstacle extends Obstacle {
	
	private double explodeRadius;
	private double posX;
	private double posY;
	private double centerX;
	private double centerY;
	private double portion = 0;
	private boolean isMoving;
	
	public ExplosiveObstacle(double posx, double posy, int shape, double explodeRadius, boolean isMoving) {
		super(posx, posy, shape, 1, isMoving);
		this.explodeRadius = explodeRadius;
		this.posX = posx;
		this.posY = posy;
		this.centerX =  (posx + explodeRadius);
		this.centerY = (posy + explodeRadius);
		this.isMoving = isMoving;

	}

	//getters and setters
	public double getExplodeRadius() {
		return explodeRadius;
	}


	public void setExplodeRadius(double explodeRadius) {
		this.explodeRadius = explodeRadius;
	}
	
	@Override
	public void move() {
		if(this.isMoving) {
			if(clockwise) {
				this.portion += 0.001;
			} else {
				this.portion -= 0.001;
			}
			
			double angle = portion * 2 * Math.PI; 
			this.posX = (this.centerX + this.explodeRadius * Math.cos(angle));
			this.posY = (this.centerY + this.explodeRadius * Math.sin(angle));
		}
	}
	
	@Override
	public double getPosx() {
		return this.posX;
	}

	
	@Override
	public double getPosy() {
		return this.posY;
	}
}
