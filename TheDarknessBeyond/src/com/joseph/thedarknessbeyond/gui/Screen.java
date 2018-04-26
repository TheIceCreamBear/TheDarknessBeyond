package com.joseph.thedarknessbeyond.gui;

import java.util.ArrayList;

public abstract class Screen extends Window {
	protected boolean visible;
	protected ArrayList<IGuiElement> children;
	
	public Screen(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public void show() {
		visible = true;
	}
	
	public void hide() {
		visible = false;
	}
}