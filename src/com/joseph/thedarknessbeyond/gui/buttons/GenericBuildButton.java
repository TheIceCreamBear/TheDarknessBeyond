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
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.Resource;

/**
 * A button the is used to build the given building. It is a generic and allocates its size based of the size of the string it will draw
 * @author Nathan
 *
 */
public class GenericBuildButton extends AbstractButton {
	private FontRenderContext frc;
	private Font font;
	private EnumBuilding b;
	private int toolTipHeight;
	private int toolTipWidth;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	
	public GenericBuildButton(int x, int y, EnumBuilding b) {
		super(x, y, (int) ScreenReference.getTheFont().getStringBounds("Build " + b.toString(), TheDarknessBeyondEngine.getInstance().getFrc()).getWidth() + (5 * ScreenReference.scale), (int) ScreenReference.getTheFont().getStringBounds("Build " + b.toString(), TheDarknessBeyondEngine.getInstance().getFrc()).getHeight() + (2 * ScreenReference.scale), true);
		this.b = b;
		this.toolTipHeight = 24 * b.getCost().length * ScreenReference.scale;
		this.frc = TheDarknessBeyondEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		
		this.toolTipWidth = 0;
		Resource[] r = b.getCost();
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
		Rectangle2D r = font.getStringBounds(b.toString(), frc);
		int yOff = (int) Math.abs(r.getY()) + 2 * ScreenReference.scale;
		int xOff = 5;
		g.drawString("Build " + b.toString(), x + xOff, y + yOff);
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
		Resource[] r = b.getCost();
		Rectangle2D r1 = font.getStringBounds(b.toString(), frc);
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
			if (VillageScreen.getVillage().buildBuilding(b)) {
				EventBus.EVENT_BUS.post(new Event(("Built a " + b.toString()) + "."));
			} else {
				EventBus.EVENT_BUS.post(new Event("More resources needed!"));
			}
			
			return true;
		}
		
		return false;
	}
	
}