package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.buttons.CollectWood;
import com.joseph.thedarknessbeyond.gui.buttons.HuntAnimalsButton;
import com.joseph.thedarknessbeyond.gui.buttons.MineStoneButton;
import com.joseph.thedarknessbeyond.gui.buttons.ScavengePlantsButton;

public class VillageScreen extends Screen {
	private Village village;
	private static VillageScreen screen;
	private CollectWood gatherWoodButton;
	private HuntAnimalsButton gatherAnimalButton;
	private MineStoneButton gatherStoneButton;
	private ScavengePlantsButton gatherPlantsButton;
	
	public VillageScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		this.village = new Village();
		
		/* When on the village screen, these buttons should be displayed, with the appropriate string on each button saying what they do 
		   and setting each button at certain coordinates.
		*/
		
		gatherWoodButton = new CollectWood(x + 100, y + 100, "Collect Wood", true);
		GameEngine.getInstance().addButton(gatherWoodButton);
		
		gatherAnimalButton = new HuntAnimalsButton(x + 100, y + 200, "Hunt Animals", true);
		GameEngine.getInstance().addButton(gatherAnimalButton);
		
		gatherStoneButton = new MineStoneButton(x + 100, y + 300, "Mine Stone", true);
		GameEngine.getInstance().addButton(gatherStoneButton);
		
		gatherPlantsButton = new ScavengePlantsButton(x + 100, y + 400, "Scavenge Plants", true);
		GameEngine.getInstance().addButton(gatherPlantsButton);
		
		screen = this;
	}

	// These methods implement and add the buttons to the village screen for the user
	
	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		gatherWoodButton.drawBackground(g, observer);
		gatherAnimalButton.drawBackground(g, observer);
		gatherStoneButton.drawBackground(g, observer);
		gatherPlantsButton.drawBackground(g, observer);
		
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		gatherWoodButton.drawUpdateableElements(g, observer); 
		gatherAnimalButton.drawUpdateableElements(g,observer);
		gatherStoneButton.drawUpdateableElements(g, observer);
		gatherPlantsButton.drawUpdateableElements(g, observer);
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		gatherWoodButton.updateUpdateableElements(deltaTime);
		gatherAnimalButton.updateUpdateableElements(deltaTime);
		gatherStoneButton.updateUpdateableElements(deltaTime);
		gatherPlantsButton.updateUpdateableElements(deltaTime);
		
		this.village.update();
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	public static Village getVillage() {
		return screen.village;
	}
	
	public static void setVillage(Village v) {
		screen.village = v;
	}
	
	@Override
	public void hide() {
		gatherWoodButton.hide();
		gatherAnimalButton.hide();
		gatherPlantsButton.hide();
		gatherStoneButton.hide();
		super.hide();
	}
	
	@Override
	public void show() {
		gatherWoodButton.show();
		gatherAnimalButton.show();
		gatherPlantsButton.show();
		gatherStoneButton.show();
		super.show();
	}
}