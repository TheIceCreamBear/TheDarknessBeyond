package com.joseph.thedarknessbeyond.resource;

public enum EnumItem {
	// Clothes
	LeadtherClothes(0.0f, 0, false), // TODO remove???
	FurClothes(1.0f, 1, false, new Resource(EnumResource.Fur, 100)),
	// Armor
	LeatherArmor(1.0f, 2, false, new Resource(EnumResource.Leather, 100)),
	ChainArmor(2.0f, 3, false, new Resource(EnumResource.Iron, 100)),
	PlateArmor(3.0f, 4, false, new Resource(EnumResource.Steel, 100)),
	// Swords
	StoneSword(2.0f, 4, true, new Resource(EnumResource.Stone, 40)),
	IronSword(3.0f, 7, true, new Resource(EnumResource.Iron, 35)),
	SteelSword(4.0f, 12, true, new Resource(EnumResource.Steel, 35)),
	// Bows
	WoodBow(2.0f, 3, true, new Resource(EnumResource.Wood, 75), new Resource(EnumResource.Cotton, 30)),
	IronBow(3.0f, 5, true, new Resource(EnumResource.Iron, 75), new Resource(EnumResource.Cotton, 50)),
	SteelBow(4.0f, 7, true, new Resource(EnumResource.Steel, 75), new Resource(EnumResource.Cotton, 100)),
	// Arrows
	WoodArrows(0.0f, 2, true), // TODO MAKE STATS
	StoneArrows(0.5f, 3, true, new Resource(EnumResource.Stone, 2), new Resource(EnumResource.Wood, 3), new Resource(EnumResource.Cotton, 4)),
	IronArrows(1.0f, 5, true, new Resource(EnumResource.Iron, 2), new Resource(EnumResource.Wood, 3), new Resource(EnumResource.Cotton, 4)),
	SteelArrows(1.5f, 6, true, new Resource(EnumResource.Steel, 2), new Resource(EnumResource.Wood, 4), new Resource(EnumResource.Cotton, 4)),
	// Firearms ONLY FOUND IN WILD
	Pistol(2.0f, 5, true),
	Rifle(6.0f, 10, true),
	SubmachineGun(4.0f, 5, true);
	
	private final float weight;
	private final int mod;
	private final boolean isModDamage;
	private final Resource[] cost;
	
	private EnumItem(float weight, int mod, boolean isModDamage, Resource... cost) {
		this.weight = weight;
		this.mod = mod;
		this.isModDamage = isModDamage;
		this.cost = cost;
	}
	
	public float getWeight() {
		return this.weight;
	}
	
	public int getMod() {
		return this.mod;
	}
	
	public boolean isModDamage() {
		return this.isModDamage;
	}
	
	public Resource[] getCost() {
		return this.cost;
	}
}