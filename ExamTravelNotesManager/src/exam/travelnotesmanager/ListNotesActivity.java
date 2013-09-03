package exam.travelnotesmanager;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import exam.travelnotesmanager.R;
import exam.travelnotesmanager.contentprovider.NoteContentProvider;
import exam.travelnotesmanager.database.NoteTable;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.TwoLineListItem;

public class ListNotesActivity extends Activity {
	private SimpleCursorAdapter adapter;
	private boolean worthVisiting = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_notes);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		populateList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_new_note:
			Intent intent = new Intent(this, NewNoteActivity.class);
			startActivity(intent);
			return true;
		case R.id.menu_toggle_worth:
			worthVisiting = worthVisiting ? false : true;
			populateList();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void populateList() {
		final ListView listNotes = (ListView) findViewById(R.id.listNotes);
		String where = null;
		String[] param = null;
		if (worthVisiting) {
			where = NoteTable.COLUMN_VISITAGAIN + " = ?";
			param = new String[] { "1" };			
		}
		Cursor cursor = getContentResolver().query(
				NoteContentProvider.CONTENT_URI, null, where, param, null);
		// startManagingCursor(cursor);
		String[] columns = new String[] { NoteTable.COLUMN_TITLE,
				NoteTable.COLUMN_ADDRESS, NoteTable.COLUMN_VISITDATE,
				NoteTable.COLUMN_VISITAGAIN, NoteTable.COLUMN_DESCRIPTION };
		int[] names = new int[] { android.R.id.text1, android.R.id.text2 };
		adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_list_item_activated_2, cursor, columns,
				names);
		listNotes.setAdapter(adapter);
		listNotes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Cursor cursor = adapter.getCursor();
				cursor.moveToPosition(arg2);
				String id = cursor.getString(cursor
						.getColumnIndex(NoteTable.COLUMN_ID));
				//Toast.makeText(getBaseContext(), "ID=" + id, Toast.LENGTH_SHORT).show();
				cursor.close();
				Intent intent = new Intent(arg1.getContext(),
						NewNoteActivity.class);
				Bundle b = new Bundle();
				b.putInt("noteId", Integer.parseInt(id));
				intent.putExtras(b);
				startActivity(intent);
				//finish();
			}

		});

		
	}
	
	private List<Note> createSomeNotes(int n) {
		List<Note> notes = new ArrayList<Note>(n);
		try {
			for (int i = 0; i <= n; i++) {
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				notes.add(new Note(i, "Tree " + i, "over the rainbow",
						"It is pretty", new Date(df.parse("01/01/2000")
								.getTime()), (i % 2 == 0 ? true : false)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notes;
	}
}
