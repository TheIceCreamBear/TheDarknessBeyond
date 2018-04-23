package com.joseph.thedarknessbeyond.gameobject;

/**
 * Object used to communicate across threads for rendering purposes.
 * 
 * @author Joseph
 *
 */
public class RenderLockObject {
	private boolean wasNotified;

	public RenderLockObject() {
		this.wasNotified = false;
	}

	public void setWasNotified(boolean wasNotified) {
		this.wasNotified = wasNotified;
	}

	public boolean wasNotified() {
		return wasNotified;
	}
}