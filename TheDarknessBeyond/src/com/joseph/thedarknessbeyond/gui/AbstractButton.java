package com.joseph.thedarknessbeyond.gui;

import java.awt.Point;

import com.joseph.thedarknessbeyond.event.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * Extension of JButton that makes all paint functions <code>NO-OP</code>, and implements {@link IGuiElement IGuiOverlay}
 * to allow for painting of the button using the methods available in the engine.
 * @author Joseph
 * @see IGuiElement
 *
 */
public abstract class AbstractButton extends GuiElement implements IMouseReliant {
	protected boolean visible;
	
	public AbstractButton(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
		this.visible = false;
	}

	@Override
	public boolean isMouseInElement() {
		Point p = ScreenReference.getMouseLocation();
		if (p == null) {
			return false;
		}
		return p.x >= x && p.x <= (x + width) && p.y >= y && p.y <= (y + height);
	}
	
	public void hide() {
		this.visible = false;
	}
	
	public void show() {
		this.visible = true;
	}
	
	public boolean isVisible() {
		return this.visible;
	}
}