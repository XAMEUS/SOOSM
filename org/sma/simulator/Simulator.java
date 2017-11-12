package org.sma.simulator;

import org.sma.events.Event;
import org.sma.events.EventManager;

import gui.Simulable;

/**
 * Simulateur général, globalement un event manager encapsublé dans une classe qui implémente Simulable.
 */

public abstract class Simulator implements Simulable {
	
	private EventManager em;
	
	public void setEventManager(EventManager em) {
		this.em = em;
	}
	
	public void setEvent(Event e) {
		this.em.addEvent(e);
	}

	public EventManager getEventManager() {
		return this.em;
	}
	
	@Override
	public void next() {
		this.em.next();
	}

}
