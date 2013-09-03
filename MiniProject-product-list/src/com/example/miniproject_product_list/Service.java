package com.example.miniproject_product_list;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.miniproject_product_list.dao.Dao;
import com.example.miniproject_product_list.model.Cart;
import com.example.miniproject_product_list.model.Product;
import com.example.miniproject_product_list.model.ShoppingItem;

public class Service {
	private static Service instance;
//	private Cart cart;
	private Context context;

	private Service(Context c){
		context = c;
		//createSomeObjects();
	}
	
	public static Service getInstance(Context c) {
		if (instance == null) {
			instance = new Service(c);
		} 
		
		return instance;
	}
	
	public List<Product> getProductList() {
		return Dao.getInstance(context).getAllProducts();
	}

	public List<ShoppingItem> getItems() {
		return Dao.getInstance(context).getAllItems();
	}
	
	public double calculateTotalPrice(){
		double price = 0;
		for(ShoppingItem si : getItems()){
			price += si.calculatePrice();
		}
		
		return price;
	}
	
	private void createSomeObjects() {
		Product banana = new Product("Banananana", "piece", 10, 5, "this");
		Product bananana = new Product("Bananananana", "piece", 10, 5, "this");
		Product bana = new Product("Banananananana", "piece", 10, 5, "this");
		Product apple = new Product("Apple", "piece", 10, 5, "this");
		Product orange = new Product("Orange", "piece", 10, 5, "this");
		Product carrot = new Product("Carrot", "piece", 10, 5, "this");
		Product bread = new Product("Bread", "piece", 10, 5, "this");

		Dao.getInstance(context).createProduct(bana);
		Dao.getInstance(context).createProduct(banana);
		Dao.getInstance(context).createProduct(bananana);
		Dao.getInstance(context).createProduct(apple);
		Dao.getInstance(context).createProduct(orange);
		Dao.getInstance(context).createProduct(carrot);
		Dao.getInstance(context).createProduct(bread);
		
		
	}
}