package com.joseph.thedarknessbeyond.handlers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import com.joseph.thedarknessbeyond.event.IMouseReliant;

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
	
	public void removeMouseReliant(IMouseReliant imr) {
		synchronized (reliants) {
			this.reliants.remove(imr);
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