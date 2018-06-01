package com.joseph.thedarknessbeyond.gameobject.map;

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
	
	public void damage(int damage) {
		this.health -= damage;
	}
	
	public boolean isDead() {
		return this.health <= 0;
	}
}