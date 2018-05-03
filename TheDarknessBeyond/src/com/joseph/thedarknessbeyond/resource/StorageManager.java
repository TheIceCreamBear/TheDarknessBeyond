package com.joseph.thedarknessbeyond.resource;

import java.util.HashMap;

public class StorageManager {
	private static StorageManager instance;
	private HashMap<EnumResource, Resource> stores;
	
	public StorageManager() {
		this.stores = new HashMap<EnumResource, Resource>();
		
		instance = this;
	}
	
	public void init() {
		this.stores.put(EnumResource.Wood, new Resource(EnumResource.Wood, 0));
		this.stores.put(EnumResource.Stone, new Resource(EnumResource.Stone, 0));
		this.stores.put(EnumResource.Belief, new Resource(EnumResource.Belief, 0));
		this.stores.put(EnumResource.Coal, new Resource(EnumResource.Coal, 0));
		this.stores.put(EnumResource.Iron, new Resource(EnumResource.Iron, 0));
		this.stores.put(EnumResource.Steel, new Resource(EnumResource.Steel, 0));
		this.stores.put(EnumResource.IronOre, new Resource(EnumResource.IronOre, 0));
		this.stores.put(EnumResource.SteelOre, new Resource(EnumResource.SteelOre, 0));
		this.stores.put(EnumResource.Bullets, new Resource(EnumResource.Bullets, 0));
		this.stores.put(EnumResource.Berries, new Resource(EnumResource.Berries, 0));
		this.stores.put(EnumResource.Meat, new Resource(EnumResource.Meat, 0));
		this.stores.put(EnumResource.PreservedMeat, new Resource(EnumResource.PreservedMeat, 0));
		this.stores.put(EnumResource.Water, new Resource(EnumResource.Water, 0));
		this.stores.put(EnumResource.Fur, new Resource(EnumResource.Fur, 0));
		this.stores.put(EnumResource.Leather, new Resource(EnumResource.Leather, 0));
		this.stores.put(EnumResource.Cotton, new Resource(EnumResource.Cotton, 0));
		this.stores.put(EnumResource.Fibers, new Resource(EnumResource.Fibers, 0));
		this.stores.put(EnumResource.HolyWater, new Resource(EnumResource.HolyWater, 0));
		this.stores.put(EnumResource.Stone, new Resource(EnumResource.Stone, 0));
	}
	
	public void update() {
		//TODO: We'll see
	}
	
	public void addResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
//		this.stores.put(er, r1); How to add a separate resource INTO the HashMap
		Resource r = this.stores.get(er);
		r.addResource(r1);
	}
	
	public boolean useResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
		Resource r = this.stores.get(er);
		return r.subtractResource(r1);
	}
	
	public static StorageManager getInstance() {
		return instance;
	}
}
