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
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * The window that shows all of the events that get fired on the {@link EventBus}
 * @author Joseph
 *
 */
public class EventWindow extends Window {
	private ArrayList<LoggedEvent> events;
	private FontRenderContext frc;
	private Font font;
	private boolean visible;
	private final int charactersPerLine;
	
	private static EventWindow instance;
	
	public EventWindow() {
		this(0, 0, 500 * ScreenReference.scale, ScreenReference.HEIGHT - 1);
	}

	public EventWindow(int x, int y, int width, int height) {
		super(x, y, width, height, true);
		this.events = new ArrayList<LoggedEvent>();
		this.frc = GameEngine.getInstance().getFrc();
		this.font = ScreenReference.getTheFont();
		this.visible = true;
		this.charactersPerLine = this.width / (11 * ScreenReference.scale) - 1;
		System.out.println(this.charactersPerLine);
		System.err.println(font.getMaxCharBounds(frc).getWidth());
		System.out.println(font.getStringBounds("B", frc));
		
		
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
			this.checkFits(e.getS());
		}
	}
	
	private void darken() {
		for (int i = 0; i < this.events.size(); i++) {
			this.events.get(i).darken();
		}
	}
	
	private void checkFits(String s) {
		if (s.length() > this.charactersPerLine) {
			int breakpointAt = charactersPerLine;
			for (int i = charactersPerLine; i > 0; i--) {
				char c = s.charAt(i);
				if (c == ' ' || c == ',' || c == ':' || c == '-' || c == ';' || c == '/' || c== '_' || c == '&') {
					breakpointAt = i;
					break;
				}
			}
			checkFits(s.substring(breakpointAt + 1));
			darken();
			this.events.add(0, new LoggedEvent(s.substring(0, breakpointAt + 1), Color.DARK_GRAY.getBlue()));
		} else {
			darken();
			this.events.add(0, new LoggedEvent(s, Color.DARK_GRAY.getBlue()));
		}
	}
	
	public static EventWindow getInstance() {
		return instance;
	}
	
	private class LoggedEvent {
		private String s;
		private int color;
		private int maxColor = 255;
		private boolean isNew;
		
		@SuppressWarnings("unused")
		public LoggedEvent(String s) {
			this(s, (short) 0);
		}
		
		public LoggedEvent(String s, int color) {
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
			if (this.color == maxColor) {
				return;
			}
			this.color += 5;
			if (color >= maxColor) {
				this.isNew = false;
				color = maxColor;
			}
		}
		
		public void darken() {
			if (this.isNew) {
				this.maxColor -= 5;
				return;
			}
			
			if (color == Color.DARK_GRAY.getBlue()) {
				return;
			}
			
			this.color -= 5;
			
			if (color < Color.DARK_GRAY.getBlue()) {
				this.color = Color.DARK_GRAY.getBlue();
			}
		}
	}
}