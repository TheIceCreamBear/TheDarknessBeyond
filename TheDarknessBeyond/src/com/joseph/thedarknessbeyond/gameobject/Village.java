package com.joseph.thedarknessbeyond.gameobject;

import java.util.HashMap;
import java.util.Set;

import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

public class Village {
	private int numResidents;
	private int numMaxResidents;
	private int gainResourceTimer;
	private final int resourceTimerMax;
	private HashMap<EnumBuilding, Integer> buildingCount;
	private HashMap<EnumJob, Integer> jobDistrubution;
	
	private static Village instance;
	
	public Village() {
		this.buildingCount = new HashMap<EnumBuilding, Integer>();
		this.initEmptyBuildingMap();
		this.jobDistrubution = new HashMap<EnumJob, Integer>();
		this.initEmptyJobMap();
		
		this.resourceTimerMax = 600;
		
		instance = this;
	}
	
	public void update() {
		if (this.gainResourceTimer == this.resourceTimerMax) {
			StorageManager sm = StorageManager.getInstance();
			Set<EnumJob> s = this.jobDistrubution.keySet();
			for (EnumJob enumJob : s) {
				Resource[] r = enumJob.production;
				for (int i = 0; i < r.length; i++) {
					sm.addResource(r[i].multiplyResource(jobDistrubution.get(enumJob)));
				}
			}
			this.gainResourceTimer = 0;
		} else {
			this.gainResourceTimer++;
		}
	}
	
	public boolean gainNewVilagers(int numGained) {
		if (this.numResidents + numGained <= this.numMaxResidents) {
			Integer i = jobDistrubution.get(EnumJob.Idiling);
			i += numGained;
			jobDistrubution.put(EnumJob.Idiling, i);
			if (!this.verifyResidentsEqual()) {
				// TODO DO SOMETHING
			}
			return true;
		} else {
			return false;
		}
	}
	
	public HashMap<EnumBuilding, Integer> getBuildingCount() {
		return this.buildingCount;
	}
	
	public HashMap<EnumJob, Integer> getJobDistrubution() {
		return this.jobDistrubution;
	}
	
	private boolean verifyResidentsEqual() {
		int i = 0;
		for (EnumJob job : jobDistrubution.keySet()) {
			i += jobDistrubution.get(job);
		}
		return i == this.numResidents && i <= this.numMaxResidents;
	}
	
	private void initEmptyJobMap() {
		EnumJob[] v = EnumJob.values();
		for (int i = 0; i < v.length; i++) {
			this.jobDistrubution.put(v[i], 0);
		}
	}
	
	private void initEmptyBuildingMap() {
		EnumBuilding[] v = EnumBuilding.values();
		for (int i = 0; i < v.length; i++) {
			this.buildingCount.put(v[i], 0);
		}
	}
	
	public boolean buildBuilding(EnumBuilding building) {
		StorageManager sm = StorageManager.getInstance();
		if (sm.canUseResources(building.cost)) {
			if (sm.useResources(false, building.cost)) {
				Integer i = this.buildingCount.get(building);
				i++;
				this.buildingCount.put(building, i);
				if (building == EnumBuilding.Hut) {
					this.numMaxResidents = this.buildingCount.get(building) * 4;
				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * this method is only for testing and will be removed later
	 */
	@Deprecated
	public void buildCheatBuilding(EnumBuilding building) {
		Integer i = this.buildingCount.get(building);
		i++;
		this.buildingCount.put(building, i);
		if (building == EnumBuilding.Hut) {
			this.numMaxResidents = this.buildingCount.get(building) * 4;
		}
	}
	
	public String getDebugString() {
		return this.buildingCount.toString() + " " + this.jobDistrubution.toString();
	}
	
	public enum EnumBuilding {
		// TODO add the cost of building
		Hut(new Resource(EnumResource.Wood, 100)), Armory, Garden, Charcuterie, Cathedral;
		
		private Resource[] cost;
		
		private EnumBuilding(Resource... cost) {
			this.cost = cost;
		}
		
		public Resource[] getCost() {
			return this.cost;
		}
	}
	
	public enum EnumJob {
		// TODO add the production that one worker produces
		// if the remove something, set the resource to be negative
		Idiling(new Resource(EnumResource.Wood, 2)), Gaurd, Lumberjack, Butcher, WaterCollector;
		
		private Resource[] production;
		
		private EnumJob(Resource... production) {
			this.production = production;
		}
		
		public Resource[] getProduction() {
			return this.production;
		}
	}
	
	public static Village getInstance() {
		return instance;
	}
}