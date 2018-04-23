package com.joseph.gametemplate.gameobject;

import com.joseph.gametemplate.engine.GameEngine;
import com.joseph.gametemplate.interfaces.IDrawable;
import com.joseph.gametemplate.interfaces.IUpdateable;

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
