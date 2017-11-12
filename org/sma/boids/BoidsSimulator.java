package org.sma.boids;

import org.sma.events.Event;
import org.sma.events.EventManager;

import gui.GUISimulator;
import gui.Simulable;

public class BoidsSimulator implements Simulable {

	private Boids boids;
	private GUISimulator gi;
	private EventManager em;
	
	private class UpdateBoids extends Event {
		public UpdateBoids(long date) {
			super(date);
		}
		@Override
		public void execute() {
			BoidsSimulator.this.gi.reset();
			BoidsSimulator.this.boids.nextStep();
			for (Boid b : BoidsSimulator.this.boids)
				BoidsSimulator.this.gi.addGraphicalElement(b);
			BoidsSimulator.this.em.addEvent(new UpdateBoids(this.getDate()+1));
		}
	}
	
	public BoidsSimulator(GUISimulator gi) {
		this.boids = new Boids(50, 0, 0, 800, 800, 10);
		this.gi = gi;
		this.em = new EventManager();
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
		this.em.addEvent(new UpdateBoids(1));
	}
	
	@Override
	public void next() {
		this.em.next();
	}

	@Override
	public void restart() {
		this.gi.reset();
		this.boids.reInit();
		for (Boid b : this.boids)
			this.gi.addGraphicalElement(b);
	}

}
