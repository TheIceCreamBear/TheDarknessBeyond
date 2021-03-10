package com.joseph.thedarknessbeyond.gui;

import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * Object realization of the {@link IGuiElement} interface. Top level class, handles position and visible
 * 
 * ^^^^ that makes zero sense reading it ~3 years later (Granted i was in a rush to doccument this)
 * @author Joseph
 *
 */
public abstract class GuiElement implements IGuiElement {
	protected boolean visible;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public GuiElement(int x, int y, GuiSize size) {
		this(x * ScreenReference.scale, y * ScreenReference.scale, size.width, size.height, true);
	}
	
	public GuiElement(int x, int y, int width, int height) {
		this(x, y, width, height, false);
	}
	
	/**
	 * Constructs the GuiElement, and delegates the setting of the position and width values to
	 * {@link GuiElement#resetDimensions(int, int, int, int, boolean) GuiElement.resetDimensions()}
	 * @param x - the x pos of this element
	 * @param y - the y pos of this element
	 * @param width - the width of this element
	 * @param height - the height of this element
	 * @param scaled - if the given values are already scaled by the caller
	 */
	public GuiElement(int x, int y, int width, int height, boolean scaled) {
		this.resetDimensions(x, y, width, height, scaled);
	}
	
	/**
	 * Resets the dimensions of the element to the given values.
	 * @param x - the x pos of this element
	 * @param y - the y pos of this element
	 * @param width - the width of this element
	 * @param height - the height of this element
	 * @param scaled - if the given values are already scaled by the caller
	 */
	protected void resetDimensions(int x, int y, int width, int height, boolean scaled) {
		if (!scaled) {
			int scale = ScreenReference.scale;
			this.x = x * scale;
			this.y = y * scale;
			this.width = width * scale;
			this.height = height * scale;
			return;
		}
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void show() {
		this.visible = true;
	}
	
	public void hide() {
		this.visible = false;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
}