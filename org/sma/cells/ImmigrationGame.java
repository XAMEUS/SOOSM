package org.sma.cells;

import java.awt.Color;

import gui.GUISimulator;

public class ImmigrationGame extends GameOfLife {

	public ImmigrationGame(int minX, int minY, int maxX, int maxY, int numRectRow, GUISimulator gi, Color[] colors) {
		super(minX, minY, maxX, maxY, numRectRow, gi, colors);
	}

	@Override
	public void next() {
		for (int x = 0; x < s.getSizeX(); x++) {
			for (int y = 0; y < s.getSizeY(); y++) {
				int nextState = (s.getState(x, y)+1) % s.numState();
				int count = s.numSameNeighbors(x, y, nextState);
				if (count >= 3) {
					s.setState(x, y, nextState);
					addRect(x, y, nextState);
				}
			}
		}
		this.s.finishUpdate();
	}
	
}
