package com.paad.todolist;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.SimpleCursorAdapter;

public class ToDoListActivity extends Activity implements NewItemFragment.OnNewItemAddedListener,
		LoaderManager.LoaderCallbacks<Cursor> {

	public SimpleCursorAdapter adapter;
	private ToDoListFragment todoListFragment;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Inflate your view
		setContentView(R.layout.main);

		// Get references to the Fragments
		FragmentManager fm = getFragmentManager();
		todoListFragment = (ToDoListFragment) fm.findFragmentById(R.id.TodoListFragment);

		// Create the cursor adapter
		// android.R.layout.simple_list_item_2
		// android.R.layout.simple_list_item_activated_2
		adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_activated_2, null,
				new String[] { ToDoContentProvider.KEY_TASK, ToDoContentProvider.KEY_CREATION_DATE }, 
				new int[] { android.R.id.text1, android.R.id.text2}, 0);

		// Bind the cursor adapter to the ListView.
		todoListFragment.setListAdapter(adapter);

		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		getLoaderManager().restartLoader(0, null, this);
	}

	public void onNewItemAdded(String newItem) {
		ContentResolver resolver = getContentResolver();

		ContentValues values = new ContentValues();
		values.put(ToDoContentProvider.KEY_TASK, newItem);
		values.put(ToDoContentProvider.KEY_CREATION_DATE, SimpleDateFormat.getDateTimeInstance().format(new Date()));

		resolver.insert(ToDoContentProvider.CONTENT_URI, values);
		getLoaderManager().restartLoader(0, null, this);
	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader loader = new CursorLoader(this, ToDoContentProvider.CONTENT_URI, null, null, null, null);
		return loader;
	}

	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		adapter.swapCursor(cursor);
	}

	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}
}