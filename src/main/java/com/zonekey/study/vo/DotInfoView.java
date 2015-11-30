package com.zonekey.study.vo;

import java.util.List;
import java.util.Map;

public class DotInfoView {
	private int resourceid;
	private long videoLength;
	private List<Map<String, Object>> content;

	public int getResourceid() {
		return resourceid;
	}

	public void setResourceid(int resourceid) {
		this.resourceid = resourceid;
	}

	public List<Map<String, Object>> getContent() {
		return content;
	}

	public void setContent(List<Map<String, Object>> content) {
		this.content = content;
	}

	public long getVideoLength() {
		return videoLength;
	}

	public void setVideoLength(long videoLength) {
		this.videoLength = videoLength;
	}

	@Override
	public String toString() {
		return "DotInfoView [resourceid=" + resourceid + ", content=" + content + "]";
	}
    
}
