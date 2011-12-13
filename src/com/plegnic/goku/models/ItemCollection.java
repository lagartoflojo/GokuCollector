package com.plegnic.goku.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemCollection extends ModelBase {
	
	private String name;
	private String description;
	private final ArrayList<Item> items = new ArrayList<Item>();
	private final List<Item> itemsSafe = Collections.unmodifiableList(items);
	
	public List<Item> getItems() {
		return itemsSafe;
	}
	
	public void addItem(Item item) {
		items.add(item);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
