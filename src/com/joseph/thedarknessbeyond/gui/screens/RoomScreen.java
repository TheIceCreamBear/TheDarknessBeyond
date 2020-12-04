package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.buttons.GenericBuildButton;
import com.joseph.thedarknessbeyond.gui.buttons.GenericCraftButton;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.EnumItem;

/**
 * the First screen the player sees. contains the crafting and building
 * @author Nathan
 *
 */
public class RoomScreen extends Screen {
	private GenericBuildButton[] buttons; 
	private GenericCraftButton[] crafters;

	
	public RoomScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		buttons = new GenericBuildButton[14];
		EnumBuilding[] values = EnumBuilding.values();
		
		int yOff = ScreenReference.charHeight + 40 * ScreenReference.scale;
		for (int i = 0; i < values.length; i++) {
			buttons[i] = new GenericBuildButton(x, y + yOff, values[i]);
			yOff += ScreenReference.charHeight + 20 * ScreenReference.scale;
		}
		
		EnumItem[] items = EnumItem.values();
		this.crafters = new GenericCraftButton[items.length];
		yOff = ScreenReference.charHeight + 40 * ScreenReference.scale;
		for (int i = 0; i < items.length; i++) {
			crafters[i] = new GenericCraftButton(x + (200 * ScreenReference.scale), y + yOff, items[i]);
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
			crafters[i].drawBackground(g, observer);
		}
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].drawUpdateableElements(g, observer);
			crafters[i].drawUpdateableElements(g, observer);
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].displayToolTip(g);
			crafters[i].displayToolTip(g);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].updateUpdateableElements(deltaTime);
			crafters[i].updateUpdateableElements(deltaTime);
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
			crafters[i].show();
		}
		this.visible = true;
	}
	
	public void hide() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].hide();
			crafters[i].hide();
		}
		this.visible = false;
	}
}
