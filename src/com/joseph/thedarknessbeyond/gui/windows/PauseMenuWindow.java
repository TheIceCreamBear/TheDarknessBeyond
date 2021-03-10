package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.io.File;

import javax.swing.JOptionPane;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.util.FileSaveSystem;

/**
 * The window that pauses the game and has the buttons associated with pausing a game
 * @author Joseph
 *
 */
public class PauseMenuWindow extends Window {
	private static PauseMenuWindow instance;
	
	private boolean visible;
	private Font font;
	private FontRenderContext frc;
	private GenericSelectableButton resume;
	private GenericSelectableButton load;
	private GenericSelectableButton save;
	private GenericSelectableButton exit;
	
	private LoadGameWindow lgw;
	
	private final String headder = "Pause Menu:";
	
	public PauseMenuWindow() {
		super((ScreenReference.WIDTH / 2 - (75 * ScreenReference.scale)) / ScreenReference.scale, (ScreenReference.HEIGHT / 2 - (85 * ScreenReference.scale)) / ScreenReference.scale, 150, 170, false);
		this.visible = false;
		this.frc = TheDarknessBeyondEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.lgw = new LoadGameWindow(FileSaveSystem.getPossibleLoadableFiles());
		
		Rectangle2D r = font.getStringBounds(this.headder, frc);
		int yOff = (int) r.getHeight() + 10 * ScreenReference.scale;
		int xOff = 5 * ScreenReference.scale;
		
		this.resume = new GenericSelectableButton(x + xOff, y + yOff, "Resume Game", true, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				// TODO BUG BUG BUG: mouse stays default when menu brought back up and mouse is in resume
				if (!PauseMenuWindow.this.visible) {
					return false;
				}
				TheDarknessBeyondEngine.getInstance().setDefaultMouse();
				PauseMenuWindow.this.hide();
				return true;
			}
		});
		yOff += this.resume.getHeight() + 10 * ScreenReference.scale;
		
		this.load = new GenericSelectableButton(x + xOff, y + yOff, "Load Game", true, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				if (!PauseMenuWindow.this.visible) {
					return false;
				}
				lgw.show();
				return true;
			}
		});
		yOff += this.load.getHeight() + 10 * ScreenReference.scale;
		
		this.save = new GenericSelectableButton(x + xOff, y + yOff, "Save Game", true, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				if (!PauseMenuWindow.this.visible) {
					return false;
				}
				try {
					String s = JOptionPane.showInputDialog(null, "What would you like to name your save file?");
					FileSaveSystem.saveGame(s);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return true;
			}
		});
		yOff += this.save.getHeight() + 10 * ScreenReference.scale;
		
		this.exit = new GenericSelectableButton(x + xOff, y + yOff, "Exit Game", true, false, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				if (!PauseMenuWindow.this.visible) {
					return false;
				}
				try {
					FileSaveSystem.autoSaveGame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.exit(0);
				return true;
			}
		});
		yOff += this.exit.getHeight() + 10 * ScreenReference.scale;
		
		TheDarknessBeyondEngine.getInstance().addButton(resume);
		TheDarknessBeyondEngine.getInstance().addButton(load);
		TheDarknessBeyondEngine.getInstance().addButton(save);
		TheDarknessBeyondEngine.getInstance().addButton(exit);
		
		
		instance = this;
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		g.setColor(Color.WHITE);
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(this.headder, frc);
		int yOff = (int) r.getHeight();
		int xOff = 5;
		g.drawString(this.headder, x + xOff, y + yOff);
		
		this.resume.drawBackground(g, observer);
		this.load.drawBackground(g, observer);
		this.save.drawBackground(g, observer);
		this.exit.drawBackground(g, observer);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		this.resume.drawUpdateableElements(g, observer);
		this.load.drawUpdateableElements(g, observer);
		this.save.drawUpdateableElements(g, observer);
		this.exit.drawUpdateableElements(g, observer);
		this.lgw.drawBackground(g, observer);
		this.lgw.drawUpdateableElements(g, observer);
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		this.resume.updateUpdateableElements(deltaTime);
		this.load.updateUpdateableElements(deltaTime);
		this.save.updateUpdateableElements(deltaTime);
		this.exit.updateUpdateableElements(deltaTime);
		this.lgw.updateUpdateableElements(deltaTime);
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	public boolean isVisible() {
		return this.visible;
	}

	public void show() {
		this.visible = true;
	}
	
	public void hide() {
		this.visible = false;
	}
	
	public boolean isLoadWindowVisible() {
		return this.lgw.isVisible();
	}
	
	public void hideLoadWindow() {
		this.lgw.hide();
	}
	
	public void notifyNewFiles(File[] f) {
		this.lgw = new LoadGameWindow(f);
	}
	
	public static PauseMenuWindow getInstance() {
		return instance;
	}
}