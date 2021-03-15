package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.util.Utilities;

/**
 * A generic type button that has a cool down and dynamically allocates its size
 * @author Joseph
 *
 */
public class GenericCoolDownButton extends AbstractButton {
	private IMouseReliant imr;
	private FontRenderContext frc;
	private Font font;
	private String text;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private boolean selected;
	private int cooldown;
	private final int maxCooldown;
	
	public GenericCoolDownButton(int x, int y, String s, int maxCooldown, IMouseReliant imr) {
		super(x, y, Utilities.getGuiSizeFromString(s));
		this.text = s;
		this.frc = TheDarknessBeyondEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		
		this.cooldown = 0;
		this.maxCooldown = maxCooldown;
		
		this.imr = imr;
	}
	
	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		if (this.cooldown > 0) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x, y, width, height);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x + 1, y + 1, cooldown * (width - 2) / maxCooldown, height - 1);
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
				TheDarknessBeyondEngine.getInstance().setSelectMouse();
			} else {
				TheDarknessBeyondEngine.getInstance().setDefaultMouse();
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
		
	}
	
	@Override
	public boolean onMouseEvent(MouseEvent e) {
		if (!visible) {
			return false;
		}
		
		if (cooldown > 0) {
			return false;
		}
		
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			imr.onMouseEvent(e);
			TheDarknessBeyondEngine.getInstance().releaseFocous();
			TheDarknessBeyondEngine.getInstance().setDefaultMouse();
			this.font = ScreenReference.getTheFont();
			this.mouseInSelf = false;
				
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
}