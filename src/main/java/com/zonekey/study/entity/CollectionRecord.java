package com.zonekey.study.entity;

import java.io.Serializable;

/**
 * 手机收藏model
 * 
 * @author admin
 * 
 */
public class CollectionRecord implements Serializable {

	private static final long serialVersionUID = -4228724685053285786L;

	private Integer id;
	private String loginname;
	private Integer resourceid;
	private String createdate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public Integer getResourceid() {
		return resourceid;
	}

	public void setResourceid(Integer resourceid) {
		this.resourceid = resourceid;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "CollectionRecord [id=" + id + ", loginname=" + loginname + ", resourceid=" + resourceid + ", createdate=" + createdate + "]";
	}

}
