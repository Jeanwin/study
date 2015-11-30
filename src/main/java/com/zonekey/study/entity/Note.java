package com.zonekey.study.entity;


/**
 * 笔记
 * @author Administrator
 *
 */
public class Note {
	private int id;
	private String resourceid;
	private String worksid;
	private String userid;
	private String content;
	private String createdate;
	private String videotime;
	private String floder;
	private String curriculumid;
	private String type;
	
		public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	

	public String getWorksid() {
		return worksid;
	}

	public void setWorksid(String worksid) {
		this.worksid = worksid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getVideotime() {
		return videotime;
	}

	public void setVideotime(String videotime) {
		this.videotime = videotime;
	}

	public String getFloder() {
		return floder;
	}

	public void setFloder(String floder) {
		this.floder = floder;
	}

	public String getCurriculumid() {
		return curriculumid;
	}

	public void setCurriculumid(String curriculumid) {
		this.curriculumid = curriculumid;
	}

	@Override
	public String toString() {
		return "Note [id=" + id + ", resourceid=" + resourceid + ", worksid="
				+ worksid + ", userid=" + userid + ", content=" + content
				+ ", createdate=" + createdate + "]";
	}

}
