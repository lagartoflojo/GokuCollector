package com.plegnic.goku.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OpenHelper extends SQLiteOpenHelper{
	private static final int DATABASE_VERSION = 1;
	
//	private Context context;

	public OpenHelper(final Context context) {
		super(context, DataConstants.DATABASE_NAME, null, DATABASE_VERSION);
//		this.context = context;
	}
	
	public void onOpen(final SQLiteDatabase db) {
		super.onOpen(db);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// create tables by calling *Table.onCreate
		ItemCollectionsTable.onCreate(db);
		ItemsTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// upgrade db by calling *Table.onUpgrade
		ItemCollectionsTable.onUpgrade(db, oldVersion, newVersion);
		ItemsTable.onUpgrade(db, oldVersion, newVersion);
	}

}
