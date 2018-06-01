/* Nathan Lim, streamlined by Joseph Terrible
 */

package com.joseph.thedarknessbeyond.resource;

import java.util.HashMap;

public class StorageManager {
	private static StorageManager instance;
	private HashMap<EnumResource, Resource> resources;
	private HashMap<EnumItem, ItemStack> items; // TODO interaction methods
	
	public StorageManager() {
		this.resources = new HashMap<EnumResource, Resource>();
		this.items = new HashMap<EnumItem, ItemStack>();
		
		this.init();
		
		instance = this;
	}
	
	public StorageManager(HashMap<EnumResource, Resource> resources, HashMap<EnumItem, ItemStack> items) {
		this.resources = resources;
		this.items = items;
		
		instance = this;
	}
	
	public void init() {
		EnumResource[] v = EnumResource.values();
		for (int i = 0; i < v.length; i++) {
			this.resources.put(v[i], new Resource(v[i], 0));
		}
		
		EnumItem[] v1 = EnumItem.values();
		for (int i = 0; i < v1.length; i++) {
			this.items.put(v1[i], new ItemStack(v1[i], 0));
		}
	}
	
	public HashMap<EnumResource, Resource> getResources() {
		return this.resources;
	}
	
	public HashMap<EnumItem, ItemStack> getItems() {
		return this.items;
	}
	
	public void addResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
		Resource r = this.resources.get(er);
		r.addResource(r1);
	}
	
	public boolean useResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
		Resource r = this.resources.get(er);
		return r.subtractResource(r1, false);
	}
	
	public boolean useResources(boolean simulate, Resource... resources) {
		if (resources == null || resources.length == 0) {
			return true;
		}
		boolean flag = true;
		for (int i = 0; i < resources.length; i++) {
			EnumResource er = resources[i].getResourceEnum();
			flag &= this.resources.get(er).subtractResource(resources[i], simulate);
		}
		return flag;
	}
	
	//Checks to make sure that the appropriate resources can be allocated from the storage
	public boolean canUseResources(Resource... resources) {
		return this.useResources(true, resources);
	}
	
	public static StorageManager getInstance() {
		return instance;
	}
}
