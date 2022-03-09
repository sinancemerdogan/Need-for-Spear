package model.controller;

import model.EnchantedSphere;


public class EnchantedSphereController {

	private static EnchantedSphere enchantedSphere;
	
	public EnchantedSphereController(EnchantedSphere es) {
		enchantedSphere = es;
	}
	
	public double getPosX() {
		return enchantedSphere.getPosX();
	}
	
	public boolean getChanceLost() {
		return enchantedSphere.getChanceLost();
	}
	
	public void setChanceLost(boolean status) {
		enchantedSphere.setChanceLost(status);
	}

	public double getPosY() {
		return enchantedSphere.getPosY();
	}

	public void move() {
		enchantedSphere.move();
	}

	public double getSphereYdir() {
		return enchantedSphere.getSphereYdir();
	}

	public double getSphereXdir() {
		return enchantedSphere.getSphereXdir();
	}

    public void setSphereYdir(double i) {
		enchantedSphere.setSphereYdir(i);
    }

	public void setSphereXdir(double i) {
		enchantedSphere.setSphereXdir(i);
    }
	
	/*public void addListener(EnchantedSphereListener eslis) {
		enchantedSphere.addEnchantedSphereListener(eslis);
	}*/
}