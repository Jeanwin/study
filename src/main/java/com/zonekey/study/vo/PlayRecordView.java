package com.zonekey.study.vo;

import com.zonekey.study.entity.PlayRecord;

public class PlayRecordView extends PlayRecord {

	private static final long serialVersionUID = 3379745489398765170L;

	private String courseName;
	private String username;
	private String deptName;
	private String picture;
	private long duration;
	private String curriculumid;
	// private String playProcess;
	private String recordTime;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getCurriculumid() {
		return curriculumid;
	}

	public void setCurriculumid(String curriculumid) {
		this.curriculumid = curriculumid;
	}

	public String getUsername() {
		return username;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	@Override
	public String toString() {
		return "PlayRecordView [courseName=" + courseName + ", username=" + username + ", deptName=" + deptName + ", picture=" + picture + ", duration=" + duration + ", curriculumid=" + curriculumid
				+ ", recordTime=" + recordTime + ", toString()=" + super.toString() + "]";
	}

}
