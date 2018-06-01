package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.gui.ToolTip;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

public class GenericSelectableButton extends AbstractButton {
	private IMouseReliant imr;
	private FontRenderContext frc;
	private Font font;
	private String text;
	private ToolTip tt;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private boolean selected;
	private boolean staySelected;
	
	public GenericSelectableButton(int x, int y, String s, boolean scaled, boolean staySelected, IMouseReliant imr) {
		this(x, y, s, scaled, staySelected, null, imr);
	}
	
	public GenericSelectableButton(int x, int y, String s, boolean scaled, boolean staySelected, ToolTip tt, IMouseReliant imr) {
		super(x, y, (int) ScreenReference.getTheFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (5 * ScreenReference.scale), (int) ScreenReference.getTheFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (2 * ScreenReference.scale), scaled);
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.imr = imr;
		
		this.staySelected = staySelected;
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
		
		g.drawRect(x, y, width, height);
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		if (isMouseInElement()) {
			this.displayToolTip(g);
		}
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(text, frc);
		int yOff = (int) Math.abs(r.getY()) + 2 * ScreenReference.scale;
		int xOff = 5;
		g.drawString(text, x + xOff, y + yOff);
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
		if (!visible) {
			return false;
		}
		
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			imr.onMouseEvent(e);
			if (this.staySelected) {
				this.selected = true;
			}
			GameEngine.getInstance().releaseFocous();
			GameEngine.getInstance().setDefaultMouse();
			this.mouseInSelf = false;
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