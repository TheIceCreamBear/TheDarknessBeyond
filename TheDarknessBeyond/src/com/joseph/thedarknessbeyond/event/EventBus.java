package com.joseph.thedarknessbeyond.event;

import java.util.ArrayList;

import com.joseph.thedarknessbeyond.gui.windows.EventWindow;

/**
 * Class that holds events that havent been executed yet and need to be
 * 
 * @author Joseph
 *
 */
public class EventBus {
	/**
	 * public instance of the Event bus
	 */
	public static final EventBus EVENT_BUS = new EventBus();
	
	private ArrayList<Event> events;
	
	public EventBus() {
		this.events = new ArrayList<Event>();
	}
	
	/**
	 * adds a new event to the queue
	 * 
	 * @param e
	 *            - the event
	 */
	public void post(Event e) {
		this.events.add(e);
	}
	
	/**
	 * resolves the topmost event
	 */
	public void resolve() {
		EventWindow.getInstance().addEvent(this.events.remove(0));
	}
	
	public boolean hasUnResolved() {
		return this.events.size() > 0;
	}
}