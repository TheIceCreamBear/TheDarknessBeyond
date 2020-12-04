package com.joseph.thedarknessbeyond.gui;

import java.awt.Graphics;

/**
 * object realization of {@link IToolTip}
 * @author S25338732
 *
 */
public class ToolTip {
	/**
	 * a null tool tip to prevent {@link NullPointerException}s
	 */
	public static final ToolTip NULL = new ToolTip(-1, -1, new IToolTip() {
		public void draw(int x, int y, Graphics g) { }
	});
	private int x;
	private int y;
	private IToolTip tt;
	
	/**
	 * Construct a new ToolTip Object
	 * @param x - the left position of the tool tip
	 * @param y - the top position of the tool tip
	 * @param tt - the anonymous inner type tooltip using x & y as the top left of the tool tip
	 */
	public ToolTip(int x, int y, IToolTip tt) {
		this.x = x;
		this.y = y;
		this.tt = tt;
	}
	
	public void draw(Graphics g) {
		this.tt.draw(x, y, g);
	}
}