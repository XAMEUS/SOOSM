package org.sma.cells;

import java.awt.Color;
import gui.GUISimulator;

/**
 * Classe représentant une Jeu de la Vie à plus de deux état, dit Jeu de l'Immigration.
 * Les règles sont les suivantes :
 * <ul>
 * <li> Chaque cellule de la grille peut prendre n états. </li>
 * <li> Une cellule à l'état k peut passer à l'état k+1 (mod n) si elle a au moins 3 voisines dans cet état. </li>
 * </ul>
 * @see GameOfLife
 * @author 3
 *
 */
public class ImmigrationGame extends GameOfLife {

	public ImmigrationGame(int minX, int minY, int maxX, int maxY, int numRectRow, GUISimulator gi, Color[] colors) {
		super(minX, minY, maxX, maxY, numRectRow, gi, colors);
	}

	@Override
	public void next() {
		for (int x = 0; x < s.getSizeX(); x++) {
			for (int y = 0; y < s.getSizeY(); y++) {
				int nextState = (s.getState(x, y)+1) % s.numState();
				int count = s.numNeighbors(x, y, nextState);
				if (count >= 3) {
					s.setState(x, y, nextState);
					addRect(x, y, nextState);
				}
			}
		}
		this.s.finishUpdate();
	}
	
}
