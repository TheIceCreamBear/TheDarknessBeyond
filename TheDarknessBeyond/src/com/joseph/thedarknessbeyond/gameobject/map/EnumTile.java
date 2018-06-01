package com.joseph.thedarknessbeyond.gameobject.map;

/**
 * Specifies all of the possible tile types
 * @author Joseph
 *
 */
public enum EnumTile {
	// Null
	Null(' ', -1 , -1, -1),
	// Village
	Village('A', -1 , -1, -1),
	// Mines
	IronMine('I', 1, 5, 5),
	CoalMine('C', 1, 10, 10),
	// Terrain
	Forest(';', -1 , -1, -1),
	Field(',', -1 , -1, -1),
	Barrens('.', -1 , -1, -1),
	// Travel
	Road('#', -1 , -1, -1),
	// Landmarks
	Outpost('P', 0, 0, 0),
	House('H', 10, 0, (int) (Map.MAP_RADIUS * 1.5)),
	Cave('v', 5, 3, 10),
	Town('T', 10, 10, 20),
	City('Y', 20, 20, (int) (Map.MAP_RADIUS * 1.5)),
	Battlefield('B', 5, 15, (int) (Map.MAP_RADIUS * 1.5));
	
	private char dispChar;
	private int maxNum;
	private int minRadius;
	private int maxRadius;
	
	private EnumTile(char car, int maxNum, int minRadius, int maxRadius) {
		this.dispChar = car;
		this.maxNum = maxNum;
		this.minRadius = minRadius;
		this.maxRadius = maxRadius;
	}
	
	public char getDispChar() {
		return this.dispChar;
	}
	
	public int getMaxNum() {
		return this.maxNum;
	}
	
	public int getMinRadius() {
		return this.minRadius;
	}
	
	public int getMaxRadius() {
		return this.maxRadius;
	}
	
	public boolean isTerrain() {
		if (this == Forest || this == Field || this == Barrens) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @return - an array of tiles that are deemed as "terrain"
	 */
	public static EnumTile[] terrain() {
		return new EnumTile[] {EnumTile.Forest, EnumTile.Field, EnumTile.Barrens};
	}
	
	/**
	 * 
	 * @return - an array of tiles that are deemed as "landmakrs"
	 */
	public static EnumTile[] landmarks() {
		return new EnumTile[] {EnumTile.House, EnumTile.Cave, EnumTile.Town, EnumTile.City, EnumTile.Battlefield, EnumTile.IronMine, EnumTile.CoalMine};
	}
}