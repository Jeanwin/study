package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 播放记录
 * 
 * @author admin
 * 
 */
public class PlayRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String type;
	private String loginname;
	private Integer resourceid;
	private long playtime;
	private String flag;
	private String createdate;
	
	private String visitor;
	private Date endtime;

	public PlayRecord() {
		super();
	}

	public PlayRecord(String type, String loginname, Integer resourceid, long playtime, String flag) {
		super();
		this.type = type;
		this.loginname = loginname;
		this.resourceid = resourceid;
		this.playtime = playtime;
		this.flag = flag;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public long getPlaytime() {
		return playtime;
	}

	public void setPlaytime(long playtime) {
		this.playtime = playtime;
	}

	public Integer getResourceid() {
		return resourceid;
	}

	public void setResourceid(Integer resourceid) {
		this.resourceid = resourceid;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	@Override
	public String toString() {
		return "PlayRecord [id=" + id + ", type=" + type + ", loginname=" + loginname + ", resourceid=" + resourceid + ", playtime=" + playtime + ", flag=" + flag + ", createdate=" + createdate + "]";
	}

	public String getVisitor() {
		return visitor;
	}

	public void setVisitor(String visitor) {
		this.visitor = visitor;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


}
