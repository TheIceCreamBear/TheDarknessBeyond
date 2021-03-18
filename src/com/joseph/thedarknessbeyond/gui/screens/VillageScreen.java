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
import com.joseph.thedarknessbeyond.gui.buttons.GenericCoolDownButton;
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
	private GenericCoolDownButton collectWood;
	private GenericCoolDownButton huntAnimals;
	private GenericCoolDownButton mineStone;
	private GenericCoolDownButton scavengePlants;
	private GenericJobAssignmentWindow[] assignments;
	
	public VillageScreen(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.village = new Village();
		
		/* When on the village screen, these buttons should be displayed, with the appropriate string on each button saying what they do 
		   and setting each button at certain coordinates.
		*/
		int xOff = 0;
		int yOff = 100;
		collectWood = new GenericCoolDownButton(x + xOff, y + yOff, "Collect Wood", 600, false, new IMouseReliant() {
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
		
		huntAnimals = new GenericCoolDownButton(x + xOff, y + yOff, "Hunt Animals", 800, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				Resource fur = new Resource(EnumResource.Fur, 3);
				StorageManager.getInstance().addResource(fur);
				
				Resource meat = new Resource(EnumResource.Meat, 4);
				StorageManager.getInstance().addResource(meat);
				
				EventBus.EVENT_BUS.post(new Event("Killing animals suck, but their fur and meat sure do come in handy!"));
				return true;
			}
		});
		TheDarknessBeyondEngine.getInstance().addButton(huntAnimals);
		yOff += huntAnimals.getHeight() + 20;
		
		mineStone = new GenericCoolDownButton(x + xOff, y + yOff, "Mine Stone", 1200, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				Resource stone = new Resource(EnumResource.Stone, 10);
				StorageManager.getInstance().addResource(stone);
				
				EventBus.EVENT_BUS.post(new Event("*Sweat drips down your face* I hope its useful."));
				return true;
			}
		});
		TheDarknessBeyondEngine.getInstance().addButton(mineStone);
		yOff += mineStone.getHeight() + 20;
		
		scavengePlants = new GenericCoolDownButton(x + xOff, y + yOff, "Scavenge Plants", 500, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				Resource berries = new Resource(EnumResource.Berries, 2);
				StorageManager.getInstance().addResource(berries);
				
				Resource cotton = new Resource(EnumResource.Cotton, 3);
				StorageManager.getInstance().addResource(cotton);
				
				EventBus.EVENT_BUS.post(new Event("Berries and cotton? Don't mind if I do!"));
				return true;
			}
		});
		TheDarknessBeyondEngine.getInstance().addButton(scavengePlants);
		
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
		collectWood.drawBackground(g, observer);
		huntAnimals.drawBackground(g, observer);
		mineStone.drawBackground(g, observer);
		scavengePlants.drawBackground(g, observer);
		
		for (int i = 0; i < assignments.length; i++) {
			assignments[i].drawBackground(g, observer);
		}
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		collectWood.drawUpdateableElements(g, observer);
		huntAnimals.drawUpdateableElements(g,observer);
		mineStone.drawUpdateableElements(g, observer);
		scavengePlants.drawUpdateableElements(g, observer);
		
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
		collectWood.updateUpdateableElements(deltaTime);
		huntAnimals.updateUpdateableElements(deltaTime);
		mineStone.updateUpdateableElements(deltaTime);
		scavengePlants.updateUpdateableElements(deltaTime);
		
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
		huntAnimals.hide();
		scavengePlants.hide();
		mineStone.hide();
		super.hide();
	}
	
	@Override
	public void show() {
		collectWood.show();
		huntAnimals.show();
		scavengePlants.show();
		mineStone.show();
		super.show();
	}
}
