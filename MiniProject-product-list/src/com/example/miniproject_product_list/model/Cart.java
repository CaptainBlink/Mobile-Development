package com.example.miniproject_product_list.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
	private List<ShoppingItem> items;

	public Cart() {
		items = new ArrayList<ShoppingItem>();
	}
	
	public double getTotalPrice(){
		double p = 0;
		
		for(ShoppingItem si : items)
			p += si.calculatePrice();
		
		return p;
	}

	public List<ShoppingItem> getItems() {
		return items;
	}

	public void setItems(List<ShoppingItem> items) {
		this.items = items;
	}
	
	
	
}
