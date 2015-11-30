package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.List;

/**
 *  
 * 
 * @className:Message.java
 * @classDescription:消息实体类
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月18日
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 2199242965539241748L;
	private int id;
	private String uuid;
	private String typeid;
	private String title;
	private String content;
	private String createdate;
	private String createuser;
	private String modifydate;
	private String modifyuser;
	private String deleteflag;
	private String createdatestring;
	private String reciewerNames;
	private String deptNames;

	// one to many
	private List<MsgStatus> msgStatus;
	private int recieverNum;

	public Message() {

	}

	public Message(int id, String uuid, String typeid, String content, String createdate, String createuser, String modifydate, String modifyuser, String deleteflag) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.typeid = typeid;
		this.content = content;
		this.createdate = createdate;
		this.createuser = createuser;
		this.modifydate = modifydate;
		this.modifyuser = modifyuser;
		this.deleteflag = deleteflag;
	}

	public String getCreatedatestring() {
		return createdatestring;
	}

	public void setCreatedatestring(String createdatestring) {
		this.createdatestring = createdatestring;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTypeid() {
		return typeid;
	}

	public void setTypeid(String typeid) {
		this.typeid = typeid;
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

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
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

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public List<MsgStatus> getMsgStatus() {
		return msgStatus;
	}

	public void setMsgStatus(List<MsgStatus> msgStatus) {
		this.msgStatus = msgStatus;
	}

	public int getRecieverNum() {
		return recieverNum;
	}

	public String getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}

	public String getReciewerNames() {
		return reciewerNames;
	}

	public void setReciewerNames(String reciewerNames) {
		this.reciewerNames = reciewerNames;
	}

	public void setRecieverNum(int recieverNum) {
		this.recieverNum = recieverNum;
	}

}
