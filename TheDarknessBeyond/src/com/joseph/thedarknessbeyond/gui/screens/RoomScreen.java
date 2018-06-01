package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.gui.Screen;

public class RoomScreen extends Screen {

	public RoomScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		buttons = new GenericBuildButton[14];
		EnumBuilding[] values = EnumBuilding.values();
		buttons[0] = new GenericBuildButton(x + 100, y + 150, EnumBuilding.Hut);
		buttons[1] = new GenericBuildButton(x + 100, y + 225, EnumBuilding.Storage);
		buttons[2] = new GenericBuildButton(x + 100, y + 300, EnumBuilding.Armory);
		buttons[3] = new GenericBuildButton(x + 100, y + 375, EnumBuilding.Workshop);
		buttons[4] = new GenericBuildButton(x + 100, y + 450, EnumBuilding.Forge);
		buttons[5] = new GenericBuildButton(x + 100, y + 525, EnumBuilding.Blacksmith);
		buttons[6] = new GenericBuildButton(x + 100, y + 600, EnumBuilding.Charcuterie);
		buttons[7] = new GenericBuildButton(x + 400, y + 150, EnumBuilding.Garden);
		buttons[8] = new GenericBuildButton(x + 400, y + 225, EnumBuilding.Quarry);
		buttons[9] = new GenericBuildButton(x + 400, y + 300, EnumBuilding.WaterWorks);
		buttons[10] = new GenericBuildButton(x + 400, y + 375, EnumBuilding.LumberMill);
		buttons[11] = new GenericBuildButton(x + 400, y + 450, EnumBuilding.GuardPost);
		buttons[12] = new GenericBuildButton(x + 400, y + 525, EnumBuilding.Barricades);
		buttons[13] = new GenericBuildButton(x + 400, y + 600, EnumBuilding.Cathedral);
		
		for (int i = 0; i < buttons.length; i++) {
			GameEngine.getInstance().addButton(this.buttons[i]);
		}
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}	
}