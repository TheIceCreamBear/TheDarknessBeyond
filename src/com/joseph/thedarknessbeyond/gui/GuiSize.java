package com.joseph.thedarknessbeyond.gui;

/**
 * Immutable class to represent the size of a Gui element.
 * Most useful for when a gui element has a dynamic size determined by a string
 * @author Joseph
 *
 */
public class GuiSize {
	public final int width;
	public final int height;
	
	public GuiSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	@Override
	public String toString() {
		return width + "x" + height;
	}
}
