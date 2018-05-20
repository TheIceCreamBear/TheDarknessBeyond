package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;
import com.joseph.thedarknessbeyond.resource.StorageManager;

public class StorageWindow extends Window {
	private FontRenderContext frc;
	private Font font;
	
	private static StorageManager manager;
	
	public StorageWindow() {
		this(900, 0, 200, ScreenRefrence.HEIGHT - 1);
	}
	
	public StorageWindow(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.frc = GameEngine.getInstance().getFrc();
		if (ScreenRefrence.scale == 2) {
			this.font = Reference.Fonts.SCALED_UP_FONT;
		} else {
			this.font = Reference.Fonts.DEFAULT_FONT;
		}
	}
	
	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		// Background
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + 1, y + 1, width - 2, height - 2);
		
		// Header
		g.setColor(Color.WHITE);
		String s = "Stores:";
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(s, frc);
		int yOff = (int) r.getHeight();
		int xOff = 5;
		g.drawString(s, x + xOff, y + yOff);
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean removeGui() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void setGuiToRemove() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isMouseInElement() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	public static StorageManager getManager() {
		return manager;
	}
}