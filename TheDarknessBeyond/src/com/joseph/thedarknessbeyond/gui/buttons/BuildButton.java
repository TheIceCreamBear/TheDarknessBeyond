package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.reference.Reference;

public class BuildButton extends AbstractButton {

	public BuildButton(int x, int y, int width, int height) {
		super(x, y, width, height, false);
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
		g.drawString("Gather Wood", x + 5, y + 20);
		
		if (isMouseInElement()) {
			displayToolTip(g);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseInElement() {
		// TODO Auto-generated method stub
		return false;
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
