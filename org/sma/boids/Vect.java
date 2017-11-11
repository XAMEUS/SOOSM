package org.sma.boids;

public class Vect {
	private double x = 0;
	private double y = 0;
	private double angle = 0;
	private double length = 0;
	
	public Vect(Vect v) {
		this.x = v.getX();
		this.y = v.getY();
		this.angle = v.getAngle();
		this.length = v.getLength();
	}

	public Vect(double x, double y) {
		this.setCart(x, y);
	}
	
	public Vect translate(Vect v) {
		Vect n = new Vect(this);
		n.setCart(this.x + v.getX(), this.y + v.getY());
		return n;
	}
	
	public void mult(double scal) {
		this.setPol(this.angle, this.length * scal);
	}
	
	public void invert() {
		if(this.x != 0 && this.y != 0)
			this.setCart(1 / this.x, 1 / this.y);
	}
	
	public void power(double scal) {
		this.setCart(Math.pow(this.x, scal), Math.pow(this.y, scal));
	}
	
	public void setCart(double x, double y) {
		this.x = x;
		this.y = y;
		this.angle = Vect.angle(x, y);
		this.length = Vect.distance(x, y);
	}

	public void setPol(double angle, double length) {
		this.angle = angle;
		this.length = length;
		this.x = Vect.x(angle, length);
		this.y = Vect.y(angle, length);
	}
	
	public boolean angleInRange(Vect v, double range) {
		double base = (this.angle % ( 2 * Math.PI)) + 2 * Math.PI;
		double candidate = (v.getAngle() % ( 2 * Math.PI)) + 2 * Math.PI;
		return base - range <= candidate && candidate <= base + range;		
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

	public double getLength() {
		return length;
	}
	
	public double distanceSq(Vect b) {
		return (this.x - b.x) * (this.x - b.x) + (this.y - b.y) * (this.y - b.y);
	}
	
	static double distanceSq(double x, double y) {
		return x * x + y * y;
	}

	static double distance(double x, double y) {
		return Math.sqrt((double) distanceSq(x, y));
	}

	static double angle(double x, double y) {
		if (x != 0)
			return Math.atan(y / x) + (x > 0 ? 0 : 1) * (y >= 0 ? 1 : -1)
					* Math.PI;
		else if (y != 0)
			return (y >= 0 ? 1 : -1) * Math.PI / 2;
		else
			return 0.;
	}

	static double x(double angle, double length) {
		return (double) (length * Math.cos(angle));
	}

	static double y(double angle, double length) {
		return (double) (length * Math.sin(angle));
	}
}
