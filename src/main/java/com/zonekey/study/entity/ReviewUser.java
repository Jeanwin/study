package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: study_review_user 实体类
 * 专家实体类
 * CreateDate: Mon May 25 15:13:59 CST 2015
 * Author: JeanwinHuang@live.com
 */

public class ReviewUser implements Serializable {
	private static final long serialVersionUID = 5426356183079003460L;
	private int id;
	private int activeId;
	private String userid;
	private Date createdate;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setActiveId(int activeId) {
		this.activeId = activeId;
	}

	public int getActiveId() {
		return activeId;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedate() {
		return createdate;
	}

	@Override
	public String toString() {
		return "ReviewUser [id=" + id + ", activeId=" + activeId + ", userid=" + userid + ", createdate=" + createdate + "]";
	}

}
