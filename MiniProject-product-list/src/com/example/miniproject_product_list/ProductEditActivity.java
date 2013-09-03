package com.example.miniproject_product_list;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.miniproject_product_list.dao.Dao;
import com.example.miniproject_product_list.model.Product;

public class ProductEditActivity extends Activity {
	private EditText editProductName, editUnit, editQuantity, editPrice, editShop;
	private Product product;
	//private boolean 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product_edit);	
		
		editProductName = (EditText) findViewById(R.id.editProductName);
		editUnit = (EditText) findViewById(R.id.editUnit);
		editQuantity = (EditText) findViewById(R.id.editQuantity);
		editPrice = (EditText) findViewById(R.id.editPrice);
		editShop = (EditText) findViewById(R.id.editShop);
		
		Bundle bundle = this.getIntent().getExtras();
		if (bundle.getSerializable("selectedProduct") != null){
			Product product = (Product) bundle.getSerializable("selectedProduct");
			Toast toast = Toast.makeText(ProductEditActivity.this, product.toString(), Toast.LENGTH_LONG);
			toast.show();
			this.product = product;
			fill(product);
		} else {
			Toast toast = Toast.makeText(ProductEditActivity.this, "No product selected", Toast.LENGTH_LONG);
			toast.show();
		}
	}

	
	private void fill(Product product) {
		editProductName.setText(product.getName());
		editUnit.setText(product.getUnit());
		editQuantity.setText(product.getQuantity() + "");
		editPrice.setText(product.getPrice() + "");
		editShop.setText(product.getShop());
	}
	
	public void save(View view) {
		Log.d("Note", "Amount before save: "+Service.getInstance(this).getProductList().size());
		if (product == null) {
			//new
			Log.d("Note", "Saving new one");
			product = new Product();
			extract();
//			Service.getInstance(this).getProductList().add(product);
			Dao.getInstance(this).createProduct(product);
		} else {
			//edit
			Log.d("Note", "Updating one");
			extract();
			Service.getInstance(this).getProductList().add(product);
			Dao.getInstance(this).updateProduct(product);
		}
		
		Log.d("Note", "Amount after save: "+Service.getInstance(this).getProductList().size());
		this.setResult(RESULT_OK);
		finish();
	}


	private void extract() {
		product.setName(editProductName.getText().toString());
		product.setUnit(editUnit.getText().toString());
		product.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
		product.setPrice(Double.parseDouble(editPrice.getText().toString()));
		product.setShop(editShop.getText().toString());
	}
	
	public void remove(View view) {
		Service.getInstance(this).getProductList().remove(product);
		finish();
	}
}
