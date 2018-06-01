package com.joseph.thedarknessbeyond.resource;

import java.util.HashMap;

/**
 * the class responsible for managing the storage
 * @author Nathan Lim, streamlined by Joseph Terrible
 *
 */
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
	
	public EnumItem getBestArmor() {
		if (items.containsKey(EnumItem.PlateArmor) && items.get(EnumItem.PlateArmor).getAmount() > 0) {
			return EnumItem.PlateArmor;
		} else if (items.containsKey(EnumItem.ChainArmor) && items.get(EnumItem.ChainArmor).getAmount() > 0) {
			return EnumItem.ChainArmor;
		} else if (items.containsKey(EnumItem.LeatherArmor) && items.get(EnumItem.LeatherArmor).getAmount() > 0) {
			return EnumItem.LeatherArmor;
		} else {
			return EnumItem.FurClothes;
		}
	}
	
	public EnumItem getBestMele() {
		if (items.containsKey(EnumItem.SteelSword) && items.get(EnumItem.SteelSword).getAmount() > 0) {
			return EnumItem.SteelSword;
		} else if (items.containsKey(EnumItem.IronSword) && items.get(EnumItem.IronSword).getAmount() > 0) {
			return EnumItem.IronSword;
		} else if (items.containsKey(EnumItem.StoneSword) && items.get(EnumItem.StoneSword).getAmount() > 0) {
			return EnumItem.StoneSword;
		} else {
			return EnumItem.None;
		}
	}
	
	public EnumItem getBestRanged() {
		if (items.containsKey(EnumItem.SteelBow) && items.get(EnumItem.SteelBow).getAmount() > 0) {
			return EnumItem.SteelBow;
		} else if (items.containsKey(EnumItem.IronBow) && items.get(EnumItem.IronBow).getAmount() > 0) {
			return EnumItem.IronBow;
		} else if (items.containsKey(EnumItem.WoodBow) && items.get(EnumItem.WoodBow).getAmount() > 0) {
			return EnumItem.WoodBow;
		} else {
			return EnumItem.None;
		}
	}
	
	public ItemStack getBestAmmo() {
		if (items.containsKey(EnumItem.SteelArrows) && items.get(EnumItem.SteelArrows).getAmount() > 0) {
			return items.get(EnumItem.SteelArrows);
		} else if (items.containsKey(EnumItem.IronArrows) && items.get(EnumItem.IronArrows).getAmount() > 0) {
			return items.get(EnumItem.IronArrows);
		} else if (items.containsKey(EnumItem.StoneArrows) && items.get(EnumItem.StoneArrows).getAmount() > 0) {
			return items.get(EnumItem.StoneArrows);
		} else {
			return null;
		}
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
	
	/**
	 * adds a resource
	 * @param r1 - the resource
	 */
	public void addResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
		Resource r = this.resources.get(er);
		r.addResource(r1);
	}
	
	/**
	 * uses a resource
	 * @param r1 - the resource
	 * @return - true if successful
	 */
	public boolean useResource(Resource r1) {
		EnumResource er = r1.getResourceEnum();
		Resource r = this.resources.get(er);
		return r.subtractResource(r1, false);
	}
	
	/**
	 * uses resources
	 * @param simulate - should the subtraction be done
	 * @param resources - the resources
	 * @return - if simulate weather or not it will work when simulate is false
	 */
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
	
	public boolean craftItem(EnumItem item) {
		if (this.canUseResources(item.getCost())) {
			this.useResources(false, item.getCost());
			this.items.get(item).craft();
			return true;
		}
		return false;
	}
	
	public static StorageManager getInstance() {
		return instance;
	}
}
