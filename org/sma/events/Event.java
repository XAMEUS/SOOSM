package org.sma.events;

public abstract class Event {

	private long date;
	
	public Event(long date) {
		this.date = date;
	}
	
	public long getDate() {
		return this.date;
	}
	
	public abstract void execute();
	
}
