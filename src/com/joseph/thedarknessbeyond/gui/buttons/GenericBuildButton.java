package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.gui.GuiSize;
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.util.Utilities;

/**
 * A button the is used to build the given building. It is a generic and allocates its size based of the size of the string it will draw
 * @author Nathan
 *
 */
public class GenericBuildButton extends AbstractButton {
	private FontRenderContext frc;
	private Font font;
	private EnumBuilding b;
	private int toolTipHeight;
	private int toolTipWidth;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	
	public GenericBuildButton(int x, int y, EnumBuilding b) {
		super(x, y, Utilities.getGuiSizeFromStringScalled("Build " + b.toString(), true));
		this.b = b;
		this.frc = TheDarknessBeyondEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();

		Resource[] r = b.getCost();
		int longestIndex = 0;
		int longestLengt = r[0].toString().length();
		for (int i = 1; i < r.length; i++) {
			if (longestLengt < r[i].toString().length()) {
				longestLengt = r[i].toString().length();
				longestIndex = i;
			}
		}
		GuiSize size = Utilities.getGuiSizeFromStringScalled(r[longestIndex].toString(), true); 
		this.toolTipWidth = size.width;
		this.toolTipHeight = (size.height) * r.length;
	}
	

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		g.fillRect(x, y, width, height);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(b.toString(), frc);
		int yOff = (int) Math.abs(r.getY()) + 2 * ScreenReference.scale;
		int xOff = 5;
		g.drawString("Build " + b.toString(), x + xOff, y + yOff);

		// draw bounding box if debug mode is on
		if (Reference.DEBUG_MODE) {
			Color save = g.getColor();
			g.setColor(Color.MAGENTA);
			g.drawRect(x, y, width, height);
			g.setColor(save);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		this.mouseInSelfPrevious = this.mouseInSelf;
		mouseInSelf = isMouseInElement();
		if (this.mouseInSelfPrevious != this.mouseInSelf) {
			if (mouseInSelf) {
				TheDarknessBeyondEngine.getInstance().setSelectMouse();
			} else {
				TheDarknessBeyondEngine.getInstance().setDefaultMouse();
			}
		}
		if (this.mouseInSelf) {
			this.font = ScreenReference.getUnderlinedFont();
		} else {
			this.font = ScreenReference.getTheFont();
		}
	}

	@Override
	public void displayToolTip(Graphics g) {
		if (!visible) {
			return;
		}
		
		if (!isMouseInElement()) {
			return;
		}
		
		// make sure that we use the non underlined font for ToolTips
		Font font = ScreenReference.getTheFont();
		Font save = g.getFont();

		g.setFont(font);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 5, y + height + 1, toolTipWidth, toolTipHeight);
		g.setColor(Color.WHITE);
		g.drawRect(x + 5, y + height + 1, toolTipWidth, toolTipHeight);
		g.setColor(Color.BLACK);
		Resource[] r = b.getCost();
		GuiSize curStr = Utilities.getGuiSizeFromStringScalled("", false);
		int yOff = curStr.height - 2 * ScreenReference.scale;
		int xOff = 10;
		for (int i = 0; i < r.length; i++) {
			g.drawString(r[i].toString(), x + xOff, y + height + yOff);
			yOff += curStr.height;
		}
		
		// give back the saved font
		g.setFont(save);
	}

	@Override
	public boolean onMouseEvent(MouseEvent e) {
		if (!visible) {
			return false;
		}
		
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			if (VillageScreen.getVillage().buildBuilding(b)) {
				EventBus.EVENT_BUS.post(new Event(("Built a " + b.toString()) + "."));
			} else {
				EventBus.EVENT_BUS.post(new Event("More resources needed!"));
			}
			
			return true;
		}
		
		return false;
	}
	
}