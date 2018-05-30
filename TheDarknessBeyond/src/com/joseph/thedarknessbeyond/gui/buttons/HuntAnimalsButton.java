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
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

public class HuntAnimalsButton extends AbstractButton {
	private IMouseReliant imr;
	private FontRenderContext frc;
	private Font font;
	private String text;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private boolean selected;
	
	public HuntAnimalsButton() {
		this(515, 100, 100, 100);
	}
	
	public HuntAnimalsButton(int x, int y, int width, int height) {
		super(x, y, width, height, false);
		this.frc = GameEngine.getInstance().getFrc();
		if (ScreenReference.scale == 2) {
			this.font = Reference.Fonts.SCALED_UP_FONT;
		} else {
			this.font = Reference.Fonts.DEFAULT_FONT;
		}
	}
	
	public HuntAnimalsButton(int x, int y, String s, boolean scaled, IMouseReliant imr) {
		super(x, y, (int) ScreenReference.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (2 * ScreenReference.scale), (int) ScreenReference.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (5 * ScreenReference.scale), scaled);
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.imr = imr;
		
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
		if (!visible) {
			return;
		}
		
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		// TODO Auto-generated method stub
		
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
			GameEngine.getInstance().releaseFocous();
			return true;
		}
		return false;
	}
}