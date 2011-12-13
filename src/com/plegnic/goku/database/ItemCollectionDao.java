package com.plegnic.goku.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.plegnic.goku.database.ItemCollectionsTable.ItemCollectionsColumns;
import com.plegnic.goku.models.ItemCollection;

public class ItemCollectionDao implements Dao<ItemCollection> {

	private static final String INSERT = "insert into "
			+ ItemCollectionsTable.TABLE_NAME + "("
			+ ItemCollectionsColumns.NAME + ", "
			+ ItemCollectionsColumns.DESCRIPTION + ") " + "values (?, ?)";

	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	private static String[] COLUMNS = new String[] {
		BaseColumns._ID,
		ItemCollectionsColumns.NAME,
		ItemCollectionsColumns.DESCRIPTION };

	public ItemCollectionDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(INSERT);
	}

	public long save(ItemCollection collection) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, collection.getName());
		insertStatement.bindString(2, collection.getDescription());
		return insertStatement.executeInsert();
	}

	public void update(ItemCollection collection) {
		final ContentValues values = new ContentValues();
		values.put(ItemCollectionsColumns.NAME, collection.getName());
		values.put(ItemCollectionsColumns.DESCRIPTION,
				collection.getDescription());
		db.update(ItemCollectionsTable.TABLE_NAME, values, BaseColumns._ID
				+ " = ?", new String[] { String.valueOf(collection.getId()) });
	}

	public void delete(ItemCollection collection) {
		if (collection.getId() > 0) {
			db.delete(ItemCollectionsTable.TABLE_NAME,
					BaseColumns._ID + " = ?",
					new String[] { String.valueOf(collection.getId()) });
		}

	}

	public ItemCollection find(long id) {
		ItemCollection collection = null;
		Cursor c = 
				db.query(ItemCollectionsTable.TABLE_NAME,
						COLUMNS,
						BaseColumns._ID + " = ?",
						new String[] { String.valueOf(id) },
						null, null, null, "1");
		if(c.moveToFirst()) {
			collection = buildCollectionFromCursor(c);
		}
		closeCursor(c);
		return collection;
	}

	public List<ItemCollection> findAll() {
		List<ItemCollection> collections = new ArrayList<ItemCollection>();
		Cursor c =
				db.query(ItemCollectionsTable.TABLE_NAME,
						COLUMNS,
						null, null, null, ItemCollectionsColumns.NAME, null);
		if(c.moveToFirst()) {
			do {
				ItemCollection collection = buildCollectionFromCursor(c);
				if(collection != null) {
					collections.add(collection);
				} 
			} while(c.moveToNext());
		}
		closeCursor(c);
		return collections;
	}
	
	private void closeCursor(Cursor c) {
		if(!c.isClosed()) {
			c.close();
		}
	}
	
	private ItemCollection buildCollectionFromCursor(Cursor c) {
		ItemCollection collection = null;
		if(c != null) {
			collection = new ItemCollection();
			collection.setId(c.getLong(0));
			collection.setName(c.getString(1));
			collection.setDescription(c.getString(2));
		}
		return collection;
	}

}