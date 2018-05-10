package com.joseph.thedarknessbeyond.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.joseph.thedarknessbeyond.reference.ScreenRefrence;

/**
 * Extension of JButton that makes all paint functions <code>NO-OP</code>, and implements {@link IGuiElement IGuiOverlay}
 * to allow for painting of the button using the methods available in the engine.
 * @author Joseph
 * @see IGuiElement
 *
 */
public abstract class AbstractButton extends JButton implements IGuiElement, ActionListener {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	public AbstractButton(int x, int y, int width, int height) {
		if (ScreenRefrence.scale == 2) {
			this.x = x * 2;
			this.y = y * 2;
			this.width = width * 2;
			this.height = height * 2;
			setBounds(x * 2, y * 2, width * 2, height * 2);
		} else {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			setBounds(x, y, width, height);
		}
	}

	@Override
	protected void paintBorder(Graphics g) {
	}
	
	@Override
	public void paint(Graphics g) {
	}
	
	@Override
	protected void paintChildren(Graphics g) {
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	}
	
	@Override
	public void paintImmediately(int x, int y, int w, int h) {
	}
	
	@Override
	public void paintImmediately(Rectangle r) {
	}
	
	@Override
	public void paintComponents(Graphics g) {
	}
	
	@Override
	public void paintAll(Graphics g) {
	}

	@Override
	public boolean isMouseInElement() {
		Point p = ScreenRefrence.getMouseLocation();
		if (p == null) {
			return false;
		}
		return p.x > x && p.x < (x + width) && p.y > y && p.y < (y + height);
	}
	
}