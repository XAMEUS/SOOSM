package org.sma.boids;

import org.sma.events.EventManager;
import org.sma.simulator.Simulator;

import gui.GUISimulator;

public class BoidsSimulator extends Simulator {

	private Boids boids;
	private GUISimulator gi;
	
	public BoidsSimulator(GUISimulator gi) {
		this.boids = new Boids(50, 0, 0, 800, 800, 10);
		this.gi = gi;
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
		EventManager em = new EventManager();
		this.setEventManager(em);
		this.setEvent(new UpdateBoids(gi, em, boids, 1));
	}

	@Override
	public void restart() {
		this.gi.reset();
		this.boids.reInit();
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
	}

}
