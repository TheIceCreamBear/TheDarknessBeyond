package com.joseph.thedarknessbeyond.gameobject.map;

import java.util.HashMap;

import com.joseph.thedarknessbeyond.resource.EnumItem;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.ItemStack;
import com.joseph.thedarknessbeyond.resource.Resource;

public class Player {
	private int x;
	private int y;
	private int health;
	private int maxHealth;
	private HashMap<EnumResource, Resource> resources;
	private EnumItem meleWeapon;
	private EnumItem rangedWeapon;
	private ItemStack ammo;
	// TODO weight
	
	public Player() {
		this.x = Map.MAP_RADIUS;
		this.y = Map.MAP_RADIUS;
	}
	
	public Player(HashMap<EnumResource, Resource> resources, EnumItem meleWeapon, EnumItem rangedWeapon, ItemStack ammo, EnumItem armor) {
		this.x = Map.MAP_RADIUS;
		this.y = Map.MAP_RADIUS;
		this.resources = resources;
		this.meleWeapon = meleWeapon;
		this.rangedWeapon = rangedWeapon;
		this.ammo = ammo;
		if (armor != null && !armor.isModDamage()) {
			this.health = 10 + armor.getMod();
		} else {
			this.health = 10;
		}
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isDead() {
		return this.health <= 0;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public void heal() {
		if (this.resources == null) {
			return;
		}
		if (this.resources.get(EnumResource.PreservedMeat).subtractResource(new Resource(EnumResource.PreservedMeat, 1), true)) {
			this.health += 8;
			if (this.health > this.maxHealth) {
				this.health = maxHealth;
			}
		}
		
	}
	
	public void damage(int damage) {
		this.health -= damage;
	}
	
	public int getMeleAttack() {
		if (this.meleWeapon == null || this.meleWeapon == EnumItem.None) {
			// fists
			return 2;
		}
		
		return 2 + this.meleWeapon.getMod();
	}
	
	public int getRangedAttack() {
		if (this.rangedWeapon == null) {
			return 0;
		}
		
		if (ammo == null) {
			return 0;
		} else {
			if (this.rangedWeapon.hasAmmo()) {
				this.ammo.consume();
			}
			return this.rangedWeapon.getMod() + this.ammo.getItem().getMod();
		}
	}
	
	public HashMap<EnumResource, Resource> getResources() {
		return this.resources;
	}
	
	public void moveUp() {
		this.y--;
		if (y < 1) {
			y = 1;
		}
	}
	
	public void moveDown() {
		this.y++;
		if (y > Map.MAP_RADIUS * 2 - 1) {
			y = Map.MAP_RADIUS * 2 - 1;
		}
	}
	
	public void moveLeft() {
		this.x--;
		if (x < 1) {
			x = 1;
		}
	}
	
	public void moveRight() {
		this.x++;
		if (x > Map.MAP_RADIUS * 2 - 1) {
			x = Map.MAP_RADIUS * 2 - 1;
		}
	}
}