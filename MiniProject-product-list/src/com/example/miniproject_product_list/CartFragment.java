package com.example.miniproject_product_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.miniproject_product_list.dao.Dao;
import com.example.miniproject_product_list.model.ShoppingItem;

public class CartFragment extends BaseFragment{
	private ListView listCart;
	private ArrayAdapter<ShoppingItem> adapter;
	private TextView total;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.fragment_shopping_list, container,
				false);
		
		
		listCart = (ListView) view.findViewById(R.id.listCart);
		total = (TextView) view.findViewById(R.id.totalPrice);
		updateList();
		

		listCart.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				ShoppingItem it = (ShoppingItem) listCart.getItemAtPosition(arg2);
				it.setBought(true);
				Dao.getInstance(mActivity).deleteItem(it);
				updateList();
			}
		});
		
		Button button = (Button) view.findViewById(R.id.createShoppingItem);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mActivity.getBaseContext(),
						ItemEditActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
		return view;
	}
	
	private void updateList() {
		Log.d("Note", "Updating list");

		adapter = new ArrayAdapter<ShoppingItem>(mActivity,
				android.R.layout.simple_list_item_1, android.R.id.text1,
				Dao.getInstance(mActivity).getAllItems());
		listCart.setAdapter(adapter);
		
		total.setText(Service.getInstance(mActivity).calculateTotalPrice()+"");

	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("Note", "Subactivity finished, results received");

		updateList();
	}
	
	
}
