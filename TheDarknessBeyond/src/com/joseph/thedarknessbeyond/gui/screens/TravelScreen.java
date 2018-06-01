package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.gui.windows.ScreenSelectionWindow;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;

public class TravelScreen extends Screen {
	private GenericSelectableButton depart;

	// This depart button will be displayed on the travel screen. When clicked on, it will take you to the map of your adventure!
	
	public TravelScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		this.depart = new GenericSelectableButton(x, y + 200, "Depart", true, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				TravelScreen.this.hide();
				ScreenSelectionWindow.getInstance().depart();
				return true;
			}
		});
		GameEngine.getInstance().addButton(depart);
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		this.depart.drawBackground(g, observer);
		
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		this.depart.drawUpdateableElements(g, observer);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		this.depart.updateUpdateableElements(deltaTime);
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
}