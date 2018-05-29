package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;

import javax.swing.JOptionPane;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericSelectableButton;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;
import com.joseph.thedarknessbeyond.util.FileSaveSystem;

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
		super((ScreenRefrence.WIDTH / 2 - (75 * ScreenRefrence.scale)) / ScreenRefrence.scale, (ScreenRefrence.HEIGHT / 2 - (85 * ScreenRefrence.scale)) / ScreenRefrence.scale, 150, 170, false);
		this.visible = false;
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenRefrence.getTheFont();
		this.lgw = new LoadGameWindow(FileSaveSystem.getPossibleLoadableFiles());
		
		Rectangle2D r = font.getStringBounds(this.headder, frc);
		int yOff = (int) r.getHeight() + 10 * ScreenRefrence.scale;
		int xOff = 5 * ScreenRefrence.scale;
		
		this.resume = new GenericSelectableButton(x + xOff, y + yOff, "Resume Game", true, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO BUG BUG BUG: mouse stays default when menu brought back up and mouse is in resume
				GameEngine.getInstance().setDefaultMouse();
				PauseMenuWindow.this.hide();
			}
		});
		yOff += this.resume.getHeight0() + 10 * ScreenRefrence.scale;
		
		this.load = new GenericSelectableButton(x + xOff, y + yOff, "Load Game", true, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				lgw.show();
			}
		});
		yOff += this.load.getHeight0() + 10 * ScreenRefrence.scale;
		
		this.save = new GenericSelectableButton(x + xOff, y + yOff, "Save Game", true, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String s = JOptionPane.showInputDialog(null, "What would you like to name your save file?");
					FileSaveSystem.saveGame(s);
					throw new Exception();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		yOff += this.save.getHeight0() + 10 * ScreenRefrence.scale;
		
		this.exit = new GenericSelectableButton(x + xOff, y + yOff, "Exit Game", true, false, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					FileSaveSystem.autoSaveGame();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		});
		yOff += this.exit.getHeight0() + 10 * ScreenRefrence.scale;
		
		GameEngine.getInstance().addButton(resume);
		GameEngine.getInstance().addButton(load);
		GameEngine.getInstance().addButton(save);
		GameEngine.getInstance().addButton(exit);
		
		
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
	
	public static PauseMenuWindow getInstance() {
		return instance;
	}
}