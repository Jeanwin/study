package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.Map;

public final class Pagebar implements Serializable {
	private static final long serialVersionUID = -1081725270632079438L;
	private int pageSize = 12;
	private long totalCount;
	private int offset = 0;
	private int totalPage;
	private int page = 1;
	
	private Map<String,Object> keywords;
	
	public Pagebar() {
	}

	public Pagebar(int pageSize) {
		if (pageSize < 1)
			this.pageSize = 10;
		else {
			this.pageSize = pageSize;
		}
		this.page = 1;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getTotalPage() {
		return this.totalPage;
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		this.totalPage = (int) (this.totalCount / this.pageSize + (this.totalCount
				% this.pageSize == 0L ? 0 : 1));
	}

	public int getPage() {
		return this.page <= 0 ? 1 : this.page;
	}

	public void setPage(int page) {
		this.offset = (page - 1) * pageSize;
		if (page < 1) {
			this.page = 1;
			this.offset = (this.page - 1) * pageSize;
			return;
		}
		this.page = page;
		this.offset = (this.page - 1) * pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getOffset() {
		return offset;
	}

	public Map<String, Object> getKeywords() {
		return keywords;
	}

	public void setKeywords(Map<String, Object> keywords) {
		this.keywords = keywords;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
