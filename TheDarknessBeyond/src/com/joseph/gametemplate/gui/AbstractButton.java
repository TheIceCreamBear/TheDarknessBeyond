package com.joseph.gametemplate.gui;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * Extension of JButton that makes all paint functions <code>NO-OP</code>, and implements {@link IGuiOverlay IGuiOverlay}
 * to allow for painting of the button using the methods available in the engine.
 * @author Joseph
 * @see IGuiOverlay
 *
 */
@SuppressWarnings("serial")
public abstract class AbstractButton extends JButton implements IGuiOverlay, ActionListener {

	@Override
	protected void paintBorder(Graphics g) {
	}
	
	@Override
	public void paint(Graphics g) {
	}
	
	@Override
	protected void paintChildren(Graphics g) {
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	}
	
	@Override
	public void paintImmediately(int x, int y, int w, int h) {
	}
	
	@Override
	public void paintImmediately(Rectangle r) {
	}
	
	@Override
	public void paintComponents(Graphics g) {
	}
	
	@Override
	public void paintAll(Graphics g) {
	}
	
}