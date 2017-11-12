package org.sma.boids;

import org.sma.events.Event;
import org.sma.events.EventManager;

import gui.GUISimulator;

class UpdateBoids extends Event {
	private GUISimulator gi;
	private Boids boids;
	private EventManager em;
	public UpdateBoids(GUISimulator gi, EventManager em, Boids boids, long date) {
		super(date);
		this.gi = gi;
		this.em = em;
		this.boids = boids;
	}
	@Override
	public void execute() {
		this.gi.reset();
		this.boids.nextStep();
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
		this.em.addEvent(new UpdateBoids(this.gi, this.em, this.boids, this.getDate()+1));
	}
}
