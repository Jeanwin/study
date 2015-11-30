package com.zonekey.study.vo;

public class ResourceView {

	private static final long serialVersionUID = 1L;
	private Integer pk;
	private String id;
	private String parentid;
	private String resource_uuid;
	private String name;
	private String nametype;
	private String isfolder;
	private String fileurl;
	private long size;
	private String source;
	private String description;
	// name,nametype,fileurl,imageurl,description,size,isfolder,resource_uuid,parentid,type,source,createdate,createuser,storeid,storetype,author,storeuser

	private String type;
	// private String size;
	private String publishstate;
	private String createdate;
	private String modifydate;
	private String createuser;
	private String modifyuser;
	private String deleteflag;
	private String watchwatchnum;
	private String group;
	private String publishdate;
	private String label;
	private String storeid;
	private String storeuser;
	private String storetype;
	private String author;
	private String subject;
	private String grade;
	private String imageurl;
	private String curriculumid;
	private String resourcecollection;
	//课前、课后资源的可见性 0代表不可见，1代表可见
		private String resourcevisable;
	private String transPath;
	private String transFlag;
	private String floder;
	private String resourcePath;
	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}
	
	public String getResourcePath() {
		return resourcePath;
	}

	public void setResourcePath(String resourcePath) {
		this.resourcePath = resourcePath;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getResource_uuid() {
		return resource_uuid;
	}

	public void setResource_uuid(String resource_uuid) {
		this.resource_uuid = resource_uuid;
	}

	public String getName() {
		return name;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsfolder() {
		return isfolder;
	}

	public void setIsfolder(String isfolder) {
		this.isfolder = isfolder;
	}

	public String getFileurl() {
		return fileurl;
	}

	public void setFileurl(String fileurl) {
		this.fileurl = fileurl;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	 * public String getSize() { return size; }
	 * 
	 * public void setSize(String size) { this.size = size; }
	 */

	public String getPublishstate() {
		return publishstate;
	}

	public void setPublishstate(String publishstate) {
		this.publishstate = publishstate;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getTransFlag() {
		return transFlag;
	}

	public void setTransFlag(String transFlag) {
		this.transFlag = transFlag;
	}

	public String getModifyuser() {
		return modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getWatchwatchnum() {
		return watchwatchnum;
	}

	public void setWatchwatchnum(String watchwatchnum) {
		this.watchwatchnum = watchwatchnum;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(String publishdate) {
		this.publishdate = publishdate;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getStoreid() {
		return storeid;
	}

	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}

	public String getStoretype() {
		return storetype;
	}

	public void setStoretype(String storetype) {
		this.storetype = storetype;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNametype() {
		return nametype;
	}

	public void setNametype(String nametype) {
		this.nametype = nametype;
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

	public String getCurriculumid() {
		return curriculumid;
	}

	public void setCurriculumid(String curriculumid) {
		this.curriculumid = curriculumid;
	}

	public String getResourcecollection() {
		return resourcecollection;
	}

	public void setResourcecollection(String resourcecollection) {
		this.resourcecollection = resourcecollection;
	}

	public String getTransPath() {
		return transPath;
	}

	public void setTransPath(String transPath) {
		this.transPath = transPath;
	}

	public String getFloder() {
		return floder;
	}

	public void setFloder(String floder) {
		this.floder = floder;
	}

	public String getStoreuser() {
		return storeuser;
	}

	public void setStoreuser(String storeuser) {
		this.storeuser = storeuser;
	}

	public String getResourcevisable() {
		return resourcevisable;
	}

	public void setResourcevisable(String resourcevisable) {
		this.resourcevisable = resourcevisable;
	}

	@Override
	public String toString() {
		return "ResourceView [id=" + id + ", parentid=" + parentid + ", resource_uuid=" + resource_uuid + ", name=" + name + ", nametype=" + nametype + ", isfolder=" + isfolder + ", fileurl="
				+ fileurl + ", size=" + size + ", source=" + source + ", description=" + description + ", type=" + type + ", publishstate=" + publishstate + ", createdate=" + createdate
				+ ", modifydate=" + modifydate + ", createuser=" + createuser + ", modifyuser=" + modifyuser + ", deleteflag=" + deleteflag + ", watchwatchnum=" + watchwatchnum + ", group=" + group
				+ ", publishdate=" + publishdate + ", label=" + label + ", storeid=" + storeid + ", storetype=" + storetype + ", author=" + author + ", subject=" + subject + ", grade=" + grade
				+ ", imageurl=" + imageurl + "]";
	}

}
