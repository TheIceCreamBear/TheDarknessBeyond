package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.gui.ToolTip;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

/**
 * collects wood and adds it to the stores
 * @author Justin
 *
 */
public class CollectWood extends AbstractButton {
	private FontRenderContext frc;
	private Font font;
	private String text;
	private ToolTip tt;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;
	private boolean selected;
	private StorageManager std;
	private int cooldown;
	private final int maxCooldown;
	
	public CollectWood(int x, int y, String s, boolean scaled) {
		this(x, y, s, scaled,null);
	}
	
	public CollectWood(int x, int y, String s, boolean scaled, ToolTip tt) {
		super(x, y, (int) ScreenReference.getTheFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getWidth() + (5 * ScreenReference.scale), (int) ScreenReference.getTheFont().getStringBounds(s, GameEngine.getInstance().getFrc()).getHeight() + (2 * ScreenReference.scale), scaled);
		this.text = s;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.std = StorageManager.getInstance();
		
		// Set a cooldown (timer)  of 600 seconds on the collect wood button
		
		this.cooldown = 0;
		this.maxCooldown = 600;
		
		if (tt == null) {
			this.tt = ToolTip.NULL;
		} else {
			this.tt = tt;
		}
	}

	/* If a button was pressed and currently on cooldown, a gray progress bar is displayed behind the button, showing how long is left until you can click
	   on the next button
	*/
	
	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		if (this.cooldown > 0) {
			g.setColor(Color.DARK_GRAY);
			g.drawRect(x , y , width, height);
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(x + 1, y + 1, cooldown * (width - 2) / maxCooldown,  height - 1);
			
		} else {
		
			g.setColor(Color.WHITE);
			g.drawRect(x, y, width, height);
			g.setColor(Color.DARK_GRAY);
			g.fillRect(x + 1, y + 1, width - 1, height - 1);
			
		}
	
	}
	
	/*Draws any elements in the overlay that might change each tick. In the drawUpdateableElements method, we are creating our shape for the button
	  and setting the color to white or gray, depending on whether or not the button was clicked and is on cooldown.
	*/
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		if (this.cooldown > 0) {
			g.setColor(Color.DARK_GRAY);
		}
		else {
			g.setColor(Color.WHITE);
		}
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(text, frc);
		int yOff = (int) Math.abs(r.getY()) + 2 * ScreenReference.scale;
		int xOff = 5;
		g.drawString(text, x + xOff, y + yOff);
		
		if (isMouseInElement()) {
			this.displayToolTip(g);
			
		}
	}
	
	/* This method makes sure that the button cooldown is decreasing each second. Also, it underlines the elements that are being hovered over by the
	   user's mouse, helping them recognize that they are on it.
	
	 */
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		if (this.cooldown > 0) {
			this.cooldown--;
			return;
		}
		
		this.mouseInSelfPrevious = this.mouseInSelf;
		mouseInSelf = isMouseInElement();
		if (this.mouseInSelfPrevious != this.mouseInSelf) {
			if (mouseInSelf) {
				GameEngine.getInstance().setSelectMouse();
			} else {
				GameEngine.getInstance().setDefaultMouse();
			}
		}
		if (this.mouseInSelf || this.selected) {
			this.font = ScreenReference.getUnderlinedFont();
		} else {
			this.font = ScreenReference.getTheFont();
		}
	}
	
	// Can disregard this, as tooltip is not needed for the CollectWoodButton
	
	@Override
	public void displayToolTip(Graphics g) {
		tt.draw(g);
	}
	
	/* While the CollectWood button is clicked, add 20 wood to our storage, and set the cooldown to 600 seconds before being able to be clicked again.
	   The EventBus method displays a string in the event tab of the game (showing an interesting description of what happens when the button is clicked.)
		
	 */
	
	@Override
	public boolean onMouseEvent(MouseEvent e) {
		if (!visible || cooldown > 0) {
			return false;
		}
		
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			GameEngine.getInstance().releaseFocous();
			GameEngine.getInstance().setDefaultMouse();
			this.mouseInSelf = false;
	
			Resource wood = new Resource(EnumResource.Wood, 20);
			std.addResource(wood);
			
			EventBus.EVENT_BUS.post(new Event("Cutting down trees and picking up sticks seems to be pretty useful."));
			
			this.cooldown = maxCooldown;
			
			return true;
		}	
		return false;
	}
	
	public void deslecet() {
		this.selected = false;
	}
	
	public void select() {
		this.selected = true;
	}
}