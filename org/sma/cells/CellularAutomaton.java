package org.sma.cells;

import java.awt.Color;
import java.lang.IllegalArgumentException;

public abstract class CellularAutomaton {
	
	protected States s;
	private int minX, minY;
	private int rectSize;
	
	public CellularAutomaton (int minX, int minY, int maxX, int maxY, int numRectRow, Color[] colors) {
		if (minX >= maxX || minY >= maxY || minX < 0 || minY < 0 || maxX < 0 || maxY < 0 || numRectRow < 0)
			throw new IllegalArgumentException();
		this.minY = minY;
		this.rectSize = Math.min((int)(maxX - minX)/numRectRow, (int)(maxY - minY)/numRectRow);
		
		int sizeX = (int)(maxX - minX)/this.rectSize;
		int sizeY = (int)(maxY - minY)/this.rectSize;
		this.s = new States(sizeX, sizeY, colors);
	}

	public int getMinX() {
		return minX;
	}

	public int getMinY() {
		return minY;
	}
	
	public int getRectSize() {
		return rectSize;
	}
	
}
