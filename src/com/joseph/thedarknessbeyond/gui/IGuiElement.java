package com.joseph.thedarknessbeyond.gui;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 * Interface defining behavior for visual elements of the game that are not
 * specifically a {@link com.joseph.thedarknessbeyond.gameobject.GameObject GameObject}
 * but are updateable and are drawable. Most used context would be in the area of menus.
 * @author Joseph
 *
 */
public interface IGuiElement {
	/**
	 * Draws the background of this overlay. The background should be the outer most elements of the 
	 * overlay and the most basic. It should also be the farthest back elements of the background. Each 
	 * tick, the background should be drawn over the old background via this method to give the illusion
	 * that the overlay hasn't changed and shouldn't interfere with the game behind it (unless part of the overlay)
	 * as the game behind it may not be redrawn every tick while the overlay is open as to preserve processing 
	 * resources. The elements drawn by this method shouldn't change position if the draw directly over the game.
	 * @param g
	 * @param observer
	 */
	public void drawBackground(Graphics g, ImageObserver observer);
	
	/**
	 * Draws those elements of the overlay that could possible change each tick. These elements will be 
	 * drawn over the background of the overlay and should not exceed the size of the overlay.
	 * @param g
	 * @param observer
	 */
	public void drawUpdateableElements(Graphics g, ImageObserver observer);
	
	/** 
	 * Updates any elements of this overlay that could possibly change every tick.
	 * @param deltaTime
	 */
	public void updateUpdateableElements(double deltaTime);
	
	/**
	 * Method to check if the mouse pointer is in the executing object. 
	 * @return True if the mouse pointer is in the GuiElement
	 */
	public boolean isMouseInElement();
	
	/**
	 * This method is called when the mouse pointer is inside the executing object. It should define the look of the tool tip.
	 * 
	 * <p>Tool tips are to be brief and informative.
	 */
	public void displayToolTip(Graphics g);
}