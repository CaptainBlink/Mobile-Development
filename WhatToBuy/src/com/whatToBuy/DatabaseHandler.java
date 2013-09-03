package com.whatToBuy;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class DatabaseHandler extends ContentProvider {

	private static final String TAG = DatabaseHandler.class.getSimpleName();
	private static final String AUTH = "com.whattobuy.provider.DatabaseHandler";

	// Table and database columns name
	public static final String DATABASE_TABLE = "productsTable";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_UNIT = "unit";
	public static final String COLUMN_SHOP = "shop";
	public static final String COLUMN_PRICE = "price";

	public static final String DATABASE_TABLE_SHOPPING = "shoppingTable";
	public static final String COLUMN_ID_SHOPPING = "_idS";
	public static final String COLUMN_NAME_SHOPPING = "nameS";
	public static final String COLUMN_UNIT_SHOPPING = "unitS";
	public static final String COLUMN_SHOP_SHOPPING = "shopS";
	public static final String COLUMN_PRICE_SHOPPING = "priceS";
	public static final String COLUMN_QUANTITY = "quantity";

	// URI in case this database will be used by other application
	public static final Uri PRODUCTS_URI = Uri.parse("content://" + AUTH + "/"
			+ DATABASE_TABLE);
	public static final Uri SHOPPING_URI = Uri.parse("content://" + AUTH + "/"
			+ DATABASE_TABLE_SHOPPING);

	public static final int PRODUCT = 1;
	public static final int SHOPPING = 2;

	private MySqlLiteOpenHelper myOpenHelper;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTH, DATABASE_TABLE, PRODUCT);
		uriMatcher.addURI(AUTH, DATABASE_TABLE_SHOPPING, SHOPPING);

	}
	private Context context;

	public DatabaseHandler(Context context) {
		this.context = context;
		myOpenHelper = new MySqlLiteOpenHelper(context);
	}

	public DatabaseHandler() {
	}

	@Override
	public boolean onCreate() {

		myOpenHelper = new MySqlLiteOpenHelper(context);
		
		return true;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case PRODUCT:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ "/vnd.com.whatToBuy.product";
		case SHOPPING:
			return ContentResolver.CURSOR_DIR_BASE_TYPE
					+ "/vnd.com.whatToBuy.product";

		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}

	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		SQLiteDatabase dbq;
		dbq = myOpenHelper.getWritableDatabase();
	
		if (uriMatcher.match(uri) == PRODUCT) {

			dbq.insert(DATABASE_TABLE, null, values);

		} else if (uriMatcher.match(uri) == SHOPPING) {
			dbq.insert(DATABASE_TABLE_SHOPPING, null, values);
		}
		dbq.close();
		context.getContentResolver().notifyChange(uri, null);
		return null;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		Cursor cursor;

		SQLiteDatabase db = myOpenHelper.getReadableDatabase();
		if (uriMatcher.match(uri) == PRODUCT) {

			cursor = db.query(DATABASE_TABLE, projection, selection,
					selectionArgs, null, null, sortOrder);

		} else if (uriMatcher.match(uri) == SHOPPING) {
			cursor = db.query(DATABASE_TABLE_SHOPPING, projection, selection,
					selectionArgs, null, null, sortOrder);
		} else {
			cursor = null;
		}
		cursor.setNotificationUri(context.getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		if (uriMatcher.match(uri) == PRODUCT) {

			db.delete(DATABASE_TABLE, selection, selectionArgs);

		} else if (uriMatcher.match(uri) == SHOPPING) {
			db.delete(DATABASE_TABLE_SHOPPING, selection, selectionArgs);
		}
		db.close();
		context.getContentResolver().notifyChange(uri, null);

		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = myOpenHelper.getWritableDatabase();

		if (uriMatcher.match(uri) == PRODUCT) {

			db.update(DATABASE_TABLE, values, selection, selectionArgs);

		} else if (uriMatcher.match(uri) == SHOPPING) {
			db.update(DATABASE_TABLE_SHOPPING, values, selection, selectionArgs);
		}
		db.close();
		context.getContentResolver().notifyChange(uri, null);

		return 0;
	}

	private class MySqlLiteOpenHelper extends SQLiteOpenHelper {

		public static final String DATABASE_NAME = "products.db";
		public static final int DATABASE_VERSION = 2;

		public MySqlLiteOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		// SQL statement to create a new database.
		private static final String DATABASE_CREATE = "create table "
				+ DATABASE_TABLE + "(" + COLUMN_ID
				+ " integer primary key autoincrement," + COLUMN_NAME
				+ " text not null , " + COLUMN_UNIT + " text not null, "
				+ COLUMN_SHOP + " text not null, " + COLUMN_PRICE
				+ " double not null);";

		private static final String DATABASE_CREATE_SHOPPING = "create table "
				+ DATABASE_TABLE_SHOPPING + "(" + COLUMN_ID_SHOPPING
				+ " integer primary key autoincrement, " + COLUMN_NAME_SHOPPING
				+ " text not null , " + COLUMN_UNIT_SHOPPING
				+ " text not null, " + COLUMN_SHOP_SHOPPING
				+ " text not null , " + COLUMN_PRICE_SHOPPING
				+ " double not null , " + COLUMN_QUANTITY + " integer);";

		@Override
		public void onCreate(SQLiteDatabase db) {
			// Execute sql statement DATABASE_CREATE
			

			db.execSQL(DATABASE_CREATE_SHOPPING);

			db.execSQL(DATABASE_CREATE);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(MySqlLiteOpenHelper.class.getName(),
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SHOPPING);
			onCreate(db);

		}

	}

}
