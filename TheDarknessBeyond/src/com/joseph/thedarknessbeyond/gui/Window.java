package com.joseph.thedarknessbeyond.gui;

public abstract class Window extends GuiElement {
	public Window(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.visible = true;
	}
	
	public Window(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
		this.visible = true;
	}
	
	@Override
	public boolean isMouseInElement() {
		return false;
	}
}