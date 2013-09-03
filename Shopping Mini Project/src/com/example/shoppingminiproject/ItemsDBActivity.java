// Used character encoding: ISO-8859-1

package com.example.shoppingminiproject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ItemsDBActivity extends Activity {
	public final static String ADD_OR_EDIT = "com.example.l3_ex4_new_activity.ItemsDBActivity.ADD_OR_EDIT";
	public final static String EDIT_ROW = "com.example.l3_ex4_new_activity.ItemsDBActivity.EDIT_ROW";
	int[] columnIds = new int[] {R.id.items_list_column1, R.id.items_list_column2, R.id.items_list_column3
                               , R.id.items_list_column4, R.id.items_list_column5}; 
	String[] columnTags = new String[] {"column1", "column2", "column3", "column4", "column5"};
	private DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
	private ArrayAdapter<String> adapterForSpinner;
	private Context itemDBActivityContext = this;
	private ListView listView;
	private static int selected;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_items_db);
		
		// Setup ListView to show item list from database
		listView = (ListView) findViewById(R.id.items_listview);       
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
               Toast.makeText(getApplicationContext(),
               "Use top menu spinner and buttons to add/edit/remove item(s)", Toast.LENGTH_LONG).show();
            }
        });
        
        // Setup button listener to add new items in child activity
        final Button addItemButton = (Button) findViewById(R.id.items_activity_button_add);
        addItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	selected = ((Spinner) findViewById(R.id.items_activity_spinner)).getSelectedItemPosition();
            	Intent intent = new Intent(itemDBActivityContext, ItemRowActivity.class);
            	intent.putExtra(ADD_OR_EDIT, "Add");
            	intent.putExtra(EDIT_ROW, "");
            	startActivity(intent);
            }
        });
		
        // Setup button listener to edit items in child activity
		final Button editItemButton = (Button) findViewById(R.id.items_activity_button_edit);
		editItemButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	selected = ((Spinner) findViewById(R.id.items_activity_spinner)).getSelectedItemPosition();
            	if(((Spinner) findViewById(R.id.items_activity_spinner)).getCount() < 1)
            		Toast.makeText(getApplicationContext(),
                		    "There are no data to edit", Toast.LENGTH_LONG).show();
            	else {
	            	String selected = (((Spinner) findViewById(R.id.items_activity_spinner)).getSelectedItem().toString());
	            	if(selected.equals("DATABASE ERROR"))
	            		Toast.makeText(getApplicationContext(),
	            		    "Database error, cannot edit row/field", Toast.LENGTH_LONG).show();
	            	else {
		            	Intent intent = new Intent(itemDBActivityContext, ItemRowActivity.class);
		            	intent.putExtra(ADD_OR_EDIT, "Edit");
		            	intent.putExtra(EDIT_ROW, selected);
		            	startActivity(intent);
	            	}
            	}
            }
        });
		
		// Setup delete button to delete rows/items
		final Button deleteItemsButton = (Button) findViewById(R.id.items_activity_button_delete);
		deleteItemsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	selected = ((Spinner) findViewById(R.id.items_activity_spinner)).getSelectedItemPosition();
            	if(((Spinner) findViewById(R.id.items_activity_spinner)).getCount() < 1)
            		Toast.makeText(getApplicationContext(),
                		    "There are no data to delete", Toast.LENGTH_LONG).show();
            	else {
	            	String selectedStr = (((Spinner) findViewById(R.id.items_activity_spinner)).getSelectedItem().toString());
	            	if(selectedStr.equals("DATABASE ERROR"))
	            		Toast.makeText(getApplicationContext(),
	            		    "Database error, cannot delete row/field", Toast.LENGTH_LONG).show();
	            	else {
	            		if(databaseHelper.deleteRow(selectedStr) == -1)
	            			Toast.makeText(getApplicationContext(),
	                        "Error deleting row!", Toast.LENGTH_LONG).show();	
	            		SimpleAdapter arrayAdapter = new SimpleAdapter(itemDBActivityContext
	            				, databaseHelper.getValuesToListView(itemDBActivityContext, columnTags) 
	            				, R.layout.items_list_row, columnTags , columnIds);
	            		listView.setAdapter(arrayAdapter);
	            		adapterForSpinner = new ArrayAdapter<String>(itemDBActivityContext,
	                    		android.R.layout.simple_spinner_item, databaseHelper.getItemNamesToSpinner());
	            		((Spinner) findViewById(R.id.items_activity_spinner)).setAdapter(adapterForSpinner);
	            		if(selected > 0)
	            			selected--;
	            		((Spinner) findViewById(R.id.items_activity_spinner)).setSelection(selected);
	            	}
            	}
            }
        });
		
		// Setup "Done" button to exit activity and return to parent activity 
		final Button doneButton = (Button) findViewById(R.id.items_activity_button_done);
		doneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	finish();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.items_db, menu);
		return true;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		SimpleAdapter arrayAdapter = new SimpleAdapter(this, databaseHelper.getValuesToListView(this, columnTags) 
				                                       , R.layout.items_list_row, columnTags , columnIds);
		listView.setAdapter(arrayAdapter);
		adapterForSpinner = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, databaseHelper.getItemNamesToSpinner());
		((Spinner) findViewById(R.id.items_activity_spinner)).setAdapter(adapterForSpinner);
		((Spinner) findViewById(R.id.items_activity_spinner)).setSelection(selected);
	}
	
}
