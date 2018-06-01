package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.gameobject.Village.EnumJob;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

//element will contain a string, two buttons
//string will be the job name; assigned via constructor
//Up-Button will be a GenericUpButton?
//Down-Button will be a GenericDownButton?
//Both buttons will have the polygon assignment
//Constructor: takes in EnumJob
//On mouse click, they will attempt to call Village.getInstance().increaseJob()
//Define increaseJob();
/*	public void increaseJob(EnumJob j) {
 * 		(look at functionality of buildBuilding gets count from hashmap, increments)
 * 		Check idling people, if no idling, cannot increase job
 * 	}
 */
//decreaseJob(): opposite of increaseJob()
public class GenericJobAssignmentWindow extends Window {
	private boolean visible;
	private EnumJob job;
	
	public GenericJobAssignmentWindow(int x, int y, int width, EnumJob j) {
		super(x, y, width, ScreenReference.charHeight + (10 * ScreenReference.scale));
		this.job = j;
		this.visible = true;
		// TODO Make width dynamic
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		
		g.drawString(job.toString(), x + 5 * ScreenReference.scale, y + ScreenReference.charHeight);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
	}

	@Override
	public void displayToolTip(Graphics g) {
		if (!visible) {
			return;
		}
		
	}
	
}
