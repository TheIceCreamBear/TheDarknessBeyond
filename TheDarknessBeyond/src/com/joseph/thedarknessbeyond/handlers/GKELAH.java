package com.joseph.thedarknessbeyond.handlers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * GKELAH, or GlobalKeyEventHandlerAndListener, is a key event handler that listens for all
 * key events and does a specific action based on the state of the engine and the key pressed.
 * Used for KeyStroke logging for text typing or for special key that must perform a specific 
 * action the moment they are pressed as opposed to waiting for the next update cycle of the 
 * object that will be using that special key.
 * 
 * <p>For legacy input, use {@link InputHandler InputHandler}.
 * 
 * @author Joseph
 * @see InputHandler
 */
public class GKELAH implements KeyListener {
	public GKELAH() {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public Point getMouseLocation() {
		return MouseInfo.getPointerInfo().getLocation();
	}
}