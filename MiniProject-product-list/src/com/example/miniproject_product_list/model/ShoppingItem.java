package com.example.miniproject_product_list.model;

import android.database.Cursor;

public class ShoppingItem {
	private Product product;
	private int amount;
	private boolean bought = false;
	private long id;
	
	public ShoppingItem(Product product, int amount) {
		this.product = product;
		this.amount = amount;
	}
	
	public ShoppingItem(Cursor c) {
		this.id = c.getLong(c.getColumnIndex("_id"));
		this.amount = c.getInt(c.getColumnIndex("amount"));
	}

	public double calculatePrice(){
		return product.getPrice()*amount;
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return product.getName() + "("+amount+") -  "+calculatePrice();
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
