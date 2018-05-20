package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.event.ConsoleEvent;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.reference.Reference;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;
import com.joseph.thedarknessbeyond.util.Utilities;

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
		this((ScreenRefrence.WIDTH - (500 * ScreenRefrence.scale)) / ScreenRefrence.scale, 100, 500, 300);
	}
	
	public ConsoleWindow(int i) {
		this(0, (ScreenRefrence.HEIGHT - ((40) * ScreenRefrence.scale)) / ScreenRefrence.scale, ScreenRefrence.WIDTH / ScreenRefrence.scale, 30);
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
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenRefrence.getTheFont();
		
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
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);
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
		
		if (e.getKeyChar() == KeyEvent.VK_SLASH && this.cursorIndex != 0) {
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
				text.add(temp);
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
				previousText.add(temp);
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
	
	private void command(String s) {
//		System.out.println(s);
		EventBus.EVENT_BUS.post(new ConsoleEvent(s));
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