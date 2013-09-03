// Used character encoding: ISO-8859-1

package com.example.shoppingminiproject;

import java.math.BigDecimal;
import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ItemRowActivity extends Activity {
	ItemRowActivity itemRowActivity;
	DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
	ArrayList<String> rowData = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_row);
		final Intent intent = getIntent();
			
		// Get data from ItemsDBActivity
		if(intent.getStringExtra(ItemsDBActivity.ADD_OR_EDIT).equals("Edit")) {
			rowData = databaseHelper.getRowDataForItem(intent.getStringExtra(ItemsDBActivity.EDIT_ROW));
			((EditText)findViewById(R.id.item_row_activity_edittext_item)).setText(rowData.get(0));
			((EditText)findViewById(R.id.item_row_activity_edittext_quantity)).setText(rowData.get(1));
			((EditText)findViewById(R.id.item_row_activity_edittext_unit)).setText(rowData.get(2));
			((EditText)findViewById(R.id.item_row_activity_edittext_price)).setText(rowData.get(3));
			((EditText)findViewById(R.id.item_row_activity_edittext_shop)).setText(rowData.get(4));
		}
		
		// Setup button listener to transfer data to database and end activity
		final Button doneButton = (Button) findViewById(R.id.item_row_activity_button_done);
		doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if((((EditText)findViewById(R.id.item_row_activity_edittext_item)).getText().toString()).equals("")) {
            		Toast.makeText(getApplicationContext(),
            	               "Field Item must contain a name", Toast.LENGTH_LONG).show();
            		((EditText)findViewById(R.id.item_row_activity_edittext_item)).requestFocus();
            	}
            	else if(-1 == isStringInteger(((EditText)findViewById(R.id.item_row_activity_edittext_quantity)).getText().toString())) {
            		Toast.makeText(getApplicationContext(),
            	               "Field Quantity must be an integer number", Toast.LENGTH_LONG).show();
            		((EditText)findViewById(R.id.item_row_activity_edittext_quantity)).requestFocus();
            	}
            	else if(-1 == isStringNumber(((EditText)findViewById(R.id.item_row_activity_edittext_price)).getText().toString())) {
            		Toast.makeText(getApplicationContext(),
            	               "Field Price must be a number", Toast.LENGTH_LONG).show();
            		((EditText)findViewById(R.id.item_row_activity_edittext_price)).requestFocus();
            	}
            	else if(intent.getStringExtra(ItemsDBActivity.ADD_OR_EDIT).equals("Add")) { // Add new row/item to DB
            		if(-1 == databaseHelper.addNewItemToDB(
            			((EditText)findViewById(R.id.item_row_activity_edittext_item)).getText().toString(), 
            			Integer.parseInt(((EditText)findViewById(R.id.item_row_activity_edittext_quantity)).getText().toString()), 
            			((EditText)findViewById(R.id.item_row_activity_edittext_unit)).getText().toString(), 
            			new BigDecimal(((EditText)findViewById(R.id.item_row_activity_edittext_price)).getText().toString()), 
            			((EditText)findViewById(R.id.item_row_activity_edittext_shop)).getText().toString(), 0, 0, new BigDecimal("0")))
            				Toast.makeText(getApplicationContext(),
            					"\tError trying to write data to database!\n (possible reason: Item name must be unique)", Toast.LENGTH_LONG).show();
            		else
                    	finish();
            	}
            	else if(intent.getStringExtra(ItemsDBActivity.EDIT_ROW). // Has column ITEM been changed (Edit mode) ?
            	   equals(((EditText)findViewById(R.id.item_row_activity_edittext_item)).getText().toString())) {
            		if(-1 == databaseHelper.updateItemRowData(
            			intent.getStringExtra(ItemsDBActivity.EDIT_ROW),
            			((EditText)findViewById(R.id.item_row_activity_edittext_item)).getText().toString(), 
            			Integer.parseInt(((EditText)findViewById(R.id.item_row_activity_edittext_quantity)).getText().toString()), 
            			((EditText)findViewById(R.id.item_row_activity_edittext_unit)).getText().toString(), 
            			new BigDecimal(((EditText)findViewById(R.id.item_row_activity_edittext_price)).getText().toString()), 
            			((EditText)findViewById(R.id.item_row_activity_edittext_shop)).getText().toString())) 
            				Toast.makeText(getApplicationContext(),
         	                     "Error trying to update database!", Toast.LENGTH_LONG).show();
            		else
            			finish();
            	} else { // Column ITEM has been changed (Edit mode)
            		if(-1 == databaseHelper.deleteRow(intent.getStringExtra(ItemsDBActivity.EDIT_ROW)) ||
            		   -1 == databaseHelper.addNewItemToDB(
            				((EditText)findViewById(R.id.item_row_activity_edittext_item)).getText().toString(), 
            				Integer.parseInt(((EditText)findViewById(R.id.item_row_activity_edittext_quantity)).getText().toString()), 
            				((EditText)findViewById(R.id.item_row_activity_edittext_unit)).getText().toString(), 
            				new BigDecimal(((EditText)findViewById(R.id.item_row_activity_edittext_price)).getText().toString()), 
            				((EditText)findViewById(R.id.item_row_activity_edittext_shop)).getText().toString(), 0, 0, new BigDecimal("0")))
            					Toast.makeText(getApplicationContext(),
            					     "Error trying to update database!", Toast.LENGTH_LONG).show();
            		else
            			finish();
            	}
            }
        });
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.item_row, menu);
		return true;
	}
	
	private int isStringNumber(String str) {
		int errorCode = 0;
		try {  
			Double.parseDouble(str);  
		} catch(Exception e) {   
		    errorCode = -1;  
		}
	    return errorCode;
	}
	
	private int isStringInteger(String str) {
		int errorCode = 0;
		try {
			Integer.parseInt(str);
		} catch(Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}

}
