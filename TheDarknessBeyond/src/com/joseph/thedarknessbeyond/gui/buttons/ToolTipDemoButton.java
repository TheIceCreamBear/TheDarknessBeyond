package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;

public class ToolTipDemoButton extends AbstractButton {

	public ToolTipDemoButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		addActionListener(this);
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		g.setColor(Color.BLUE);
		g.setFont(Reference.Fonts.DEFAULT_FONT);
		g.drawString("Gather Wood", x, y + 20);
		
		if (isMouseInElement()) {
			displayToolTip(g);
		}
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		
	}

	@Override
	public boolean removeGui() {
		return false;
	}

	@Override
	public void setGuiToRemove() {
	}

	@Override
	public boolean isMouseInElement() {
		Point p = ScreenRefrence.getMouseLocation();
		if (p == null) {
			return false;
		}
		return p.x > x && p.x < (x + width) && p.y > y && p.y < (y + height);
	}

	@Override
	public void displayToolTip(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 5, y + height + 1, width, 30);
		g.setColor(Color.WHITE);
		g.drawRect(x + 5, y + height + 1, width, 30);
		g.setColor(Color.BLACK);
		g.drawString("Tool Tip", x + 5, y + height + 20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GameEngine.getInstance().releaseFocous();
	}
}