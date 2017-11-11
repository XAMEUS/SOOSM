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

	static {
		shape.moveTo(-size / 2, -size / 2);
		shape.lineTo(-size / 2, size / 2);
		shape.lineTo(size * 1.5, 0);
		shape.closePath();
	}
	
	public Boid() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void paint(Graphics2D g2d) {
		// TODO: rotate the shape instead of the Graphics2D?
		AffineTransform save = g2d.getTransform();

		g2d.translate(50, 50);
		
		g2d.rotate(Math.PI / 5);
		g2d.setStroke(new BasicStroke(2));
		g2d.setColor(Colors.color6);
		g2d.drawLine((int) 0, 0, (int) (size * 1.5) + 50, 0);

		g2d.setTransform(save);
		g2d.translate(50, 50);
		g2d.rotate(0);
		
		g2d.setColor(Colors.color1);
		g2d.fill(shape);
		g2d.setColor(Colors.color2);
		g2d.draw(shape);

		// show acceleration
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Colors.color5);
		g2d.drawLine((int) (size * 1.5), 0, (int) (size * 1.5) + 50, 0);

		g2d.setTransform(save);
	}

}
