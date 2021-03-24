package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * a button that is used to decrement some variable
 * @author Nathan
 *
 */
public class GenericDownButton extends AbstractButton {
	private static final Polygon TRIANGLE = new Polygon(new int[] {10, 20, 0}, new int[] {20, 0, 0}, 3);
	private static final Polygon TRIANGLE2 = new Polygon(new int[] {20, 40, 0}, new int[] {40, 0, 0}, 3);
	private Polygon triangle;
	private IMouseReliant imr;
	private boolean mouseInSelf;
	private boolean mouseInSelfPrevious;

	public GenericDownButton(int x, int y, boolean scaled, IMouseReliant imr) {
		super(x, y, 20 * ScreenReference.scale, 20 * ScreenReference.scale, scaled);
		if (ScreenReference.scale == 2) {
			triangle = new Polygon(TRIANGLE2.xpoints, TRIANGLE2.ypoints, TRIANGLE2.npoints);			
		} else {
			triangle = new Polygon(TRIANGLE.xpoints, TRIANGLE.ypoints, TRIANGLE.npoints);
		}
		triangle.translate(x, y);
		this.imr = imr;
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		g.drawPolygon(triangle);

		// draw bounding box if debug mode is on
		if (Reference.DEBUG_MODE) {
			Color save = g.getColor();
			g.setColor(Color.MAGENTA);
			g.drawRect(x, y, width, height);
			g.setColor(save);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		this.mouseInSelfPrevious = this.mouseInSelf;
		this.mouseInSelf = isMouseInElement();
		if (this.mouseInSelfPrevious != this.mouseInSelf) {
			if (mouseInSelf) {
				TheDarknessBeyondEngine.getInstance().setSelectMouse();
			} else {
				TheDarknessBeyondEngine.getInstance().setDefaultMouse();
			}
		}
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	@Override
	public boolean onMouseEvent(MouseEvent e) {
		if (!visible) {
			return false;
		}
		
		int x = e.getX();
		int y = e.getY();
		// Check mouse is in element on click
		if (x >= this.x && x <= (this.x +this.width) && y >= this.y && y <= (this.y +this.height)) {
			this.imr.onMouseEvent(e);
			return true;
		}
	
		return false;	
	}
	
}
