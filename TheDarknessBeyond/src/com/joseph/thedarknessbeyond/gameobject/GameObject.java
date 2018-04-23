package com.joseph.thedarknessbeyond.gameobject;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.interfaces.IDrawable;
import com.joseph.thedarknessbeyond.interfaces.IUpdateable;

/**
 * Abstract object used mainly for storage of objects in {@link GameEngine} and to 
 * combine {@link IUpdateable} and {@link IDrawable} objects together.
 * 
 * @author Joseph
 * @see IUpdateable
 * @see IDrawable
 *
 */
public abstract class GameObject implements IDrawable, IUpdateable {
	
	public GameObject() {
		
	}
}
