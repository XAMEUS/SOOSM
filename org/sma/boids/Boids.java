package org.sma.boids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Boids implements Iterable<Boid> {
	private List<Boid> boidsInit;
	private List<Boid> boids;
	private static int minx, miny, maxx, maxy;
	
	public Boids(int n, int minx, int miny, int maxx, int maxy, int max_speed) {
		this.boids = new ArrayList<>();
		for (int i = 0; i < n; i++)
			this.boids.add(new Boid((int)(Math.random() * (maxx - minx)) + minx, (int)(Math.random() * (maxy - minx)) + minx, (int)(Math.random() * max_speed), (int)(Math.random() * max_speed)));
		this.boidsInit = new ArrayList<>(boids);
		Boids.minx = minx;
		Boids.miny = miny;
		Boids.maxx = maxx;
		Boids.maxy = maxy;
	}
	
	public Iterator<Boid> getNeighbors(Boid boid, double maxDist) {
		return this.boids.stream().filter(x -> boid.isVisible(x, maxDist)).iterator();
	}
	
	public void nextStep() {
		double maxSpeed = 20;
		List<Boid> nBoids = new ArrayList<>();
		for(int i = 0; i < boids.size(); i++) {
			Boid b = boids.get(i);
			Vect a = cohesion(b).translate(alignment(b)).translate(separation(b)).translate(screenKeeper(b)).translate(noFriend(b));
			a.mult(Boid.getWeight());
			Vect v = b.getV().translate(a);
			v.setPol(v.getAngle(), Math.min(v.getLength(), maxSpeed));
			Vect p = b.getP().translate(v);
			nBoids.add(new Boid(p, v, a));
		}
		this.boids = nBoids;
	}
	
	public void reInit() {
		this.boids = new ArrayList<>(boidsInit);
	}
	
	public Vect cohesion(Boid b) {
		double coef = 10;
		double max_force = 10;
		int nbVois = -1;
		Vect nPos = new Vect(-b.getP().getX(), -b.getP().getY());
		for(Iterator<Boid> i = this.getNeighbors(b, 500*500); i.hasNext(); nbVois++)
			nPos = nPos.translate(i.next().getP());
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(1 / ((double) nbVois));
		nPos = nPos.translate(new Vect(-b.getP().getX(), -b.getP().getY()));
		nPos.mult(coef);
		nPos.setPol(nPos.getAngle(), Math.min(Math.pow(nPos.getLength(), 0.2), max_force));
		return nPos;			
	}
	
	public Vect alignment(Boid b) {
		double coef = 1;
		int nbVois = -1;
		Vect nVit = new Vect(-b.getV().getX(), -b.getV().getY());
		for(Iterator<Boid> i = this.getNeighbors(b, 500*500); i.hasNext(); nbVois++)
			nVit = nVit.translate(i.next().getV());
		if(nbVois < 1)
			return new Vect(0, 0);
		double ecart = nVit.getAngle() - b.getV().getAngle();
		nVit.setPol((ecart > 0 ? 1 : -1) * Math.PI / 2, coef * Math.abs(ecart));
		return nVit;
	}
	
	public Vect separation(Boid b) {
		double coef = 10;
		double max_force = 1;
		int nbVois = -1;
		Vect moinsB = new Vect(-b.getP().getX(), -b.getP().getY());
		Vect nPos = new Vect(0, 0);
		for(Iterator<Boid> i = this.getNeighbors(b, 50 * 50); i.hasNext(); nbVois++) {
			Vect toAdd = new Vect(0, 0);
			toAdd = toAdd.translate(i.next().getP()).translate(moinsB);
			toAdd.setPol(toAdd.getAngle(), Math.max(toAdd.getLength(), max_force));
			toAdd.invert();
			nPos = nPos.translate(toAdd);			
		}
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(coef / ((double) nbVois));
		nPos.setPol((-nPos.getAngle()) % (2 * Math.PI), nPos.getLength());
		return nPos;
	}
	
	public Vect screenKeeper(Boid b) {
		double coef = 20;
		double seuil = 0.01;
		double x = b.getP().getX();
		double y = b.getP().getY();
		Vect force = new Vect((x < minx + seuil * (maxx - minx) ? minx + seuil * (maxx - minx) - x: 0) -
							  (x > maxx - seuil * (maxx - minx) ? x - maxx + seuil * (maxx - minx) : 0),
							  (y < miny + seuil * (maxy - miny) ? miny + seuil * (maxy - miny) - y : 0) -
							  (y > maxy - seuil * (maxy - miny) ? y - maxy + seuil * (maxy - miny) : 0));
		force.mult(coef);
		return force;
	}
	
	public Vect noFriend(Boid b) {
		int nbVois = -1;
		for(Iterator<Boid> i = this.getNeighbors(b, 500 * 500); i.hasNext(); nbVois++) i.next();
		Vect force = new Vect(0, 0);
		if(nbVois == 0) {
			force.setPol(Math.PI / 2, 0.01);
		}
		return force;
	}

	@Override
	public Iterator<Boid> iterator() {
		return this.boids.iterator();
	}
}
