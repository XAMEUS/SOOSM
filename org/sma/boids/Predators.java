package org.sma.boids;

import java.awt.Color;
import java.util.Iterator;

/**
 * @author 3 Un ensemble de prédateurs est un ensemble de boids ayant la
 *         particuliarité de rechercher la proximité avec ses proies...
 */
public class Predators extends Boids {
	private Boids victims;

	public Predators(int n, int minx, int miny, int maxx, int maxy, int max_speed, Color color, Boids victims) {
		super(n, minx, miny, maxx, maxy, max_speed, color);
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
