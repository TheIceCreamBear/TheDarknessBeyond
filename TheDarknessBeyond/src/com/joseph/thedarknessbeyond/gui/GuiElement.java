package com.joseph.thedarknessbeyond.gui;

import com.joseph.thedarknessbeyond.reference.ScreenRefrence;

public abstract class GuiElement implements IGuiElement {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public GuiElement(int x, int y, int width, int height) {
		if (ScreenRefrence.scale == 2) {
			this.x = x * 2;
			this.y = y * 2;
			this.width = width * 2;
			this.height = height * 2;
		} else {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
	}
}