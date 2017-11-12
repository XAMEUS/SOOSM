package org.sma.cells;

import java.awt.Color;
import gui.GUISimulator;

/**
 * Test du modèle de Schelling
 * @see SchellingModel
 * @author 3
 *
 */
public class TestSchellingModel {

	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(400, 400, Color.white);
		Color colors[] = {Color.white, Color.red, Color.blue, Color.green, Color.yellow, Color.pink};
		SchellingModel schmdl = new SchellingModel(30, 30, 800, 600, 50, 5, gi, colors);
		gi.setSimulable(schmdl);
	}
}
