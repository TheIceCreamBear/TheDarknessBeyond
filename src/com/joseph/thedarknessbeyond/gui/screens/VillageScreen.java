package com.joseph.thedarknessbeyond.gui.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.HashMap;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumJob;
import com.joseph.thedarknessbeyond.gui.Screen;
import com.joseph.thedarknessbeyond.gui.buttons.CollectWood;
import com.joseph.thedarknessbeyond.gui.buttons.GenericCoolDownButton;
import com.joseph.thedarknessbeyond.gui.buttons.HuntAnimalsButton;
import com.joseph.thedarknessbeyond.gui.buttons.MineStoneButton;
import com.joseph.thedarknessbeyond.gui.buttons.ScavengePlantsButton;
import com.joseph.thedarknessbeyond.gui.windows.GenericJobAssignmentWindow;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

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
	private GenericCoolDownButton collectWood;
	private HuntAnimalsButton gatherAnimalButton;
	private MineStoneButton gatherStoneButton;
	private ScavengePlantsButton gatherPlantsButton;
	private GenericJobAssignmentWindow[] assignments;
	
	public VillageScreen(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.village = new Village();
		
		/* When on the village screen, these buttons should be displayed, with the appropriate string on each button saying what they do 
		   and setting each button at certain coordinates.
		*/
		int xOff = 0;
		int yOff = 100;
		gatherWoodButton = new CollectWood(x + xOff, y + yOff, "Collect Wood");
//		TheDarknessBeyondEngine.getInstance().addButton(gatherWoodButton);
//		yOff += gatherWoodButton.getHeight() + 20;
		
		collectWood = new GenericCoolDownButton(x + xOff, y + yOff, "Collect Wood", 600, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				Resource wood = new Resource(EnumResource.Wood, 20);
				StorageManager.getInstance().addResource(wood);
				
				EventBus.EVENT_BUS.post(new Event("Cutting down trees and picking up sticks seems to be pretty useful."));
				return true;
			}
		});
		TheDarknessBeyondEngine.getInstance().addButton(collectWood);
		yOff += collectWood.getHeight() + 20;
		
		gatherAnimalButton = new HuntAnimalsButton(x + xOff, y + yOff, "Hunt Animals");
		TheDarknessBeyondEngine.getInstance().addButton(gatherAnimalButton);
		yOff += gatherAnimalButton.getHeight() + 20;
		
		gatherStoneButton = new MineStoneButton(x + xOff, y + yOff, "Mine Stone");
		TheDarknessBeyondEngine.getInstance().addButton(gatherStoneButton);
		yOff += gatherStoneButton.getHeight() + 20;
		
		gatherPlantsButton = new ScavengePlantsButton(x + xOff, y + yOff, "Scavenge Plants");
		TheDarknessBeyondEngine.getInstance().addButton(gatherPlantsButton);
		
		
		xOff += 200;
		yOff = 100;
		this.assignments = new GenericJobAssignmentWindow[village.getJobDistrubution().size()];
		for (int i = 0; i < assignments.length; i++) {
			assignments[i] = new GenericJobAssignmentWindow(x + xOff, y + yOff, EnumJob.values()[i]);
			yOff += assignments[i].getHeight() + 20;
		}		
		
		screen = this;
	}

	// These methods implement and add the buttons to the village screen for the user
	
	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
//		gatherWoodButton.drawBackground(g, observer);
		collectWood.drawBackground(g, observer);
		gatherAnimalButton.drawBackground(g, observer);
		gatherStoneButton.drawBackground(g, observer);
		gatherPlantsButton.drawBackground(g, observer);
		
		for (int i = 0; i < assignments.length; i++) {
			assignments[i].drawBackground(g, observer);
		}
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
//		gatherWoodButton.drawUpdateableElements(g, observer);
		collectWood.drawUpdateableElements(g, observer);
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
//		gatherWoodButton.updateUpdateableElements(deltaTime);
		collectWood.updateUpdateableElements(deltaTime);
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
		collectWood.hide();
//		gatherWoodButton.hide();
		gatherAnimalButton.hide();
		gatherPlantsButton.hide();
		gatherStoneButton.hide();
		super.hide();
	}
	
	@Override
	public void show() {
		collectWood.show();
//		gatherWoodButton.show();
		gatherAnimalButton.show();
		gatherPlantsButton.show();
		gatherStoneButton.show();
		super.show();
	}
}
