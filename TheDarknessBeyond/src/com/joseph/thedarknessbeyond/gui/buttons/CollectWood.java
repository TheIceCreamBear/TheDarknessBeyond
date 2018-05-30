package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.event.IMouseReliant;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

public class CollectWood extends AbstractButton {
	private IMouseReliant imr;
	private FontRenderContext frc;
	private Font font;
	private String text;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	
	public CollectWood(int x, int y, String s, boolean scaled, IMouseReliant imr) {
		super(x, y, (int) ScreenReference.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (2 * ScreenReference.scale), (int) ScreenReference.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (5 * ScreenReference.scale), scaled);
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.imr = imr;
		
	}
	
	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		g.drawRect(x, y, width, height);
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (isMouseInElement()) {
//			this.displayToolTip(g);
		}
		g.setColor(Color.WHITE);
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(text, frc);
		int yOff = (int) r.getHeight();
		int xOff = 5;
		g.drawString(text, x + xOff, y + yOff);
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		this.mouseInSelfPrevious = this.mouseInSelf;
		mouseInSelf = isMouseInElement();
		if (this.mouseInSelfPrevious != this.mouseInSelf) {
			if (mouseInSelf) {
				GameEngine.getInstance().setSelectMouse();
			} else {
				GameEngine.getInstance().setDefaultMouse();
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
		
	}
	
	public int getWidth0() {
		return this.width;
	}
	
	public int getHeight0() {
		return this.height;
	}

	@Override
	public void onMouseEvent(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			imr.onMouseEvent(e);
			GameEngine.getInstance().releaseFocous();
		}
		
	}
}