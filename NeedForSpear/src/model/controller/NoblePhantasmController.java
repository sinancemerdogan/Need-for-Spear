package model.controller;

import java.util.ArrayList;

import model.GameMain;
import model.NoblePhantasm;
import model.obstacle.Particle;

public class NoblePhantasmController {
	
	private static NoblePhantasm noblePhantasm;
	
	public NoblePhantasmController(NoblePhantasm np) {
		noblePhantasm = np;
	}

	public void moveLeft() {
		noblePhantasm.moveLeft();
	}
	
	public void moveRight() {
		noblePhantasm.moveRight();
	}

	public void rotateLeft() {
		noblePhantasm.rotateLeft();
	}

	public void rotateRight() {
		noblePhantasm.rotateRight();
	}

	public void rotateBack() {
		noblePhantasm.rotateBack();
	}
	//getters to use variables in ui
	public int getPosX() { 
		return noblePhantasm.getPosX();
	}
	
	public int getWidth() {
		return noblePhantasm.getWidth();
	}
	
	public double getAngle() {
		return noblePhantasm.getAngle();
	}

	public void move(String movedirection) {
		noblePhantasm.move(movedirection);
		
	}
	
	public boolean hasCannons() {
		return noblePhantasm.hasCannons();
	}
	
	public void generateHex() {
		noblePhantasm.generateHex();
	}
	
	public ArrayList<Particle> getHexes() {
		return noblePhantasm.getHexes();
	}
	
}
