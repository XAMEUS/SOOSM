package org.sma.balls;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gui.Oval;

public class Balls implements Iterable<Ball> {
	
	public List<Ball> balls;
	private int dx, dy;
	
	public Balls(int n) {
		this.balls = new ArrayList<>();
		for (int i = 0; i < n; i++)
			this.balls.add(new Ball((int)(Math.random() * 100), (int)(Math.random() * 100), Color.GREEN, Color.darkGray, 20));
	}
	
	public void translate(int dx, int dy) {
		this.dx += dx;
		this.dy += dy;
		for (Oval b:this.balls)
			b.translate(dx, dy);
	}
	
	public void reInit() {
		for (Oval b:this.balls)
			b.translate(-this.dx, -this.dy);
		this.dx = 0;
		this.dy = 0;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < this.balls.size() - 1; i++)
			s += this.balls.get(i) + ", ";
		if (this.balls.size() > 1)
			s += this.balls.get(this.balls.size() - 1);
		return s;
	}

	@Override
	public Iterator<Ball> iterator() {
		return this.balls.iterator();
	}
	
}
