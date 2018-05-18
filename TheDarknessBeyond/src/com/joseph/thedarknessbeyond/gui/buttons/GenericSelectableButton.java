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
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;

public class GenericSelectableButton extends AbstractButton {
	private String text;
	private FontRenderContext frc;
	private Font font;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private ActionListener al;
	
	public GenericSelectableButton(int x, int y, String s) {
		super(x, y, (int) ScreenRefrence.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (2 * ScreenRefrence.scale), (int) ScreenRefrence.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (5 * ScreenRefrence.scale));
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenRefrence.getTheFont();
		
	}
	
	public GenericSelectableButton(int x, int y, String s, ActionListener al) {
		super(x, y, (int) ScreenRefrence.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (2 * ScreenRefrence.scale), (int) ScreenRefrence.getUnderlinedFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (5 * ScreenRefrence.scale));
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenRefrence.getTheFont();
		this.addActionListener(al);
		
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
				this.font = ScreenRefrence.getUnderlinedFont();
			} else {
				GameEngine.getInstance().setDefaultMouse();
				this.font = ScreenRefrence.getTheFont();
			}
		}
	}
	
	@Override
	public boolean removeGui() {
		return false;
	}
	
	@Override
	public void setGuiToRemove() {		
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 5, y + height + 1, width, 30);
		g.setColor(Color.WHITE);
		g.drawRect(x + 5, y + height + 1, width, 30);
		g.setColor(Color.BLACK);
		g.drawString("Tool Tip", x + 5, y + height + 20);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		al.actionPerformed(e);
	}
}