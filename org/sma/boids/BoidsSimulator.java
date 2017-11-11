package org.sma.boids;

import gui.GUISimulator;
import gui.Simulable;

public class BoidsSimulator implements Simulable {

	private Boids boids;
	private GUISimulator gi;
	
	public BoidsSimulator(GUISimulator gi) {
		this.boids = new Boids(10, 400, 400, 10);
		this.gi = gi;
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
	}
	
	@Override
	public void next() {
		this.gi.reset();
		this.boids.nextStep();
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
	}

	@Override
	public void restart() {
		this.gi.reset();
		this.boids.reInit();
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
	}

}
