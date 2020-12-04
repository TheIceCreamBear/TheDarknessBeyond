package com.joseph.thedarknessbeyond.gameobject.map;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Random;

import com.joseph.thedarknessbeyond.gui.screens.MapScreen;
import com.joseph.thedarknessbeyond.gui.windows.ScreenSelectionWindow;

/**
 * An object representing the Map of the world.
 * @author Joseph
 *
 */
public class Map {
	/**
	 * the distance of the edge of the map from the center
	 */
	public static final int MAP_RADIUS = 30;
	
	/**
	 * the radius of tiles around the player that are exlpored when the player moves.
	 */
	private static final int EXPLORE_DISTANCE = 2;
	
	/**
	 * the minimum distance the player will travel before encountering another fight.
	 */
	private static final int MIN_DIST_AFTER_FIGHT = 3;
	
	/**
	 * the chance on any given tile to encounter an enemy 
	 */
	private static final float FIGHT_CHANCE = 0.2f;
	private static Map instance;
	
	/**
	 * map of tile types to floats of the chance that a tile will be generated
	 */
	private HashMap<EnumTile, Float> chances;
	
	/**
	 * the map
	 */
	private Tile[][] map;
	private char[][] mapButAsChars;
	private Random r;
	private Player player;
	
	/**
	 * counter to go along with {@link this#MIN_DIST_AFTER_FIGHT Map.MIN_DIST_AFTER_FIGHT}
	 */
	private int distanceTravledAfterLastFight;
	
	/**
	 * the chance that a tile will be the same type as the tiles next to it
	 */
	private float howSticky = 0.5f;
	
	public Map() {
		this.r = new Random(123456789123456789l);
		this.player = new Player();
		this.chances = new HashMap<EnumTile, Float>();
		this.initChances();
		this.map = new Tile[MAP_RADIUS * 2 + 1][MAP_RADIUS * 2 + 1];
		this.generate();
		this.mapButAsChars = new char[MAP_RADIUS * 2 + 1][MAP_RADIUS * 2 + 1];
		this.makeCharMap();
		
		this.illuminate();
		
		instance = this;
	}
	
	/**
	 * build the char map from the tile map
	 */
	private void makeCharMap() {
		for (int i = 0; i < mapButAsChars.length; i++) {
			for (int j = 0; j < mapButAsChars[i].length; j++) {
				this.mapButAsChars[i][j] = this.map[i][j].getChar();
			}
		}
		this.mapButAsChars[player.getY()][player.getX()] = '@';
	}
	
	private void initChances() {
		this.chances.put(EnumTile.Forest, 0.15f);
		this.chances.put(EnumTile.Field, 0.35f);
		this.chances.put(EnumTile.Barrens, 0.5f);
	}
	
	/**
	 * generates the map spiraling outward from the center to the out
	 */
	private void generate() {
		// THIS ALGO generates in a box formation around the center
		// Generates top, right, bottom, left and then increases the distance from the origin
		// it was borrowed from the original game to save time (and not reinvent the wheel)
		// SOURCE: https://github.com/Continuities/adarkroom/blob/5fcd897b17e82b43f24e60e8e0009cfb0c64d375/script/world.js#L627
		this.map[MAP_RADIUS][MAP_RADIUS] = Tile.VILLAGE;
		for (int r = 1; r <= MAP_RADIUS; r++) {
			for (int t = 0; t < r * 8; t++) {
				int x;
				int y;
				if (t < 2 * r) {
					x = MAP_RADIUS - r + t;
					y = MAP_RADIUS - r;
				} else if (t < 4 * r) {
					x = MAP_RADIUS + r;
					y = MAP_RADIUS - (3 * r) + t;
				} else if (t < 6 * r) {
					x = MAP_RADIUS + (5 * r) - t;
					y = MAP_RADIUS + r;
				} else {
					x = MAP_RADIUS - r;
					y = MAP_RADIUS + (7 * r) - t;
				}
				
				map[y][x] = generateTile(x, y);
			}
		}
		
		// TODO landmarks
		EnumTile[] landmarks = EnumTile.landmarks();
		for (int i = 0; i < landmarks.length; i++) {
			EnumTile currentLandmark = landmarks[i];
			for (int j = 0; j < currentLandmark.getMaxNum(); j++) {
				placeLandmark(currentLandmark);
			}
		}
	}
	
	/**
	 * randomly generates the location of the given landmark
	 * @param landmark - the landmark to generate
	 */
	private void placeLandmark(EnumTile landmark) {
		// init to middle of map
		int x = MAP_RADIUS;
		int y = MAP_RADIUS;
		
		do {
			// get random radius from center inside the max and min of the landmark
			int radius;
			if (landmark.getMaxRadius() == landmark.getMinRadius()) {
				radius = landmark.getMinRadius();
			} else {
				radius = r.nextInt(landmark.getMaxRadius() - landmark.getMinRadius()) + landmark.getMinRadius();
			}
			
			// create a 'vector' with magnitude radius
			int dx = r.nextInt(radius);
			int dy = radius - dx;
			
			// Randomly swap some sings
			if (r.nextBoolean()) {
				dx = -dx;
			}
			if (r.nextBoolean()) {
				dy = -dy;
			}
			
			x = MAP_RADIUS + dx;
			y = MAP_RADIUS + dy;
			
			// Check bounds
			if (x < 0) {x = 0;}
			if (x > MAP_RADIUS * 2) {x = MAP_RADIUS * 2;}
			if (y < 0) {y = 0;}
			if (y > MAP_RADIUS * 2) {y = MAP_RADIUS * 2;}
			
			// if the random location is already a landmark, find a new random location
		} while (!map[y][x].getTile().isTerrain());
		map[y][x] = new Tile(x, y, false, landmark);
		
	}
	
	/**
	 * generates a new tile a position (x, y) with a random terrain type but has a higher chance to be of a type that is adjacent to it
	 * @param x - location x
	 * @param y - location y
	 * @return - the tile generated for the given position
	 */
	private Tile generateTile(int x, int y) {
		Tile[] adjecents = new Tile[4];
		adjecents[0] = (y > 0) ? map[y - 1][x] : Tile.NULL;
		adjecents[1] = (y < MAP_RADIUS * 2) ? map[y + 1][x] : Tile.NULL;
		adjecents[2] = (x < MAP_RADIUS * 2) ? map[y][x + 1] : Tile.NULL;
		adjecents[3] = (x > 0) ? map[y][x - 1] : Tile.NULL;
		
		LinkedHashMap<EnumTile, Float> localChances = new LinkedHashMap<EnumTile, Float>();
		
		float stickyLeft = 1;
		for (int i = 0; i < adjecents.length; i++) {
			if (adjecents[i]  == Tile.VILLAGE) {
				return new Tile(x, y, false, EnumTile.Forest);
			} else if (adjecents[i] != null) {
				EnumTile key = adjecents[i].getTile();
				if (localChances.containsKey(key)) {
					localChances.put(key, localChances.get(key) + howSticky);
				} else {
					localChances.put(key, howSticky);
				}
				stickyLeft -= howSticky;
			}
		}
		 
		EnumTile[] tiles = EnumTile.terrain();
		for (int i = 0; i < tiles.length; i++) {
			EnumTile key = tiles[i];
			float var = 0;
			if (localChances.containsKey(key)) {
				var += localChances.get(key);
			}
			var += this.chances.get(key) * stickyLeft;
			localChances.put(key, var);
		}
		
		ArrayList<EnumTile> keys = new ArrayList<EnumTile>(localChances.keySet());
		keys.sort(new Comparator<EnumTile>() {
			@Override
			public int compare(EnumTile o1, EnumTile o2) {
				float f1 = localChances.get(o1);
				float f2 = localChances.get(o2);
				if (f1 < f2) {
					return -1;
				} else if (f1 > f2) {
					return 1;
				} else {
					return 0;
				}
			}
			
		});
		System.out.println(keys);
		System.out.println(localChances);
		
		float chance = 0;
		float chanceNeeded = r.nextFloat();
		for (int i = 0; i < keys.size(); i++) {
			chance += localChances.get(keys.get(i));
			if (chance > chanceNeeded) {
				return new Tile(x, y, false, keys.get(i));
			}
		}		
		return new Tile(x, y, false, EnumTile.Barrens);
	}
	
	/**
	 * reveals part of the map at the players location
	 */
	private void illuminate() {
		map[player.getY()][player.getX()].discover();
		
		// Loop over the tiles that are EXPLORE_DISTANCE or less away from the player
		for (int y = -EXPLORE_DISTANCE; y <= EXPLORE_DISTANCE; y++) {
			for (int x = -EXPLORE_DISTANCE + Math.abs(y); x <= EXPLORE_DISTANCE - Math.abs(y); x++) {
				if (player.getX() + x >= 0 && player.getX() + x <= MAP_RADIUS * 2 && player.getY() + y >= 0 && player.getY() + y <= MAP_RADIUS * 2) {
					map[player.getY() + y][player.getX() + x].discover();
				}
			}
		}
	}
	
	public void addPlayer(Player p) {
		this.player = p;
		this.distanceTravledAfterLastFight = 0;
		
	}
	
	public Tile[][] getMap() {
		return this.map;
	}
	
	public char[][] getCharArray() {
		this.makeCharMap();
		return this.mapButAsChars;
	}
	
	public void movePlayerUp() {
		if (MapScreen.getInstance().isFightGoingOn()) {
			return;
		}
		this.player.moveUp();
		this.move();
	}
		
	public void movePlayerDown() {
		if (MapScreen.getInstance().isFightGoingOn()) {
			return;
		}
		this.player.moveDown();
		this.move();
	}
	
	public void movePlayerLeft() {
		if (MapScreen.getInstance().isFightGoingOn()) {
			return;
		}
		this.player.moveLeft();
		this.move();
	}
	
	public void movePlayerRight() {
		if (MapScreen.getInstance().isFightGoingOn()) {
			return;
		}
		this.player.moveRight();
		this.move();
	}
	
	/**
	 * common statements that need to run every time a player moves
	 */
	private void move() {
		this.illuminate();
		if (player.getX() == MAP_RADIUS && player.getY() == MAP_RADIUS) {
			ScreenSelectionWindow.getInstance().returnHome();
			return;
		}
		if (map[player.getY()][player.getX()].getTile() != EnumTile.Road) {
			distanceTravledAfterLastFight++;
			if (distanceTravledAfterLastFight > MIN_DIST_AFTER_FIGHT) {
				if (r.nextDouble() < FIGHT_CHANCE) {
					MapScreen.getInstance().showEnemy(new Enemy(r.nextInt(10) + 10, r.nextInt(4) + 2, r.nextInt(300) + 300));
				}
			}
		}
	}
	
	
	public static Map getInstance() {
		return instance;
	}
}