package com.plegnic.goku.database;

import java.util.List;

import com.plegnic.goku.models.Item;
import com.plegnic.goku.models.ItemCollection;

public interface DataManagerInterface {
	public ItemCollection findItemCollection(long id);
	public List<ItemCollection> findAllItemCollections();
	public long saveItemCollection(ItemCollection collection);
	public boolean deleteItemCollection(long id);
	
	public Item findItem(long id);
	public List<Item> findAllItems();
	public List<Item> findAllItemsByCollection(ItemCollection collection);
	public List<Item> findAllItemsByCollectionId(long id);
	public long saveItem(Item item);
	public boolean deleteItem(long id);
}
