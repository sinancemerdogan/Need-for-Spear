package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.controller.GameController;
import model.controller.NoblePhantasmController;
import model.factory.FactoryParticle;
import model.obstacle.Particle;

public class NoblePhantasm implements Serializable {
	
	
	private static final long serialVersionUID = 6L;
	private int width;
	private int posX;
	private double speed;
	private double angle;
	private boolean hasCannons = false;
	public static ArrayList<Particle> hexList = new ArrayList<Particle>();
	
	public NoblePhantasm() {
		this.width = GameController.getFRAME_X()/10;
		this.posX = GameController.getFRAME_X()/2-this.width/2;
		this.angle = 0;
	}
	
	//setters and getters
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public void setPosX(int newX) {
		this.posX = newX;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	//action functions
	
	/**
	 * @author Sinan Cem Erdo�an
	 *
	 */
	
	public void moveLeft() {
		
		
		/***
		* @MODIFIES x position of noble phantasm.
		*/
		
		/***
		* @EFFECTS if position of x <= 2 then it returns, otherwise it decrements the position of x by 2.
		*/
		
		if(posX <= 2)
			return;
		this.posX -= 2;
	}
	
	public void moveRight() {
		
		/***
		* @MODIFIES x position of noble phantasm.
		*/
		
		/***
		* @EFFECTS if position of x >= (x axis of game frame * 9/10) then it returns, otherwise it increments the position of x by 2.
		*/
		
		if(posX + width >= GameController.getFRAME_X())
			return;
		this.posX += 2;
	}
	
	public void move(String movedirection) {
		if(movedirection.equals("LEFT")) {
			this.moveLeft();
		}
		else if(movedirection.equals("RIGHT")) {
			this.moveRight();
		}
		
	}
	
	public void rotateLeft() {
		
		/***
		* @MODIFIES angle of noble phantasm.
		*/
		
		/***
		* @EFFECTS if angle of noble phantasm satisfy the following condition (angle-0.85 >= -45 && this.angle-0.85 <= 45) then it returns, otherwise it decrements the angle by 0.85.
		*/
		
		
		if(this.angle-0.85 >= -45 && this.angle-0.85 <= 45){
			this.angle -= 0.85;
		}
	}
	
	public void rotateRight() {
		
		/***
		* @MODIFIES angle of noble phantasm.
		*/
		
		/***
		* @EFFECTS if angle of noble phantasm satisfy the following condition (angle+0.85 >= -45 && angle+0.85 <= 45) then it returns, otherwise it increments the angle by 0.85.
		*/
		
		if(this.angle+0.85 >= -45 && this.angle+0.85 <= 45){
			this.angle += 0.85;
		}
	}
	
	public void rotateBack() {
		
		/***
		* @MODIFIES angle of noble phantasm.
		*/
		
		/***
		* @EFFECTS if angle of noble phantasm satisfy the following condition (angle - 1.91 > 0) then it decrements the angle by 1.91.
		* 		   else if angle of noble phantasm satisfy the following condition (angle - 1.91 < 0 && angle > 0) then it sets angle to 0.
		* 		   else if angle of noble phantasm satisfy the following condition (angle + 1.91 < 0) then it increments the angle by 1.91.
		* 		   else if angle of noble phantasm satisfy the following condition (angle + 1.91 > 0 && angle < 0) then it sets angle to 0.
		*/

		 	if (this.angle - 1.91 > 0) {
				this.angle -= 1.91;
			}
	
			else if (this.angle - 1.91 < 0 && this.angle > 0) {
				this.angle = 0;
			}
	
			else if (this.angle + 1.91 < 0) {
				this.angle += 1.91;
			}
	
			else if (this.angle + 1.91 > 0 && this.angle < 0) {
				this.angle = 0;
			}
		
		
	}
	public void addCannons() {
		this.hasCannons = true;
	}
	
	public void removeCannons() {
		this.hasCannons = false;
	}
	public boolean hasCannons() {
		return this.hasCannons;
	}
	
	public void generateHex() {
		Particle leftHex = FactoryParticle.getInstance().generateParticle("MagicalHex", 10, 10, this.posX - 5, Constants.FRAME_Y - 120);
		Particle rightHex = FactoryParticle.getInstance().generateParticle("MagicalHex", 10, 10, this.posX + this.width - 5, Constants.FRAME_Y - 120);
		hexList.add(leftHex);
		hexList.add(rightHex);
	}
	
	public ArrayList<Particle> getHexes() {
		return hexList;
	}
}
