package org.sma.cells;

import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import java.awt.Color;

/**
 * Classe représentant un automate cellulaire de type Jeu de la Vie de Conway.
 * Les règles sont les suivantes :
 * <ul>
 * <li>Chaque cellule de la grille peut prendre deux états : vivant ou mort.</li>
 * <li> Une cellule morte possèdant exactement trois voisines vivantes devient vivante.</li>
 * <li> Une cellule vivante meurt si elle ne possède pas deux ou trois voisines vivantes.</li> 
 * </ul>
 * @see CellularAutomaton
 * @author 3
 *
 */
public class GameOfLife extends CellularAutomaton implements Simulable {

	/**
	 * GUISimulator.
	 */
	protected GUISimulator gi;
	
	public GameOfLife (int minX, int minY, int maxX, int maxY, int numRectRow, GUISimulator gi, Color[] colors) {
		super(minX, minY, maxX, maxY, numRectRow, colors);
		this.gi = gi;
		initRect();
	}

	@Override
	public void addRect(int x, int y, int state) {
		Rectangle r = newRectangle(x, y, state);
		gi.addGraphicalElement(r);
	}
	
	@Override
	public void next() {
		gi.reset();
		for (int x = 0; x < s.getSizeX(); x++) {
			for (int y = 0; y < s.getSizeY(); y++) {
				int count = s.numNeighbors(x, y, 1);
				int state = s.getState(x, y);
				if (state == 0 && count == 3) {
					s.setState(x, y, 1);
					state = 1;
				} else if (state == 1 && count != 3 && count !=  4) {
					s.setState(x, y, 0);
					state = 0;
				}
				addRect(x, y, state);
			}
		}
		s.finishUpdate();
	}
	
}