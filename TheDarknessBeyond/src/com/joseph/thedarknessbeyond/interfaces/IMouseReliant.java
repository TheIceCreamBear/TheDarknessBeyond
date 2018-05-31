package com.joseph.thedarknessbeyond.interfaces;
// TODO move to com.joseph.thedarknessbeyond.interfaces

import java.awt.event.MouseEvent;

public interface IMouseReliant {
	/**
	 * 
	 * @param e
	 * @return a boolean weather or not this object used the mouse event
	 */
	public boolean onMouseEvent(MouseEvent e);
}