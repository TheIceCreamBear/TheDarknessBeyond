package com.joseph.thedarknessbeyond.interfaces;

/**
 * IUpdatable is a functional interface that specifies that an object is able to
 * be updated. This means that it has behavior, and that its state changes over
 * time. It's only method, <code> update(double deltaTime) </code>, takes in the
 * parameter deltaTime, which specifies the amount of time that has happened
 * before the last update, or the previous frame
 * 
 * @author David Santamaria
 *
 */
public interface IUpdateable {
	public void update(double deltaTime);
}
