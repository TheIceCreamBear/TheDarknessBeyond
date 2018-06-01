/* Nathan Lim
 */

package com.joseph.thedarknessbeyond.gui.buttons;

import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gui.AbstractButton;
import com.joseph.thedarknessbeyond.gui.screens.VillageScreen;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;

public class GenericUpButton extends AbstractButton {
	private static final Polygon TRIANGLE = new Polygon(new int[] {10, 20, 0}, new int[] {0, 20, 20}, 3);
	private Polygon triangle;
	private IMouseReliant imr;
	
	public GenericUpButton(int x, int y, int width, int height, boolean scaled, IMouseReliant imr) {
		super(x, y, 20, 20, scaled);
		triangle = new Polygon(TRIANGLE.xpoints, TRIANGLE.ypoints, TRIANGLE.npoints);
		triangle.translate(x + width - 20, y);
		this.imr = imr;
		
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		g.drawPolygon(triangle);
		
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayToolTip(Graphics g) {
		// TODO Auto-generated method stub
		
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
			imr.onMouseEvent(e);
			
			return true;
		}
		
		return false;
	}
	
}
