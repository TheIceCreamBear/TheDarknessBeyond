package com.joseph.gametemplate.interfaces;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * Drawable is a functional interface that specifies that an object can be drawn
 * It's only method, <code> draw(Graphics g, ImageObserver observer); </code> is
 * used within all objects that can be draw to draw them upon the graphics
 * instance
 * 
 * @author David Santamaria
 *
 */
public interface IDrawable {
	public void draw(Graphics g, ImageObserver observer);
}
