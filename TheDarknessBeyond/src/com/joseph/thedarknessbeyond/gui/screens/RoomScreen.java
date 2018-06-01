package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.buttons.GenericBuildButton;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

public class RoomScreen extends Screen {
	private GenericBuildButton[] buttons; 
	
	
	/* When first running the game, you are placed in the room screen. In this screen, each building that can be made is displayed. 
	   When the user's mouse is hovered over the buttons, a cost will be displayed underneath, showing you how of a specific resource
	   it takes to build it. 
	*/
	public RoomScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		buttons = new GenericBuildButton[14];
		EnumBuilding[] values = EnumBuilding.values();
		
		int yOff = ScreenReference.charHeight + 40 * ScreenReference.scale;
		for (int i = 0; i < values.length; i++) {
			buttons[i] = new GenericBuildButton(x, y + yOff, values[i]);
			yOff += ScreenReference.charHeight + 20 * ScreenReference.scale;
		}
		
		for (int i = 0; i < buttons.length; i++) {
			GameEngine.getInstance().addButton(this.buttons[i]);
		}
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].drawBackground(g, observer);
		}
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].drawUpdateableElements(g, observer);
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].displayToolTip(g);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].updateUpdateableElements(deltaTime);
		}
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}	
	
	public boolean isVisible() {
		return this.visible;
	}

	public void show() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].show();
		}
		this.visible = true;
	}
	
	public void hide() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].hide();
		}
		this.visible = false;
	}
}
