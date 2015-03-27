package com.prevention.disease.bean;

import java.io.Serializable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Column implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int columnId;
	@DatabaseField
	private String name;
	@ForeignCollectionField
	private ForeignCollection<Item> items;

	public int getColumnId() {
		return columnId;
	}

	public void setColumnId(int columnId) {
		this.columnId = columnId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ForeignCollection<Item> getItems() {
		return items;
	}

	public void setItems(ForeignCollection<Item> items) {
		this.items = items;
	}

}
