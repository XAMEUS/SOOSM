package org.sma.cells;

public class State {

	private int oriState, oldState, currState;
	
	public State (int state) {
		this.oriState = state;
		this.oldState = state;
		this.currState = state;
	}
	
	public void setOriState(int oriState) {
		this.oriState = oriState;
		this.oldState = oriState;
		this.currState = oriState;
	}
	
	public void updateState(int newState)
	{
		this.oldState = currState;
		this.currState = newState;
	}
	
	public void finishUpdate()
	{
		this.oldState = this.currState;
	}
	
	public int backOrigin() {
		this.oldState = this.oriState;
		this.currState = this.oriState;
		return this.oriState;
	}
	
	public int getOldState () {
		return this.oldState;
	}

}
