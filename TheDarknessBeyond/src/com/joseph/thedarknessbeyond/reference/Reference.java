package com.joseph.thedarknessbeyond.reference;

import java.awt.Color;
import java.awt.Font;

/**
 * Commonly used Objects or primitives stored in one location for quick Refrence
 * 
 * @author Joseph
 *
 */
public class Reference {
	public static final String DIRPREFIX = System.getProperty("user.dir");
	public static boolean DEBUG_MODE = true;
	public static boolean HARD_CORE_DEBUG_MODE = false;
	
	public static class Colors {
		public static final Color CURSOR_COLOR = new Color(96, 96, 96);
	}
	
	public static class Fonts {
		public static final Font DEFAULT_FONT = new Font("Consolas", 0, 20);
		public static final Font SCALED_UP_FONT = new Font("Consolas", 0, 40);
	}
}