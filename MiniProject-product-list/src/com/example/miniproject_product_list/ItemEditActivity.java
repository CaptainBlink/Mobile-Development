package com.example.miniproject_product_list;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.miniproject_product_list.dao.Dao;
import com.example.miniproject_product_list.model.Product;
import com.example.miniproject_product_list.model.ShoppingItem;

public class ItemEditActivity extends Activity {
	private EditText amount;
	private Spinner products;
	private ShoppingItem item;
	//private boolean 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_edit_layout);	
		
		amount = (EditText) findViewById(R.id.itemAmount);
		products = (Spinner) findViewById(R.id.spinnerProducts);
		
		ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				Service.getInstance(this).getProductList());
		products.setAdapter(adapter);
	}

	
	public void save(View view) {
			//new
			Log.d("Note", "Saving new one");
			item = new ShoppingItem((Product) products.getSelectedItem(), Integer.parseInt(amount.getText().toString()));
			Dao.getInstance(this).createItem(item);
		
		Log.d("Note", "Amount after save: "+Service.getInstance(this).getProductList().size());
		this.setResult(RESULT_OK);
		finish();
	}
}