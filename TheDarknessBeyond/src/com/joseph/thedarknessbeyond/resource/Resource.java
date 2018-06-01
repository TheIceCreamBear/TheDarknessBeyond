package com.joseph.thedarknessbeyond.resource;

/**
 * object representation of {@link EnumResource}
 * @author Nathan
 *
 */
public class Resource {
	// TODO make generic type
	private EnumResource eResource;
	private int amount = 0;
	
	public Resource() {
		this(EnumResource.Invalid, -1);
	}
	
	public Resource(EnumResource eResource, int amt) {
		this.eResource = eResource;
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
	
	/**
	 * 
	 * @param factor
	 * @return - A new Resource object with the amount of this * factor
	 */
	public Resource multiplyResource(int factor) {
		return new Resource(eResource, amount * factor);
	}
	
	private void add(int a) {
		this.amount += a;
	}
	
	/**
	 * 
	 * @param r1 - the resource
	 * @param simulate - weather or not the resource should actually be subtracted or only the checks should be run only.
	 * @return
	 */
	public boolean subtractResource(Resource r1, boolean simulate) {
		if (this.eResource != r1.eResource) {
			return false;
		}
		if (this.amount >= r1.amount) {
			if (!simulate) {
				this.add(-r1.amount);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.eResource.toString() + ": " + this.amount;
	}
	
	/**
	 * loads a new Resource from the given String. Used in file loading
	 * @param s - the string
	 * @return - the new resource
	 */
	public static Resource fromString(String s) {
		String[] arr = s.split(": ");
		return new Resource(EnumResource.valueOf(arr[0]), Integer.parseInt(arr[1]));
	}
}