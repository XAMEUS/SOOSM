package org.sma.cells;

import java.awt.Color;
import java.util.Arrays;
import static java.lang.Math.*;
import java.lang.IllegalArgumentException;

public class States {

	private State[][] states;
	private int sizeX, sizeY;
	private Color[] colors;
	
	public States (int sizeX, int sizeY, Color...colors) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		if (colors.length < 2)
			throw new IllegalArgumentException("Array of Colors has at least two Colors");
		this.colors = Arrays.copyOf(colors, colors.length);
		this.states = new State[sizeX][sizeY];
		for (int ix = 0; ix < sizeX; ix++) {
			for (int iy = 0; iy < sizeY; iy++) {
				states[ix][iy] = new State(0);
			}
		}
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}
	
	public State getState(int x, int y) {
		return states[x][y];
	}
	
	public int numState() {
		return colors.length;
	}
	
	public int numSameNeighbors(int x, int y, int state) {
		int count = 0;
		for (int ix = max(x-1, 0); ix <= min(x+1, sizeX-1); ix++) {
			for (int iy = max(y-1, 0); iy <= min(y+1, sizeY-1); iy++) {
					if (states[ix][iy].getOldState() == state)
						count++;
			}
		}
		return count;
	}
	
	public int numDiffNeighbors(int x, int y, int state) {
		int count = 0;
		for (int ix = max(x-1, 0); ix <= min(x+1, sizeX-1); ix++) {
			for (int iy = max(y-1, 0); iy <= min(y+1, sizeY-1); iy++) {
					if (states[ix][iy].getOldState() != state)
						count++;
			}
		}
		return count;
	}
	
	public void setState(int x, int y, int newState)
	{
		states[x][y].updateState(newState);
	}
	
	public void setOrigin(int x, int y, int oriState)
	{
		states[x][y].setOriState(oriState);
	}
	
	public int restart(int x, int y) {
		return states[x][y].backOrigin();
	}
	
	public int setRandomState(int x, int y)
	{
		int randomState = (int)(Math.random() * colors.length);
		this.setState(x, y, randomState);
		return randomState;
	}
	
	public Color getColor(int state) {
		if (state < 0 || state >= colors.length)
			throw new IllegalArgumentException("Not in range of colors");
		return colors[state];
	}
	
	public void finishUpdate() {
		for (int x = 0; x < sizeX; x++)
			for (int y = 0; y < sizeY; y++)
				states[x][y].finishUpdate();
	}
	

}
