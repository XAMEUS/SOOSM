package org.sma.boids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Boids {
	public List<Boid> boids;
	public List<Boid> n_boids;
	
	public Boids(int n, int maxx, int maxy, int max_speed) {
		this.boids = new ArrayList<>();
		for (int i = 0; i < n; i++)
			this.boids.add(new Boid((int)(Math.random() * maxx), (int)(Math.random() * maxy), (int)(Math.random() * max_speed), (int)(Math.random() * max_speed)));
	}
	
	public Iterator<Boid> getNeighbors(Boid boid) {
		return this.boids.stream().filter(x -> boid.isVisible(x)).iterator();
	}
	
	public void nextStep() {
		this.n_boids = new ArrayList<>();
		for(int i = 0; i < boids.size(); i++) {
			this.n_boids.add(2);
		}
		boids = n_boids;
	}
}
