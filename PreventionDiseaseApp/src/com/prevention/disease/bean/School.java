package com.prevention.disease.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "school")
public class School {
	
	@DatabaseField(generatedId = true)
	private int id;
	
	@DatabaseField(unique = true)
	private String name;
	
	@DatabaseField
	private String contact_no;
	@DatabaseField
	private String address;
	@DatabaseField(unique = true)
	private int shcoolId;
	@DatabaseField
	private String email;
	@DatabaseField
	private String avatar;
	@DatabaseField
	private int longitude;
	@DatabaseField
	private int latitude;
	@DatabaseField
	private int centerId;
	@DatabaseField
	private int history;
	@DatabaseField
	private boolean  bChecked;
	
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
	public String getContact_no() {
		return contact_no;
	}
	public void setContact_no(String contact_no) {
		this.contact_no = contact_no;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getShcoolId() {
		return shcoolId;
	}
	public void setShcoolId(int shcoolId) {
		this.shcoolId = shcoolId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public int getCenterId() {
		return centerId;
	}
	public void setCenterId(int centerId) {
		this.centerId = centerId;
	}
	public int getHistory() {
		return history;
	}
	public void setHistory(int history) {
		this.history = history;
	}
	public boolean isbChecked() {
		return bChecked;
	}
	public void setbChecked(boolean bChecked) {
		this.bChecked = bChecked;
	}

}