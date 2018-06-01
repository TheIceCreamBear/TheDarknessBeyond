package com.joseph.thedarknessbeyond.interfaces;

import java.awt.event.MouseEvent;

/**
 * Specifies that the implementing object relies on mouse input
 * @author Joseph
 *
 */
public interface IMouseReliant {
	/**
	 * called when a mouse even occours
	 * @param e - the event
	 * @return a boolean weather or not this object used the mouse event
	 */
	public boolean onMouseEvent(MouseEvent e);
}