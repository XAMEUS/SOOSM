package org.sma.cells;

import java.awt.Color;
import java.util.Queue;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import gui.Rectangle;
import gui.Simulable;
import gui.GUISimulator;


public class SchellingModel extends CellularAutomaton implements Simulable, Iterable<Rectangle> {

	private int k;
	private List<Rectangle> taken;
	private Queue<Rectangle> free;
	private GUISimulator gi;
	
	public SchellingModel (int minX, int minY, int maxX, int maxY, int numRectRow, int k, GUISimulator gi, Color[] colors) {
		super(minX, minY, maxX, maxY, numRectRow, colors);
		this.k = k;
		this.taken = new LinkedList<>();
		this.free = new LinkedList<>();
		this.gi = gi;
		initRect();
	}
	
	public void addRect(int x, int y, int familyColor) {
		Rectangle r = newRectangle(x, y, familyColor);
		if (familyColor == 0)
			free.add(r);
		else taken.add(r);
		gi.addGraphicalElement(r);
	}
	
	private void move(Rectangle oldHouse, int relOldX, int relOldY, int familyColor) {
		// quelques problemes de demenagements...
		Rectangle newHouse = free.poll();
		if (newHouse != null) {
			int newX = newHouse.getX();
			int newY = newHouse.getY();
			int oldX = oldHouse.getX();
			int oldY = oldHouse.getY();
			oldHouse.translate(newX - oldX, newY - oldY);
			newHouse.translate(oldX - newX, oldY - newY);
			s.setState(relOldX, relOldY, 0);
			s.setState(relativeX(newX), relativeY(newY), familyColor);
			free.add(newHouse);
		}
	}

	@Override
	public void next() {
		for (Rectangle r : taken) {
			int x = relativeX(r.getX());
			int y = relativeY(r.getY());
			int familyColor = s.getState(x, y);
			int count = s.numNeighbors(x, y, familyColor);
			if ((9 - count) >= k)
				move(r, x, y, familyColor);
		}
	}

	@Override
	public Iterator<Rectangle> iterator() {
		List<Rectangle> allRectangles = new LinkedList<>();
		allRectangles.addAll(taken);
		allRectangles.addAll(free);
		return allRectangles.iterator();
	}
	
	@Override
	public void restart() {
		taken = new LinkedList<>();
		free = new LinkedList<>();
		super.restart();
	}
}
