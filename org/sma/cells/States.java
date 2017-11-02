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
		return this.sizeX;
	}
	
	public int getSizeY() {
		return this.sizeY;
	}
	
	public State getState(int x, int y) {
		return this.states[x][y];
	}
	
	public int[] numNeighbors(int x, int y) {
		int[] count = new int[this.colors.length];
		count[this.states[x][y].getOldState()]--;
		for (int ix = max(x-1, 0); ix <= min(x+1, this.sizeX-1); ix++) {
			for (int iy = max(y-1, 0); iy <= min(y+1, this.sizeY-1); iy++) {
					count[this.states[ix][iy].getOldState()]++;
			}
		}
		return count;
	}
	
	public void setState(int x, int y, int newState)
	{
		this.states[x][y].updateState(newState);
	}
	
	public void setOrigin(int x, int y, int oriState)
	{
		this.states[x][y].setOriState(oriState);
	}
	
	public int restart(int x, int y) {
		return this.states[x][y].backOrigin();
	}
	
	public int setRandomState(int x, int y)
	{
		int randomState = (int)(Math.random() * (this.colors.length - 1)) + 1;
		this.setState(x, y, randomState);
		return randomState;
	}
	
	public Color getColor(int state) {
		if (state < 0 || state >= this.colors.length)
			throw new IllegalArgumentException("Not in range of colors");
		return this.colors[state];
	}
	
	public void finishUpdate() {
		for (int x = 0; x < this.sizeX; x++)
			for (int y = 0; y < this.sizeY; y++)
				this.states[x][y].finishUpdate();
	}
	

}
