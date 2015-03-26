package com.prevention.disease.bean;

import java.io.Serializable;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Item implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int itemId;
	@DatabaseField
	private String name;
	@DatabaseField
	private String avatar;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Column column;
	@ForeignCollectionField
	private ForeignCollection<Attach> attachs;

	public Item() {

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

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Column getColumn() {
		return column;
	}

	public void setColumn(Column column) {
		this.column = column;
	}

	public ForeignCollection<Attach> getAttachs() {
		return attachs;
	}

	public void setAttachs(ForeignCollection<Attach> attachs) {
		this.attachs = attachs;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	

}
