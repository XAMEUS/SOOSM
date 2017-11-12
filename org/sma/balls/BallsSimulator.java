package org.sma.balls;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sma.events.Event;
import org.sma.events.EventManager;

import gui.Simulable;

/**
 * Classe représentant un simulateur de Ball.
 * @author julie
 *
 */
public class BallsSimulator implements Simulable, Iterable<Ball> {

	/**
	 * Liste des Ball du simulateur.
	 */
	private List<Ball> balls;
	
	/**
	 * Borne de la zone de simulation en pixels.
	 */
	private int minX, minY, maxX, maxY;
	private EventManager em;
	
	private class UpdateBalls extends Event {
		public UpdateBalls(long date) {
			super(date);
		}
		@Override
		public void execute() {
			for (Ball b : BallsSimulator.this.balls)
				b.update(BallsSimulator.this.minX, BallsSimulator.this.maxX, BallsSimulator.this.minY, BallsSimulator.this.maxY);
			BallsSimulator.this.em.addEvent(new UpdateBalls(this.getDate()+1));
		}
	}

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
		em = new EventManager();
		em.addEvent(new UpdateBalls(1));
	}

	@Override
	public void next() {
		this.em.next();
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
