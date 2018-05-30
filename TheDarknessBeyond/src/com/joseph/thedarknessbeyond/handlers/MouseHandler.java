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
		this.reliants.add(imr);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		for (IMouseReliant imr : reliants) {
			imr.onMouseEvent(e);
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