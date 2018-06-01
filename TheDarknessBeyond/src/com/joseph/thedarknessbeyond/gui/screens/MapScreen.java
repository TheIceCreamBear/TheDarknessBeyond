package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gameobject.map.Map;
import com.joseph.thedarknessbeyond.gameobject.map.Tile;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

public class MapScreen extends Screen {
	private Map map;
	private String[] nextArr;
	private FontRenderContext frc;
	private Font font;
	private Rectangle2D r;

	public MapScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		this.map = new Map();
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getMapFont();
		r = font.getStringBounds(";", frc);
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(font);
//		long start = System.nanoTime();
		/* 
//		 * Draw by Strings
		Rectangle2D r = font.getStringBounds("Event Log:", frc);
		int yOff = (int) r.getHeight() * 2 + 15 * ScreenReference.scale;
		int xOff = 200;
		
		for (int i = 0; i < nextArr.length; i++) {
			String s = nextArr[i];
			g.drawString(s, x + xOff, y + yOff);
			
			Rectangle2D r0 = font.getStringBounds(s, frc);
			yOff += r0.getHeight() - 5 * ScreenReference.scale;
		}
		*/
//		/*
//		 * Draw by chars
		Tile[][] tiles = map.getMap();
		char[][] chars = map.getCharArray();
		int yOff = 100 * ScreenReference.scale;
		for (int i = 0; i < chars.length; i++) {
			int xOff = 100 * ScreenReference.scale;
			for (int j = 0; j < chars[i].length; j++) {
				if (tiles[i][j].isDiscovered() || Reference.DEBUG_MODE) {
//					long start1 = System.nanoTime();
					g.drawString(String.valueOf(chars[i][j]), x + xOff, y + yOff);
//					long stop1 = System.nanoTime();
//					System.err.println(stop1 - start1);
				}
				xOff += r.getHeight();
			}
			yOff += r.getHeight();
		}
//		 */
//		long stop = System.nanoTime();
//		System.out.println(stop - start);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		this.nextArr = map.getStrings();
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}
}