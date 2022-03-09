package model.factory;
import model.obstacle.*;
public class FactoryParticle {

	
	/*
	 * Overview: FactoryParticle is the singleton class 
	 * that creates different kinds of particles
	 * 
	 */
	
	// Private constructor
	private FactoryParticle() {
	}

	private static volatile FactoryParticle instance = null;

	public static FactoryParticle getInstance() {
		if (instance == null) {
			synchronized (FactoryParticle.class) {
				if (instance == null) {
					instance = new FactoryParticle();
				}
			}
		}
		return instance;
	}


//first method
public Particle generateParticle(String particletype, int width, int height, double posx, double posy) {
	/***
	* @REQUIRES this function can only be called on an instance of a FactoryParticle,
	* therefore needs a instance of FactoryParticle
	*/
	/***
	* @EFFECTS creates and returns a Particle object with the values provided by the function, 
	* particleObject's type is determined by the String 'particleType'.
	*/
	Particle newParticle = null;
	if (particletype.equals("ExplosiveParticle")) {
	    newParticle = new Particle(particletype, width, height, posx, posy);
	}
	if (particletype.equals("GiftParticle")) {
	    newParticle = new Particle(particletype, width, height, posx, posy);
	    }
	if (particletype.equals("MagicalHex")) {
	    newParticle = new Particle(particletype, width, height, posx, posy);
	    }
	
	return newParticle;
	}
}
