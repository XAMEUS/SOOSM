package org.sma.simulator;

import org.sma.events.Event;
import org.sma.events.EventManager;

import gui.Simulable;

/**
 * Simulateur général, globalement un event manager encapsulé dans une classe qui implémente Simulable.
 */

public abstract class Simulator implements Simulable {
	
	private EventManager em;
	
	public void setEventManager(EventManager em) {
		this.em = em;
	}
	
	public void setFirstEvent(Event e) {
		this.em.addEvent(e);
		this.em.setFirstEvent(e);
	}

	public EventManager getEventManager() {
		return this.em;
	}
	
	@Override
	public void next() {
		this.em.next();
	}
	

	@Override
	public void restart() {
		this.em.restart();
	}

}
