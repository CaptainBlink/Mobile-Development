package com.whatToBuy;

import java.util.HashMap;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class Service {

	private DatabaseHandler dbHandler;
	private TableLayout tableLayout, tbLayoutShopping;

	private HashMap<Integer, Integer> quantities = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> compare = new HashMap<Integer, Integer>();

	private Activity activity;

	private static Service instance;

	public static Service getInstance() {

		if (instance == null) {
			instance = new Service();
		}
		return instance;

	}

	private Service() {

	}

	public void buyProducts() {

		dbHandler.delete(DatabaseHandler.SHOPPING_URI, null, null);
		compare = new HashMap<Integer, Integer>();

	}

	public void insertProductInTables(ContentValues values, int table) {
		if (table == DatabaseHandler.PRODUCT) {
			dbHandler.insert(DatabaseHandler.PRODUCTS_URI, values);
		} else if (table == DatabaseHandler.SHOPPING) {

			dbHandler.insert(DatabaseHandler.SHOPPING_URI, values);

		} else
			throw new IndexOutOfBoundsException();
		refreshView();
	}

	public void updateProductInTable(ContentValues values) {

		int id = values.getAsInteger(DatabaseHandler.COLUMN_ID);
		dbHandler.update(DatabaseHandler.PRODUCTS_URI, values,
				DatabaseHandler.COLUMN_ID + "= " + id, null);

		refreshView();
	}

	public void createRow(Product product, Activity activity) {

		// Create a new row to be added.

		TableRow tr = new TableRow(activity);

		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		// Create a Button to be the row-content.

		TextView text = new TextView(activity);

		text.setText(product.getName());

		text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1f));
		text.setTextSize(40);
		int white = Color.WHITE;
		text.setTextColor(white);

		EditText edit = new EditText(activity);
		edit.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1f));

		// Sets the maximum lenght of imput caracters

		int maxLength = 5;
		InputFilter[] x = new InputFilter[1];
		x[0] = new InputFilter.LengthFilter(maxLength);
		edit.setFilters(x);

		edit.setInputType(InputType.TYPE_CLASS_NUMBER);
		// Add Button to row.

		TextView txtId = new TextView(activity);
		txtId.setText(product.getId() + "");
		// Visibility 1 hides the id
		txtId.setVisibility(View.INVISIBLE);

		tr.addView(text, 0);
		tr.addView(edit, 1);
		tr.addView(txtId, 2);

		ItemListener itemListener = new ItemListener(activity, product, tr);

		// Adds listener to table row

		tr.setClickable(true);

		text.setOnClickListener(itemListener);
		text.setOnLongClickListener(itemListener);

		edit.setOnFocusChangeListener(itemListener);

		/* Add row to TableLayout. */

		tableLayout.addView(tr, new TableLayout.LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

	}

	public void createShoppingRow(Product product, String quantity,
			TableLayout tl, Activity sActivity) {

		// Create a new row to be added.

		TableRow tr = new TableRow(activity);

		tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

		// Create a Button to be the row-content.

		TextView text = new TextView(activity);

		text.setText(product.getName() + " : " + quantity);

		text.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT, 1f));
		text.setTextSize(40);
		int white = Color.WHITE;
		text.setTextColor(white);
		// text.setClickable(true);

		// Add Button to row.

		tr.addView(text, 0);

		ItemListener itemListener = new ItemListener(sActivity, product, tr);

		// Add listener to table row
		tr.setClickable(true);
		//
		text.setOnClickListener(itemListener);

		/* Add row to TableLayout. */
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));

	}

	public void updateShoppingList() {

		String[] projection = { DatabaseHandler.COLUMN_ID,
				DatabaseHandler.COLUMN_NAME, DatabaseHandler.COLUMN_PRICE,
				DatabaseHandler.COLUMN_SHOP, DatabaseHandler.COLUMN_UNIT };
		String selection = DatabaseHandler.COLUMN_ID + " =?";
		Integer[] select = quantities.keySet().toArray(new Integer[0]);
		String[] selectionArgs = new String[select.length];

		for (int i = 0; i < select.length; i++) {
			String s = select[i] + "";
			selectionArgs[i] = s;

		}

		for (int i = 0; i < selectionArgs.length; i++) {

			Cursor cursor = dbHandler.query(DatabaseHandler.PRODUCTS_URI,
					projection, selection, new String[] { selectionArgs[i] },
					DatabaseHandler.COLUMN_ID + " ASC");

			if (cursor.moveToFirst()) {

				do {
					int j = Integer.parseInt(cursor.getString(cursor
							.getColumnIndex(DatabaseHandler.COLUMN_ID)));

					ContentValues values = new ContentValues();
					values.put(DatabaseHandler.COLUMN_ID_SHOPPING, cursor
							.getString(cursor
									.getColumnIndex(DatabaseHandler.COLUMN_ID)));

					values.put(
							DatabaseHandler.COLUMN_NAME_SHOPPING,
							cursor.getString(cursor
									.getColumnIndex(DatabaseHandler.COLUMN_NAME)));

					values.put(
							DatabaseHandler.COLUMN_PRICE_SHOPPING,
							cursor.getString(cursor
									.getColumnIndex(DatabaseHandler.COLUMN_PRICE)));
					values.put(
							DatabaseHandler.COLUMN_UNIT_SHOPPING,
							cursor.getString(cursor
									.getColumnIndex(DatabaseHandler.COLUMN_UNIT)));

					values.put(
							DatabaseHandler.COLUMN_SHOP_SHOPPING,
							cursor.getString(cursor
									.getColumnIndex(DatabaseHandler.COLUMN_SHOP)));

					values.put(DatabaseHandler.COLUMN_QUANTITY,
							quantities.get(j));

					if (!compare.containsKey(j)) {
						dbHandler.insert(DatabaseHandler.SHOPPING_URI, values);
						compare.put(j, quantities.get(j));
						Log.d("insert", "aici");
					} else {

						dbHandler.update(DatabaseHandler.SHOPPING_URI, values,
								DatabaseHandler.COLUMN_ID_SHOPPING + " =?",
								new String[] { j + "" });
						Log.d("update", "dincolo");
					}
				} while (cursor.moveToNext());
			}
			cursor.close();

		}

	}

	public void updateViewShopingList(TableLayout tl, Activity sActivity) {

		String[] projection = { DatabaseHandler.COLUMN_ID_SHOPPING,
				DatabaseHandler.COLUMN_NAME_SHOPPING,
				DatabaseHandler.COLUMN_PRICE_SHOPPING,
				DatabaseHandler.COLUMN_UNIT_SHOPPING,
				DatabaseHandler.COLUMN_SHOP_SHOPPING,
				DatabaseHandler.COLUMN_QUANTITY };
		String sortOrder = DatabaseHandler.COLUMN_NAME_SHOPPING + " ASC";
		Cursor cursor = dbHandler.query(DatabaseHandler.SHOPPING_URI,
				projection, null, null, sortOrder);
		if (cursor.moveToFirst()) {
			do {
				String id = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_ID_SHOPPING));
				String name = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_NAME_SHOPPING));
				String price = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_PRICE_SHOPPING));
				String shop = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_SHOP_SHOPPING));
				String unit = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_UNIT_SHOPPING));
				String quantity = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_QUANTITY));

				Product product = new Product(Integer.parseInt(id), name, unit,
						shop, Integer.parseInt(price));

				createShoppingRow(product, quantity, tl, sActivity);

			} while (cursor.moveToNext());
		}
		cursor.close();

	}

	public int calcTotalPrice() {

		int sum = 0;
		String[] projection = { DatabaseHandler.COLUMN_ID_SHOPPING,
				DatabaseHandler.COLUMN_PRICE_SHOPPING,
				DatabaseHandler.COLUMN_QUANTITY };
		String sortOrder = DatabaseHandler.COLUMN_NAME_SHOPPING + " ASC";
		Cursor cursor = dbHandler.query(DatabaseHandler.SHOPPING_URI,
				projection, null, null, sortOrder);
		if (cursor.moveToFirst()) {
			do {

				int price = Integer
						.parseInt(cursor.getString(cursor
								.getColumnIndex(DatabaseHandler.COLUMN_PRICE_SHOPPING)));
				int quantity = Integer.parseInt(cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_QUANTITY)));

				sum = sum + price * quantity;

			} while (cursor.moveToNext());
		}
		cursor.close();
		return sum;

	}

	public void run(Activity activity) {

		this.activity = activity;
		dbHandler = new DatabaseHandler(activity);
		refreshView();

	}

	public void refreshView() {

		tableLayout = (TableLayout) activity.findViewById(R.id.table_main);

		tableLayout.removeAllViews();
		String[] projection = { DatabaseHandler.COLUMN_ID,
				DatabaseHandler.COLUMN_NAME, DatabaseHandler.COLUMN_PRICE,
				DatabaseHandler.COLUMN_UNIT, DatabaseHandler.COLUMN_SHOP, };
		String sortOrder = DatabaseHandler.COLUMN_NAME + " ASC";
		Cursor cursor = dbHandler.query(DatabaseHandler.PRODUCTS_URI,
				projection, null, null, sortOrder);
		if (cursor.moveToFirst()) {
			do {
				String id = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_ID));
				String name = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_NAME));
				String price = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_PRICE));
				String shop = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_SHOP));
				String unit = cursor.getString(cursor
						.getColumnIndex(DatabaseHandler.COLUMN_UNIT));

				Product product = new Product(Integer.parseInt(id), name, unit,
						shop, Integer.parseInt(price));
				createRow(product, activity);

			} while (cursor.moveToNext());
		}
		cursor.close();

	}

	private class ItemListener implements View.OnClickListener,
			View.OnLongClickListener, OnFocusChangeListener {

		Activity activity;
		Product product;
		TableRow tr;
		int value;

		public ItemListener(Activity activity, Product product,
				TableRow tableRow) {
			this.activity = activity;
			this.product = product;
			this.tr = tableRow;
		}

		@Override
		public void onClick(View v) {

			final PopupWindow popWindow;

			// We need to get the instance of the LayoutInflater, use the
			// context of this activity
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// Inflate the view from a predefined XML layout
			View layout = inflater.inflate(R.layout.popup_details,
					(ViewGroup) activity.findViewById(R.id.popup_text));
			// create a 300px width and 470px height PopupWindow
			popWindow = new PopupWindow(layout, 300, 470, true);
			// this closes the PopupWindow by pressing the back button or the
			// background
			popWindow.setBackgroundDrawable(new BitmapDrawable());

			// display the PopupWindow in the center
			popWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

			TextView mResultText = (TextView) layout
					.findViewById(R.id.popup_text);
			mResultText.setText(" Product: " + product.getName() + " Unit: "
					+ product.getUnit() + " Price: " + product.getPrice()
					+ " Shop: " + product.getShop());
			Button cancelButton = (Button) layout
					.findViewById(R.id.btn_popup_OK);

			cancelButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					popWindow.dismiss();
				}
			});

		}

		@Override
		public boolean onLongClick(View v) {
			final PopupWindow popWindow;

			// We need to get the instance of the LayoutInflater, use the
			// context of this activity
			LayoutInflater inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// Inflate the view from a predefined XML layout

			View layout = inflater.inflate(R.layout.popup_buttons,
					(ViewGroup) activity
							.findViewById(R.id.layout_manage_buttons));
			// create a 470px width and 300px height PopupWindow
			popWindow = new PopupWindow(layout, 470, 300, true);
			// this closes the PopupWindow by pressing the back button or the
			// background
			popWindow.setBackgroundDrawable(new BitmapDrawable());
			// display the PopupWindow in the center
			popWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

			Button deleteButton = (Button) layout
					.findViewById(R.id.btn_popup_delete);
			deleteButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					popWindow.dismiss();
					TextView t = (TextView) tr.getChildAt(2);
					int id = Integer.parseInt(t.getText().toString());

					String[] idd = { id + "" };
					dbHandler
							.delete(DatabaseHandler.PRODUCTS_URI, "_id=?", idd);
					refreshView();

				}
			});
			Button updateButton = (Button) layout
					.findViewById(R.id.btn_popup_update);
			updateButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent(activity, CreateActivity.class);
					Bundle bundle = new Bundle();
					bundle.putParcelable("product", product);
					bundle.putInt("requestCode", 2);
					intent.putExtras(bundle);
					activity.startActivityForResult(intent, 2);
					popWindow.dismiss();
				}
			});

			return false;
		}

		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			EditText edit = (EditText) v;
			Log.d("SIZE OF MAP ", quantities.size() + "");
			if (quantities.size() > 3) {

			}
			int i = 0;
			if (!hasFocus) {

				if (edit.getText().toString().length() > 0) {

					quantities.put(product.getId(),
							Integer.parseInt(edit.getText() + ""));

					value = Integer.parseInt(edit.getText().toString());
					i++;
				}

			}

		}

		public int getValues(Service service) {

			return value;
		}
	}

}