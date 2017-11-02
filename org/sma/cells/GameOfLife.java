package org.sma.cells;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;

import java.awt.Color;

public class GameOfLife extends CellularAutomaton implements Simulable {

	private GUISimulator gi;
	
	public GameOfLife (int minX, int minY, int maxX, int maxY, int numRectRow, GUISimulator gi, Color[] colors) {
		super(minX, minY, maxX, maxY, numRectRow, colors);
		
		this.gi = gi;
		for (int x = 0; x < s.getSizeX(); x++) {
			for (int y = 0; y < s.getSizeY(); y++) {
				int randState = s.setRandomState(x, y);
				s.setOrigin(x, y, randState);
				addRect(x, y, randState);
			}
		}
	}
	
	public void addRect(int x, int y, int state) {
		int xAbs = x * getRectSize() + getMinX();
		int yAbs = y * getRectSize() + getMinY();
		Rectangle r = new Rectangle(xAbs, yAbs, Color.black, s.getColor(state), getRectSize());
		gi.addGraphicalElement(r);
	}
		
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