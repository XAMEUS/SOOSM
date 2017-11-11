package org.sma.cells;

import java.awt.Color;
import gui.GUISimulator;


/**
 * Test du Jeu de l'Immigration.
 * @see ImmagrationGame
 * @author 3
 *
 */
public class TestImmigrationGame {

	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(400, 400, Color.white);
		Color colors[] = {Color.white, Color.yellow, Color.orange, Color.red};
		ImmigrationGame img = new ImmigrationGame(10, 10, 400, 400, 20, gi, colors);
		gi.setSimulable(img);
	}
}