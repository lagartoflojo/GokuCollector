package com.plegnic.goku.database;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

public class ItemCollectionsTable {
	public static final String TABLE_NAME = "item_collections";
	
	public static class ItemCollectionsColumns implements BaseColumns {
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
	}
	
	public static void onCreate(SQLiteDatabase db) {
		StringBuilder createsql = new StringBuilder();
		createsql.append("CREATE TABLE " + TABLE_NAME + " (");
		createsql.append(ItemCollectionsColumns._ID + " INTEGER PRIMARY KEY, ");
		createsql.append(ItemCollectionsColumns.NAME + " TEXT UNIQUE NOT NULL, ");
		createsql.append(ItemCollectionsColumns.DESCRIPTION + " TEXT");
		createsql.append(");");
		db.execSQL(createsql.toString());
	}
	
	public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// do nothing for now.
	}

}