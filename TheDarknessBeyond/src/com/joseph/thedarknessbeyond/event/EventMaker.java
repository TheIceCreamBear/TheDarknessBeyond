package com.joseph.thedarknessbeyond.event;

import java.util.Random;

import com.joseph.thedarknessbeyond.gameobject.Village;

public class EventMaker {
	private Random r;
	private int populateWaitCounter;
	
	public EventMaker() {
		this.r = new Random();
		this.populateWaitCounter = r.nextInt(40 * 60) + 20 * 60;
	}
	
	public void update() {
		if (this.populateWaitCounter > 0) {
			this.populateWaitCounter--;
			if (this.populateWaitCounter == 0) {
				Village.getInstance().gainNewVilagers(r.nextInt(Village.getInstance().maxGainableVillagers() - 1) + 1);
			}
			return;
		}
	}
}