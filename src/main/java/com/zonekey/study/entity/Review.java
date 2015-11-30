package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 评审模板
 * 
 * @author admin
 * 
 */

public class Review implements Serializable {
	private static final long serialVersionUID = -3709510424080156196L;
	private String id;
	private String name;
	private String description;
	private String markType;
	private int assess;
	private String createuser;
	private Date createtime;
	private String modifyuser;
	private Date modifytime;
	private String deleteflag;
	private int usetimes;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setMarkType(String markType) {
		this.markType = markType;
	}

	public String getMarkType() {
		return markType;
	}

	public void setAssess(int assess) {
		this.assess = assess;
	}

	public int getAssess() {
		return assess;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public String getModifyuser() {
		return modifyuser;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setDeleteflag(String deleteflag) {
		this.deleteflag = deleteflag;
	}

	public String getDeleteflag() {
		return deleteflag;
	}

	public void setUsetimes(int usetimes) {
		this.usetimes = usetimes;
	}

	public int getUsetimes() {
		return usetimes;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", name=" + name + ", description=" + description + ", markType=" + markType + ", assess=" + assess + ", createuser=" + createuser + ", createtime=" + createtime
				+ ", modifyuser=" + modifyuser + ", modifytime=" + modifytime + ", deleteflag=" + deleteflag + ", usetimes=" + usetimes + "]";
	}

}
