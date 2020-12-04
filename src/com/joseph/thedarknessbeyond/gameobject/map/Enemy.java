package com.joseph.thedarknessbeyond.gameobject.map;

/**
 * An enemy to the player
 * @author Joseph
 *
 */
public class Enemy {
	private int health;
	private int attack;
	private int attackTimer;
	
	public Enemy(int health, int attack, int attackTimer) {
		this.health = health;
		this.attack = attack;
		this.attackTimer = attackTimer;
	}
	
	public int getHealth() {
		return this.health;
	}
	
	public int getAttackTimer() {
		return this.attackTimer;
	}
	
	public int getAttack() {
		return this.attack;
	}
	
	/**
	 * hurts the enemy by damage
	 * @param damage
	 */
	public void damage(int damage) {
		this.health -= damage;
	}
	
	/**
	 * 
	 * @return true if the enemy is dead
	 */
	public boolean isDead() {
		return this.health <= 0;
	}
}