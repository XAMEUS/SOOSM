package org.sma.boids;

public class Vect {
	private int x = 0;
	private int y = 0;
	private double angle = 0;
	private double length = 0;
	
	public Vect(Vect v) {
		this.x = v.getX();
		this.y = v.getY();
		this.angle = v.getAngle();
		this.length = v.getLength();
	}

	public Vect(int x, int y) {
		this.setCart(x, y);
	}

	public Vect(double angle, double length) {
		this.setPol(angle, length);
	}
	
	public void translate(Vect v) {
		setCart(this.x + v.getX(), this.y + v.getY());
	}
	
	public void setCart(int x, int y) {
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

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

	public double getLength() {
		return length;
	}
	
	static int distanceSq(int x, int y) {
		return x * x + y * y;
	}

	static double distance(int x, int y) {
		return Math.sqrt((double) distanceSq(x, y));
	}

	static double angle(int x, int y) {
		if (x != 0)
			return Math.atan(y / x) + (x > 0 ? 1 : 0) * (y >= 0 ? 1 : -1)
					* Math.PI;
		else if (y != 0)
			return (y >= 0 ? 1 : -1) * Math.PI / 2;
		else
			return 0.;
	}

	static int x(double angle, double length) {
		return (int) (length * Math.cos(angle));
	}

	static int y(double angle, double length) {
		return (int) (length * Math.sin(angle));
	}
}
