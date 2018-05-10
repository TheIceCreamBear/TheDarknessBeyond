package com.joseph.thedarknessbeyond.reference;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import com.joseph.thedarknessbeyond.engine.GameEngine;

/**
 * Like Reference.java, but for the Screen
 * 
 * @author David Santamaria
 *
 */
public class ScreenRefrence {
	public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static int scale;

//	public static final int width = 1200;
//	public static final int height = 800;
	
	public static void doScreenCalc() {
		if (Reference.DEBUG_MODE) {
			System.err.println(Toolkit.getDefaultToolkit().getScreenResolution());
			System.err.println(Toolkit.getDefaultToolkit().getScreenSize());
		}
		
		if (Toolkit.getDefaultToolkit().getScreenResolution() > 100) {
			scale = 2;
		} else {
			scale = 1;
		}
	}
	
	public static Font getTheFont() {
		return (scale == 2) ? Reference.Fonts.SCALED_UP_FONT : Reference.Fonts.DEFAULT_FONT;
	}
	
	public static Font getUnderlinedFont() {
		return (scale == 2) ? Reference.Fonts.SCALED_UP_UNDERLINED_FONT : Reference.Fonts.DEFAULT_UNDERLINED_FONT;
	}
	
	public static Point getMouseLocation() {
		return GameEngine.getInstance().getMouseLocation();
	}
}