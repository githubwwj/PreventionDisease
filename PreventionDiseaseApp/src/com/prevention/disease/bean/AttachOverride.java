package com.prevention.disease.bean;

import com.j256.ormlite.field.DatabaseField;

public class AttachOverride 
{
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField
	private int attachOverrideId;
	@DatabaseField
	private String thumb;
	@DatabaseField
	private String pic;
	@DatabaseField
	private String content;
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	private Attach attach;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAttachOverrideId() {
		return attachOverrideId;
	}
	public void setAttachOverrideId(int attachOverrideId) {
		this.attachOverrideId = attachOverrideId;
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
	public Attach getAttach() {
		return attach;
	}
	public void setAttach(Attach attach) {
		this.attach = attach;
	}

	

}
