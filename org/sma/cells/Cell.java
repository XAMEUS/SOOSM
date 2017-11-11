package org.sma.cells;

import gui.Rectangle;


/**
 * Classe représentant une cellule dans le modèle de Schelling.
 * @see ShellingModel
 * 
 * @author 3
 *
 */
public class Cell {
	
	private int x, y;
	
	private Rectangle rect;
	
	public Cell(int x, int y, Rectangle rect) {
		this.x = x;
		this.y = y;
		this.rect = rect;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Rectangle getRect() {
		return rect;
	}
	
	public int getRectX() {
		return rect.getX();
	}
	
	public int getRectY() {
		return rect.getY();
	}
	
	public void translate(int rectX, int rectY) {
		rect.translate(rectX, rectY);
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
