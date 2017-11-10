package org.sma.balls;
import java.awt.Color;

import gui.GUISimulator;
import gui.Rectangle;

public class TestBallsGUI {
	
	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(800, 800, Color.darkGray);
		BallsSimulator bs = new BallsSimulator(500, 0, 0, 800, 800);
		gi.addGraphicalElement(new Rectangle(400, 400, Color.CYAN, Color.gray, 800));
		gi.setSimulable(bs);
		for (Ball b: bs)
			gi.addGraphicalElement(b);
	}

}
