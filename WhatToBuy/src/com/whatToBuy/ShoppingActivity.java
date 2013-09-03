package com.whatToBuy;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class ShoppingActivity extends Activity {

	Service service = Service.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.shopping_list);
		TableLayout tl = (TableLayout) findViewById(R.id.table_shoping_list);

		TextView text = new TextView(this);
		text.setText("Your Products");
		int white = Color.parseColor("#A9E3FF");
		text.setTextColor(white);
		text.setTextSize(35);
		text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1f));
		tl.addView(text);

		service.updateViewShopingList(tl, this);

		TextView total = (TextView) findViewById(R.id.txt_total_price);
		total.append(" " + service.calcTotalPrice());

	}

	public void buy(View v) {
		service.buyProducts();
		this.finish();
	}

	public void continueShopping(View v) {
		this.finish();
	}
}
