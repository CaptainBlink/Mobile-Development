package com.whatToBuy;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	Service service = Service.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		service.run(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void createProduct(View v) {

		Intent intent = new Intent(this, CreateActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("string", "value");
		
		bundle.putInt("requestCode", 1);
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);
	}

	public void updateShoppingList(View v) {

		service.updateShoppingList();

	}

	public void showShoppingList(View v) {
		Intent intent = new Intent(this, ShoppingActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("string", "value2");
		intent.putExtras(bundle);
		startActivityForResult(intent, 1);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 1) {
			Bundle msg = data.getExtras();
			Product product = msg.getParcelable("product");
			ContentValues val = new ContentValues();

			val.put(DatabaseHandler.COLUMN_NAME, product.getName());
			val.put(DatabaseHandler.COLUMN_PRICE, product.getPrice());
			val.put(DatabaseHandler.COLUMN_SHOP, product.getShop());
			val.put(DatabaseHandler.COLUMN_UNIT, product.getUnit());

			service.insertProductInTables(val, 1);

		}
		if (resultCode == RESULT_OK && requestCode == 2) {
			Bundle msg = data.getExtras();
			Product product = msg.getParcelable("product");
			ContentValues val = new ContentValues();

			val.put(DatabaseHandler.COLUMN_ID, product.getId());
			val.put(DatabaseHandler.COLUMN_NAME, product.getName());
			val.put(DatabaseHandler.COLUMN_PRICE, product.getPrice());
			val.put(DatabaseHandler.COLUMN_SHOP, product.getShop());
			val.put(DatabaseHandler.COLUMN_UNIT, product.getUnit());

			service.updateProductInTable(val);
		}
	}
}
