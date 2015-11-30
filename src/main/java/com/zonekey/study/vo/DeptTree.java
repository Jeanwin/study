package com.zonekey.study.vo;

import java.util.List;

import com.zonekey.study.entity.Dept;

public class DeptTree {
	private String id;
	private String title;
	private int userAmount;
	private List<Dept> nodes;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Dept> getNodes() {
		return nodes;
	}
	public void setNodes(List<Dept> nodes) {
		this.nodes = nodes;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUserAmount() {
		return userAmount;
	}
	public void setUserAmount(int userAmount) {
		this.userAmount = userAmount;
	}
	
}
