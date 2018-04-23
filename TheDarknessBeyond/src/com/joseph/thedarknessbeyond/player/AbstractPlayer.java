package com.joseph.thedarknessbeyond.player;

import com.joseph.thedarknessbeyond.gameobject.GameObject;

/**
 * An abstract player class. Used to contain common types and methods used by various player objects.
 * 
 * @author Joseph
 * @author David Santamaria
 *
 */
public abstract class AbstractPlayer extends GameObject {
	protected enum EnumDirection {
		UP, DOWN, LEFT, RIGHT;
	}
}