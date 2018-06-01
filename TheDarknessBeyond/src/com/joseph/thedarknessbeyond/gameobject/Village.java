package com.joseph.thedarknessbeyond.gameobject;

import java.util.HashMap;
import java.util.Set;

import com.joseph.thedarknessbeyond.event.Event;
import com.joseph.thedarknessbeyond.event.EventBus;
import com.joseph.thedarknessbeyond.resource.EnumResource;
import com.joseph.thedarknessbeyond.resource.Resource;
import com.joseph.thedarknessbeyond.resource.StorageManager;

/**
 * A village class. Handles all the logic involved with having a village.
 * @author Joseph
 *
 */
public class Village {
	private int numResidents;
	private int numMaxResidents;
	private int gainResourceTimer;
	private final int resourceTimerMax = 600;
	private HashMap<EnumBuilding, Integer> buildingCount;
	private HashMap<EnumJob, Integer> jobDistrubution;
	
	private static Village instance;
	
	public Village() {
		this.buildingCount = new HashMap<EnumBuilding, Integer>();
		this.initEmptyBuildingMap();
		this.jobDistrubution = new HashMap<EnumJob, Integer>();
		this.initEmptyJobMap();
		
		instance = this;
	}
	
	/**
	 * loads a village from file
	 * @param buildings
	 * @param jobs
	 */
	public Village(HashMap<EnumBuilding, Integer> buildings, HashMap<EnumJob, Integer> jobs) {
		this.buildingCount = buildings;
		this.jobDistrubution = jobs;
		
		instance = this;
	}
	
	/**
	 * updates the village logic of the village
	 */
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
	
	public int maxGainableVillagers() {
		return canGainNewVillagers(3) ? 3 : this.numMaxResidents - numResidents;
	}
	
	public boolean canGainNewVillagers(int numGained) {
		return this.numResidents + numGained <= this.numMaxResidents;
	}
	
	/**
	 * adds new villagers to the village with the job Idiling
	 * @param numGained - the amount of villagers to gain
	 * @return - true if the operation was successful
	 */
	public boolean gainNewVilagers(int numGained) {
		if (this.canGainNewVillagers(numGained)) {
			Integer i = jobDistrubution.get(EnumJob.Idiling);
			i += numGained;
			jobDistrubution.put(EnumJob.Idiling, i);
			
			EventBus.EVENT_BUS.post(new Event(numGained + " villagers setteled in your village."));
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
	
	private void initEmptyJobMap() {
		EnumJob[] v = EnumJob.values();
		for (int i = 0; i < v.length; i++) {
			this.jobDistrubution.put(v[i], 0);
		}
	}
	
	/**
	 * adds villager to the specified job
	 * @param job - the specific job
	 * @return - true if successful
	 */
	public boolean increaseJob(EnumJob job) {
		int numIdling = this.jobDistrubution.get(EnumJob.Idiling);
		if (numIdling > 0) {
			Integer i = this.jobDistrubution.get(job);
			i++;
			this.jobDistrubution.put(job, i);
			numIdling--;
			this.jobDistrubution.put(EnumJob.Idiling, numIdling);
			return true;
		}
		
		return false;
	}
	
	/**
	 * opposite of {@link this#increaseJob(EnumJob)}
	 * @param job - the specific job
	 * @return - true if successful
	 */
	public boolean decreaseJob(EnumJob job) {
		int numInJob = this.jobDistrubution.get(job);
		if (numInJob > 0) {
			Integer i = this.jobDistrubution.get(EnumJob.Idiling);
			i++;
			this.jobDistrubution.put(EnumJob.Idiling, i);
			numInJob--;
			this.jobDistrubution.put(job, numInJob);
			return true;
		}	
		
		return false;
	}
	
	private void initEmptyBuildingMap() {
		EnumBuilding[] v = EnumBuilding.values();
		for (int i = 0; i < v.length; i++) {
			this.buildingCount.put(v[i], 0);
		}
	}
	
	/**
	 * builds a new building of type building
	 * @param building - the building
	 * @return - true if successful
	 */
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
	
	/**
	 * specifies all possible buildings and their cost
	 * @author Joseph
	 */
	public enum EnumBuilding {
		Hut(new Resource(EnumResource.Wood, 100)),
		Armory(new Resource(EnumResource.Wood, 200)),
		Garden(new Resource(EnumResource.Wood, 50)),
		Charcuterie(new Resource(EnumResource.Wood, 100), new Resource(EnumResource.Stone, 50)),
		Cathedral(new Resource(EnumResource.Wood, 2000), new Resource(EnumResource.Stone, 2000), new Resource(EnumResource.Iron, 600), new Resource(EnumResource.Steel, 600)),
		Storage(new Resource(EnumResource.Wood, 100)),
		Quarry(new Resource(EnumResource.Wood, 225)),
		Blacksmith(new Resource(EnumResource.Wood, 400), new Resource(EnumResource.Stone, 400)),
		Forge(new Resource(EnumResource.Wood, 400), new Resource(EnumResource.Stone, 200)), 
		Workshop(new Resource(EnumResource.Wood, 50)),
		WaterWorks(new Resource(EnumResource.Wood, 1500), new Resource(EnumResource.Stone, 1000), new Resource(EnumResource.Iron, 500), new Resource(EnumResource.Steel, 200)),
		LumberMill(new Resource(EnumResource.Wood, 400)),
		GuardPost(new Resource(EnumResource.Wood, 200), new Resource(EnumResource.Stone, 100)),
		Barricades(new Resource(EnumResource.Stone, 500));
		
		private Resource[] cost;
		
		private EnumBuilding(Resource... cost) {
			this.cost = cost;
		}
		
		public Resource[] getCost() {
			return this.cost;
		}
	}
	
	
	/**
	 * specifies all the possible jobs of a villager
	 * @author Joseph
	 *
	 */
	public enum EnumJob {
		// TODO do something if "20 sec"
		Idiling(),
		Guard(new Resource(EnumResource.PreservedMeat, -1)), // 20 sec
		Lumberjack(new Resource(EnumResource.Wood, 1)),
		Butcher(new Resource(EnumResource.PreservedMeat, 1), new Resource(EnumResource.Meat, -2), new Resource(EnumResource.Wood, -10)),
		Miner(new Resource(EnumResource.IronOre, 1), new Resource(EnumResource.SteelOre, 1), new Resource(EnumResource.PreservedMeat, -1)), // 20 sec
		WaterCollector(new Resource(EnumResource.Water, 1));
		
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