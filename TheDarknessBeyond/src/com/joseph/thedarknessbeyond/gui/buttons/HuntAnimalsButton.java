package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;

public class HuntAnimalsButton extends AbstractButton{
	private ActionListener al;
	private FontRenderContext frc;
	private Font font;
	private String text;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private boolean selected;
	
	public HuntAnimalsButton(){
		this (515,100,100,100);
	}
	
	public HuntAnimalsButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.frc = GameEngine.getInstance().getFrc();
		if (ScreenRefrence.scale == 2) {
			this.font = Reference.Fonts.SCALED_UP_FONT;
		} else {
			this.font = Reference.Fonts.DEFAULT_FONT;
		}
	}
	
	public HuntAnimalsButton(int x, int y, String s, boolean scaled, ActionListener al) {
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayToolTip(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
