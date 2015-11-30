package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: study_discuss 实体类 
 * 评论表
 * CreateDate: Mon May 25 15:11:08 CST 2015
 * Author: JeanwinHuang@live.com
 */

public class Discuss implements Serializable {
	private static final long serialVersionUID = -9107148545116193231L;
	private int id;
	private String uuid;
	private String userid;
	private String resourceid;
	private String content;
	private Date discusstime;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUserid() {
		return userid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getResourceid() {
		return resourceid;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setDiscusstime(Date discusstime) {
		this.discusstime = discusstime;
	}

	public Date getDiscusstime() {
		return discusstime;
	}

	@Override
	public String toString() {
		return "Discuss [id=" + id + ", uuid=" + uuid + ", userid=" + userid + ", resourceid=" + resourceid + ", content=" + content + ", discusstime=" + discusstime + "]";
	}

}
