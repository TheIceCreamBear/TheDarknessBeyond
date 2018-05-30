package com.joseph.thedarknessbeyond.resource;

public class ItemStack {
	private EnumItem item;
	private int amount;
	
	public ItemStack(EnumItem item, int amount) {
		this.item = item;
		this.amount = amount;
	}
	
	public EnumItem getItem() {
		return this.item;
	}
	
	public int getAmount() {
		return this.amount;
	}
	
	public void add(ItemStack i) {
		if (this.item == i .item) {
			this.add(i.getAmount());
		}
	}
	
	private void add(int i) {
		this.amount += i;
	}
	
	public boolean subtractItem(ItemStack i, boolean simulate) {
		if (this.item != i.item) {
			return false;
		}
		
		if (this.amount >= i.amount) {
			if (!simulate) {
				this.add(-i.getAmount());
			}
			return true;
		} else {
			return false;
		}
	}
	
	public float getWeight() {
		return this.item.getWeight() * this.amount;
	}

	@Override
	public String toString() {
		return this.item.toString() + ": " + this.amount;
	}
	
	public static ItemStack fromString(String s) {
		String[] arr = s.split(": ");
		return new ItemStack(EnumItem.valueOf(arr[0]), Integer.parseInt(arr[1]));
	}
}