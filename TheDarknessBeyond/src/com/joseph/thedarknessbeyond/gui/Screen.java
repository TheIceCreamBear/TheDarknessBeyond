package com.joseph.thedarknessbeyond.gui;

public abstract class Screen extends Window {
	public Screen(int x, int y, int width, int height) {
		super(x, y, width, height);
	}
	
	public Screen(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
	}
}