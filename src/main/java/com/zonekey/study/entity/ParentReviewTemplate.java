package com.zonekey.study.entity;

/**
 * 评审父模板
 * 
 * @author Administrator
 * 
 */
public class ParentReviewTemplate {
	private String id;
	private String name;
	private String assess;//主观评价 1需要 0 不需要

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAssess() {
		return assess;
	}

	public void setAssess(String assess) {
		this.assess = assess;
	}

	@Override
	public String toString() {
		return "ParentReviewTemplate [id=" + id + ", name=" + name + "]";
	}

}
