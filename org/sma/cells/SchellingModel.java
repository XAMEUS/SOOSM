package org.sma.cells;

import java.awt.Color;
import java.util.Queue;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;

import gui.Rectangle;
import gui.Simulable;
import gui.GUISimulator;

/**
 * Classe représentant un automate cellulaire de type Modèle de Schelling.
 * Les règles sont les suivantes :
 * <ul>
 * <li> Chaque cellule représente une habitation. </li>
 * <li> Une habitation peut être vacante ou habitée par une famille de couleur c. </li>
 * <li> Si une famille de couleur c a plus de k voisins de couleur différentes
 * (ie non égale à la sienne), elle déménage dans une habitation vacante.
 * Son habitation précédente devient alors vacante. </li>
 * </ul>
 * @author 3
 *
 */
public class SchellingModel extends CellularAutomaton implements Simulable, Iterable <Cell> {

	/**
	 * Seuil de déménagement : 
	 * nombre de voisins de couleurs différentes maximum avant déménagement.
	 */
	private int k;
	
	/**
	 * Liste des habitations prises.
	 */
	private List<Cell> taken;
	
	/**
	 * File de priorité des habitations vacantes.
	 */
	private Queue<Cell> free;
	
	/**
	 * GUISimulator.
	 */
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
		gi.addGraphicalElement(c.getRect());
	}
	
	/**
	 * Déménage une famille dans une nouvelle habitation vacante.
	 * @param takenLand Habitation courante de la famille.
	 * @param familyColor Couleur de la famille.
	 */
	private void move(Cell takenLand, int familyColor) {
		Cell freeLand = free.poll();
		if (freeLand != null) {
			int freeX = freeLand.getRectX();
			int freeY = freeLand.getRectY();
			int takenX = takenLand.getRectX();
			int takenY = takenLand.getRectY();
			freeLand.translate(takenX - freeX, takenY - freeY);
			takenLand.translate(freeX - takenX, freeY - takenY);
			
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
