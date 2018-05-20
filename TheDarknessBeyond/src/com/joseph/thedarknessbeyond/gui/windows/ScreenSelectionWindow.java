package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;

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
//		this.screens[this.selectedIndex].drawBackground(g, observer);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		// Draw actual updateable
		
		// draw button updateable
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].drawUpdateableElements(g, observer);
		}
		
		// draw selected screen updateable
//		this.screens[this.selectedIndex].drawUpdateableElements(g, observer);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		// Update self
		
		// Update buttons
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].updateUpdateableElements(deltaTime);
		}
		
		// Update screen
//		this.screens[this.selectedIndex].updateUpdateableElements(deltaTime);
		
	}

	@Override
	@Deprecated
	public boolean removeGui() {
		return false;
	}

	@Override
	@Deprecated
	public void setGuiToRemove() {
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
		this.screens = new Screen[4];
		
	}
	
	private void initButtonsToDefault() {
		this.buttons = new GenericSelectableButton[4];
		int xOff = 0;
		int yOff = 10 * ScreenRefrence.scale;
		this.buttons[0] = new GenericSelectableButton(x + xOff, y + yOff, "Button 0", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScreenSelectionWindow.this.refocousSelection(0);
			}
		});
		xOff += this.buttons[0].getWidth0() + (yOff * 2);
		
		this.buttons[1] = new GenericSelectableButton(x + xOff, y + yOff, "Button 1", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScreenSelectionWindow.this.refocousSelection(1);
				
			}
		});
		xOff += this.buttons[1].getWidth0() + (yOff * 2);
		
		this.buttons[2] = new GenericSelectableButton(x + xOff, y + yOff, "Button 2", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScreenSelectionWindow.this.refocousSelection(2);
				
			}
		});
		xOff += this.buttons[2].getWidth0() + (yOff * 2);
		
		this.buttons[3] = new GenericSelectableButton(x + xOff, y + yOff, "Button 3", true, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ScreenSelectionWindow.this.refocousSelection(3);
				
			}
		});
		xOff += this.buttons[3].getWidth0() + (yOff * 2);
		
		for (int i = 0; i < buttons.length; i++) {
			GameEngine.getInstance().addButton(this.buttons[i]);
		}
	}
}