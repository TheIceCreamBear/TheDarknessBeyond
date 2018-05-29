package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gui.Screen;

public class VillageScreen extends Screen {
	private Village village;
	private static VillageScreen screen;
	
	public VillageScreen(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.village = new Village();
		screen = this;
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		this.village.update();
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	public static void setVillage(Village v) {
		screen.village = v;
	}
}