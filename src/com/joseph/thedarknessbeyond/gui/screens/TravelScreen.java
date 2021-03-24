package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.gui.windows.ScreenSelectionWindow;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;

/**
 * The Screen that you go to before you depart on a trip into the wilderness
 * @author Joseph
 *
 */
public class TravelScreen extends Screen {
	private GenericSelectableButton depart;
	
	
	public TravelScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		
		// TODO the scaling setting in this and other windowed classes
		// TODO departing with items consumes them from your stores
		
		this.depart = new GenericSelectableButton(x, y + 200, "Depart", false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				TravelScreen.this.hide();
				ScreenSelectionWindow.getInstance().depart();
				return true;
			}
		});
		TheDarknessBeyondEngine.getInstance().addButton(depart);
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
		this.depart.updateUpdateableElements(deltaTime);
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
}