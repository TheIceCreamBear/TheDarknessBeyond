package com.joseph.thedarknessbeyond.gui;

public abstract class GuiElement implements IGuiElement {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public GuiElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}