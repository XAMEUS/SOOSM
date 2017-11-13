package org.sma.boids;

import java.awt.Color;

import org.sma.colors.Colors;

import gui.GUISimulator;

public class TestBoids {
	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(200, 200, new Color(242, 239, 234));
		BoidsSimulator bs = new BoidsSimulator(gi);
		int minX = 100;
		int maxX = 800;
		int minY = 100;
		int maxY = 800;
		Boids victims = new Boids(20, minX, minY, maxX, maxY, 20, Colors.color1);
		bs.addBoids(victims);
		Boids predator = new Predators(10, minX, minY, maxX, maxY, 25, Colors.color6, victims);
		bs.addBoids(predator);
		bs.addBoids(new Predators(5, minX, minY, maxX, maxY, 30, Colors.color5, predator));
		gi.setSimulable(bs);
	}
}
