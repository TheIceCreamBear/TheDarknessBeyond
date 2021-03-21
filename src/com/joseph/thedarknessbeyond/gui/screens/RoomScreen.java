package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
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
		yOff = ScreenReference.charHeight + 40 * ScreenReference.scale;
		ArrayList<GenericCraftButton> craftButtons = new ArrayList<GenericCraftButton>();
		for (int i = 0; i < items.length; i++) {
			EnumItem item = items[i];
			if (item.getCost() == null || item.getCost().length == 0) {
				continue;
			}
			craftButtons.add(new GenericCraftButton(x + (200 * ScreenReference.scale), y + yOff, item));
			yOff += ScreenReference.charHeight + 20 * ScreenReference.scale;
		}
		
		for (int i = 0; i < buttons.length; i++) {
			TheDarknessBeyondEngine.getInstance().addButton(this.buttons[i]);
		}
		
		this.crafters = new GenericCraftButton[craftButtons.size()];
		for (int i = 0; i < crafters.length; i++) {
			this.crafters[i] = craftButtons.get(i);
			TheDarknessBeyondEngine.getInstance().addButton(this.crafters[i]);
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
		for (int i = 0; i < crafters.length; i++) {			
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
		}
		for (int i = 0; i < crafters.length; i++) {			
			crafters[i].drawUpdateableElements(g, observer);
		}
		
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].displayToolTip(g);
		}
		for (int i = 0; i < crafters.length; i++) {			
			crafters[i].displayToolTip(g);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].updateUpdateableElements(deltaTime);
		}
		for (int i = 0; i < crafters.length; i++) {			
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
		}
		for (int i = 0; i < crafters.length; i++) {			
			crafters[i].show();
		}
		this.visible = true;
	}
	
	public void hide() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].hide();
		}
		for (int i = 0; i < crafters.length; i++) {			
			crafters[i].hide();
		}
		this.visible = false;
	}
}
