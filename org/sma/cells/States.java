package org.sma.cells;

import java.awt.Color;
import java.util.Arrays;
import static java.lang.Math.*;
import java.lang.IllegalArgumentException;

/**
 * Classe permettant de conserver l'état de la grille de cellules d'un automate cellulaire.
 * @see CellularAutomaton
 * @author 3
 *
 */
public class States {

	/**
	 * Grille des états des cellules.
	 * @see State
	 */
	private State[][] states;
	
	/**
	 * Taille de la grille.
	 */
	private int sizeX, sizeY;
	
	/**
	 * Tableau de Color.
	 * <ul>
	 * <li> L'état d'une cellule est l'indice dans ce tableau. </li>
	 * <li> Le nombre d'état correspond à la taille du tableau. </li>
	 * <li> La Color d'indice 0 correspond à l'état vide ou "case libre". </li>
	 */
	private Color[] colors;
	
	/**
	 * Constructeur.
	 * @param sizeX Largeur de la grille.
	 * @param sizeY Hauteur de la grille.
	 * @param colors Tableau de Color.
	 * @throws IllegalArgumentException si le tableau de Color a moins de 2 éléments.
	 */
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
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}
	
	/**
	 * Retourne l'état de la cellule.
	 * @param x Abcisse de la cellule.
	 * @param y Ordonnée de la cellule.
	 * @return L'état de la cellule.
	 */
	public int getState(int x, int y) {
		return states[x][y].getOldState();
	}
	
	/**
	 * Retourne le nombre d'états.
	 * @return Le nombre d'états.
	 */
	public int numState() {
		return colors.length;
	}
	
	/**
	 * Retourne le nombre de voisins de la cellule aux coordonnées données
	 * dans le même état que l'état en paramètre.
	 * @param x Abscisse de la cellule.
	 * @param y Coordonnée de la cellule.
	 * @param state Etat à tester.
	 * @return Le nombre de voisins selon les conditions.
	 */
	public int numNeighbors(int x, int y, int state) {
		int count = 0;
		for (int ix = max(x-1, 0); ix <= min(x+1, sizeX-1); ix++) {
			for (int iy = max(y-1, 0); iy <= min(y+1, sizeY-1); iy++) {
					if (getState(ix, iy) == state)
						count++;
			}
		}
		return count;
	}
	
	/**
	 * Retourne le nombre de voisins de la cellule aux coordonnées données
	 * qui n'est ni dans le même état que l'état en paramètre ni nul.
	 * @param x Abscisse de la cellule.
	 * @param y Coordonnée de la cellule.
	 * @param state Etat à tester.
	 * @return Le nombre de voisins selon les conditions.
	 */
	public int numDiffNeighbors(int x, int y, int state) {
		int count = 0, currState;
		for (int ix = max(x-1, 0); ix <= min(x+1, sizeX-1); ix++) {
			for (int iy = max(y-1, 0); iy <= min(y+1, sizeY-1); iy++) {
					currState = getState(ix, iy);
					if (currState != state && currState != 0)
						count++;
			}
		}
		return count;
	}
	
	/**
	 * Met à jour l'état de la cellule.
	 * @param x Abscisse de la cellule.
	 * @param y Ordonnée de la cellule.
	 * @param newState Nouvel état.
	 */
	public void setState(int x, int y, int newState) {
		states[x][y].updateState(newState);
	}
	
	/**
	 * Initialise l'état d'origine de la cellule.
	 * @param x Abscisse de la cellule.
	 * @param y Ordonnée de la cellule.
	 * @param oriState Etat d'origine.
	 */
	public void setOrigin(int x, int y, int oriState) {
		states[x][y].setOriState(oriState);
	}
	
	/**
	 * Réinitialise un état de la grille à l'état initial.
	 * @param x Abscisse de la cellule.
	 * @param y Coordonnée de la cellule.
	 * @return L'état initial de la cellule aux coordonnées données.
	 */
	public int restart(int x, int y) {
		return states[x][y].backOrigin();
	}
	
	/**
	 * Initialise une cellule de la grille à un état aléatoire.
	 * @param x Abscisse de la cellule.
	 * @param y Coordonnée de la cellule.
	 * @return L'état aléatoire obtenu.
	 */
	public int setRandomState(int x, int y) {
		int randomState = (int)(Math.random() * colors.length);
		this.setState(x, y, randomState);
		return randomState;
	}
	
	/**
	 * Associe une Color à un état.
	 * @param state Etat à associer.
	 * @return Color associée.
	 */
	public Color getColor(int state) {
		if (state < 0 || state >= colors.length)
			throw new IllegalArgumentException("Not in range of colors");
		return colors[state];
	}
	
	/**
	 * Finit la mise à jour de la grille.
	 */
	public void finishUpdate() {
		for (int x = 0; x < sizeX; x++)
			for (int y = 0; y < sizeY; y++)
				states[x][y].finishUpdate();
	}
	

}
