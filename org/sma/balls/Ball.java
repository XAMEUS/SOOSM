package org.sma.balls;

import java.awt.Color;

import gui.Oval;


/**
 * Classe représentant une balle.
 * @author julie
 *
 */
public class Ball extends Oval {

	/**
	 * Coordonnée de la balle.
	 */
	private int xOri, yOri;
	
	/**
	 * Coordonnée du vecteur vitesse de la balle.
	 */
	private int vx, vy;
	
	/**
	 * Rayon par défaut d'une balle.
	 */
	private static int R = 10;

	public Ball(int x, int y, Color drawColor, Color fillColor, int size) {
		super(x, y, drawColor, fillColor, size);
		this.xOri = x;
		this.yOri = y;
	}
	
	public Ball(int x, int y, Color drawColor, Color fillColor) {
		super(x, y, drawColor, fillColor, 2 * R);
		this.xOri = x;
		this.yOri = y;
	}

	public static int getR() {
		return R;
	}

	public void setVelocity(int vx, int vy) {
		this.vx = 4 * vx;
		this.vy = 4 * vy;
	}

	/**
	 * Translate la balle.
	 * Rebondit si elle dépasse les bornes.
	 * @param minX
	 * @param maxX
	 * @param minY
	 * @param maxY
	 */
	public void update(int minX, int maxX, int minY, int maxY) {
		super.translate(this.vx, this.vy);
		if (this.getX() - Ball.R < minX)
			this.vx = -this.vx;
		if (this.getX() + Ball.R > maxX)
			this.vx = -this.vx;
		if (this.getY() - Ball.R < minY)
			this.vy = -this.vy;
		if (this.getY() + Ball.R > maxY)
			this.vy = -this.vy;
	}

	/**
	 * Place la balle à sa position initiale.
	 */
	public void init() {
		this.translate(this.xOri - this.getX(), this.yOri - this.getY());
	}

}
