package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.Resource;

public class GenericBuildButton extends AbstractButton {
	EnumBuilding b;
	int toolTipHeight;
	
	public GenericBuildButton(int x, int y, EnumBuilding b) {
		super(x, y, 100, 100, false);
		this.b = b;
		toolTipHeight = 24 * b.getCost().length * ScreenReference.scale;
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
		g.drawString(b.toString(), x + 5, y + 20);
		
		if (isMouseInElement()) {
			displayToolTip(g);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		
	}

	@Override
	public void displayToolTip(Graphics g) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 5, y + height + 1, width, toolTipHeight);
		g.setColor(Color.WHITE);
		g.drawRect(x + 5, y + height + 1, width, toolTipHeight);
		g.setColor(Color.BLACK);
		Resource[] r = b.getCost();
		int yOff = 23;
		for (int i = 0; i < r.length; i++) {
			g.drawString(r[i].toString(), x + 5, y + height + 20);
			yOff += 24 * ScreenReference.scale;
		}
		
	}

	@Override
	public void onMouseEvent(MouseEvent e) {
		if (!visible) {
			return;
		}
		
		//if (Village.buildBuilding()) {
			
		//}
	}
	
}