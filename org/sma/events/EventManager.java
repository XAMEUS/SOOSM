package org.sma.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventManager {

	private long currentDate;
	private Map<Long, List<Event>> listEvent;
	private Event firstEvent; // utile pour le restart()

	public EventManager() {
		this.currentDate = 0;
		this.listEvent = new HashMap<>();
	}

	public void addEvent(Event e) {
		if (!this.listEvent.containsKey(e.getDate()))
			this.listEvent.put(e.getDate(), new ArrayList<>());
		this.listEvent.get(e.getDate()).add(e);
	}
	
	public void setFirstEvent(Event e) {
		this.firstEvent = e;
	}

	public void next() {
		this.currentDate++;
		System.out.println("Next... Current date : " + this.currentDate);
		if (this.listEvent.containsKey(this.currentDate))
			for (Event e : this.listEvent.get(this.currentDate))
				e.execute();
	}

	public boolean isFinished() {
		for (long l : this.listEvent.keySet())
			if (l > this.currentDate)
				return false;
		return true;
	}

	public void restart() {
		this.currentDate = 0;
		this.listEvent.clear();
		this.addEvent(this.firstEvent);
	}

}
