package com.plegnic.goku.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;

import com.plegnic.goku.database.ItemsTable.ItemsColumns;
import com.plegnic.goku.models.Item;

public class ItemDao implements Dao<Item> {
	
	private static final String INSERT = "insert into "
			+ ItemsTable.TABLE_NAME + "("
			+ ItemsColumns.NAME + ", "
			+ ItemsColumns.DESCRIPTION + "," 
			+ ItemsColumns.LATITUDE+ ","
			+ ItemsColumns.LONGITUDE + ") " + "values (?, ?)";
	
	private SQLiteDatabase db;
	private SQLiteStatement insertStatement;
	
	private static String[] COLUMNS = new String[] {
		BaseColumns._ID,
		ItemsColumns.NAME,
		ItemsColumns.DESCRIPTION,
		ItemsColumns.LATITUDE,
		ItemsColumns.LONGITUDE};
	
	public ItemDao(SQLiteDatabase db) {
		this.db = db;
		insertStatement = db.compileStatement(INSERT);
	}

	public long save(Item item) {
		insertStatement.clearBindings();
		insertStatement.bindString(1, item.getName());
		insertStatement.bindString(2, item.getDescription());
		insertStatement.bindDouble(3, item.getLatitude());
		insertStatement.bindDouble(4, item.getLongitude());
		return insertStatement.executeInsert();
	}

	public void update(Item item) {
		final ContentValues values = new ContentValues();
		values.put(ItemsColumns.NAME, item.getName());
		values.put(ItemsColumns.DESCRIPTION, item.getDescription());
		values.put(ItemsColumns.LATITUDE, item.getLatitude());
		values.put(ItemsColumns.LONGITUDE, item.getLongitude());
		db.update(ItemsTable.TABLE_NAME, values, BaseColumns._ID + " = ?",
				new String[]{String.valueOf(item.getId())});
	}

	public void delete(Item item) {
		if(item.getId() > 0) {
			db.delete(ItemsTable.TABLE_NAME, BaseColumns._ID + " = ?",
					new String[]{ String.valueOf(item.getId())});
		}
	}

	public Item find(long id) {
		Item item = null;
		Cursor c =
				db.query(ItemsTable.TABLE_NAME, COLUMNS, BaseColumns._ID + " = ?",
						new String[] { String.valueOf(id) },
						null, null, "1");
		if(c.moveToFirst()) {
			item = buildItemFromCursor(c);
		}
		closeCursor(c);
		return item;
	}

	public List<Item> findAll() {
		List<Item> items = new ArrayList<Item>();
		Cursor c =
				db.query(ItemsTable.TABLE_NAME, COLUMNS, null, null, null, ItemsColumns.NAME, null);
		if(c.moveToFirst()) {
			do {
				Item item = buildItemFromCursor(c);
				if(item != null) {
					items.add(item);
				}
			} while(c.moveToNext());
		}
		closeCursor(c);
		return items;
	}
	
	private void closeCursor(Cursor c) {
		if(!c.isClosed()) {
			c.close();
		}
	}
	
	private Item buildItemFromCursor(Cursor c) {
		Item item = null;
		if (c != null) {
			item = new Item(c.getString(1));
			item.setId(c.getLong(0));
			item.setDescription(c.getString(2));
			item.setLatitude(c.getFloat(3));
			item.setLongitude(c.getFloat(4));
		}
		return item;
	}

}
