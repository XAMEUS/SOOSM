package org.sma.balls;
import java.awt.Color;

import org.sma.colors.Colors;

import gui.GUISimulator;
import gui.Rectangle;

public class TestBallsGUI {
	
	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(800, 800, Colors.color3);
		BallsSimulator bs = new BallsSimulator(100, 0, 0, 800, 800);
		gi.addGraphicalElement(new Rectangle(400, 400, Colors.color6, null, 800));
		gi.setSimulable(bs);
		for (Ball b: bs)
			gi.addGraphicalElement(b);
	}

}
