// Used character encoding: ISO-8859-1

package com.example.shoppingminiproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
	Context mainActivityContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Setup button listener to enter ItemsDBActivity
		final Button itemsButton = (Button) findViewById(R.id.main_activity_button_item_list);
		itemsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(mainActivityContext, ItemsDBActivity.class);
            	startActivity(intent);
            }
        });
		
		// Setup button listener to enter ShoppingActivity
		final Button shoppingButton = (Button) findViewById(R.id.main_activity_button_shopping_list);
		shoppingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent intent = new Intent(mainActivityContext, ShoppingActivity.class);
            	startActivity(intent);
            }
        });
		
		// Setup button listener to reset and clear database to default items
		final Button resetDBDefaultButton = (Button) findViewById(R.id.main_activity_button_reset_db_default);
		resetDBDefaultButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if((databaseHelper.createNewDB()) != -1) 
            		if(databaseHelper.createDefaultDBRows() == 0) 
            			Toast.makeText(getApplicationContext(),
            		               "Defualt database created with success.", Toast.LENGTH_LONG).show();
            	else 
	            	Toast.makeText(getApplicationContext(),
     		               "Error creating defualt DB!", Toast.LENGTH_LONG).show();
            }
        });
		
		// Setup button listener to reset and clean database to empty database
		final Button resetDBCleanButton = (Button) findViewById(R.id.main_activity_button_reset_db_clean);
		resetDBCleanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if((databaseHelper.createNewDB()) != -1) 
            		Toast.makeText(getApplicationContext(),
  		               "Database reset and cleared with success.", Toast.LENGTH_LONG).show();
            	else 
            		Toast.makeText(getApplicationContext(),
       		               "Error clearing DB!", Toast.LENGTH_LONG).show();
            }
        });
		
		// Setup button listener to finish application
				final Button doneButton = (Button) findViewById(R.id.main_activity_button_done);
				doneButton.setOnClickListener(new View.OnClickListener() {
		            public void onClick(View v) {
		            	finish();
		            }
		        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
