package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.event.ConsoleEvent;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gameobject.Village;
import com.joseph.thedarknessbeyond.gameobject.Village.EnumBuilding;
import com.joseph.thedarknessbeyond.gameobject.map.Enemy;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.screens.MapScreen;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenReference;
import com.joseph.thedarknessbeyond.util.FileSaveSystem;
import com.joseph.thedarknessbeyond.util.Utilities;

/**
 * the window that acts as a console for command input
 * @author Joseph
 *
 */
public class ConsoleWindow extends Window {
	private ArrayList<String> previousCommands;
	private ArrayList<Character> text;
	private ArrayList<Character> previousText;
	private FontRenderContext frc;
	private Font font;
	private boolean previousEdited;
	private boolean cursor;
	private boolean visible;
	private int cursorTick;
	private int previousIndex;
	private int cursorIndex;
	
	private static ConsoleWindow instance;
	
	public ConsoleWindow() {
		this((ScreenReference.WIDTH - (500 * ScreenReference.scale)) / ScreenReference.scale, 100, 500, 300);
	}
	
	public ConsoleWindow(int i) {
		this(0, (ScreenReference.HEIGHT - ((40) * ScreenReference.scale)) / ScreenReference.scale, ScreenReference.WIDTH / ScreenReference.scale, 30);
	}
	
	public ConsoleWindow(int x, int y, int width, int height) {
		super(x, y, width, height);
		this.previousCommands = new ArrayList<String>();
		this.previousCommands.add("");
		this.text = new ArrayList<Character>();
		this.previousText = new ArrayList<Character>();
		this.cursor = false;
		this.previousEdited = false;
		this.cursorTick = 0;
		this.cursorIndex = 0;
		this.visible = false;
		this.frc = TheDarknessBeyondEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		
		instance = this;
	}
	
	public boolean isVisible() {
		return this.visible;
	}

	public void show() {
		this.cursor = false;
		this.cursorTick = 0;
		this.visible = true;
	}
	
	public void hide() {
		this.visible = false;
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(font);
		String s;
		if (previousIndex == 0) {
			s = Utilities.getStringRepresentation(text);
		} else {
			if (previousEdited) {
				s = Utilities.getStringRepresentation(previousText);
			} else {
				s = previousCommands.get(previousCommands.size() - this.previousIndex);
			}
		}

		Rectangle2D r = this.font.getStringBounds(s, frc);
		int yOffset = (int) r.getHeight();
		
		g.drawString(s, this.x, this.y + yOffset);
		if (this.cursor) {
			String offsetStr = s.substring(0, this.cursorIndex);
			int xOffset = (int) font.getStringBounds(offsetStr, frc).getWidth() + 1;
			g.setColor(Reference.Colors.CURSOR_COLOR);
			g.fillRect(this.x + xOffset, this.y + 5, 2, yOffset);
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		if (this.cursorTick > 0) {
			this.cursorTick--;
		}
		
		if (this.cursorTick == 0) {
			this.cursor = !this.cursor;
			this.cursorTick = 20;
		}
	}
	
	@Override
	public boolean isMouseInElement() {
		return false;
	}
	
	@Override
	public void displayToolTip(Graphics g) {
	}
	
	public void notifyKeyTyped(KeyEvent e) {
		if (!visible) {
			return;
		}
		
		// permantly disable the back quote from showing up in the console
		if (e.getKeyChar() == KeyEvent.VK_BACK_QUOTE) {
			return;
		}
		
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			return;
		}
		
		if (e.getID() == KeyEvent.KEY_TYPED) {
			if (this.previousIndex == 0) {
				char temp = e.getKeyChar();
				if (temp == KeyEvent.VK_BACK_SPACE) {
					if (cursorIndex - 1 == 0) {
						hide();
						return;
					}
					text.remove(cursorIndex - 1);
					this.cursorIndex--;
					return;
				} else if (temp == KeyEvent.VK_DELETE) {
					if (cursorIndex == this.text.size()) {
						return;
					}
					text.remove(cursorIndex);
					return;
				}
				text.add(cursorIndex, temp);
				this.cursorIndex++;
			} else {
				if (!previousEdited) {
					previousEdited = true;
				}
				
				char temp = e.getKeyChar();
				if ((int) temp == KeyEvent.VK_BACK_SPACE) {
					if (cursorIndex - 1 == 0) {
						return;
					}
					previousText.remove(cursorIndex - 1);
					this.cursorIndex--;
					return;
				} else if (temp == KeyEvent.VK_DELETE) {
					if (cursorIndex == this.previousText.size()) {
						return;
					}
					previousText.remove(cursorIndex);
					return;
				}
				previousText.add(cursorIndex, temp);
				this.cursorIndex++;
			}
			
		}
	}
	
	public void notifyKeyPressed(KeyEvent e) {
		if (!visible) {
			return;
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			up();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			down();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			left();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			right();
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			execute();
		}
	}
	
	private void execute() {
		String s;
		if (previousIndex == 0) {
			s = Utilities.getStringRepresentation(text);
			this.previousCommands.add(s);
		} else {
			if (previousEdited) {
				s = Utilities.getStringRepresentation(previousText);
				this.previousCommands.add(s);
				this.previousText.clear();
			} else {
				s = previousCommands.get(previousCommands.size() - this.previousIndex);
				
			}
			
			this.previousIndex = 0;
		}
		
		this.text.clear();
		this.text.add('/');
		this.cursorIndex = 1;
		
		command(s);
	}
	
	@SuppressWarnings("deprecation")
	private void command(String s) {
		if (s.startsWith("save")) {
			try {
				FileSaveSystem.autoSaveGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (s.startsWith("load")) {
			try {
				FileSaveSystem.contineGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (s.startsWith("build")) {
			Village.getInstance().buildCheatBuilding(EnumBuilding.valueOf(s.split(" ")[1]));
		} else if (s.startsWith("village")) {
			EventBus.EVENT_BUS.post(new ConsoleEvent(Village.getInstance().getDebugString()));
		} else if (s.startsWith("populate")) {
			Village.getInstance().gainNewVilagers(Integer.parseInt(s.split(" ")[1]));
		} else if (s.startsWith("e")) {
			MapScreen.getInstance().showEnemy(new Enemy(10, 2, 700));
		} else {
			EventBus.EVENT_BUS.post(new ConsoleEvent("ERROR: Invalid command"));
		}
	}
	
	private void up() {
		if (this.previousIndex == this.previousCommands.size() - 1 || this.previousCommands.size() == 0) {
			return;
		}
		this.previousIndex++;
		this.cursorIndex = previousCommands.get(previousCommands.size() - this.previousIndex).toCharArray().length;
		this.previousText.clear();
		char[] chars = previousCommands.get(previousCommands.size() - this.previousIndex).toCharArray();
		for (int i = 0; i < chars.length; i++) {
			this.previousText.add(chars[i]);
		}
	}
	
	private void down() {
		if (this.previousIndex == 0) {
			return;
		}
		this.previousIndex--;
		this.previousText.clear();
		if (this.previousIndex != 0)  {
			this.cursorIndex = previousCommands.get(previousCommands.size() - this.previousIndex).toCharArray().length;
			char[] chars = previousCommands.get(previousCommands.size() - this.previousIndex).toCharArray();
			for (int i = 0; i < chars.length; i++) {
				this.previousText.add(chars[i]);
			}
		} else {
			this.cursorIndex = text.size();
		}
	}
	
	private void left() {
		if (this.cursorIndex == 1) {
			return;
		}
		this.cursorIndex--;
	}
	
	private void right() {
		if (this.previousIndex == 0) {
			if (this.cursorIndex == this.text.size()) {
				return;
			}
		} else {
			if (this.cursorIndex == this.previousText.size()) {
				return;
			}
		}
		this.cursorIndex++;
	}
	
	public static ConsoleWindow getInstance() {
		return instance;
	}
}