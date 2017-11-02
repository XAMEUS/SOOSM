package org.sma.cells;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.Color;
import java.lang.IllegalArgumentException;

public class GameOfLife implements Simulable {

	private GUISimulator gi;
	private States s;
	private int minX, minY;
	private int rectSize;
	
	public GameOfLife (int minX, int minY, int maxX, int maxY, int numRectRow, int n, GUISimulator gi, Color... colors) {
		if (minX >= maxX || minY >= maxY || minX < 0 || minY < 0 || maxX < 0 || maxY < 0 || numRectRow < 0)
			throw new IllegalArgumentException();
		this.minX = minX;
		this.minY = minY;
		this.rectSize = Math.min((int)(maxX - minX)/numRectRow, (int)(maxY - minY)/numRectRow);
		this.gi = gi;
		
		int sizeX = (int)(maxX - minX)/this.rectSize;
		int sizeY = (int)(maxY - minY)/this.rectSize;
		this.s = new States(sizeX, sizeY, colors);
		
		for (int x = 0; x < sizeX; x++) {
			for (int y = 0; y < sizeY; y++) {
				Rectangle r = new Rectangle(x * this.rectSize + minX, y * this.rectSize + minY, Color.black, colors[0], rectSize);
				gi.addGraphicalElement(r);
			}
		}
		this.initialize(n);
	}
	
	private void addRect(int x, int y, int state) {
		int xAbs = x * this.rectSize + this.minX;
		int yAbs = y * this.rectSize + this.minY;
		Rectangle r = new Rectangle(xAbs, yAbs, Color.black, this.s.getColor(state), this.rectSize);
		gi.addGraphicalElement(r);
	}
	
	private void initialize(int n) {
		int randx, randy;
		for (int i = 0; i < n; i++) {
			randx = (int) (this.s.getSizeX() * Math.random());
			randy = (int) (this.s.getSizeY() * Math.random());
			int randState = this.s.setRandomState(randx, randy);
			this.s.setOrigin(randx, randy, randState);
			this.addRect(randx, randy, randState);
		}
	}
	
	@Override
	public void next() {
		for (int x = 0; x < this.s.getSizeX(); x++) {
			for (int y = 0; y < this.s.getSizeY(); y++) {
				int[] countState = this.s.numNeighbors(x, y);
				int state = s.getState(x, y).getOldState();
				if (state == 0 && countState[1] == 3) {
					this.s.setState(x, y, 1);
					this.addRect(x, y, 1);
				}
				else if (state == 1 && countState[1] != 2 && countState[1] !=  3) {
					this.s.setState(x, y, 0);
					this.addRect(x, y, 0);
				}
			}
		}
		this.s.finishUpdate();
	}

	@Override
	public void restart() {
		for (int x = 0; x < this.s.getSizeX(); x++)
			for (int y = 0; y < this.s.getSizeY(); y++)
				this.addRect(x, y, this.s.restart(x, y));
	}
	
}