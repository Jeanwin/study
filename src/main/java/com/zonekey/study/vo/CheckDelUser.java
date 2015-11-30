package com.zonekey.study.vo;

public class CheckDelUser {
	private String userId;
	private Integer activeId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getActiveId() {
		return activeId;
	}

	public void setActiveId(Integer activeId) {
		this.activeId = activeId;
	}

	@Override
	public String toString() {
		return "CheckDelUser [userId=" + userId + ", activeId=" + activeId + "]";
	}

}
