package org.sma.boids;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Boids implements Iterable<Boid> {
	private List<Boid> boidsInit;
	private List<Boid> boids;
	private static int minx, miny, maxx, maxy;
	private static double maxSpeed = 20;
	private static double coefCohesion = 10;
	private static double distanceCohesion = 500;
	private static double powCohesion = 0.001;
	private static double fMaxCohesion = 10;
	private static double coefAlignment = 1;
	private static double distanceAlignment = 500;
	private static double coefSeparation = 10;
	private static double distanceSeparation = 50;
	private static double fMaxSeparation = 1;
	private static double coefKeeper = 0.1;
	private static double seuilKeeper = 0.01;
	private static double distanceNoFriend = 500;
	private static double fNoFriend = 0.01;
	
	
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
		List<Boid> nBoids = new ArrayList<>();
		for(int i = 0; i < boids.size(); i++) {
			Boid b = boids.get(i);
			Vect a = cohesion(b).translate(alignment(b)).translate(separation(b)).translate(screenKeeper(b)).translate(noFriend(b));
			a.mult(Boid.getWeight());
			Vect v = b.getV().translate(a);
			v.setPol(v.getAngle(), Math.min(v.getLength(), Boids.maxSpeed));
			Vect p = b.getP().translate(v);
			nBoids.add(new Boid(p, v, a));
		}
		this.boids = nBoids;
	}
	
	public void reInit() {
		this.boids = new ArrayList<>(boidsInit);
	}
	
	public Vect cohesion(Boid b) {
		int nbVois = -1;
		Vect nPos = new Vect(-b.getP().getX(), -b.getP().getY());
		for(Iterator<Boid> i = this.getNeighbors(b, Boids.distanceCohesion*Boids.distanceCohesion); i.hasNext(); nbVois++)
			nPos = nPos.translate(i.next().getP());
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(1 / ((double) nbVois));
		nPos = nPos.translate(new Vect(-b.getP().getX(), -b.getP().getY()));
		nPos.mult(Boids.coefCohesion);
		nPos.setPol(nPos.getAngle(), Math.min(Math.pow(nPos.getLength(), Boids.powCohesion), Boids.fMaxCohesion));
		return nPos;			
	}
	
	public Vect alignment(Boid b) {
		int nbVois = -1;
		Vect nVit = new Vect(-b.getV().getX(), -b.getV().getY());
		for(Iterator<Boid> i = this.getNeighbors(b, Boids.distanceAlignment * Boids.distanceAlignment); i.hasNext(); nbVois++)
			nVit = nVit.translate(i.next().getV());
		if(nbVois < 1)
			return new Vect(0, 0);
		double ecart = nVit.getAngle() - b.getV().getAngle();
		nVit.setPol((ecart > 0 ? 1 : -1) * Math.PI / 2, Boids.coefAlignment * Math.abs(ecart));
		return nVit;
	}
	
	public Vect separation(Boid b) {
		int nbVois = -1;
		Vect moinsB = new Vect(-b.getP().getX(), -b.getP().getY());
		Vect nPos = new Vect(0, 0);
		for(Iterator<Boid> i = this.getNeighbors(b, Boids.distanceSeparation * Boids.distanceSeparation); i.hasNext(); nbVois++) {
			Vect toAdd = new Vect(0, 0);
			toAdd = toAdd.translate(i.next().getP()).translate(moinsB);
			toAdd.setPol(toAdd.getAngle(), Math.max(toAdd.getLength(), Boids.fMaxSeparation));
			toAdd.invert();
			nPos = nPos.translate(toAdd);			
		}
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(Boids.coefSeparation / ((double) nbVois));
		nPos.setPol((-nPos.getAngle()) % (2 * Math.PI), nPos.getLength());
		return nPos;
	}
	
	public Vect screenKeeper(Boid b) {
		double x = b.getP().getX();
		double y = b.getP().getY();
		Vect force = new Vect((x < minx + Boids.seuilKeeper * (maxx - minx) ? minx + Boids.seuilKeeper * (maxx - minx) - x: 0) -
							  (x > maxx - Boids.seuilKeeper * (maxx - minx) ? x - maxx + Boids.seuilKeeper * (maxx - minx) : 0),
							  (y < miny + Boids.seuilKeeper * (maxy - miny) ? miny + Boids.seuilKeeper * (maxy - miny) - y : 0) -
							  (y > maxy - Boids.seuilKeeper * (maxy - miny) ? y - maxy + Boids.seuilKeeper * (maxy - miny) : 0));
		force.mult(Boids.coefKeeper);
		return force;
	}
	
	public Vect noFriend(Boid b) {
		int nbVois = -1;
		for(Iterator<Boid> i = this.getNeighbors(b, Boids.distanceNoFriend * Boids.distanceNoFriend); i.hasNext(); nbVois++) i.next();
		Vect force = new Vect(0, 0);
		if(nbVois == 0) {
			force.setPol(Math.PI / 2, Boids.fNoFriend);
		}
		return force;
	}

	@Override
	public Iterator<Boid> iterator() {
		return this.boids.iterator();
	}
}
