package com.example.traveldiary.model;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.traveldiary.dao.MyContentProvider;
import com.example.traveldiary.dao.NotesTable;

public class Service {
	private static Service instance;

	private Context context;
	NoteEditActivity activity;

	private Service(Context c){
		context = c;
//createSomeObjects();
	}
	
	public static Service getInstance(Context c) {
		if (instance == null) {
			instance = new Service(c);
		} 
		
		return instance;
	}

	
	public void createTravelNote(TravelNote p) {
	
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(NotesTable.COLUMN_TITLE, "TEST TITLE");
		values.put(NotesTable.COLUMN_ADDRESS, "TEST ADDRESS");
		values.put(NotesTable.COLUMN_DESCRIPTION, "TEST DESCRIPTION");
		values.put(NotesTable.COLUMN_AGAIN, "TEST AGAIN");
		values.put(NotesTable.COLUMN_DATE, "TEST DATE");
		resolver.insert(MyContentProvider.CONTENT_URI, values);
	System.out.println("SAVED TO DATABAAAAAAAAAAAAAAAAASEEEEEEEEEEEE");
	}
	
	public void createSomeObjects() {
		TravelNote Paris = new TravelNote("Trip to Paris","Eifel Tower","Very nice place","22-12-2012","Yes");
		TravelNote Milano = new TravelNote("Trip to Milano","Romeo and Juliet","Great House of love","02-02-2012","Yes");
		TravelNote Kopenhavn = new TravelNote("Trip to Kopenhavn","Little Mermaid","Very nice statue","11-02-2012","Yes");
		TravelNote Aarhus = new TravelNote("Trip to Aarhus","Aros","Awesome museum","10-10-2012","No");
		TravelNote Prague = new TravelNote("Trip to Prague","Great Castle","Beautiful castle","13-04-2011","No");
		TravelNote Amsterdam = new TravelNote("Trip to Amsterdam","Anne Frank","House museum","30-11-2009","Yes");
		
//	activity.save(Paris);
//	activity.save(Milano);
//	activity.save(Kopenhavn);
//	activity.save(Aarhus);
		
		
	}
}