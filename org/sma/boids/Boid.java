package org.sma.boids;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

import org.sma.colors.Colors;

import gui.GraphicalElement;


public class Boid implements GraphicalElement {

	static final Path2D shape = new Path2D.Double();
	static final int size = 50;
	static int maxDSq = 10;
	Vect p, v, a;

	static {
		shape.moveTo(-size / 2, -size / 2);
		shape.lineTo(-size / 2, size / 2);
		shape.lineTo(size * 1.5, 0);
		shape.closePath();
	}
	
	public Boid(int x, int y, int vx, int vy) {
		p = new Vect(x, y);
		v = new Vect(vx, vy);
		a = new Vect(0, 0);
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

	public boolean isVisible(Boid b) {
		if(this.getP().distanceSq(b.getP()) >= maxDSq)
			return false;
		return true;
	}

	@Override
	public void paint(Graphics2D g2d) {
		// TODO: rotate the shape instead of the Graphics2D?
		AffineTransform save = g2d.getTransform();

		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Colors.color6);
		g2d.drawLine(p.getX(), p.getY(), a.getX(), a.getY());

		g2d.translate(p.getX(), p.getY());
		g2d.rotate(p.getAngle());

		g2d.setColor(Colors.color1);
		g2d.fill(shape);
		g2d.setColor(Colors.color2);
		g2d.draw(shape);

		// show speed
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Colors.color5);
		g2d.drawLine((int) (size * 1.5), 0, (int) (size * 1.5) + (int) v.getLength(), 0);

		g2d.setTransform(save);
	}

}
