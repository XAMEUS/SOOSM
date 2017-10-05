

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import gui.GUISimulator;
import gui.Oval;
import gui.Rectangle;

public class TestGUI {
	public static void main(String[] args) {
		GUISimulator gi = new GUISimulator(200, 200, Color.CYAN);
//		gi.setSimulable(new BallsSimulator());
		Oval o = new Oval(10, 10, Color.RED, Color.YELLOW, 20);
		gi.addGraphicalElement(o);
	}
}