package org.sma.boids;

import java.util.Iterator;

public class Predator extends Boids {
	private Boids victims;

	public Predator(int n, int minx, int miny, int maxx, int maxy, int max_speed, Boids victims) {
		super(n, minx, miny, maxx, maxy, max_speed);
		this.setCohesion(1, 700, 0.01, 10);
		this.setAlignment(0, 500);
		this.setSeparation(0, 50, 1);
		this.setKeeper(0.1, 0.01);
		this.setNoFriend(700, 0.01);
		this.victims = victims;
	}
	public Iterator<Boid> getNeighbors(Boid boid, double maxDist) {
		return victims.getBoids().stream().filter(x -> boid.isVisible(x, maxDist)).iterator();
	}
}
