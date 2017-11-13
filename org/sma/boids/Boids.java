package org.sma.boids;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Boids implements Iterable<Boid> {
	private List<Boid> boidsInit;
	private List<Boid> boids;
	private int minx, miny, maxx, maxy;
	private double maxSpeed;
	
	private double coefCohesion;
	private double distanceCohesion;
	private double powCohesion;
	private double fMaxCohesion;
	
	private double coefAlignment;
	private double distanceAlignment;

	private double coefSeparation;
	private double distanceSeparation;
	private double fMaxSeparation;
	
	private double coefKeeper;
	private double seuilKeeper;
	
	private double distanceNoFriend;
	private double fNoFriend;
	
	private Color color;
	
	public Boids(int n, int minx, int miny, int maxx, int maxy, int max_speed, Color color) {
		this.boids = new ArrayList<>();
		this.color = color;
		for (int i = 0; i < n; i++)
			this.boids.add(new Boid((int)(Math.random() * (maxx - minx)) + minx,
									(int)(Math.random() * (maxy - minx)) + minx,
									(int)(Math.random() * max_speed),
									(int)(Math.random() * max_speed),
									this.color));
		this.boidsInit = new ArrayList<>(boids);
		this.minx = minx;
		this.miny = miny;
		this.maxx = maxx;
		this.maxy = maxy;
		this.maxSpeed = max_speed;
		this.setCohesion(1, 500, 0.001, 10);
		this.setAlignment(1, 500);
		this.setSeparation(10, 50, 1);
		this.setKeeper(0.1, 0.01);
		this.setNoFriend(500, 5);
	}
	
	public void setCohesion(double coefCohesion, double distanceCohesion, double powCohesion, double fMaxCohesion) {
		this.coefCohesion = coefCohesion;
		this.distanceCohesion = distanceCohesion;
		this.powCohesion = powCohesion;
		this.fMaxCohesion = fMaxCohesion;
	}
	
	public void setAlignment(double coefAlignment, double distanceAlignment) {
		this.coefAlignment = coefAlignment;
		this.distanceAlignment = distanceAlignment;
	}
	
	public void setSeparation(double coefSeparation, double distanceSeparation, double fMaxSeparation) {
		this.coefSeparation = coefSeparation;
		this.distanceSeparation = distanceSeparation;
		this.fMaxSeparation = fMaxSeparation;
	}
	
	public void setKeeper(double coefKeeper, double seuilKeeper) {
		this.coefKeeper = coefKeeper;
		this.seuilKeeper = seuilKeeper;
	}
	
	public void setNoFriend(double distanceNoFriend, double fNoFriend) {
		this.distanceNoFriend = distanceNoFriend;
		this.fNoFriend = fNoFriend;
	}
	
	public Iterator<Boid> getNeighbors(Boid boid, double maxDist) {
		return this.boids.stream().filter(x -> boid.isVisible(x, maxDist)).iterator();
	}
	
	public List<Boid> getBoids() {
		return boids;
	}
	/**
	 * Implémente le pas suivant. La simulation réalise un PFD sur chaque boid à partir des données de ce qui va devenir l'ancien pas.
	 */
	public void nextStep() {
		List<Boid> nBoids = new ArrayList<>();
		for(int i = 0; i < boids.size(); i++) {
			Boid b = boids.get(i);
			Vect a = cohesion(b).translate(alignment(b)).translate(separation(b)).translate(screenKeeper(b)).translate(noFriend(b));
			a.mult(Boid.getWeight());
			Vect v = b.getV().translate(a);
			v.setPol(v.getAngle(), Math.min(v.getLength(), this.maxSpeed));
			Vect p = b.getP().translate(v);
			nBoids.add(new Boid(p, v, a, this.color));
		}
		this.boids = nBoids;
	}
	
	public void reInit() {
		this.boids = new ArrayList<>(boidsInit);
	}
	
	/**
	 * Implémente la force de cohésion. Pour plus de détails lire le sujet.
	 * @param b
	 * @return
	 */
	public Vect cohesion(Boid b) {
		int nbVois = -1;
		Vect nPos = new Vect(-b.getP().getX(), -b.getP().getY());
		for(Iterator<Boid> i = this.getNeighbors(b, this.distanceCohesion*this.distanceCohesion); i.hasNext(); nbVois++)
			nPos = nPos.translate(i.next().getP());
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(1 / ((double) nbVois));
		nPos = nPos.translate(new Vect(-b.getP().getX(), -b.getP().getY()));
		nPos.mult(this.coefCohesion);
		nPos.setPol(nPos.getAngle(), Math.min(Math.pow(nPos.getLength(), this.powCohesion), this.fMaxCohesion));
		return nPos;			
	}
	
	/**
	 * Implémente la force d'alignement. Pour plus de détails lire le sujet.
	 * @param b
	 * @return
	 */
	public Vect alignment(Boid b) {
		int nbVois = -1;
		Vect nVit = new Vect(-b.getV().getX(), -b.getV().getY());
		for(Iterator<Boid> i = this.getNeighbors(b, this.distanceAlignment * this.distanceAlignment); i.hasNext(); nbVois++)
			nVit = nVit.translate(i.next().getV());
		if(nbVois < 1)
			return new Vect(0, 0);
		double ecart = nVit.getAngle() - b.getV().getAngle();
		nVit.setPol((ecart > 0 ? 1 : -1) * Math.PI / 2, this.coefAlignment * Math.abs(ecart));
		return nVit;
	}
	
	/**
	 * Implémente la force de séparation. Pour plus de détails lire le sujet.
	 * @param b
	 * @return
	 */
	public Vect separation(Boid b) {
		int nbVois = -1;
		Vect moinsB = new Vect(-b.getP().getX(), -b.getP().getY());
		Vect nPos = new Vect(0, 0);
		for(Iterator<Boid> i = this.getNeighbors(b, this.distanceSeparation * this.distanceSeparation); i.hasNext(); nbVois++) {
			Vect toAdd = new Vect(0, 0);
			toAdd = toAdd.translate(i.next().getP()).translate(moinsB);
			toAdd.setPol(toAdd.getAngle(), Math.max(toAdd.getLength(), this.fMaxSeparation));
			toAdd.invert();
			nPos = nPos.translate(toAdd);			
		}
		if(nbVois < 1)
			return new Vect(0, 0);
		nPos.mult(this.coefSeparation / ((double) nbVois));
		nPos.setPol((-nPos.getAngle()) % (2 * Math.PI), nPos.getLength());
		return nPos;
	}
	
	/**
	 * Si b sort de l'écran, screenKeeper va retourner une force suffisamment grande pour lui permettre de revenir dans l'écran.
	 * @param b
	 * @return
	 */
	public Vect screenKeeper(Boid b) {
		double x = b.getP().getX();
		double y = b.getP().getY();
		Vect force = new Vect((x < minx + this.seuilKeeper * (maxx - minx) ? minx + this.seuilKeeper * (maxx - minx) - x: 0) -
							  (x > maxx - this.seuilKeeper * (maxx - minx) ? x - maxx + this.seuilKeeper * (maxx - minx) : 0),
							  (y < miny + this.seuilKeeper * (maxy - miny) ? miny + this.seuilKeeper * (maxy - miny) - y : 0) -
							  (y > maxy - this.seuilKeeper * (maxy - miny) ? y - maxy + this.seuilKeeper * (maxy - miny) : 0));
		force.mult(this.coefKeeper);
		return force;
	}
	
	/**
	 * Si b n'a pas de voisin visible, noFriend va retourner une force perpendiculaire à sa vitesse, lui permettant de tourner.
	 * @param b
	 * @return
	 */
	public Vect noFriend(Boid b) {
		int nbVois = -1;
		for(Iterator<Boid> i = this.getNeighbors(b, this.distanceNoFriend * this.distanceNoFriend); i.hasNext(); nbVois++) i.next();
		Vect force = new Vect(0, 0);
		if(nbVois == 0) {
			force.setPol((b.getV().getAngle() + Math.PI / 2) % (2 * Math.PI), this.fNoFriend);
		}
		return force;
	}

	@Override
	public Iterator<Boid> iterator() {
		return this.boids.iterator();
	}
}
