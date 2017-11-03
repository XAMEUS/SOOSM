package org.sma.cells;

import java.awt.Color;
import java.util.Queue;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import gui.Rectangle;
import gui.Simulable;
import gui.GUISimulator;


public class SchellingModel extends CellularAutomaton implements Simulable, Iterable <Cell> {

	private int k;
	private List<Cell> taken;
	private Queue<Cell> free;
	private GUISimulator gi;
	
	public SchellingModel (int minX, int minY, int maxX, int maxY, int numRectRow, int k, GUISimulator gi, Color[] colors) {
		super(minX, minY, maxX, maxY, numRectRow, colors);
		this.k = k;
		this.taken = new LinkedList<>();
		this.free = new LinkedList<>();
		this.gi = gi;
		initRect();
	}
	
	@Override
	public void addRect(int x, int y, int familyColor) {
		Rectangle r = newRectangle(x, y, familyColor);
		Cell c = new Cell(x, y, r);
		if (familyColor == 0)
			free.add(c);
		else taken.add(c);
		gi.addGraphicalElement(c.r);
	}
	
	private void move(Cell takenLand, int familyColor) {
		Cell freeLand = free.poll();
		if (freeLand != null) {
			int freeX = freeLand.r.getX();
			int freeY = freeLand.r.getY();
			int takenX = takenLand.r.getX();
			int takenY = takenLand.r.getY();
			freeLand.r.translate(takenX - freeX, takenY - freeY);
			takenLand.r.translate(freeX - takenX, freeY - takenY);
			
			freeX = freeLand.getX();
			freeY = freeLand.getY();
			takenX = takenLand.getX();
			takenY = takenLand.getY();
			freeLand.setXY(takenX, takenY);
			takenLand.setXY(freeX, freeY);
			s.setState(freeX, freeY, familyColor);
			s.setState(takenX, takenY, 0);
			
			free.add(freeLand);
		}
	}

	@Override
	public void next() {
		for (Cell c : taken) {
			int x = c.getX(), y = c.getY();
			int familyColor = s.getState(x, y);
			int count = s.numDiffNeighbors(x, y, familyColor);
			if (count >= k) {
				move(c, familyColor);
			}
		}
		s.finishUpdate();
	}

	@Override
	public Iterator<Cell> iterator() {
		List<Cell> allCells = new LinkedList<>();
		allCells.addAll(taken);
		allCells.addAll(free);
		return allCells.iterator();
	}
	
	@Override
	public void restart() {
		taken = new LinkedList<>();
		free = new LinkedList<>();
		super.restart();
	}
}
