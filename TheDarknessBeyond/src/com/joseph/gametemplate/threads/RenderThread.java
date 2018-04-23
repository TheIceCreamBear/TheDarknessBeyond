package com.joseph.gametemplate.threads;

import com.joseph.gametemplate.engine.GameEngine;
import com.joseph.gametemplate.gameobject.RenderLockObject;

/**
 * This thread is responsible for initiating the rendering of the engine onto the frame.
 * Using multithreading techniques, this thread is designed to only run once the engine
 * notifies the {@link com.joseph.gametemplate.gameobject.RenderLockObject RenderLockObject}
 * allowing the update thread to update for the next frame while this thread renders the
 * current thread.
 * @author Joseph
 *
 */
public class RenderThread extends Thread {
	private RenderLockObject rlo;
	private GameEngine gEngine;
	
	/**
	 * Constructs a new RenderThread
	 * @param name - name of the thread
	 * @param rlo - {@link com.joseph.gametemplate.gameobject.RenderLockObject RenderLockObject} 
	 * 		used by {@link com.joseph.gametemplate.engine.GameEngine GameEngine} to communicate across threads.
	 * @param ge - 
	 */
	public RenderThread(String name, RenderLockObject rlo, GameEngine ge) {
		super(name);
		this.rlo = rlo;
		this.gEngine = ge;
	}
	
	@Override
	public void run() {
		synchronized (rlo) {
			while (GameEngine.isRunning()) {
				try {
					rlo.wait();
					if (!rlo.wasNotified()) {
						continue;
					} else {
						gEngine.render();
						rlo.setWasNotified(false);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
	}
}