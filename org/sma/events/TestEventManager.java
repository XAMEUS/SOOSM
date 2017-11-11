package org.sma.events;

public class TestEventManager {
	public static void main(String[] args) throws InterruptedException {
		EventManager em = new EventManager();
		
		for (int i = 2; i <= 10; i+= 2)
			em.addEvent(new MessageEvent(i, " [PING]"));
		
		for (int i = 3; i <= 10; i+= 3)
			em.addEvent(new MessageEvent(i, " [PONG]"));
		
		while (!em.isFinished()) {
			em.next();
			Thread.sleep(1000);
		}
	}
}
