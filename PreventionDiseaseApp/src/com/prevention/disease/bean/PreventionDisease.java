package com.prevention.disease.bean;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class PreventionDisease {
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int PDID;
	public int getPDID() {
		return PDID;
	}
	public void setPDID(int pDID) {
		PDID = pDID;
	}
	@DatabaseField(unique = true)
	private String name;
	@DatabaseField
	private String comment;
	@DatabaseField
	private String avatar;
	@ForeignCollectionField
	private ForeignCollection<Category> photos;
	
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
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public ForeignCollection<Category> getPhotos() {
		return photos;
	}
	public void setPhotos(ForeignCollection<Category> photos) {
		this.photos = photos;
	}
	
	

}
