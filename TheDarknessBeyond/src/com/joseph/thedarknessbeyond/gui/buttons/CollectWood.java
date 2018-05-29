package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import javax.swing.JButton;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;
import com.joseph.thedarknessbeyond.resource.StorageManager;



public class CollectWood extends AbstractButton {
	private ActionListener al;
	private FontRenderContext frc;
	private Font font;
	private String text;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private boolean selected;
	
	
	
	public CollectWood(int x, int y, String s, boolean scaled, ActionListener al) {
		super(x, y, (int) ScreenRefrence.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (2 * ScreenRefrence.scale), (int) ScreenRefrence.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (5 * ScreenRefrence.scale), scaled);
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenRefrence.getTheFont();
		this.al = al;
		this.addActionListener(this);
		
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
		if (this.mouseInSelf || this.selected) {
			this.font = ScreenRefrence.getUnderlinedFont();
		} else {
			this.font = ScreenRefrence.getTheFont();
		}
			
		}
		
	
	
	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		al.actionPerformed(e);
		this.selected = true;
		GameEngine.getInstance().releaseFocous();
	}
	
	public void deslecet() {
		this.selected = false;
	}
	
	public void select() {
		this.selected = true;
	}
	
	public int getWidth0() {
		return this.width;
	}
	

}
