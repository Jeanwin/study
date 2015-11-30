package com.zonekey.study.entity;

/**
 * 课程明细
 * 
 * @author Administrator
 * 
 */
public class CurriculumDetails {
	private String subject;//标题
	private String teachername;//教师名字
	private String loginname;
	private String teacherIntroduce;//讲课老师介绍
	private String courseIntroduce;//课程介绍
	private String date;//上课时间
	private String resourceid;
	private String pictureURL;
	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	

	public String getTeachername() {
		return teachername;
	}

	public void setTeachername(String teachername) {
		this.teachername = teachername;
	}

	public String getTeacherIntroduce() {
		return teacherIntroduce;
	}

	public void setTeacherIntroduce(String teacherIntroduce) {
		this.teacherIntroduce = teacherIntroduce;
	}

	public String getCourseIntroduce() {
		return courseIntroduce;
	}

	public void setCourseIntroduce(String courseIntroduce) {
		this.courseIntroduce = courseIntroduce;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Override
	public String toString() {
		return "CurriculumDetails [subject=" + subject + ", teachername="
				+ teachername + ", teacherIntroduce=" + teacherIntroduce
				+ ", courseIntroduce=" + courseIntroduce + ", date=" + date
				+ "]";
	}

	

	

}
