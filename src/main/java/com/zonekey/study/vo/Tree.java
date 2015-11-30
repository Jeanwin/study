package com.zonekey.study.vo;

import java.util.List;

public class Tree {
	private String id;
	private String resource_uuid;
	private String isfolder;
	private String title;
	private String titletype;
	private String subject;
	private String grade;
	private String description;
	//视频播放地址
	private String fileUrl;
	private String imageurl;
	private List<Tree> nodes;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Tree> getNodes() {
		return nodes;
	}
	public void setNodes(List<Tree> nodes) {
		this.nodes = nodes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIsfolder() {
		return isfolder;
	}
	public void setIsfolder(String isfolder) {
		this.isfolder = isfolder;
	}
	public String getResource_uuid() {
		return resource_uuid;
	}
	public void setResource_uuid(String resource_uuid) {
		this.resource_uuid = resource_uuid;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getTitletype() {
		return titletype;
	}
	public void setTitletype(String titletype) {
		this.titletype = titletype;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
