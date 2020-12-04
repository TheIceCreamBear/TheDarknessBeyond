package com.joseph.thedarknessbeyond.gui;

/**
 * A sub class of GuiElement and Window that specifies that it is a screen
 * @author Joseph
 *
 */
public abstract class Screen extends Window {
	public Screen(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public Screen(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
	}
}