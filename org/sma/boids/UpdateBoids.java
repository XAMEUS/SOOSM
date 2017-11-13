package org.sma.boids;

import java.util.List;

import org.sma.events.Event;
import org.sma.events.EventManager;

import gui.GUISimulator;

class UpdateBoids extends Event {
	private GUISimulator gi;
	private List<Boids> boids;
	private EventManager em;

	public UpdateBoids(GUISimulator gi, EventManager em, List<Boids> boids, long date) {
		super(date);
		this.gi = gi;
		this.em = em;
		this.boids = boids;
	}

	@Override
	public void execute() {
		this.gi.reset();
		for (Boids boids : this.boids) {
			boids.nextStep();
			for (Boid b : boids)
				this.gi.addGraphicalElement(b);
		}
		this.em.addEvent(new UpdateBoids(this.gi, this.em, boids, this.getDate() + 1));
	}
}
