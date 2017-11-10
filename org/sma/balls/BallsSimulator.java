package org.sma.balls;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.Simulable;

public class BallsSimulator implements Simulable, Iterable<Ball> {

	private List<Ball> balls;
	private int minX, minY, maxX, maxY;

	public BallsSimulator(int n, int minX, int minY, int maxX, int maxY) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.balls = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			int x = this.maxX - this.minX - 2 * Ball.getR();
			int y = this.maxY - this.minY - 2 * Ball.getR();
			Ball b = new Ball((int)(this.minX + Ball.getR() + Math.random() * x), (int)(this.minY + Ball.getR() + Math.random() * y), Color.GREEN, Color.darkGray);
			b.setVelocity(1 - (int) (Math.random() * 2) * 2, 1 - (int) (Math.random() * 2) * 2);
			this.balls.add(b);
		}
	}

	@Override
	public void next() {
		for (Ball b : this.balls)
			b.update(this.minX, this.maxX, this.minY, this.maxY);
	}

	@Override
	public void restart() {
		for (Ball b : this.balls)
			b.init();
	}

	@Override
	public Iterator<Ball> iterator() {
		return this.balls.iterator();
	}

}
