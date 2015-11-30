package com.zonekey.study.entity;


import java.io.Serializable;

/**
 * 评课记录 
 * @author zq
 *
 */
public class ReviewRecord implements Serializable {
	private int worksId;
	private String name;
	private String subject;
	private String grade;
	private String description;
	private String type;
	private String picture;
	private String resourceid;
	private String activeid;
	private String userid;
	private String createtime;
	

	private int reviewId;
	private String content;
	private String score;
	private String templateid;
	private String createdate;
	private String remark;
	private String isover;
	
	
	
	public int getWorksId() {
		return worksId;
	}
	public void setWorksId(int worksId) {
		this.worksId = worksId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getResourceid() {
		return resourceid;
	}
	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}
	public String getActiveid() {
		return activeid;
	}
	public void setActiveid(String activeid) {
		this.activeid = activeid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getTemplateid() {
		return templateid;
	}
	public void setTemplateid(String templateid) {
		this.templateid = templateid;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIsover() {
		return isover;
	}
	public void setIsover(String isover) {
		this.isover = isover;
	}
	
	
	@Override
	public String toString() {
		return "ReviewRecord [worksId=" + worksId + ", name=" + name
				+ ", subject=" + subject + ", grade=" + grade
				+ ", description=" + description + ", type=" + type
				+ ", picture=" + picture + ", resourceid=" + resourceid
				+ ", activeid=" + activeid + ", userid=" + userid
				+ ", createtime=" + createtime + ", reviewId=" + reviewId
				+ ", content=" + content + ", score=" + score + ", templateid="
				+ templateid + ", createdate=" + createdate + ", remark="
				+ remark + ", isover=" + isover + "]";
	}
	
}