package com.whatToBuy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateActivity extends Activity {
	Product product;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.manage_product);
		if (getIntent().getExtras().getInt("requestCode") == 2) {
			product = getIntent().getExtras().getParcelable("product");
			((EditText) findViewById(R.id.txf_name)).setText(product.getName());
			((EditText) findViewById(R.id.txf_unit)).setText(product.getUnit());
			((EditText) findViewById(R.id.txf_shop)).setText(product.getShop());
			((EditText) findViewById(R.id.txf_price)).setText(product
					.getPrice() + "");
		}

	}

	public void onOK(View v) {

		String name = ((EditText) findViewById(R.id.txf_name)).getText()
				.toString().trim();
		String unit = ((EditText) findViewById(R.id.txf_unit)).getText()
				.toString().trim();
		String shop = ((EditText) findViewById(R.id.txf_shop)).getText()
				.toString().trim();

		double price = Double
				.parseDouble(((EditText) findViewById(R.id.txf_price))
						.getText().toString().trim());

		if (getIntent().getExtras().getInt("requestCode") == 1) {

			product = new Product(-1, name, unit, shop, price);

		}

		else if (getIntent().getExtras().getInt("requestCode") == 2) {
			product.setName(name);
			product.setPrice(price);
			product.setShop(shop);
			product.setUnit(unit);

		}

		Bundle bundle = new Bundle();
		bundle.putParcelable("product", product);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		setResult(Activity.RESULT_OK, intent);

		finish();

	}

	public void onCancel(View v) {
		this.finish();
	}
}
