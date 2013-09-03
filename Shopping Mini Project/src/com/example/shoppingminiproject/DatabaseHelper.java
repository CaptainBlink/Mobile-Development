// Used character encoding: ISO-8859-1

package com.example.shoppingminiproject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "SHOPPING_APP";
	private static final String TABLE_NAME = "ITEMS_AND_SHOPPING_TABLE";
	private final Context myContext;
	private static DatabaseHelper myInstance;
	private SQLiteDatabase db = null;
	
	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
		this.myContext = context;
	}

	public static DatabaseHelper getInstance(Context context) {
		if (myInstance == null)  
			myInstance = new DatabaseHelper(context);
		return myInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) { 
		this.db = db;
		createNewDB();
		createDefaultDBRows();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
		onCreate(db);
	}
	
	public int createNewDB() {
		int errorCode = 0; // I use C-style error codes: 0 for success and -1 (typically) or any other integer value for failure(s)
		if(!db.isOpen())
			db = this.getWritableDatabase();
		try { 
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			db.execSQL("CREATE TABLE " + TABLE_NAME + "("
					+ "ITEM" + " TEXT PRIMARY KEY,"
	                + "QUANTITY" + " INTEGER,"
					+ "UNIT" + " TEXT,"
	                + "PRICE" + " INTEGER," // Column PRICE is 'integer packed decimal'
					+ "SHOP" + " TEXT,"
	                + "SHOPPING_LIST_QUANTITY" + " INTEGER,"
					+ "PURCHASED" + " INTEGER,"   // Column PURCHASED is used as a boolean
					+ "SHOPPING_PRICE" + " INTEGER)");
		} catch (Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}
	
	public int addNewItemToDB(String item, int quantity, String unit, BigDecimal price
			                  ,String shop, int shoppingQuantity, int purchased, BigDecimal shoppingPrice) {
		int errorCode = 0;
		if(!db.isOpen())
			db = this.getWritableDatabase();
        try {
        	ContentValues values = new ContentValues();
	        values.put("ITEM", item); 
	        values.put("QUANTITY", quantity); 
	        values.put("UNIT", unit);
	        values.put("PRICE", price.multiply(new BigDecimal("100")).toBigInteger().intValue()); // Encode to 'integer packed decimal'
	        values.put("SHOP", shop);
	        values.put("SHOPPING_LIST_QUANTITY", shoppingQuantity);
	        values.put("PURCHASED", purchased);
	        values.put("SHOPPING_PRICE", shoppingPrice.multiply(new BigDecimal("100")).toBigInteger().intValue()); // Encode to 'integer packed decimal'
	        errorCode = db.insertOrThrow(TABLE_NAME, null, values) == -1 ? 1 : 0;
        } catch (Exception e) {
        	errorCode = -1;
        }
        return errorCode;
	}
	
	public ArrayList<HashMap<String, String>> getValuesToListView(Object caller, String[] columnTags) {
		ArrayList<HashMap<String, String>> itemList = new ArrayList<HashMap<String, String>>();
		db = this.getWritableDatabase();
	    try {
	    	boolean isShoppingActivity = caller.getClass().getSimpleName().equals("ShoppingActivity");
		    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		    while(cursor.moveToNext()) {
		    	HashMap<String,String> map = new HashMap<String, String>();
		    	for(int i = 0; i < 5 + 3 * (isShoppingActivity ? 1 : 0) ; i++) {
		    		if(i == 1 ||  i == 5) // Column is QUANTIRY or SHOPPING_LIST_QUANTITY or 
		    			map.put(columnTags[i], Integer.toString(cursor.getInt(i)));
		            else if(i == 3 || i == 7) // Column is PRICE (an 'integer packed decimal' value)
		            	map.put(columnTags[i], (new BigDecimal(cursor.getInt(i)).divide(new BigDecimal("100"))).setScale(2).toString());
		            else if(i == 6) // Column is PURCHACED
		            	map.put(columnTags[i], cursor.getInt(i) == 0 ? "No" : "Yes");
		            else // Column is ITEM, UNIT or SHOP
		            	map.put(columnTags[i], "" + cursor.getString(i));
		        }
		    	itemList.add(map);
		    }
	    } catch (Exception e) {
	    	HashMap<String,String> map = new HashMap<String, String>();
	      	itemList.clear();
	       	for(int i = 0; i < 5; i++)
	       		map.put(columnTags[i], "DATABASE ERROR");
	       	itemList.add(map);
	    }
        return itemList;
	}
	
	public ArrayList<String> getItemNamesToSpinner() {
		ArrayList<String> itemList = new ArrayList<String>();
		db = this.getWritableDatabase();
	    try {
	    	Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
		    while(cursor.moveToNext()) 
		      	itemList.add(cursor.getString(0));
	    } catch (Exception e) {
	       	itemList.clear();
	       	itemList.add("DATABASE ERROR");
	    }
        return itemList;
	}
	
	public ArrayList<String> getRowDataForItem(String itemName) {
		ArrayList<String> itemRow = new ArrayList<String>();
		db = this.getWritableDatabase();
        try {
	        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ITEM = '" + itemName + "'", null);
	        cursor.moveToFirst();
	        for(int i = 0; i < 5; i++) {
	        	if(i == 1) // Column is QUANTIRY or SHOPPING_LIST_QUANTITY or 
	        		itemRow.add(Integer.toString(cursor.getInt(i)));
	        	else if(i == 3) // Column is PRICE (an 'integer packed decimal' value)
	        		itemRow.add((new BigDecimal(cursor.getInt(i)).divide(new BigDecimal("100"))).setScale(2).toString());
	        	else // Column is ITEM, UNIT, or SHOP
	        		itemRow.add(cursor.getString(i));
	        }
        } catch (Exception e) {
        	itemRow.clear();
        	itemRow.add("-1");
        }
        return itemRow;
        	
	}
	
	public int updateItemRowData(String rowItemName, String item, int quantity, String unit, BigDecimal price
            ,String shop) {
		db = this.getWritableDatabase();
		int errorCode = -1;
		try {
		ContentValues values = new ContentValues();
	        values.put("QUANTITY", quantity); 
	        values.put("UNIT", unit);
	        values.put("PRICE", price.multiply(new BigDecimal("100")).toBigInteger().intValue()); // Encode to 'integer packed decimal'
	        values.put("SHOP", shop);
	        errorCode = db.update(TABLE_NAME, values, "ITEM='" + rowItemName + "'", null);
		} catch (Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}
	
	public int deleteRow(String rowName) {
		db = this.getWritableDatabase();
		int errorCode = 0;
		try {
			db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ITEM = '" + rowName + "'");
		} catch (Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}
	
	public int getShoppingListQuantity(String itemName) { // When transferring data with return, then -1 means failure and anything else means valid data
		db = this.getWritableDatabase();
		int quantity = -1;
		try {
			Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ITEM = '" + itemName + "'", null);
	        cursor.moveToFirst();
	        quantity = cursor.getInt(5);        
		} catch (Exception e) {
			quantity = -1;
		}
		return quantity;
	}
	
	public int setShoppingListQuantity(String itemName, int quantity) {
		db = this.getWritableDatabase();
		int errorCode = -1;
		try {
			ContentValues values = new ContentValues();
	        values.put("SHOPPING_LIST_QUANTITY", quantity); 
	        errorCode = db.update(TABLE_NAME, values, "ITEM='" + itemName + "'", null);
		} catch (Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}
	
	public BigDecimal getShoppingPrice(String itemName) {
		db = this.getWritableDatabase();
		BigDecimal price = new BigDecimal("-1");
		try {
			Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ITEM = '" + itemName + "'", null);
	        cursor.moveToFirst();
	        price = new BigDecimal(cursor.getInt(7)).divide(new BigDecimal("100")).setScale(2);        
		} catch (Exception e) {
			price = new BigDecimal("-1");
		}
		return price;
	}
	
	public int setShoppingPrice(String itemName, BigDecimal price) {
		db = this.getWritableDatabase();
		int errorCode = 0;
		try {
			ContentValues values = new ContentValues();
			int intPrice = price.multiply(new BigDecimal("100")).toBigInteger().intValue(); // Encode to 'integer packed decimal'); 
	        values.put("SHOPPING_PRICE", intPrice);
	        db.update(TABLE_NAME, values, "ITEM='" + itemName + "'", null);
		} catch (Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}
	
	public BigDecimal getItemPrice(String itemName) {
		db = this.getWritableDatabase();
		BigDecimal price = new BigDecimal("-1");
		try {
			Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ITEM = '" + itemName + "'", null);
	        cursor.moveToFirst();
	        price = new BigDecimal(cursor.getInt(3)).divide(new BigDecimal("100")).setScale(2);       
		} catch (Exception e) {
			price = new BigDecimal("-1");
		}
		return price;
	}
	
	public int setMarkBought(String itemName) {
		db = this.getWritableDatabase();
		int errorCode = 0;
		try {
			ContentValues values = new ContentValues();
	        values.put("PURCHASED", 1);
	        db.update(TABLE_NAME, values, "ITEM='" + itemName + "'", null);
		} catch (Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}
	
	public int setUnmarkBought(String itemName) {
		db = this.getWritableDatabase();
		int errorCode = 0;
		try {
			ContentValues values = new ContentValues();
	        values.put("PURCHASED", 0);
	        db.update(TABLE_NAME, values, "ITEM='" + itemName + "'", null);
		} catch (Exception e) {
			errorCode = -1;
		}
		return errorCode;
	}
	
	public int getBoughtStatus(String itemName) {
		db = this.getReadableDatabase();
		int status = -1;
	    try {
		    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE ITEM = '" + itemName + "'", null);
		    while(cursor.moveToNext()) 
		    	status = cursor.getInt(6);
	    } catch (Exception e) {
	    	status = -1;
	    }
        return status;
	}
	
	public BigDecimal getShoppingTotalPrice() {
		if(!db.isOpen())
			db = this.getReadableDatabase();
		BigDecimal price = new BigDecimal("0");
	    try {
		    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE PURCHASED = '0'", null);
		    while(cursor.moveToNext()) 
		    	price = price.add(new BigDecimal(cursor.getInt(7)).divide(new BigDecimal("100"))).setScale(2);
	    } catch (Exception e) {
	    	price = new BigDecimal("-1");
	    }
        return price;
	}
	
	public BigDecimal getBoughtTotalPrice() {
		db = this.getReadableDatabase();
		BigDecimal price = new BigDecimal("0");
	    try {
		    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE PURCHASED = '1'", null);
		    while(cursor.moveToNext()) 
		    	price = price.add(new BigDecimal(cursor.getInt(7)).divide(new BigDecimal("100"))).setScale(2);
	    } catch (Exception e) {
	    	price = new BigDecimal("-1");
	    } 
        return price;
	}
	
	public int createDefaultDBRows() {
		int errorCode;
		if((errorCode = addNewItemToDB("ASRock 970 mainboard, AM3+, 2133 MHZ bus", 1, "PCS/PKT",
		    new BigDecimal("586.00"), "SHG", 0, 0, new BigDecimal("0"))) == 0)
		  if((errorCode = addNewItemToDB("Intel DX79 mainboard, LGA2011, 2400 MHZ bus", 1, "PCS/PKT",
			  new BigDecimal("1542.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			if((errorCode = addNewItemToDB("AMD FX-8350 CPU, 8 cores, AM3+", 1, "PCS/PKT", 
			    new BigDecimal("1468.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			  if((errorCode = addNewItemToDB("AMD FX-4170 CPU, 4 cores, AM3+", 1, "PCS/PKT",
				  new BigDecimal("956.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			    if((errorCode = addNewItemToDB("Intel i7-3820 CPU, 4 cores, LGA2011", 1, "PCS/PKT", 
					new BigDecimal("2175.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			      if((errorCode = addNewItemToDB("Kingston RAM, 8GB, 2400", 1, "PCS/PKT", 
					  new BigDecimal("548.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			        if((errorCode = addNewItemToDB("Kingston RAM, 8GB, 2133", 1, "PCS/PKT", 
					    new BigDecimal("536.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			          if((errorCode = addNewItemToDB("Raspberry PI Linux PC, 700 MHZ, 512 MB RAM", 1, "PCS/PKT", 
					      new BigDecimal("349.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			            if((errorCode = addNewItemToDB("Rikomagic MK802 Android USB stick PC, 8 GB Flash RAM", 1, "PCS/PKT", 
						    new BigDecimal("649.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			        	  if((errorCode = addNewItemToDB("Inter-Tech Energon EPS-550W Ultra Silent PSU", 1, "PCS/PKT", 
							  new BigDecimal("227.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			        	    if((errorCode = addNewItemToDB("Toshiba DT01ACA 2TB HD SATA-600", 1, "PCS/PKT", 
						        new BigDecimal("674.00"),"SHG", 0, 0, new BigDecimal("0"))) == 0)
			                  errorCode = addNewItemToDB("Android tablet, Denver 70042, ARM9, 7\"", 1, "PCS/PKT", 
						          new BigDecimal("899.00"),"FONA", 1, 1, new BigDecimal("899.00"));
	    return errorCode;
	}
	
}
