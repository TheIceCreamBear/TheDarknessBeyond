package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import com.joseph.thedarknessbeyond.engine.GameEngine;
import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.reference.ScreenRefrence;

public class EventWindow extends Window {
	private ArrayList<LoggedEvent> events;
	private FontRenderContext frc;
	private Font font;
	private boolean visible;
	private int charactersPerLine;
	
	private static EventWindow instance;
	
	public EventWindow() {
		this(0, 0, 500 * ScreenRefrence.scale, ScreenRefrence.HEIGHT - 1);
	}

	public EventWindow(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		this.events = new ArrayList<LoggedEvent>();
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenRefrence.getTheFont();
		this.visible = true;
		
		
		
		instance = this;
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

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!this.visible) {
			return;
		}
		
		// Background
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		// Header
		g.setColor(Color.WHITE);
		String s = "Event Log:";
		g.setFont(font);
		Rectangle2D r = font.getStringBounds(s, frc);
		int yOff = (int) r.getHeight();
		int xOff = 5;
		g.drawString(s, x + xOff, y + yOff);
	}

	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!this.visible) {
			return;
		}
		
		Rectangle2D r0 = font.getStringBounds("Event Log:", frc);
		int yOff = (int) r0.getHeight() * 2;
		int xOff = 5;
		
		for (int i = 0; i < events.size(); i++) {
			LoggedEvent e = events.get(i);
			g.setColor(e.getColor());
			String s = e.getS();
			g.setFont(font);
			Rectangle2D r = font.getStringBounds(s, frc);
			
//			if (r.getWidth() > width - 10) {
//				Rectangle2D r2 = Reference.Fonts.DEFAULT_FONT.getMaxCharBounds(frc);
//			}
			
			g.drawString(s, x + xOff, y + yOff);
			
			yOff += r.getHeight() + 5;
			
		}
	}

	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!this.visible) {
			return;
		}
		// Prevent modification from other threads while iterating over events
		synchronized (events) {
			for (LoggedEvent le : events) {
				if (le.isNew()) {
					le.fadeIn();
				}
			}
		}
	}

	@Override
	public boolean isMouseInElement() {
		return false;
	}

	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	public void addEvent(Event e) {
		// Prevent modification from other threads while iterating over events
		synchronized (events) {
			darken();
			this.events.add(0, new LoggedEvent(e.getS(), (short) Color.DARK_GRAY.getBlue()));
		}
	}
	
	private void darken() {
		for (LoggedEvent e : events) {
			e.darken();
		}
	}
	
	public static EventWindow getInstance() {
		return instance;
	}
	
	private class LoggedEvent {
		private String s;
		private short color;
		private boolean isNew;
		
		@SuppressWarnings("unused")
		public LoggedEvent(String s) {
			this(s, (short) 0);
		}
		
		public LoggedEvent(String s, short color) {
			this.s = s;
			this.color = color;
			this.isNew = true;
		}
		
		public String getS() {
			return this.s;
		}
		
		public Color getColor() {
			return new Color(color, color, color);
		}
		
		public boolean isNew() {
			return this.isNew;
		}
		
		public void fadeIn() {
			if (this.color == 255) {
				return;
			}
			this.color += 5;
			if (color >= 255) {
				this.isNew = false;
				color = 255;
			}
		}
		
		public void darken() {
			if (color == 0) {
				return;
			}
			
			this.color -= 5;
		}
	}
}