package com.joseph.thedarknessbeyond.gui;

import com.joseph.thedarknessbeyond.gui.buttons.GenericBuildButton;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

public abstract class GuiElement implements IGuiElement {
	protected boolean visible;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public GuiElement(int x, int y, int width, int height) {
		this(x, y, width, height, false);
	}
	
	public GuiElement(int x, int y, int width, int height, boolean scaled) {
		if (scaled || ScreenReference.scale == 1) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		} else if (this instanceof GenericSelectableButton && ScreenReference.scale == 2) {
			this.x = x * 2;
			this.y = y * 2;
			this.width = width;
			this.height = height;
		} else if (this instanceof GenericBuildButton && ScreenReference.scale == 2) {
			this.x = x * 2;
			this.y = y * 2;
			this.width = width;
			this.height = height;
		} else {
			this.x = x * 2;
			this.y = y * 2;
			this.width = width * 2;
			this.height = height * 2;
		}
	}
	
	protected void resetDimensions(int x, int y, int width, int height, boolean scaled) {
		if (scaled || ScreenReference.scale == 1) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		} else if (this instanceof GenericSelectableButton && ScreenReference.scale == 2) {
			this.x = x * 2;
			this.y = y * 2;
			this.width = width;
			this.height = height;
		} else {
			this.x = x * 2;
			this.y = y * 2;
			this.width = width * 2;
			this.height = height * 2;
		}
	}
	
	public void show() {
		this.visible = true;
	}
	
	public void hide() {
		this.visible = false;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public int getWidth0() {
		return this.width;
	}
	
	public int getHeight0() {
		return this.height;
	}
}