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
		Color colors[] = {Color.white, Color.red, Color.blue, Color.green};
		SchellingModel schmdl = new SchellingModel(50, 50, 500, 800, 20, 3, gi, colors);
		gi.setSimulable(schmdl);
	}
}
