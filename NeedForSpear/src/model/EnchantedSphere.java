package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.controller.GameController;



public class EnchantedSphere implements Serializable{
	
	private static final long serialVersionUID = 6L;
	private double posX, posY;
	private double sphereXdir = 1;
	private double sphereYdir = -1;
	private boolean chanceLost = false;
	private boolean unstoppable = false;
	
	
	public EnchantedSphere() {
		this.posX = GameController.getFRAME_X()/2 - 8;
		this.posY = GameController.getFRAME_Y() - 115;
	}
	
	//setters and getters
	
	public double getPosX() {
		return posX;
	}
	
	public double getPosY() {
		return posY;
	}
	
	public void setSphereXdir (double newDir) {
		this.sphereXdir = newDir;
	}
	
	public void setSphereYdir (double newDir) {
		this.sphereYdir = newDir;
	}
	
	public double getSphereXdir() {
		return this.sphereXdir;
	}
	
	public double getSphereYdir() {
		return this.sphereYdir;
	}
	
	
	//actions
	
	public void move() {
		posX += sphereXdir;
		posY += sphereYdir;
		
		//left wall
		if(posX < 0 ) {
			sphereXdir = -sphereXdir;
		}
		//right wall
		else if(posX > GameController.getFRAME_X() - 30 ) {
			sphereXdir = -sphereXdir;
		}
		//top wall
		else if(posY < 50 ) {
			sphereYdir = -sphereYdir;
		}
		//bottom
		else if(posY >= GameController.getFRAME_Y() - 60) {
			//lose a chance, bottom will act like a wall for now
			chanceLost = true;
			posX = GameMain.getNp().getPosX() + (GameMain.getNp().getWidth()/2) - 8;
			posY = GameController.getFRAME_Y() - 115;
			sphereXdir = 1;
			sphereYdir = -1;
			GameMain.remainingChances -= 1;
		}
	}
	
	public boolean getChanceLost() {
		return chanceLost;
	}
	
	public void setChanceLost(boolean status) {
		chanceLost = status;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}
	
	public void setUnstoppable() {
		unstoppable = true;
	}
	
	public void setNormal() {
		unstoppable = false;
	}
	
	public boolean isUnstoppable() {
		return unstoppable;
	}
	
	public boolean getUnstoppableStatus() {
		return unstoppable;
	}
	
	public void setSpeed(double[] esSpeed) {
		setSphereXdir(esSpeed[0]);
		setSphereYdir(esSpeed[1]);
	}
	
	/*public void addEnchantedSphereListener(EnchantedSphereListener listener) {
		listeners.add(listener);
	}*/
	
	/*public void publishStartEvent() {
		for(EnchantedSphereListener lis: listeners)
			lis.onStartEvent();
	} */
}
