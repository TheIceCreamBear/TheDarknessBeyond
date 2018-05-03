package com.joseph.thedarknessbeyond.event;

import java.util.ArrayList;

import com.joseph.thedarknessbeyond.gui.windows.EventWindow;

public class EventBus {
	public static final EventBus EVENT_BUS = new EventBus();
	
	private ArrayList<Event> events;
	public EventBus() {
		this.events = new ArrayList<Event>();
	}
	
	public void post(Event e) {
		this.events.add(e);
	}
	
	public void resolve() {
		EventWindow.getInstance().addEvent(this.events.remove(0));
	}
	
	public boolean hasUnResolved() {
		return this.events.size() > 0;
	}
}