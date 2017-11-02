package org.sma.cells;

import java.awt.Color;
import java.lang.IllegalArgumentException;

import gui.Rectangle;

public abstract class CellularAutomaton {
	
	protected States s;
	private int minX, minY;
	private int rectSize;
	
	public CellularAutomaton (int minX, int minY, int maxX, int maxY, int numRectRow, Color[] colors) {
		if (minX >= maxX || minY >= maxY || minX < 0 || minY < 0 || maxX < 0 || maxY < 0 || numRectRow < 0)
			throw new IllegalArgumentException();
		this.minX = minX;
		this.minY = minY;
		this.rectSize = Math.min((int)(maxX - minX)/numRectRow, (int)(maxY - minY)/numRectRow);
		
		int sizeX = (int)(maxX - minX)/this.rectSize;
		int sizeY = (int)(maxY - minY)/this.rectSize;
		this.s = new States(sizeX, sizeY, colors);
	}
	
	public void initRect() {
		for (int x = 0; x < s.getSizeX(); x++) {
			for (int y = 0; y < s.getSizeY(); y++) {
				int randState = s.setRandomState(x, y);
				s.setOrigin(x, y, randState);
				addRect(x, y, randState);
			}
		}
	}

	public Rectangle newRectangle (int x, int y, int state) {
		int absX = x * rectSize + minX;
		int absY = y * rectSize + minY;
		return new Rectangle(absX, absY, Color.black, s.getColor(state), rectSize);
	}
	
	public int relativeX(int absoluteX) {
		return (absoluteX - minX) / rectSize;
	}
	
	public int relativeY(int absoluteY) {
		return (absoluteY - minY) / rectSize;
	}

	public int getRectSize() {
		return rectSize;
	}
	
	public abstract void addRect(int x, int y, int state);
	
	public void restart() {
		for (int x = 0; x < s.getSizeX(); x++)
			for (int y = 0; y < s.getSizeY(); y++)
				addRect(x, y, s.restart(x, y));
	}
	
}
