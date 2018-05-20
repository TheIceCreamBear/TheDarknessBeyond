package com.joseph.thedarknessbeyond.gui;

public abstract class Window extends GuiElement {
	public Window(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public Window(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
	}
	
	@Override
	public boolean isMouseInElement() {
		return false;
	}
}