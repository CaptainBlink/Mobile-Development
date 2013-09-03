package exam.travelnotesmanager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import exam.travelnotesmanager.contentprovider.NoteContentProvider;
import exam.travelnotesmanager.database.NoteTable;

public class NewNoteActivity extends Activity {
	private Integer currentNoteId;
	private EditText editTitle;
	private EditText editAddress;
	private EditText editDesc;
	private EditText editDate;
	private DatePicker dateVisit;
	private CheckBox checkBoxVisitAgain;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_note);
		initFields();
		Bundle b = getIntent().getExtras();
		if (b != null && b.containsKey("noteId"))
				currentNoteId = b.getInt("noteId");
		
		if (currentNoteId != null) {
			
			String where = NoteTable.COLUMN_ID + " = ?";
			String[] param = new String[] { currentNoteId.toString() };
			
			Cursor cursor = getContentResolver().query(
					NoteContentProvider.CONTENT_URI, null, where, param, null);

			if (cursor.getCount() < 1) {
				currentNoteId = null;
			} else {
				cursor.moveToFirst();
				String title = cursor.getString(1);
				String address = cursor.getString(2);
				String desc = cursor.getString(3);
				String date = cursor.getString(4);
				Integer visitAgain = cursor.getInt(5);
				
				editTitle.setText(title);
				editAddress.setText(address);
				editDesc.setText(desc);
				if (date != null) {
					final Calendar c = Calendar.getInstance();
				    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    try {
						c.setTime(sdf.parse(date));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			 
					// set current date into datepicker
					dateVisit.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), null);
				}
				checkBoxVisitAgain.setChecked(visitAgain != null && visitAgain == 1 ? true : false);
			}
			
			cursor.close();
		}
	}

	private void initFields() {
		editTitle = (EditText) findViewById(R.id.editTitle);
		editAddress = (EditText) findViewById(R.id.editAddress);
		editDesc = (EditText) findViewById(R.id.editDesc);
		editDate = (EditText) findViewById(R.id.editDate);
		dateVisit = (DatePicker) findViewById(R.id.dateVisit);
		checkBoxVisitAgain = (CheckBox) findViewById(R.id.checkBoxVisitAgain);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_note, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
        case R.id.menu_new_note:
            saveNote();
            return true;
        case R.id.menu_edit_note:
            test();
            return true;
        case R.id.menu_delete_note:
        	deleteNote();
        	return true;
        case R.id.menu_show_map:
        	showMap();
        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	private void showMap() {
		Intent intent = new Intent(this, MapActivity.class);
		Bundle b = new Bundle();
		b.putString("address", editAddress.getText().toString());
		intent.putExtras(b);
		startActivity(intent);
	}
	
	private void deleteNote() {
		if (currentNoteId != null) {
			ContentResolver resolver = getContentResolver();
			resolver.delete(NoteContentProvider.CONTENT_URI, NoteTable.COLUMN_ID + " = ?", new String[] { currentNoteId.toString() });
			finish();
		} else {
			Toast.makeText(getBaseContext(), "No Travel Note selected", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void saveNote() {/*
		final EditText editTitle = (EditText) findViewById(R.id.editTitle);
		final EditText editAddress = (EditText) findViewById(R.id.editAddress);
		final EditText editDesc = (EditText) findViewById(R.id.editDesc);
		final EditText editDate = (EditText) findViewById(R.id.editDate);
		final DatePicker dateVisit = (DatePicker) findViewById(R.id.dateVisit);
		final CheckBox checkBoxVisitAgain = (CheckBox) findViewById(R.id.checkBoxVisitAgain);*/
		
		if (currentNoteId == null) {
			
			ContentResolver resolver = getContentResolver();
			ContentValues values = new ContentValues();
			values.put(NoteTable.COLUMN_TITLE, editTitle.getText().toString());
			values.put(NoteTable.COLUMN_ADDRESS, editAddress.getText().toString());
			values.put(NoteTable.COLUMN_DESCRIPTION, editDesc.getText().toString());
			values.put(NoteTable.COLUMN_VISITAGAIN, (checkBoxVisitAgain.isChecked() ? 1 : 0));
			values.put(NoteTable.COLUMN_VISITDATE, editDate.getText().toString());

			resolver.insert(NoteContentProvider.CONTENT_URI, values);
		} else {

			ContentResolver resolver = getContentResolver();
			ContentValues values = new ContentValues();
			values.put(NoteTable.COLUMN_TITLE, editTitle.getText().toString());
			values.put(NoteTable.COLUMN_ADDRESS, editAddress.getText().toString());
			values.put(NoteTable.COLUMN_DESCRIPTION, editDesc.getText().toString());
			values.put(NoteTable.COLUMN_VISITAGAIN, (checkBoxVisitAgain.isChecked() ? 1 : 0));
			//values.put(NoteTable.COLUMN_VISITDATE, editDate.getText().toString());
			values.put(NoteTable.COLUMN_VISITDATE, dateVisit.getYear() + "-" + dateVisit.getMonth() + "-" + dateVisit.getDayOfMonth());
			new Date(dateVisit.getYear(), dateVisit.getMonth(), dateVisit.getDayOfMonth());
			
			resolver.update(NoteContentProvider.CONTENT_URI, values, NoteTable.COLUMN_ID + " = ?", new String[] { currentNoteId.toString() });
		}
		finish();
	}
	
	private void test() {
		// Queries the user dictionary and returns results
		/*Cursor mCursor = getContentResolver().query(
			NoteContentProvider.CONTENT_URI,   // The content URI of the words table
		    new String[] { NoteTable.COLUMN_ID, NoteTable.COLUMN_TITLE },                        // The columns to return for each row
		    "",             // Selection criteria
		    new String[] {""},                     // Selection criteria
		    "");                        // The sort order for the returned rows
		while (mCursor.moveToNext()) {
			System.out.println(mCursor.getString(1));
		}*/
		

		ContentResolver resolver = getContentResolver();

		ContentValues values = new ContentValues();
		values.put(NoteTable.COLUMN_TITLE, "TIIITUUUULJ!!!");
		values.put(NoteTable.COLUMN_ADDRESS, "TAAAAM!!!");
		values.put(NoteTable.COLUMN_VISITAGAIN, "TAAAAM!!!");
		values.put(NoteTable.COLUMN_VISITDATE, SimpleDateFormat.getDateTimeInstance().format(new Date()));

		resolver.insert(NoteContentProvider.CONTENT_URI, values);
		//AGOOOOOOOOOOOOOONJ!
		
		
		//getLoaderManager().restartLoader(0, null, this);
		
		/*ContentValues contentValues = new ContentValues();
		contentValues.put(NoteTable.COLUMN_TITLE, "TITUL");
		contentValues.put(NoteTable.COLUMN_ADDRESS, "GDETA");
		contentValues.put(NoteTable.COLUMN_VISITAGAIN, 0);
		NoteDatabaseHelper hlp = new NoteDatabaseHelper(getBaseContext());
		SQLiteDatabase db = hlp.getWritableDatabase();
		db.insert(NoteTable.TABLE_NOTE, null, contentValues);*/
	}
}
