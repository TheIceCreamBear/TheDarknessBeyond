package com.joseph.thedarknessbeyond.gui;

import java.util.ArrayList;

public abstract class Screen extends Window {
	protected ArrayList<IGuiElement> children;
	
	public Screen(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public Screen(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
	}
}