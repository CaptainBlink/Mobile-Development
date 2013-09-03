package com.example.miniproject_product_list.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.miniproject_product_list.model.Product;
import com.example.miniproject_product_list.model.ShoppingItem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {
	private SQLiteDatabase database;
	private DbHelper dbHelper;

	private static Dao instance;

	private Dao(Context c) {
		dbHelper = new DbHelper(c);
		database = dbHelper.getWritableDatabase();
	}

	public static Dao getInstance(Context c) {
		if (instance == null)
			instance = new Dao(c);

		return instance;
	}

	public Product createProduct(Product p) {
		ContentValues values = new ContentValues();
		values.put("name", p.getName());
		values.put("unit", p.getUnit());
		values.put("quantity", p.getQuantity());
		values.put("price", p.getPrice());
		values.put("shop", p.getShop());

		long id = database.insert("products", null, values);

		p.setId(id);

		return p;
	}
	
	public ShoppingItem createItem(ShoppingItem si) {
		ContentValues values = new ContentValues();
		values.put("amount", si.getAmount());
		values.put("product_id", si.getProduct().getId());

		long id = database.insert("items", null, values);

		si.setId(id);

		return si;
	}
	
	public List<ShoppingItem> getAllItems() {
		ArrayList<ShoppingItem> items = new ArrayList<ShoppingItem>();

		Cursor cursor = database.query("items", null, null, null, null,
				null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			ShoppingItem si = new ShoppingItem(cursor);
			si.setProduct(getProduct(cursor.getInt(cursor.getColumnIndex("product_id"))));
			items.add(si);
			cursor.moveToNext();
		}

		return items;
	}
	
	public ShoppingItem deleteItem(ShoppingItem si){
		database.delete("items", "_id = ?", new String[] {si.getId() + ""});

		return si;
	}

	public List<Product> getAllProducts() {
		ArrayList<Product> products = new ArrayList<Product>();

		Cursor cursor = database.query("products", null, null, null, null,
				null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Product p = new Product(cursor);
			products.add(p);
			cursor.moveToNext();
		}

		return products;
	}

	public Product getProduct(int id) {
		Cursor cursor = database.query("products", null, "_id = ?",
				new String[] { id + "" }, null, null, null, 1 + "");
		cursor.moveToFirst();
		return new Product(cursor);
	}
	
	public Product updateProduct(Product p){
		ContentValues values = new ContentValues();
		values.put("name", p.getName());
		values.put("unit", p.getUnit());
		values.put("quantity", p.getQuantity());
		values.put("price", p.getPrice());
		values.put("shop", p.getShop());
		
		int a = database.update("products", values, "_id = ?", new String[]{p.getId()+""});
		Log.d("note", "Affected rows: " + a);
		
		return p;
	}
	
	public Product deleteProduct(Product p){
		database.delete("products", "_id = ?", new String[] {p.getId() + ""});

		return p;
	}

	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}

	public void close() {
		dbHelper.close();
	}
}