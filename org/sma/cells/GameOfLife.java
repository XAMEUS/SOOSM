package org.sma.cells;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.Color;
import java.lang.IllegalArgumentException;

public class GameOfLife implements Simulable {

	private GUISimulator gi;
	protected States s;
	private int minX, minY;
	private int rectSize;
	
	public GameOfLife (int minX, int minY, int maxX, int maxY, int numRectRow, GUISimulator gi, Color[] colors) {
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
				int randState = s.setRandomState(x, y);
				s.setOrigin(x, y, randState);
				Rectangle r = new Rectangle(x * this.rectSize + minX, y * this.rectSize + minY, Color.black, s.getColor(randState), rectSize);
				gi.addGraphicalElement(r);
			}
		}
//		this.initialize(n);
	}
	
	public void addRect(int x, int y, int state) {
		int xAbs = x * rectSize + minX;
		int yAbs = y * rectSize + minY;
		Rectangle r = new Rectangle(xAbs, yAbs, Color.black, s.getColor(state), rectSize);
		gi.addGraphicalElement(r);
	}
	
//	private void initialize(int n) {
//		int randx, randy;
//		for (int i = 0; i < n; i++) {
//			randx = (int) (s.getSizeX() * Math.random());
//			randy = (int) (s.getSizeY() * Math.random());
//			int randState = s.setRandomState(randx, randy);
//			s.setOrigin(randx, randy, randState);
//			addRect(randx, randy, randState);
//		}
//	}
	
	@Override
	public void next() {
		for (int x = 0; x < s.getSizeX(); x++) {
			for (int y = 0; y < s.getSizeY(); y++) {
				int count = s.numNeighbors(x, y, 1);
				int state = s.getState(x, y).getOldState();
				if (state == 0 && count == 3) {
					s.setState(x, y, 1);
					addRect(x, y, 1);
				}
				else if (state == 1 && count != 3 && count !=  4) {
					s.setState(x, y, 0);
					addRect(x, y, 0);
				}
			}
		}
		s.finishUpdate();
	}

	@Override
	public void restart() {
		for (int x = 0; x < s.getSizeX(); x++)
			for (int y = 0; y < s.getSizeY(); y++)
				addRect(x, y, s.restart(x, y));
	}
	
}