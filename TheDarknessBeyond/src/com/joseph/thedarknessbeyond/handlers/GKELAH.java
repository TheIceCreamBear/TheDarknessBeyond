package com.joseph.thedarknessbeyond.handlers;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.joseph.thedarknessbeyond.gameobject.map.Map;
import com.joseph.thedarknessbeyond.gui.screens.MapScreen;
import com.joseph.thedarknessbeyond.gui.windows.ConsoleWindow;
import com.joseph.thedarknessbeyond.gui.windows.PauseMenuWindow;

/**
 * GKELAH, or GlobalKeyEventHandlerAndListener, is a key event handler that listens for all
 * key events and does a specific action based on the state of the engine and the key pressed.
 * Used for KeyStroke logging for text typing or for special key that must perform a specific 
 * action the moment they are pressed as opposed to waiting for the next update cycle of the 
 * object that will be using that special key.
 * 
 * @author Joseph
 * @see InputHandler
 */
public class GKELAH implements KeyListener {
	public GKELAH() {
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if (ConsoleWindow.getInstance().isVisible()) {
			ConsoleWindow.getInstance().notifyKeyTyped(e);
			return;
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
//		if (e.getKeyCode() == KeyEvent.VK_F2) {
//			System.exit(0);
//		}
//		
//		if (e.getKeyCode() == KeyEvent.VK_F1) {
//			Reference.DEBUG_MODE = !Reference.DEBUG_MODE;
//			return;
//		}
		
		if (PauseMenuWindow.getInstance().isVisible()) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				if (PauseMenuWindow.getInstance().isLoadWindowVisible()) {
					PauseMenuWindow.getInstance().hideLoadWindow();
					return;
				}
				PauseMenuWindow.getInstance().hide();
				return;
			}
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_SLASH && !ConsoleWindow.getInstance().isVisible()) {
			ConsoleWindow.getInstance().show();
			return;
		}
		
		
		if (ConsoleWindow.getInstance().isVisible()) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				ConsoleWindow.getInstance().hide();
				return;
			} else {
				ConsoleWindow.getInstance().notifyKeyPressed(e);
				return;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			PauseMenuWindow.getInstance().show();
		}
		
		if (MapScreen.getInstance().isVisible()) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_W:
					Map.getInstance().movePlayerUp();
					break;
				case KeyEvent.VK_DOWN:
				case KeyEvent.VK_S:
					Map.getInstance().movePlayerDown();
					break;
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_A:
					Map.getInstance().movePlayerLeft();
					break;
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_D:
					Map.getInstance().movePlayerRight();
					break;
				default:
					break;
			}
			
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		
	}
	
	public Point getMouseLocation() {
		return MouseInfo.getPointerInfo().getLocation();
	}
}