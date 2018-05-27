package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Set;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

public class StorageWindow extends Window {
	private FontRenderContext frc;
	private Font font;
	
	private static StorageManager manager = new StorageManager();
	
	public StorageWindow() {
		this(1700, 40, 200, 800);
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
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
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
		Rectangle2D r0 = font.getStringBounds("Stores:", frc);
		int yOff = (int) r0.getHeight() * 2;
		int xOff = 5;
		
		HashMap<EnumResource, Resource> local = manager.getStores();
		Set<EnumResource> set = local.keySet();
		
		EnumResource[] er = new EnumResource[set.size()];
		set.toArray(er);
		for (int i = 0; i < er.length; i++) {
			//check for resource
			//if debug is true, display all resources even though it is 0
			String s = "";
			if (local.get(er[i]).getAmount() == 0 && !Reference.DEBUG_MODE) {
				continue;
			}
			s += er[i] + ": " + local.get(er[i]).getAmount();
			g.drawString(s, x + xOff, y + yOff);
			Rectangle2D r = font.getStringBounds(s, frc);
			yOff += r.getHeight() + 5;
		}
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		
	}
	
	@Override
	public boolean isMouseInElement() {
		return false;
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	public static StorageManager getManager() {
		return manager;
	}
}