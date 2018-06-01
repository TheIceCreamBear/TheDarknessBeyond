package com.joseph.thedarknessbeyond.gameobject.map;

public class Player {
	private int x;
	private int y;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void moveUp() {
		this.y--;
	}
	
	public void moveDown() {
		this.y++;
	}
	
	public void moveLeft() {
		this.x--;
	}
	
	public void moveRight() {
		this.x++;
	}
}