// Author: Nathan Lim

package com.joseph.thedarknessbeyond.resource;
	
public class Resource {
	private EnumResource eResource;
	private int amount = 0;
	
	public Resource() {
		this(EnumResource.Invalid, -1);
	}
	
	public Resource(EnumResource eResource1, int amt) {
		this.eResource = eResource1;
		this.amount += amt;
	}
	 
	public int getAmount() {
		return this.amount;
	}
	
	public EnumResource getResourceEnum() {
		return this.eResource;
	}
	
	public void addResource(Resource r1) {
		if (this.eResource == r1.eResource) {
			add(r1.amount);
		}
	}
	
	private void add(int a) {
		this.amount += a;
	}
	
	public boolean subtractResource(Resource r1) {
		if (this.eResource != r1.eResource) {
			return false;
		}
		if (this.amount > r1.amount) {
			this.add(-r1.amount);
			return true;
		} else {
			return false;
		}
	}
	
}
