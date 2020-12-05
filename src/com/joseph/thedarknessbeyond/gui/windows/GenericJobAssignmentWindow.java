package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumJob;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericDownButton;
import com.joseph.thedarknessbeyond.gui.buttons.GenericUpButton;
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * Generic button that assigns the jobs between the different jobs in the village
 * @author Nathan Lim
 *
 */
public class GenericJobAssignmentWindow extends Window {
	private boolean visible;
	private EnumJob job;
	private GenericUpButton upButton;
	private GenericDownButton downButton;
	
	public GenericJobAssignmentWindow(int x, int y, EnumJob j) {
		super(x, y, (int) ScreenReference.getTheFont().getStringBounds(j.toString() + ": 0000", TheDarknessBeyondEngine.getInstance().getFrc()).getWidth() + (25 * ScreenReference.scale), 40 * ScreenReference.scale, true);
		this.job = j;
		this.visible = true;
		
		if (this.job != EnumJob.Idiling) {
			this.upButton = new GenericUpButton(this.x + width - 20 * ScreenReference.scale, this.y, true, new IMouseReliant() {
				@Override
				public boolean onMouseEvent(MouseEvent e) {
					VillageScreen.getVillage().increaseJob(j);
					return false;
				}
			});
			
			this.downButton = new GenericDownButton(this.x + width - 20 * ScreenReference.scale, this.y + 20 * ScreenReference.scale, true, new IMouseReliant() {
				@Override
				public boolean onMouseEvent(MouseEvent e) {
					VillageScreen.getVillage().decreaseJob(j);
					return false;
				}
			});
			TheDarknessBeyondEngine.getInstance().addButton(upButton);
			TheDarknessBeyondEngine.getInstance().addButton(downButton);
		}
		
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
		
		g.setFont(ScreenReference.getTheFont());
		
		g.drawString(job.toString() + ": " + Village.getInstance().getJobDistrubution().get(job), x + 5 * ScreenReference.scale, y + 4 + ScreenReference.charHeight);
		
		if (this.job != EnumJob.Idiling) {
			upButton.drawUpdateableElements(g, observer);
			downButton.drawUpdateableElements(g, observer);
		}
		
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		if (this.job != EnumJob.Idiling) {
			this.downButton.updateUpdateableElements(deltaTime);
			this.upButton.updateUpdateableElements(deltaTime);
		}
	}

	@Override
	public void displayToolTip(Graphics g) {
		if (!visible) {
			return;
		}
		
	}
	
}
