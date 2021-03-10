package com.joseph.thedarknessbeyond.gui;

import java.awt.Point;

import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * Extension of GuiElement that acts as a clickable button
 * @author Joseph
 * @see IGuiElement
 *
 */
public abstract class AbstractButton extends GuiElement implements IMouseReliant {	
	public AbstractButton(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
		this.visible = true;
	}
	
	public AbstractButton(int x, int y, GuiSize size) {
		super(x, y, size);
		this.visible = true;
	}

	@Override
	public boolean isMouseInElement() {
		Point p = ScreenReference.getMouseLocation();
		if (p == null) {
			return false;
		}
		return p.x >= x && p.x <= (x + width) && p.y >= y && p.y <= (y + height);
	}
}