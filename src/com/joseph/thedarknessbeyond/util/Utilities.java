package com.joseph.thedarknessbeyond.util;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gui.GuiSize;
import com.joseph.thedarknessbeyond.reference.Reference;

/**
 * collection of util methods
 * @author Joseph
 *
 */
public class Utilities {
	/**
	 * creates a string representation of an {@code ArrayList<character>}
	 * @param list - text to take in
	 * @return - a string created from the chars in list
	 */
	public static String getStringRepresentation(ArrayList<Character> list) {
		StringBuilder builder = new StringBuilder(list.size());
		for (Character ch : list) {
			builder.append(ch);
		}
		return builder.toString();
	}
	
	/**
	 * Method to get the gui size of a specific string, useful for the gui classes
	 * that determine their size based on a string passed in or generated during construction
	 * @param s - the string to base the size of the gui off of
	 * @return a representation of the size of the gui in an instance of GuiSize
	 */
	public static GuiSize getGuiSizeFromStringScalled(String s) {
		GuiSize absSize = getGuiSizeFromStringAbs(s);
		return new GuiSize(absSize.width * 2, absSize.height * 2);
	}
	
	/**
	 * Method to get the gui size of a specific string, useful for the gui classes
	 * that determine their size based on a string passed in or generated during construction.
	 * This method returns the size with scale = 1
	 * @param s - the string to base the size of the gui off of
	 * @return a representation of the size of the gui in an instance of GuiSize
	 */
	public static GuiSize getGuiSizeFromStringAbs(String s) {
		Font current = Reference.Fonts.DEFAULT_FONT;
		FontRenderContext frc = TheDarknessBeyondEngine.getInstance().getFrc();
		Rectangle2D bounds = current.getStringBounds(s, frc);
		
		int width = ((int) bounds.getWidth()) + 10;
		int height = ((int) bounds.getHeight()) + 2;
		GuiSize size = new GuiSize(width, height);
		System.out.println(s + "=" + size);
		return size;
	}
}