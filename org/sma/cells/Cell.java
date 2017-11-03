package org.sma.cells;

import gui.Rectangle;

public class Cell {
	
	private int x;
	private int y;
	Rectangle r;
	
	public Cell (int x, int y, Rectangle r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
