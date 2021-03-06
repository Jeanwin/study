package com.zonekey.study.vo;


public class StoreResourceView {
	// 收藏来源 1：个人空间 2：录播机 3：智慧教室
	private String source;
	// 收藏谁的
	private String author;
	// 资源id,可以是录播，个人空间或智慧教室资源id
	private String resourceid;

	public String getResourceid() {
		return resourceid;
	}

	public void setResourceid(String resourceid) {
		this.resourceid = resourceid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
