package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.EnumItem;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

/**
 * A generic button that crafts the given item
 * @author Joseph
 *
 */
public class GenericCraftButton extends AbstractButton {
	private FontRenderContext frc;
	private Font font;
	private EnumItem item;
	private int toolTipHeight;
	private int toolTipWidth;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	
	public GenericCraftButton(int x, int y, EnumItem item) {
		super(x, y, (int) ScreenReference.getTheFont().getStringBounds("Craft " + item.toString(), TheDarknessBeyondEngine.getInstance().getFrc()).getWidth() + (5 * ScreenReference.scale), (int) ScreenReference.getTheFont().getStringBounds("Build " + item.toString(), TheDarknessBeyondEngine.getInstance().getFrc()).getHeight() + (2 * ScreenReference.scale), true);
		this.item = item;
		this.toolTipHeight = 24 * item.getCost().length * ScreenReference.scale;
		this.frc = TheDarknessBeyondEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		
		this.toolTipWidth = 0;
		Resource[] r = item.getCost();
		for (int i = 0; i < r.length; i++) {
		    if (this.toolTipWidth < r[i].toString().length()) {
		    	this.toolTipWidth = r[i].toString().length();
		    }
		}
		this.toolTipWidth *= ScreenReference.charWidth;
		this.toolTipWidth += 10 * ScreenReference.scale;
	}
	

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		g.fillRect(x, y, width, height);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(item.toString(), frc);
		int yOff = (int) Math.abs(r.getY()) + 2 * ScreenReference.scale;
		int xOff = 5;
		g.drawString("Craft " + item.toString(), x + xOff, y + yOff);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		this.mouseInSelfPrevious = this.mouseInSelf;
		mouseInSelf = isMouseInElement();
		if (this.mouseInSelfPrevious != this.mouseInSelf) {
			if (mouseInSelf) {
				TheDarknessBeyondEngine.getInstance().setSelectMouse();
			} else {
				TheDarknessBeyondEngine.getInstance().setDefaultMouse();
			}
		}
		if (this.mouseInSelf) {
			this.font = ScreenReference.getUnderlinedFont();
		} else {
			this.font = ScreenReference.getTheFont();
		}
	}

	@Override
	public void displayToolTip(Graphics g) {
		if (!visible) {
			return;
		}
		
		if (!isMouseInElement()) {
			return;
		}
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 5, y + height + 1, toolTipWidth, toolTipHeight);
		g.setColor(Color.WHITE);
		g.drawRect(x + 5, y + height + 1, toolTipWidth, toolTipHeight);
		g.setColor(Color.BLACK);
		Resource[] r = item.getCost();
		Rectangle2D r1 = font.getStringBounds(item.toString(), frc);
		int yOff = (int) Math.abs(r1.getY()) + 2 * ScreenReference.scale;
		int xOff = 10;
		for (int i = 0; i < r.length; i++) {
			g.drawString(r[i].toString(), x + xOff, y + height + yOff);
			yOff += (int) Math.abs(r1.getY()) + 2 * ScreenReference.scale;
		}
		
	}

	@Override
	public boolean onMouseEvent(MouseEvent e) {
		if (!visible) {
			return false;
		}
		
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			if (StorageManager.getInstance().craftItem(item)) {
				EventBus.EVENT_BUS.post(new Event(("Crafted a " + item.toString()) + "."));
			} else {
				EventBus.EVENT_BUS.post(new Event("More resources needed!"));
			}
			
			return true;
		}
		
		return false;
	}
	
}