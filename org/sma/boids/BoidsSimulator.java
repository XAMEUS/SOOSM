package org.sma.boids;

import java.util.ArrayList;
import java.util.List;

import org.sma.events.EventManager;
import org.sma.simulator.Simulator;

import gui.GUISimulator;

public class BoidsSimulator extends Simulator {

	private List<Boids> boids;
	private GUISimulator gi;
	
	public BoidsSimulator(GUISimulator gi) {
		this.boids = new ArrayList<>();
		this.gi = gi;
		EventManager em = new EventManager();
		this.setEventManager(em);
		this.setFirstEvent(new UpdateBoids(gi, em, this.boids, 1));
	}
	
	public void addBoids(Boids boids) {
		this.boids.add(boids);
		for (Boid b : boids)
			this.gi.addGraphicalElement(b);
	}

	@Override
	public void restart() {
		super.restart();
		this.gi.reset();
		for (Boids boids : this.boids) {			
			boids.reInit();
			for (Boid b : boids)
				this.gi.addGraphicalElement(b);
		}
	}

}
