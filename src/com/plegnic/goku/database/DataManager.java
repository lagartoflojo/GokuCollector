package com.plegnic.goku.database;

import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.plegnic.goku.models.Item;
import com.plegnic.goku.models.ItemCollection;

public class DataManager implements DataManagerInterface {
	
	private Context context;
	private SQLiteDatabase db;
	private ItemCollectionDao collectionDao;
	private ItemDao itemDao;
	
	public DataManager(Context context) {
		this.context = context;
		SQLiteOpenHelper openHelper = new OpenHelper(context);
		db = openHelper.getWritableDatabase();
		collectionDao = new ItemCollectionDao(db);
		itemDao = new ItemDao(db);
	}

	public ItemCollection findItemCollection(long id) {
		
		return null;
	}

	public List<ItemCollection> findAllItemCollections() {
		// TODO Auto-generated method stub
		return null;
	}

	public long saveItemCollection(ItemCollection collection) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean deleteItemCollection(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	public Item findItem(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> findAllItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> findAllItemsByCollection(ItemCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Item> findAllItemsByCollectionId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public long saveItem(Item item) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean deleteItem(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
