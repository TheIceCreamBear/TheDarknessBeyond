package com.joseph.thedarknessbeyond.reference;

import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import com.joseph.thedarknessbeyond.engine.GameEngine;

/**
 * Like {@link Reference}, but for the Screen
 * 
 * @author David Santamaria
 *
 */
public class ScreenReference {
	public static final int WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	public static final int HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	public static int scale;
	public static int charWidth;
	public static int charHeight;

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
		
		charWidth = 11 *  scale;
		charHeight = 23 * scale;
	}
	
	public static Font getMapFont() {
		return (scale == 2) ? Reference.Fonts.SCALED_UP_MAP_FONT : Reference.Fonts.MAP_FONT;
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