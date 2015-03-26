package com.prevention.disease.bean;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public class Attach 
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int attachId;
	@DatabaseField
	private String introduce;
	@DatabaseField
	private String title;
	@DatabaseField
	private String thumb;
	@DatabaseField
	private String pic;
	@DatabaseField
	private String content;
	@DatabaseField
	private boolean bListView;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Item item;
	@ForeignCollectionField
	private List<AttachOverride> attachOverrides;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAttachId() {
		return attachId;
	}
	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public List<AttachOverride> getAttachOverrides() {
		return attachOverrides;
	}
	public void setAttachOverrides(List<AttachOverride> attachOverrides) {
		this.attachOverrides = attachOverrides;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public boolean isbListView() {
		return bListView;
	}
	public void setbListView(boolean bListView) {
		this.bListView = bListView;
	}
	
	
}
