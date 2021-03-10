package com.joseph.thedarknessbeyond.gui.windows;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;

import com.joseph.thedarknessbeyond.engine.TheDarknessBeyondEngine;
import com.joseph.thedarknessbeyond.gameobject.map.Enemy;
import com.joseph.thedarknessbeyond.gameobject.map.Player;
import com.joseph.thedarknessbeyond.gui.Window;
import com.joseph.thedarknessbeyond.gui.buttons.GenericCoolDownButton;
import com.joseph.thedarknessbeyond.interfaces.IMouseReliant;
import com.joseph.thedarknessbeyond.reference.ScreenReference;

/**
 * The Window that displays the combat
 * @author Joseph
 *
 */
public class CombatWindow extends Window {
	private Player player;
	private Enemy enemy;
	private int enemyAttackTimer;
	private GenericCoolDownButton attack;
	private GenericCoolDownButton ranged;
	private GenericCoolDownButton heal;
	// TODO health
	
	public CombatWindow(int x, int y, int width, int height, Player player) {
		super(x, y, width, height);
		this.player = player;
		this.enemy = new Enemy(0, 0, 0);
		this.attack = new GenericCoolDownButton(x + 20, y + 50, "Attack", 450, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				enemy.damage(CombatWindow.this.player.getMeleAttack());
				return true;
			}
		});
		
		this.ranged = new GenericCoolDownButton(x + 20, y + 55 + attack.getHeight0(), "Ranged Attack", 600, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				enemy.damage(CombatWindow.this.player.getRangedAttack());
				return false;
			}
		});
		
		this.heal = new GenericCoolDownButton(x + 20, y + 60 + attack.getHeight0() + ranged.getHeight0(), "Heal", 600, new IMouseReliant() {
			@Override
			public boolean onMouseEvent(MouseEvent e) {
				CombatWindow.this.player.heal();
				return true;
			}
		});
		
		TheDarknessBeyondEngine.getInstance().addButton(attack);
		TheDarknessBeyondEngine.getInstance().addButton(ranged);
		TheDarknessBeyondEngine.getInstance().addButton(heal);
	}

	@Override
	public void drawBackground(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y, width, height);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(x + 1, y + 1, width - 1, height - 1);
		
		this.attack.drawBackground(g, observer);
		this.ranged.drawBackground(g, observer);
		this.heal.drawBackground(g, observer);
	}
	
	@Override
	public void drawUpdateableElements(Graphics g, ImageObserver observer) {
		if (!visible) {
			return;
		}
		
		g.setColor(Color.WHITE);
		g.setFont(ScreenReference.getTheFont());
		g.drawString("Health: " + player.getHealth(), x + (20 * ScreenReference.scale), y + (40 * ScreenReference.scale));
		g.drawString("Enemy Health: " + enemy.getHealth(), x + (200 * ScreenReference.scale), y + (40 * ScreenReference.scale));
		
		g.drawString("Enemy Attack Cooldown: ", x + (200 * ScreenReference.scale), y + (60 * ScreenReference.scale));
		
		g.setColor(Color.BLACK);
		g.drawRect(x + (200 * ScreenReference.scale), y + (70 * ScreenReference.scale), 100, 20 * ScreenReference.scale);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + (200 * ScreenReference.scale), y + (70 * ScreenReference.scale), 100 * this.enemyAttackTimer / this.enemy.getAttackTimer(), 20 * ScreenReference.scale);
		
		this.attack.drawUpdateableElements(g, observer);
		this.ranged.drawUpdateableElements(g, observer);
		this.heal.drawUpdateableElements(g, observer);
	}
	
	@Override
	public void updateUpdateableElements(double deltaTime) {
		if (!visible) {
			return;
		}
		
		this.attack.updateUpdateableElements(deltaTime);
		this.ranged.updateUpdateableElements(deltaTime);
		this.heal.updateUpdateableElements(deltaTime);
		if (this.enemyAttackTimer > 0) {
			this.enemyAttackTimer--;
		} else if (this.enemyAttackTimer == 0 && !this.enemy.isDead()) {
			this.player.damage(enemy.getAttack());
			this.enemyAttackTimer = this.enemy.getAttackTimer();
		}
		
		if (this.enemy.isDead() || this.player.isDead()) {
			this.hide();
		}
	}
	
	@Override
	public void displayToolTip(Graphics g) {
		
	}
	
	public void show(Player player, Enemy newEnemy) {
		this.player = player;
		this.enemy = newEnemy;
		this.enemyAttackTimer = enemy.getAttackTimer() / 60;
		this.attack.show();
		this.heal.show();
		super.show();
	}
	
	@Override
	public void hide() {
		this.attack.hide();
		this.heal.hide();
		super.hide();
	}
}