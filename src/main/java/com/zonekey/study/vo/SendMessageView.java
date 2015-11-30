package com.zonekey.study.vo;

import java.util.List;

import com.zonekey.study.entity.SysUser;

public class SendMessageView {
	private List<String> recieverIds;
	private String title;
	private String content;
	private int replyid;
	private String typeid;
	private String isEmail;

	public int getReplyid() {
		return replyid;
	}

	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}

	public String getIsEmail() {
		return isEmail;
	}

	public void setIsEmail(String isEmail) {
		this.isEmail = isEmail;
	}

	public List<String> getRecieverIds() {
		return recieverIds;
	}

	public void setRecieverIds(List<String> recieverIds) {
		this.recieverIds = recieverIds;
	}
}
