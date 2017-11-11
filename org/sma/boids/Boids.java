package org.sma.boids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Boids implements Iterable<Boid> {
	private List<Boid> boidsInit;
	private List<Boid> boids;
	
	public Boids(int n, int maxx, int maxy, int max_speed) {
		this.boids = new ArrayList<>();
		for (int i = 0; i < n; i++)
			this.boids.add(new Boid((int)(Math.random() * maxx), (int)(Math.random() * maxy), (int)(Math.random() * max_speed), (int)(Math.random() * max_speed)));
		this.boidsInit = new ArrayList<>(boids);
	}
	
	public Iterator<Boid> getNeighbors(Boid boid) {
		return this.boids.stream().filter(x -> boid.isVisible(x)).iterator();
	}
	
	public void nextStep() {
		List<Boid> nBoids = new ArrayList<>();
		for(int i = 0; i < boids.size(); i++) {
			Boid b = boids.get(i);
			Vect a = cohesion(b).translate(alignment(b)).translate(separation(b));
			a.mult(Boid.getWeight());
			Vect v = b.getV().translate(a);
			Vect p = b.getP().translate(v);
			nBoids.add(new Boid(p, v, a));
		}
		this.boids = nBoids;
	}
	
	public void reInit() {
		this.boids = new ArrayList<>(boidsInit);
	}
	
	public Vect cohesion(Boid b) {
		double coef = 1;
		int nbVois = -1;
		Vect nPos = new Vect(-b.getP().getX(), -b.getP().getY());
		for(Iterator<Boid> i = this.getNeighbors(b); i.hasNext(); nbVois++)
			nPos = nPos.translate(i.next().getP());
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(1 / ((double) nbVois));
		nPos.translate(new Vect(-b.getP().getX(), -b.getP().getY()));
		nPos.mult(coef);
		return nPos;
	}
	
	public Vect alignment(Boid b) {
		double coef = 1;
		int nbVois = -1;
		Vect nVit = new Vect(-b.getV().getX(), -b.getV().getY());
		for(Iterator<Boid> i = this.getNeighbors(b); i.hasNext(); nbVois++)
			nVit = nVit.translate(i.next().getV());
		if(nbVois < 1)
			return new Vect(0, 0);
		nVit.mult(coef / ((double) nbVois));
		return nVit;
	}
	
	public Vect separation(Boid b) {
		double coef = 1;
		int nbVois = -1;
		Vect moinsB = new Vect(-b.getP().getX(), -b.getP().getY());
		Vect nPos = new Vect(0, 0);
		for(Iterator<Boid> i = this.getNeighbors(b); i.hasNext(); nbVois++)
			nPos = nPos.translate(i.next().getP()).translate(moinsB);
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(coef / ((double) nbVois));
		return nPos;
	}

	@Override
	public Iterator<Boid> iterator() {
		return this.boids.iterator();
	}
}
