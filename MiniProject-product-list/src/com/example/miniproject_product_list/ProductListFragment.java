package com.example.miniproject_product_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.miniproject_product_list.model.Product;

public class ProductListFragment extends BaseFragment {
	private ListView listProducts;
	private ArrayAdapter<Product> adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_product_list, container,
				false);

		listProducts = (ListView) view.findViewById(R.id.listProducts);
		updateList();

		listProducts.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Intent intent = new Intent(mActivity.getBaseContext(),
						ProductEditActivity.class);
				intent.putExtra("selectedProduct",
						(Product) listProducts.getItemAtPosition(arg2));
				intent.putExtra("testString", "test!");
				startActivityForResult(intent, 1);
			}

		});

		Button button = (Button) view.findViewById(R.id.createItem);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mActivity.getBaseContext(),
						ProductEditActivity.class);
				intent.putExtra("selectedProduct", (Product) null);
				intent.putExtra("testString", "test!");
				startActivityForResult(intent, 1);
			}
		});

		return view;
	}

	private void updateList() {
		Log.d("Note", "Updating list");

		adapter = new ArrayAdapter<Product>(mActivity,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				Service.getInstance(mActivity).getProductList());
		listProducts.setAdapter(adapter);

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("Note", "Subactivity finished, results received");

		updateList();
	}
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.activity_product_list, menu);
	// return true;
	// }
}
