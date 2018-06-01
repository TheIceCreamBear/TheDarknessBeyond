package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import javax.swing.JButton;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.gui.ToolTip;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;


public class MineStoneButton extends AbstractButton {
	private FontRenderContext frc;
	private Font font;
	private String text;
	private ToolTip tt;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private boolean selected;
	private StorageManager std;
	private int cooldown;
	private final int maxCooldown;
	
	public MineStoneButton(int x, int y, String s, boolean scaled) {
		this(x, y, s, scaled,null);
	}
	
	public MineStoneButton(int x, int y, String s, boolean scaled, ToolTip tt) {
		super(x, y, (int) ScreenReference.getTheFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (5 * ScreenReference.scale), (int) ScreenReference.getTheFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (2 * ScreenReference.scale), scaled);
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.std = StorageManager.getInstance();
		
		this.cooldown = 0;
		this.maxCooldown = 1200;
		
		if (tt == null) {
			this.tt = ToolTip.NULL;
		} else {
			this.tt = tt;
		}
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		if (this.cooldown > 0) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x , y , width, height);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x + 1, y + 1, cooldown * (width - 2) / maxCooldown,  height - 1);
			
		} else {
		
			g.setColor(Color.WHITE);
			g.drawRect(x, y, width, height);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x + 1, y + 1, width - 1, height - 1);
			
		}
	
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		if (this.cooldown > 0) {
			g.setColor(Color.DARK_GRAY);
		}
		else {
			g.setColor(Color.WHITE);
		}
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(text, frc);
		int yOff = (int) Math.abs(r.getY()) + 2 * ScreenReference.scale;
		int xOff = 5;
		g.drawString(text, x + xOff, y + yOff);
		
		if (isMouseInElement()) {
			this.displayToolTip(g);
			
		}
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		if (this.cooldown > 0) {
			this.cooldown--;
			return;
		}
		
		this.mouseInSelfPrevious = this.mouseInSelf;
		mouseInSelf = isMouseInElement();
		if (this.mouseInSelfPrevious != this.mouseInSelf) {
			if (mouseInSelf) {
				GameEngine.getInstance().setSelectMouse();
			} else {
				GameEngine.getInstance().setDefaultMouse();
			}
		}
		if (this.mouseInSelf || this.selected) {
			this.font = ScreenReference.getUnderlinedFont();
		} else {
			this.font = ScreenReference.getTheFont();
		}
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		tt.draw(g);
	}
	
	@Override
	public boolean onMouseEvent(MouseEvent e) {
		if (!visible || cooldown > 0) {
			return false;
		}
		
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			GameEngine.getInstance().releaseFocous();
			GameEngine.getInstance().setDefaultMouse();
			this.mouseInSelf = false;
	
			Resource stone = new Resource(EnumResource.Stone, 10);
			std.addResource(stone);
			
			EventBus.EVENT_BUS.post(new Event("*Sweat drips down your face* I hope its useful."));
			
			this.cooldown = maxCooldown;
			
			return true;
		}	
		return false;
	}
	
	public void deslecet() {
		this.selected = false;
	}
	
	public void select() {
		this.selected = true;
	}
	
	/**
	 * Gets the width of the button, but does not override {@link JButton#getWidth()} 
	 * @return
	 */
	public int getWidth0() {
		return this.width;
	}
	
	public int getHeight0() {
		return this.height;
	}
}