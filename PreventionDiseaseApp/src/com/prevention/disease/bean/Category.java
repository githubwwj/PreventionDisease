package com.prevention.disease.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "moment")
public class Category {
	@DatabaseField
	private int id;
	@DatabaseField
	private String avatar;
	@DatabaseField
	private String name;
	@DatabaseField
	private String comment;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private PreventionDisease preventionDisease;
	@ForeignCollectionField
	private ForeignCollection<Column> photos;

	public Category() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public PreventionDisease getPreventionDisease() {
		return preventionDisease;
	}

	public void setPreventionDisease(PreventionDisease preventionDisease) {
		this.preventionDisease = preventionDisease;
	}

	public ForeignCollection<Column> getPhotos() {
		return photos;
	}

	public void setPhotos(ForeignCollection<Column> photos) {
		this.photos = photos;
	}
	
	

	
}
