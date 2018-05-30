package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.Reference;

public class ToolTipDemoButton extends AbstractButton {

	public ToolTipDemoButton(int x, int y, int width, int height) {
		super(x, y, width, height, false);
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.BLUE);
		g.setFont(Reference.Fonts.DEFAULT_FONT);
		g.drawString("Gather Wood", x + 5, y + 20);
		
		if (isMouseInElement()) {
			displayToolTip(g);
		}
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		
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
	public void onMouseEvent(MouseEvent e) {
		if (!visible) {
			return;
		}
		
		EventBus.EVENT_BUS.post(new Event("Demo button"));
		GameEngine.getInstance().releaseFocous();
	}
}