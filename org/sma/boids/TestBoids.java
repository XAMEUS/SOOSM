package org.sma.boids;

import java.awt.Color;

import gui.GUISimulator;

public class TestBoids {
	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(200, 200, new Color(242, 239, 234));
		gi.setSimulable(new BoidsSimulator(gi));
	}
}
