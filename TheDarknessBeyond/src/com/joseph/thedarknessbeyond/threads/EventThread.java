package com.joseph.thedarknessbeyond.threads;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.event.EventBus;

public class EventThread extends Thread {
	public EventThread() {
		super("EventHandlerThread");
	}

	@Override
	public void run() {
		while (GameEngine.isRunning()) {
			if (EventBus.EVENT_BUS.hasUnResolved()) {
				EventBus.EVENT_BUS.resolve();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}