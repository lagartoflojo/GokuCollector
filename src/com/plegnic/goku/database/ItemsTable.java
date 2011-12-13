package com.plegnic.goku.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class ItemsTable {
	
	public static final String TABLE_NAME = "items";
	
	public static class ItemsColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String LATITUDE = "latitude";
        public static final String LONGITUDE = "longitude";
        public static final String ITEM_COLLECTION_ID = "item_collection_id";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder createsql = new StringBuilder();
		createsql.append("CREATE TABLE " + TABLE_NAME + " (");
		createsql.append(ItemsColumns._ID + " INTEGER PRIMARY KEY, ");
		createsql.append(ItemsColumns.NAME + " TEXT UNIQUE NOT NULL, ");
		createsql.append(ItemsColumns.DESCRIPTION + " TEXT, ");
		createsql.append(ItemsColumns.LATITUDE + " REAL, ");
		createsql.append(ItemsColumns.LONGITUDE + " REAL, ");
		createsql.append(ItemsColumns.ITEM_COLLECTION_ID + " INTEGER NOT NULL, ");
		createsql.append("FOREIGN KEY(" + ItemsColumns.ITEM_COLLECTION_ID +
				") REFERENCES " + ItemCollectionsTable.TABLE_NAME + " (" + BaseColumns._ID + ")");
		createsql.append(");");
		db.execSQL(createsql.toString());
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// do nothing for now.
	}

}