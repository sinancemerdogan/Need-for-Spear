package model.obstacle;

import java.io.Serializable;

import model.Constants;

public abstract class Obstacle  implements Serializable {
	private static final long serialVersionUID = 6L;
	private double posx;
	private double posy;
	protected int xDir = 1;
	protected boolean clockwise = true;
	private int prevDir = 0;
	private int shape;
	private int remainingHit;
	private boolean isMoving;
	private boolean isFrozen = false;

	public Obstacle(double posx, double posy, int shape, int remainingHit, boolean isMoving) {
		this.posx = posx;
		this.posy = posy;
		this.shape = shape;
		this.isMoving = isMoving;
		this.remainingHit = remainingHit;
	}
 
	public void move() {
		if(this.isMoving) {
			if(this.posx >= Constants.FRAME_X - 45 || this.posx <= 0) {
				changeDirection();
			} 
			this.posx += this.xDir;
		}
	}
	
	public void changeDirection() {
		if(this.shape == 1) {
			this.xDir = -this.xDir;
		} else {
			this.clockwise = !this.clockwise;
		}
		
	}
	
	// getters and setters
	public double getPosx() {
		return this.posx;
	}

	public void setPosx(double posx) {
		this.posx = posx;
	}

	public double getPosy() {
		return this.posy;
	}

	public void setPosy(double posy) {
		this.posy = posy;
	}

	public int getShape() {
		return shape;
	}

	public void setShape(int shape) {
		this.shape = shape;
	}

	public int getRemainingHit() {
		return remainingHit;
	}

	public void setRemainingHit(int remainingHit) {
		this.remainingHit = remainingHit;
	}

	public boolean isMoving() {
		return this.isMoving;
	}

	public int getXDir() {
		return this.xDir;
	}
	
	public void setXDir(int dir) {
		this.prevDir = xDir;
		this.xDir = dir;
	}
	
	public int getPrevDir() {
		return this.prevDir;
	}
	
	public void setFrozen() {
		this.isFrozen = !this.isFrozen;
	}
	
	public boolean isFrozen() {
		return this.isFrozen;
	}
	
	public void getHit() {
		this.remainingHit--;
		if (this.remainingHit <= 0) {
			this.destroyed();
		}
	}

	private int destroyed() {
		return 0;
	}
	
	@Override
	public String toString() {
		String shape = " ";
		if (this.getShape() == 1) {
			shape = "Rectangle";
		}
		else if (this.getShape() == 2) {
			shape = "Circle";
		}
		return "X Coordinate: " + posx + "\nYCoordinate: " + posy + "\nShape: " + shape + "\nRemainingHit: " + remainingHit;
	}

}
