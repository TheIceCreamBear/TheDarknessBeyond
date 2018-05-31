package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.gui.screens.RoomScreen;
import com.joseph.thedarknessbeyond.gui.screens.TravelScreen;
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

public class ScreenSelectionWindow extends Window {
	private Screen[] screens;
	private GenericSelectableButton[] buttons;
	private int selectedIndex;

	public ScreenSelectionWindow(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.initScreens();
		this.initButtonsToDefault();
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
	
	private void refocousSelection(int newIndex) {
		this.buttons[this.selectedIndex].deslecet();
		this.selectedIndex = newIndex;
	}
	
	private void initScreens() {
		this.screens = new Screen[3];
		this.screens[0] = new RoomScreen(x, y, width, height);
		this.screens[1] = new VillageScreen(x, y, width, height);
		this.screens[2] = new TravelScreen(x, y, width, height);
	}
	
	private void initButtonsToDefault() {
		this.buttons = new GenericSelectableButton[3];
		int xOff = 0;
		int yOff = 10 * ScreenReference.scale;
		this.buttons[0] = new GenericSelectableButton(x + xOff, y + yOff, "Room", true, true, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				ScreenSelectionWindow.this.refocousSelection(0);
				return true;
			}
		});
		xOff += this.buttons[0].getWidth0() + (yOff * 2);
		
		this.buttons[1] = new GenericSelectableButton(x + xOff, y + yOff, "Village", true, true, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				ScreenSelectionWindow.this.refocousSelection(1);
				return true;
			}
		});
		xOff += this.buttons[1].getWidth0() + (yOff * 2);
		
		this.buttons[2] = new GenericSelectableButton(x + xOff, y + yOff, "Travel", true, true, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				ScreenSelectionWindow.this.refocousSelection(2);
				return true;
			}
		});
		xOff += this.buttons[2].getWidth0() + (yOff * 2);
				
		this.buttons[0].select();
		
		for (int i = 0; i < buttons.length; i++) {
			GameEngine.getInstance().addButton(this.buttons[i]);
		}
	}
}