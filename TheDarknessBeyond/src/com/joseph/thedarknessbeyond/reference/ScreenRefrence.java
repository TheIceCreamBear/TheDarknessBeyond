package com.joseph.thedarknessbeyond.reference;

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
//		System.err.println(Toolkit.getDefaultToolkit().getScreenResolution());
//		System.err.println(Toolkit.getDefaultToolkit().getScreenSize());
		if (Toolkit.getDefaultToolkit().getScreenResolution() > 100) {
			scale = 2;
		} else {
			scale = 1;
		}
	}
	
	public static Point getMouseLocation() {
		return GameEngine.getInstance().getMouseLocation();
	}
}