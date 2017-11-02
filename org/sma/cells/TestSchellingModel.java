package org.sma.cells;

import java.awt.Color;
import gui.GUISimulator;

public class TestSchellingModel {

	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(400, 400, Color.white);
		Color colors[] = {Color.white, Color.red, Color.blue, Color.green};
		SchellingModel schmdl = new SchellingModel(10, 10, 400, 400, 20, 5, gi, colors);
		gi.setSimulable(schmdl);
//		for (Rectangle r: schmdl)
//			gi.addGraphicalElement(r);
	}
}
