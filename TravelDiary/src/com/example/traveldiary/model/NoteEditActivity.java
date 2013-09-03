package com.example.traveldiary.model;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.traveldiary.dao.MyContentProvider;
import com.example.traveldiary.dao.NotesTable;

public class NoteEditActivity extends Activity {
	private EditText editTitle, editAddress, editDescription, editDate,
			editAgain;
	private Button buttonSave, buttonMap, buttonRemove;
	private TravelNote note;

	private Integer currentId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_edit);

		editTitle = (EditText) findViewById(R.id.editNoteTitle);
		editAddress = (EditText) findViewById(R.id.editAddress);
		editDescription = (EditText) findViewById(R.id.editDescription);
		editDate = (EditText) findViewById(R.id.editDate);

		editAgain = (EditText) findViewById(R.id.editAgain);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null && bundle.containsKey("id"))
			currentId = bundle.getInt("id");

		if (currentId != null) {

			String where = NotesTable.COLUMN_ID + " = ?";
			String[] param = new String[] { currentId.toString() };

			Cursor cursor = getContentResolver().query(
					MyContentProvider.CONTENT_URI, null, where, param, null);

			if (cursor.getCount() < 1) {
				currentId = null;
			} else {
				cursor.moveToFirst();
				String title = cursor.getString(1);
				String address = cursor.getString(2);
				String desc = cursor.getString(3);
				String date = cursor.getString(4);
				Integer again = cursor.getInt(5);

				editTitle.setText(title);
				editAddress.setText(address);
				editDescription.setText(desc);
				editDate.setText(date);
				editAgain.setText(again);
			}

			cursor.close();
		}

		
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				save();
			}
		});

		buttonRemove = (Button) findViewById(R.id.buttonRemove);
		buttonRemove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				remove();

			}
		});
		buttonMap = (Button) findViewById(R.id.btnMap);
		buttonMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				initializeMap();
			}
		});

	}

	private void fill(TravelNote note) {
		editTitle.setText(note.getTitle());
		editAddress.setText(note.getAddress());
		editDescription.setText(note.getDescription() + "");
		editDate.setText(note.getDate() + "");

		editAgain.setText(note.getAgain());
	}

	public void save() {
		ContentValues values = new ContentValues();
		ContentResolver resolver = getContentResolver();
		
		Log.d("SAVING", "SAVING");
		String[] answer = { "Yes,No,yes,no" };
		String input = editAgain.getText().toString();

		for (String s : answer) {
			if (s.equals(input))
				values.put(NotesTable.COLUMN_AGAIN, s);
			else {
				Toast.makeText(getBaseContext(), "Only Yes or No is allowed !",
						Toast.LENGTH_SHORT).show();
			}
		}
		if (note == null) {

			values.put(NotesTable.COLUMN_TITLE, editTitle.getText().toString());
			values.put(NotesTable.COLUMN_ADDRESS, editAddress.getText()
					.toString());
			values.put(NotesTable.COLUMN_DESCRIPTION, editDescription.getText()
					.toString());
			values.put(NotesTable.COLUMN_AGAIN, editAgain.getText().toString());
			values.put(NotesTable.COLUMN_DATE, editDate.getText().toString());
			resolver.insert(MyContentProvider.CONTENT_URI, values);
		} else {
			Log.d("Note", "Updating one");
			values.put(NotesTable.COLUMN_TITLE, editTitle.getText().toString());
			values.put(NotesTable.COLUMN_ADDRESS, editAddress.getText()
					.toString());
			values.put(NotesTable.COLUMN_DESCRIPTION, editDescription.getText()
					.toString());
			values.put(NotesTable.COLUMN_DATE, editDate.getText().toString());
			resolver.update(MyContentProvider.CONTENT_URI, values,
					NotesTable.COLUMN_ID + " = ?",
					new String[] { note.toString() });
		}

	}

	private void extract() {
		note.setTitle(editTitle.getText().toString());
		note.setAddress(editAddress.getText().toString());
		note.setDescription(editDescription.getText().toString());
		note.setDate(editDate.getText().toString());

		note.setAgain(editAgain.getText().toString());
	}

	public void remove() {
		if (note != null) {
			ContentResolver resolver = getContentResolver();
			resolver.delete(MyContentProvider.CONTENT_URI, NotesTable.COLUMN_ID
					+ " = ?", new String[] { note.toString() });
			finish();
		} else {
			Toast.makeText(getBaseContext(),
					"There is No Travel Note selected", Toast.LENGTH_SHORT)
					.show();
		}

	}

	public void initializeMap() {

		Intent intent = new Intent(this, MapActivity.class);
		Bundle b = new Bundle();
		b.putString("address", editAddress.getText().toString());
		intent.putExtras(b);
		startActivity(intent);

	}
}
