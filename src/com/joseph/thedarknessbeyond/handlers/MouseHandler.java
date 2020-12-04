package com.joseph.thedarknessbeyond.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;

/**
 * Like {@link GKELAH} but for mouse events. Use to distribute the mouse events
 * to all of the objects that are registered to rely on mouse events
 * 
 * @author Joseph
 *
 */
public class MouseHandler implements MouseListener {
	private ArrayList<IMouseReliant> reliants;
	
	public MouseHandler() {
		this.reliants = new ArrayList<IMouseReliant>();
	}
	
	public void registerMouseReliant(IMouseReliant imr) {
		synchronized (reliants) {
			this.reliants.add(imr);
		}
	}
	
	public boolean removeMouseReliant(IMouseReliant imr) {
		synchronized (reliants) {
			return this.reliants.remove(imr);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		synchronized (reliants) {
			for (IMouseReliant imr : reliants) {
				if (imr.onMouseEvent(e)) {
					break;
				}
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// NO-OP
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// NO-OP
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// NO-OP
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// NO-OP
	}
}