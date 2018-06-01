package com.joseph.thedarknessbeyond.util;

import java.util.ArrayList;

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
}