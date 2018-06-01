package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.File;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.util.FileSaveSystem;

public class LoadGameWindow extends Window {
	private boolean visible;
	private Font font;
	private FontRenderContext frc;
	private GenericSelectableButton[] gsbs;
	private final String headder = "Load Game:";
	
	public LoadGameWindow(File[] files) {
		super(0, 0, 0, 0, false);
		if (files == null) {
			return;
		}
		this.visible = false;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.gsbs = new GenericSelectableButton[files.length];
		
		Rectangle2D r = font.getStringBounds(this.headder, frc);
		int xOff = 5 * ScreenReference.scale;
		int yOff = (int) r.getHeight() + 10 * ScreenReference.scale;
		int maxL = headder.length();
		
		String[] possible = new String[files.length];
		for (int i = 0; i < files.length; i++) {
			String name = files[i].getName();
			if (name.equals("NEW_GAME_FILE.tbdSave")) {
				possible[i] = "New Game";
			} else {
				possible[i] = name.substring(0, name.lastIndexOf("."));
			}
			if (maxL < possible[i].length()) {
				maxL = possible[i].length();
			}
		}
		maxL *= ScreenReference.charWidth;
		maxL += 15 * ScreenReference.scale;
		int h = ((int) r.getHeight() + 12 * ScreenReference.scale) * (files.length + 1);
//		super.resetDimensions(ScreenRefrence.WIDTH / 2 - (maxL / 2), ScreenRefrence.HEIGHT / 2 - (h / 2), maxL, h, true);
//		super.resetDimensions(600, 400, maxL, h, true);
		super.resetDimensions(1100 * ScreenReference.scale, ScreenReference.HEIGHT / 2 - (h / 2), maxL, h, true);
		
		for (int i = 0; i < files.length; i++) {
			final int ii = i;
			gsbs[i] = new GenericSelectableButton(x + xOff, y + yOff, possible[i], true, false, new IMouseReliant() {
				@Override
				public boolean onMouseEvent(MouseEvent e) {
					LoadGameWindow.this.hide();
					PauseMenuWindow.getInstance().hide();
					try {
						FileSaveSystem.loadGame(files[ii]);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					return true;
				}
			});
			
			GameEngine.getInstance().addButton(gsbs[i]);
			yOff += gsbs[i].getHeight0() + 10 * ScreenReference.scale;
		}
		
//		System.out.println(this.x + " " + this.y + " " + this.width + " " + this.height);
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(this.headder, frc);
		int yOff = (int) r.getHeight();
		int xOff = 5;
		g.drawString(this.headder, x + xOff, y + yOff);
		
		for (int i = 0; i < gsbs.length; i++) {
			gsbs[i].drawBackground(g, observer);
		}
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		for (int i = 0; i < gsbs.length; i++) {
			gsbs[i].drawUpdateableElements(g, observer);
		}
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		for (int i = 0; i < gsbs.length; i++) {
			gsbs[i].updateUpdateableElements(deltaTime);
		}
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	public boolean isVisible() {
		return this.visible;
	}

	public void show() {
		this.visible = true;
	}
	
	public void hide() {
		this.visible = false;
	}
}