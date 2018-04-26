package com.joseph.thedarknessbeyond.util;

import java.util.ArrayList;

public class Utilities {
	
	public static void saveGame() {
		
	}
	
	public static void loadGame() {
		
	}
	
	public static String getStringRepresentation(ArrayList<Character> list) {
		StringBuilder builder = new StringBuilder(list.size());
		for (Character ch : list) {
			builder.append(ch);
		}
		return builder.toString();
	}
}