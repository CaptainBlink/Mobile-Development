package com.example.miniproject_product_list.model;

import java.io.Serializable;

import android.database.Cursor;

public class Product implements Serializable {
	private static final long serialVersionUID = 5071161021295591959L;
	private long id;
	private String name;
	private String unit = "piece";
	private int quantity = 0;
	private double price = 0;
	private String shop = "";
	
	public Product() {
	}
	
	public Product(Cursor c){
		this.id = c.getLong(c.getColumnIndex("_id"));
		this.name = c.getString(c.getColumnIndex("name"));
		this.unit = c.getString(c.getColumnIndex("unit"));
		this.quantity = c.getInt(c.getColumnIndex("quantity"));
		this.price = c.getDouble(c.getColumnIndex("price"));
		this.shop = c.getString(c.getColumnIndex("shop"));
	}
	 
	public Product(String name, String unit, int quantity, double price,
			String shop) {
		super();
		this.name = name;
		this.unit = unit;
		this.quantity = quantity;
		this.price = price;
		this.shop = shop;
	}
	
	@Override
	public String toString() {
		return name + " " + quantity + " " + unit + " " + price;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
