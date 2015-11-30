package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: zonekey_dept 实体类 CreateDate: Tue Mar 24 16:49:26 CST 2015
 * Author: JeanwinHuang@live.com
 */

public class Dept implements Serializable {

	private static final long serialVersionUID = -6622561816145051307L;
	private String id;
	// private String uuid;
	private String parentid;
	private String code;
	private String name;
	private String attribute;
	private String description;
	private int Sort;
	private String state;
	private Date classyear;
	// 本部门用户数
	private int userAmount;

	private Date createdate;
	private String createuser;
	private Date modifydate;
	private String modifyuser;
	private String deleteflag;
	//
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	/*
	 * public void setUuid(String uuid) { this.uuid = uuid; }
	 * 
	 * public String getUuid() { return uuid; }
	 */

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getParentid() {
		return parentid;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setSort(int Sort) {
		this.Sort = Sort;
	}

	public int getSort() {
		return Sort;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public int getUserAmount() {
		return userAmount;
	}

	public void setUserAmount(int userAmount) {
		this.userAmount = userAmount;
	}

	public Date getClassyear() {
		return classyear;
	}

	public void setClassyear(Date classyear) {
		this.classyear = classyear;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	public Date getCreatedate() {
		return createdate;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public String getModifyuser() {
		return modifyuser;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", parentid=" + parentid + ", code=" + code + ", name=" + name + ", attribute=" + attribute + ", description=" + description + ", Sort=" + Sort + ", state=" + state
				+ ", classyear=" + classyear + ", userAmount=" + userAmount + ", createdate=" + createdate + ", createuser=" + createuser + ", modifydate=" + modifydate + ", modifyuser=" + modifyuser
				+ ", deleteflag=" + deleteflag + ", checked=" + checked + "]";
	}

}
