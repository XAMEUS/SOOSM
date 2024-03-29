package org.sma.boids;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import org.sma.colors.Colors;

import gui.GraphicalElement;

public class Boid implements GraphicalElement {

	static final Path2D shape = new Path2D.Double();
	static final int size = 20;
	static double maxAngle = Math.PI * 2 / 3;
	static double weight = 0.3;
	private double maxSpeed;

	public static double getWeight() {
		return weight;
	}

	Vect p, v, a;

	private final Color color;

	static {
		shape.moveTo(-size / 2, -size / 2);
		shape.lineTo(-size / 2, size / 2);
		shape.lineTo(size * 1.5, 0);
		shape.closePath();
	}

	public Boid(int x, int y, int vx, int vy, Color color, double maxSpeed) {
		p = new Vect(x, y);
		v = new Vect(vx, vy);
		a = new Vect(0, 0);
		this.maxSpeed = maxSpeed;
		this.color = color;
	}

	public Boid(Vect p, Vect v, Vect a, Color color, double maxSpeed) {
		this.p = p;
		this.v = v;
		this.a = a;
		this.maxSpeed = maxSpeed;
		this.color = color;
	}

	public Vect getP() {
		return p;
	}

	public Vect getV() {
		return v;
	}

	public Vect getA() {
		return a;
	}

	public double getMaxSpeed() {
		return this.maxSpeed;
	}

	/**
	 * Returns if b is visible by this object. Uses also Boid.maxAngle.
	 * 
	 * @param b
	 * @param maxDist:
	 *            Maximum distance between this object and b
	 * @return
	 */
	public boolean isVisible(Boid b, double maxDist) {
		return this.getP().distanceSq(b.getP()) <= maxDist && this.getV().angleInRange(b.getV(), Boid.maxAngle);
	}

	@Override
	public void paint(Graphics2D g2d) {
		// TODO: rotate the shape instead of the Graphics2D?
		AffineTransform save = g2d.getTransform();

		g2d.translate((int) p.getX(), (int) p.getY());
		g2d.rotate(v.getAngle());

		// g2d.setColor(Colors.color1);
		g2d.setColor(this.color);
		g2d.fill(shape);
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Colors.color2);
		g2d.draw(shape);

		// show speed
		g2d.setColor(Colors.color5);
		g2d.drawLine((int) (size * 1.5), 0, (int) (size * 1.5) + (int) v.getLength(), 0);

		g2d.setTransform(save);

		// g2d.setStroke(new BasicStroke(2));
		// g2d.setColor(Colors.color6);
		// g2d.drawLine((int) p.getX(), (int) p.getY(), (int) a.getX() + (int)
		// p.getX(), (int) a.getY() + (int) p.getY());
	}

}
