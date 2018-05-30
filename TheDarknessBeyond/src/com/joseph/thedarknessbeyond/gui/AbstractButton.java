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
	
	public AbstractButton(int x, int y, int width, int height, boolean scaled) {
		super(x, y, width, height, scaled);
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