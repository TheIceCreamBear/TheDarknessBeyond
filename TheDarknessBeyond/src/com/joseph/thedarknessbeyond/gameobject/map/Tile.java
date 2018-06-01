package com.joseph.thedarknessbeyond.gameobject.map;

public class Tile {
	public static final Tile NULL = new Tile(-1, -1, false, EnumTile.Null);
	public static final Tile VILLAGE = new Tile(Map.MAP_RADIUS, Map.MAP_RADIUS, true, EnumTile.Village);
	private int x;
	private int y;
	private boolean discovered;
	private EnumTile tile;
	
	public Tile(int x, int y, boolean discovered, EnumTile tile) {
		this.x = x;
		this.y = y;
		this.discovered = discovered;
		this.tile = tile;
	}
	
	public boolean isDiscovered() {
		return this.discovered;
	}
	
	public void discover() {
		this.discovered = true;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public EnumTile getTile() {
		return tile;
	}
	
	public char getChar() {
		return this.tile.getDispChar();
//		return this.discovered ? this.tile.getDispChar() : ' ';
	}
}