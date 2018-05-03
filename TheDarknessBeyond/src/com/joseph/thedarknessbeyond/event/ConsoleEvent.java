package com.joseph.thedarknessbeyond.event;

public class ConsoleEvent extends Event {
	public ConsoleEvent(String s) {
		super("CONSOLE:" + s);
	}
}