package org.sma.cells;

import java.awt.Color;
import java.lang.IllegalArgumentException;

import gui.Rectangle;

/**
 * Classe abstraite représentant un automate cellulaire.
 * @author 3
 *
 */
public abstract class CellularAutomaton {
	
	/**
	 * Etat de la grille.
	 */
	protected States s;
	
	/**
	 * Coordonnée minimale.
	 */
	private int minX, minY;
	
	/**
	 * Largeur et hauteur des Rectangle.
	 */
	private int rectSize;
	
	/**
	 * Constructeur d'un automate cellulaire.
	 * @param minX Extremum ouest de la grille en pixels.
	 * @param minY Extremum nord de la grille en pixels.
	 * @param maxX Extremum est de la grille en pixels.
	 * @param maxY Extremum sud de la grille en pixels.
	 * @param numRectRow Nombre de rectangle maximal dans une ligne ou colonne de la grille.
	 * @param colors Tableau de colors.
	 * @throws IllegalArgumentException si les extremums de la grille sont mal définis.
	 */
	public CellularAutomaton (int minX, int minY, int maxX, int maxY, int numRectRow, Color[] colors) {
		if (minX >= maxX || minY >= maxY || minX < 0 || minY < 0 || maxX < 0 || maxY < 0 || numRectRow < 0)
			throw new IllegalArgumentException();
		this.minX = minX;
		this.minY = minY;
		this.rectSize = Math.min((int)(maxX - minX)/numRectRow, (int)(maxY - minY)/numRectRow);
		
		int sizeX = (int)(maxX - minX)/this.rectSize;
		int sizeY = (int)(maxY - minY)/this.rectSize;
		this.s = new States(sizeX, sizeY, colors);
	}
	
	/**
	 * Initialise les Rectangle dans l'automate.
	 */
	public void initRect() {
		for (int x = 0; x < s.getSizeX(); x++) {
			for (int y = 0; y < s.getSizeY(); y++) {
				int randState = s.setRandomState(x, y);
				s.setOrigin(x, y, randState);
				addRect(x, y, randState);
			}
		}
	}

	/**
	 * Construit un Rectangle en fonction des paramètres donnés.
	 * @param x Abscisse de la cellule dans la grille.
	 * @param y Ordonnée de la cellule dans la grille.
	 * @param state Etat de la cellule.
	 * @return Rectangle.
	 */
	public Rectangle newRectangle (int x, int y, int state) {
		int absX = x * rectSize + minX;
		int absY = y * rectSize + minY;
		return new Rectangle(absX, absY, Color.black, s.getColor(state), rectSize);
	}
	
	/**
	 * Ajoute un Rectangle à la simulation en fonction des paramètres
	 * de sa cellule correspondante dans la grille.
	 * @param x Abscisse de la cellule.
	 * @param y Ordonnée de la cellule.
	 * @param state Etat de la cellule.
	 */
	public abstract void addRect(int x, int y, int state);
	
	/**
	 * Réinitialise l'automate cellulaire.
	 */
	public void restart() {
		for (int x = 0; x < s.getSizeX(); x++)
			for (int y = 0; y < s.getSizeY(); y++)
				addRect(x, y, s.restart(x, y));
	}
	
}
