package org.sma.cells;

import java.awt.Color;

import gui.GUISimulator;

public class TestGameOfLife {

	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(400, 400, Color.white);
		Color colors[] = {Color.white, Color.blue};
		GameOfLife gl = new GameOfLife(10, 10, 400, 400, 20, 100, gi, colors);
		gi.setSimulable(gl);
	}
}