package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.HashMap;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumJob;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.buttons.CollectWood;
import com.joseph.thedarknessbeyond.gui.buttons.HuntAnimalsButton;
import com.joseph.thedarknessbeyond.gui.buttons.MineStoneButton;
import com.joseph.thedarknessbeyond.gui.buttons.ScavengePlantsButton;
import com.joseph.thedarknessbeyond.gui.windows.GenericJobAssignmentWindow;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * the screen responsible for containing, drawing, and updating the village on the screen and internally
 * @author Joseph
 * @author Justin
 *
 */
public class VillageScreen extends Screen {
	private Village village;
	private static VillageScreen screen;
	private CollectWood gatherWoodButton;
	private HuntAnimalsButton gatherAnimalButton;
	private MineStoneButton gatherStoneButton;
	private ScavengePlantsButton gatherPlantsButton;
	private GenericJobAssignmentWindow[] assignments;
	
	public VillageScreen(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		this.village = new Village();
		
		/* When on the village screen, these buttons should be displayed, with the appropriate string on each button saying what they do 
		   and setting each button at certain coordinates.
		*/
		int xOff = 00 * ScreenReference.scale;
		int yOff = 100 * ScreenReference.scale;
		gatherWoodButton = new CollectWood(x + xOff, y + yOff, "Collect Wood", true);
		GameEngine.getInstance().addButton(gatherWoodButton);
		yOff += gatherWoodButton.getHeight0() + 20 * ScreenReference.scale;
		
		gatherAnimalButton = new HuntAnimalsButton(x + xOff, y + yOff, "Hunt Animals", true);
		GameEngine.getInstance().addButton(gatherAnimalButton);
		yOff += gatherAnimalButton.getHeight0() + 20 * ScreenReference.scale;
		
		gatherStoneButton = new MineStoneButton(x + xOff, y + yOff, "Mine Stone", true);
		GameEngine.getInstance().addButton(gatherStoneButton);
		yOff += gatherStoneButton.getHeight0() + 20 * ScreenReference.scale;
		
		gatherPlantsButton = new ScavengePlantsButton(x + xOff, y + yOff, "Scavenge Plants", true);
		GameEngine.getInstance().addButton(gatherPlantsButton);
		
		
		xOff += 200 * ScreenReference.scale;
		yOff = 100 * ScreenReference.scale;
		this.assignments = new GenericJobAssignmentWindow[village.getJobDistrubution().size()];
		for (int i = 0; i < assignments.length; i++) {
			assignments[i] = new GenericJobAssignmentWindow(x + xOff, y + yOff, EnumJob.values()[i]);
			yOff += assignments[i].getHeight0() + 20 * ScreenReference.scale;
		}		
		
		screen = this;
	}

	// These methods implement and add the buttons to the village screen for the user
	
	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		gatherWoodButton.drawBackground(g, observer);
		gatherAnimalButton.drawBackground(g, observer);
		gatherStoneButton.drawBackground(g, observer);
		gatherPlantsButton.drawBackground(g, observer);
		
		for (int i = 0; i < assignments.length; i++) {
			assignments[i].drawBackground(g, observer);
		}
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		gatherWoodButton.drawUpdateableElements(g, observer); 
		gatherAnimalButton.drawUpdateableElements(g,observer);
		gatherStoneButton.drawUpdateableElements(g, observer);
		gatherPlantsButton.drawUpdateableElements(g, observer);
		
		g.setFont(ScreenReference.getTheFont());
		g.setColor(Color.white);
		g.drawString("Jobs: ", x + 300 * ScreenReference.scale, y + 80 * ScreenReference.scale);
		
		for (int i = 0; i < assignments.length; i++) {
			assignments[i].drawUpdateableElements(g, observer);
		}
		
		EnumBuilding[] buildings = EnumBuilding.values();
		HashMap<EnumBuilding, Integer> map = village.getBuildingCount();
		g.setFont(ScreenReference.getTheFont());
		g.setColor(Color.white);
		int xOff = 500 * ScreenReference.scale;
		int yOff = 100 * ScreenReference.scale;
		for (int i = 0; i < buildings.length; i++) {
			g.drawString(buildings[i] + ": " + map.get(buildings[i]), x + xOff, y + yOff);
			yOff += ScreenReference.charHeight + 10 * ScreenReference.scale;
		}
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		gatherWoodButton.updateUpdateableElements(deltaTime);
		gatherAnimalButton.updateUpdateableElements(deltaTime);
		gatherStoneButton.updateUpdateableElements(deltaTime);
		gatherPlantsButton.updateUpdateableElements(deltaTime);
		
		for (int i = 0; i < assignments.length; i++) {
			assignments[i].updateUpdateableElements(deltaTime);
		}
		
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
