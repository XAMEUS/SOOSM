package org.sma.balls;

import java.awt.Color;

import gui.Oval;

public class Ball extends Oval {

	private int xOri, yOri;
	private int vx, vy;

	public Ball(int x, int y, Color drawColor, Color fillColor, int size) {
		super(x, y, drawColor, fillColor, size);
		this.xOri = x;
		this.yOri = y;
	}

	public void setVelocity(int vx, int vy) {
		this.vx = 4 * vx;
		this.vy = 4 * vy;
	}

	public void update(int minX, int maxX, int minY, int maxY) {
		super.translate(this.vx, this.vy);
		if (this.getX() < minX)
			this.vx = -this.vx;
		if (this.getX() > maxX)
			this.vx = -this.vx;
		if (this.getY() < minY)
			this.vy = -this.vy;
		if (this.getY() > maxY)
			this.vy = -this.vy;
	}

	public void init() {
		this.translate(this.xOri - this.getX(), this.yOri - this.getY());
	}

}
