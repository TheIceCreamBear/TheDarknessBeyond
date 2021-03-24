package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.gui.screens.MapScreen;
import com.joseph.thedarknessbeyond.gui.screens.RoomScreen;
import com.joseph.thedarknessbeyond.gui.screens.TravelScreen;
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;

/**
 * the window that selects which screen is to be shown
 * @author Joseph
 *
 */
public class ScreenSelectionWindow extends Window {
	private static ScreenSelectionWindow instance;
	private Screen[] screens;
	private GenericSelectableButton[] buttons;
	private int selectedIndex;

	public ScreenSelectionWindow(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.initScreens();
		this.initButtonsToDefault();
		
		instance = this;
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		// Draw actual background
		
		// draw button background
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].drawBackground(g, observer);
		}
		
		// draw selected screen background
		this.screens[this.selectedIndex].drawBackground(g, observer);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		// Draw actual updateable
		
		// draw button updateable
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].drawUpdateableElements(g, observer);
		}
		
		// draw selected screen updateable
		this.screens[this.selectedIndex].drawUpdateableElements(g, observer);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		// Update self
		
		// Update buttons
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].updateUpdateableElements(deltaTime);
		}
		
		// Update screen
		for (int i = 0; i < screens.length; i++) {
			screens[i].updateUpdateableElements(deltaTime);
		}
		
	}

	@Override
	public void displayToolTip(Graphics g) {
		// NO-OP
	}
	
	public void depart() {
		this.refocousSelection(3);
	}
	
	public void returnHome() {
		this.refocousSelection(2);
	}
	
	private void refocousSelection(int newIndex) {
		if (this.selectedIndex != 3) {
			this.buttons[this.selectedIndex].deslecet();
		}
		this.screens[selectedIndex].hide();
		this.screens[newIndex].show();
		this.selectedIndex = newIndex;
	}
	
	private void initScreens() {
		this.screens = new Screen[4];
		this.screens[0] = new RoomScreen(x, y, width, height);
		this.screens[1] = new VillageScreen(x, y, width, height);
		this.screens[2] = new TravelScreen(x, y, width, height);
		this.screens[3] = new MapScreen(x, y, width, height);
		for (int i = 1; i < screens.length; i++) {
			this.screens[i].hide();
		}
	}
	
	private void initButtonsToDefault() {
		this.buttons = new GenericSelectableButton[3];
		int xOff = 0;
		int yOff = 10;
		this.buttons[0] = new GenericSelectableButton(x + xOff, y + yOff, "Room", true, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				ScreenSelectionWindow.this.refocousSelection(0);
				return true;
			}
		});
		xOff += this.buttons[0].getWidth() + 20;
		
		this.buttons[1] = new GenericSelectableButton(x + xOff, y + yOff, "Village", true, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				ScreenSelectionWindow.this.refocousSelection(1);
				return true;
			}
		});
		xOff += this.buttons[1].getWidth() + 20;
		
		this.buttons[2] = new GenericSelectableButton(x + xOff, y + yOff, "Travel", true, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				ScreenSelectionWindow.this.refocousSelection(2);
				return true;
			}
		});
		xOff += this.buttons[2].getWidth() + 20;
		
		this.refocousSelection(0);
		this.buttons[0].select();
		
		for (int i = 0; i < buttons.length; i++) {
			TheDarknessBeyondEngine.getInstance().addButton(this.buttons[i]);
		}
	}
	
	public static ScreenSelectionWindow getInstance() {
		return instance;
	}
}