package com.joseph.thedarknessbeyond.resource;

import java.util.HashMap;

public class StorageManager {
	private static StorageManager instance;
	private HashMap<EnumResource, Resource> stores;
	
	public StorageManager() {
		this.stores = new HashMap<EnumResource, Resource>();
		
		this.init();
		
		instance = this;
	}
	
	public StorageManager(HashMap<EnumResource, Resource> stores) {
		this.stores = stores;
		
		instance = this;
	}
	
	public void init() {
		EnumResource[] v = EnumResource.values();
		for (int i = 0; i < v.length; i++) {
			this.stores.put(v[i], new Resource(v[i], 0));
		}
	}
	
	public HashMap<EnumResource, Resource> getStores() {
		return this.stores;
	}
	
	public void addResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
		Resource r = this.stores.get(er);
		r.addResource(r1);
	}
	
	public boolean useResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
		Resource r = this.stores.get(er);
		return r.subtractResource(r1, false);
	}
	
	public boolean useResources(boolean simulate, Resource... resources) {
		if (resources == null || resources.length == 0) {
			return true;
		}
		boolean flag = true;
		for (int i = 0; i < resources.length; i++) {
			EnumResource er = resources[i].getResourceEnum();
			flag &= this.stores.get(er).subtractResource(resources[i], simulate);
		}
		return flag;
	}
	
	public boolean canUseResources(Resource... resources) {
		return this.useResources(true, resources);
	}
	
	public static StorageManager getInstance() {
		return instance;
	}
}
