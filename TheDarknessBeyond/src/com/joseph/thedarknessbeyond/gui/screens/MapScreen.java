package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gameobject.map.Enemy;
import com.joseph.thedarknessbeyond.gameobject.map.Map;
import com.joseph.thedarknessbeyond.gameobject.map.Player;
import com.joseph.thedarknessbeyond.gameobject.map.Tile;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.windows.CombatWindow;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.StorageManager;

/**
 * Screen responsible for containing, drawing, and updating the map on the screen and internally
 * @author Joseph
 *
 */
public class MapScreen extends Screen {
	private static MapScreen instance;
	private Map map;
	private FontRenderContext frc;
	private Font font;
	private Rectangle2D r;
	private Player player;
	private CombatWindow cw;

	public MapScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		this.map = new Map();
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getMapFont();
		this.r = font.getStringBounds(";", frc);
		this.cw = new CombatWindow(x + (200 * ScreenReference.scale), y + (400 * ScreenReference.scale), 450 * ScreenReference.scale, 200 * ScreenReference.scale, player);
		this.cw.hide();
		
		instance = this;
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
		Tile[][] tiles = map.getMap();
		char[][] chars = map.getCharArray();
		int yOff = 65 * ScreenReference.scale;
		for (int i = 0; i < chars.length; i++) {
			int xOff = 100 * ScreenReference.scale;
			for (int j = 0; j < chars[i].length; j++) {
				if (tiles[i][j].isDiscovered() || chars[i][j] == '@' || Reference.DEBUG_MODE) {
					g.drawString(String.valueOf(chars[i][j]), x + xOff, y + yOff);
				}
				xOff += r.getHeight();
			}
			yOff += r.getHeight();
		}
		
		cw.drawBackground(g, observer);
		cw.drawUpdateableElements(g, observer);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		cw.updateUpdateableElements(deltaTime);
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	@Override
	public void show() {
		player = new Player(null, StorageManager.getInstance().getBestMele(), StorageManager.getInstance().getBestRanged(), StorageManager.getInstance().getBestAmmo(), StorageManager.getInstance().getBestArmor());
		this.map.addPlayer(player);
		super.show();
	}
	
	public void showEnemy(Enemy enemy) {
		this.cw.show(player, enemy);
	}
	
	public boolean isFightGoingOn() {
		return this.cw.isVisible();
	}
	
	public static MapScreen getInstance() {
		return instance;
	}
}