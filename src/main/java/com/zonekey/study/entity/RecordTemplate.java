package com.zonekey.study.entity;

/**
 * 听课模板
 * 
 * @author Administrator
 * 
 */
public class RecordTemplate {
	private String id;
	private String parentid;
	private String childKey;// 字段
	private String childValue;// 说明
	private String sort;// 排序

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getChildKey() {
		return childKey;
	}

	public void setChildKey(String childKey) {
		this.childKey = childKey;
	}

	public String getChildValue() {
		return childValue;
	}

	public void setChildValue(String childValue) {
		this.childValue = childValue;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "RecordTemplate [id=" + id + ", childKey=" + childKey
				+ ", childValue=" + childValue + ", sort=" + sort + "]";
	}

	

}
