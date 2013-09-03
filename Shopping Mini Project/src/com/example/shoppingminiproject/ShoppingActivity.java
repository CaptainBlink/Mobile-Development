// Used character encoding: ISO-8859-1

package com.example.shoppingminiproject;

import java.math.BigDecimal;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ShoppingActivity extends Activity {
	private DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
	private ArrayAdapter<String> adapterForSpinner;
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shopping);
		
		// Setup ListView to show item/shopping list from database
		listView = (ListView) findViewById(R.id.shopping_listview);       
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Toast.makeText(getApplicationContext()
					,"Use top menu spinner and buttons to add/remove item(s) to shopping list,\n\t\t\t\t\t and to mark/unmark item(s) as bought"
			    	, Toast.LENGTH_LONG).show();
			}
		});
		
		// Setup ArrayAdapter for database->spinner
		adapterForSpinner = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, databaseHelper.getItemNamesToSpinner());
		((Spinner) findViewById(R.id.shopping_activity_spinner)).setAdapter(adapterForSpinner);
		        
		// Setup button listener to add new items to shopping list
		final Button addItemToShoppingButton = (Button) findViewById(R.id.shopping_activity_button_add_shopping_item);
		addItemToShoppingButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(((Spinner) findViewById(R.id.shopping_activity_spinner)).getCount() < 1)
            		Toast.makeText(getApplicationContext(),
                		    "There are no items to add", Toast.LENGTH_LONG).show();
				else {
					String selected = ((Spinner) findViewById(R.id.shopping_activity_spinner)).getSelectedItem().toString();
					int shoppingListQuantity = databaseHelper.getShoppingListQuantity(selected);
					if(shoppingListQuantity == -1)
						Toast.makeText(getApplicationContext(),
								"Error adding to shopping list, could not get shopping quantity from database!", Toast.LENGTH_LONG).show();
					else {
						if(databaseHelper.setShoppingListQuantity(selected, ++shoppingListQuantity) == -1)
							Toast.makeText(getApplicationContext(),
									"Error adding to shopping list, error writing shopping list quantity to database!", Toast.LENGTH_LONG).show();
						else {
							BigDecimal errorCode = new BigDecimal("0");
							BigDecimal itemPrice = (errorCode = databaseHelper.getItemPrice(selected));
							if(errorCode == new BigDecimal("-1")) 
								Toast.makeText(getApplicationContext(),
										"Error adding to shopping list, could not get item price from database!", Toast.LENGTH_LONG).show();
							else {
								BigDecimal shoppingPrice = itemPrice.multiply(new BigDecimal(shoppingListQuantity));
								if(databaseHelper.setShoppingPrice(selected, shoppingPrice) == -1)
									Toast.makeText(getApplicationContext(),
											"Error adding to shopping list, error writing shopping price to database!", Toast.LENGTH_LONG).show();
							}
						}			
						calculateAndSetTotals();
						setupListViewArrayAdapter();
					}
				}
		    }
		});
		
		// Setup button listener to remove items from shopping list
		final Button removeItemFromShoppingButton = (Button) findViewById(R.id.shopping_activity_button_remove_shopping_item);
		removeItemFromShoppingButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(((Spinner) findViewById(R.id.shopping_activity_spinner)).getCount() < 1)
            		Toast.makeText(getApplicationContext(),
                		    "There are no items to remove", Toast.LENGTH_LONG).show();
				else {
					String selected = ((Spinner) findViewById(R.id.shopping_activity_spinner)).getSelectedItem().toString();
					int shoppingListQuantity = databaseHelper.getShoppingListQuantity(selected);
					if(shoppingListQuantity == -1)
						Toast.makeText(getApplicationContext(),
								"Error removing item from shopping list, could not get shopping quantity from database!", Toast.LENGTH_LONG).show();
					else if(shoppingListQuantity < 1)
						Toast.makeText(getApplicationContext(),
								"You can only have i positive number of shopping items!", Toast.LENGTH_LONG).show();
					else {
						if(databaseHelper.setShoppingListQuantity(selected, --shoppingListQuantity) == -1)
							Toast.makeText(getApplicationContext(),
									"Error removing item from shopping list, error writing shopping list quantity to database!", Toast.LENGTH_LONG).show();
						else {
							BigDecimal errorCode = new BigDecimal("0");
							BigDecimal itemPrice = (errorCode = databaseHelper.getItemPrice(selected));
							if(errorCode == new BigDecimal("-1")) 
								Toast.makeText(getApplicationContext(),
										"Error removing item from shopping list, could not get item price from database!", Toast.LENGTH_LONG).show();
							else {
								BigDecimal shoppingPrice = itemPrice.multiply(new BigDecimal(shoppingListQuantity));
								if(databaseHelper.setShoppingPrice(selected, shoppingPrice) == -1)
									Toast.makeText(getApplicationContext(),
											"Error removing item from shopping list, error writing shopping price to database!", Toast.LENGTH_LONG).show();
							}
						}			
						calculateAndSetTotals();
						setupListViewArrayAdapter();
					}
				}
		    }
		});
		
		// Setup button listener to mark items as bought
		final Button markBoughtButton = (Button) findViewById(R.id.shopping_activity_button_mark_purchase);
		markBoughtButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(((Spinner) findViewById(R.id.shopping_activity_spinner)).getCount() < 1)
            		Toast.makeText(getApplicationContext(),
                		    "There are no items to mark", Toast.LENGTH_LONG).show();
				else {
					String selected = ((Spinner) findViewById(R.id.shopping_activity_spinner)).getSelectedItem().toString();
					if(-1 == databaseHelper.getShoppingListQuantity(selected))
						Toast.makeText(getApplicationContext(),
								"Error marking item as bought, could not get shopping quantity from database!", Toast.LENGTH_LONG).show();
					else if(0 == databaseHelper.getShoppingListQuantity(selected))
						Toast.makeText(getApplicationContext(),
								"Item must be add to shopping list before it can be marked as bought", Toast.LENGTH_LONG).show();
					else if(1 == databaseHelper.getBoughtStatus(selected))
						Toast.makeText(getApplicationContext(),
								"Item is already marked as bought!", Toast.LENGTH_LONG).show();
					else if(-1 == databaseHelper.getBoughtStatus(selected))
						Toast.makeText(getApplicationContext(),
								"Error reading bought status from database!", Toast.LENGTH_LONG).show();
					else if(-1 == databaseHelper.setMarkBought(selected))
						Toast.makeText(getApplicationContext(),
								"Error setting bought status to database!", Toast.LENGTH_LONG).show();
					calculateAndSetTotals();
					setupListViewArrayAdapter();
				}
		    }
			
		});
		
		// Setup button listener to unmark items as bought
		final Button unmarkBoughtButton = (Button) findViewById(R.id.shopping_activity_button_unmark_purchase);
		unmarkBoughtButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(((Spinner) findViewById(R.id.shopping_activity_spinner)).getCount() < 1)
	            	Toast.makeText(getApplicationContext(),
	               		    "There are no items to unmark", Toast.LENGTH_LONG).show();
				else {
					String selected = ((Spinner) findViewById(R.id.shopping_activity_spinner)).getSelectedItem().toString();
					if(0 == databaseHelper.getBoughtStatus(selected))
						Toast.makeText(getApplicationContext(),
								"Item is already unmarked as not bought!", Toast.LENGTH_LONG).show();
					else if(-1 == databaseHelper.getBoughtStatus(selected))
						Toast.makeText(getApplicationContext(),
								"Error reading bought status from database!", Toast.LENGTH_LONG).show();
					else if(-1 == databaseHelper.setUnmarkBought(selected))
						Toast.makeText(getApplicationContext(),
								"Error setting bought status to database!", Toast.LENGTH_LONG).show();
					calculateAndSetTotals();
					setupListViewArrayAdapter();
				}
		    }
		});
		
		// Setup button listener to end this activity and return to parent activity 
		final Button doneButton = (Button) findViewById(R.id.shopping_activity_button_done);
		doneButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				finish();
		    }
		});
		
		calculateAndSetTotals();
		setupListViewArrayAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shopping, menu);
		return true;
	}
	
	// Setup ArrayAdapter for Database->ListView
	private void setupListViewArrayAdapter() {
		String[] columnTags = new String[] {"column1", "column2", "column3", "column4", "column5", "column6", "column7", "column8"};
		int[] columnIds = new int[] {R.id.shopping_list_column1, R.id.shopping_list_column2, R.id.shopping_list_column3, R.id.shopping_list_column4
				                   , R.id.shopping_list_column5, R.id.shopping_list_column6, R.id.shopping_list_column7, R.id.shopping_list_column8};
		SimpleAdapter arrayAdapter = new SimpleAdapter(this, databaseHelper.getValuesToListView(this, columnTags), R.layout.shopping_list_row
		                                               , columnTags , columnIds);
		listView.setAdapter(arrayAdapter);
	}
	
	// Calculate totals and show amount in TextViews 
	private void calculateAndSetTotals() {
		BigDecimal boughtPrice, shoppingPriceExclBoughtPrice;
		if(new BigDecimal("-1") == (boughtPrice = databaseHelper.getBoughtTotalPrice()))
			Toast.makeText(getApplicationContext(),
					"Error reading bought total price from database!", Toast.LENGTH_LONG).show();
		else if(new BigDecimal("-1") == (shoppingPriceExclBoughtPrice = databaseHelper.getShoppingTotalPrice()))
			Toast.makeText(getApplicationContext(),
					"Error reading shopping total price from database!", Toast.LENGTH_LONG).show();	
		else {
			((TextView)findViewById(R.id.shopping_activity_textview_bought_total_amount)).setText(boughtPrice.toString());
			((TextView)findViewById(R.id.shopping_activity_textview_total_amount)).setText(shoppingPriceExclBoughtPrice.toString());
			((TextView)findViewById(R.id.shopping_activity_textview_total_amount_incl_bought))
				.setText((shoppingPriceExclBoughtPrice.add(boughtPrice)).toString());
		}
	}

}
