package com.example.traveldiary.model;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.traveldiary.dao.MyContentProvider;
import com.example.traveldiary.dao.NotesTable;

public class NotesListFragment extends BaseFragment {
	private ListView listNotes;
	private SimpleCursorAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.activity_notes_list, container,
				false);

		listNotes = (ListView) view.findViewById(R.id.listNotes);
		updateList();
		listNotes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Cursor cursor = adapter.getCursor();
				cursor.moveToPosition(arg2);
				int id = cursor.getInt(cursor
						.getColumnIndex(NotesTable.COLUMN_ID));
				cursor.close();
				Intent intent = new Intent(arg1.getContext(),
						NoteEditActivity.class);
				Bundle b = new Bundle();
				b.putInt("id", id);
				intent.putExtras(b);
				startActivity(intent);

			}

		});

		Button btnFilter = (Button) view.findViewById(R.id.btnFilter);
		btnFilter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				updateListWithWorthVisitingAgain();

			}
		});
		Button button = (Button) view.findViewById(R.id.createItem);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(mActivity.getBaseContext(),
						NoteEditActivity.class);
				intent.putExtra("selectedNote", (TravelNote) null);
				intent.putExtra("testString", "test!");
				startActivityForResult(intent, 1);
			}
		});

		return view;

	}

	private void updateList() {
		Log.d("Note", "Updating list");
		String condition = null;
		String[] args = null;

		Cursor cursor = mActivity.getContentResolver().query(
				MyContentProvider.CONTENT_URI, null, condition, args, null);

		String[] columns = new String[] { NotesTable.COLUMN_TITLE,
				NotesTable.COLUMN_ADDRESS, NotesTable.COLUMN_DATE,
				NotesTable.COLUMN_AGAIN, NotesTable.COLUMN_DESCRIPTION };
		int[] names = new int[] { android.R.id.text1, android.R.id.text2 };

		adapter = new SimpleCursorAdapter(mActivity.getApplicationContext(),
				android.R.layout.simple_list_item_activated_2, cursor, columns,
				names);
		listNotes.setAdapter(adapter);

	}

	
	private void updateListWithWorthVisitingAgain() {
		Log.d("Note", "Updating list");
		String condition = NotesTable.COLUMN_AGAIN + " = ?";
		String[] args = new String[] { "Yes", "yes" };

		Cursor cursor = mActivity.getContentResolver().query(
				MyContentProvider.CONTENT_URI, null, condition, args, null);

		String[] columns = new String[] { NotesTable.COLUMN_TITLE,
				NotesTable.COLUMN_ADDRESS, NotesTable.COLUMN_DATE,
				NotesTable.COLUMN_AGAIN, NotesTable.COLUMN_DESCRIPTION };
		int[] names = new int[] { android.R.id.text1, android.R.id.text2 };

		adapter = new SimpleCursorAdapter(mActivity.getApplicationContext(),
				android.R.layout.simple_list_item_activated_2, cursor, columns,
				names);
		listNotes.setAdapter(adapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("Note", "Subactivity finished, results received");

		updateList();
	}

	public void removeAllItems() {
		getActivity().getContentResolver().delete(
				MyContentProvider.CONTENT_URI, null, null);
	}
	//
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.activity_product_list, menu);
	// return true;
	// }

}
