package com.zonekey.study.entity;

import java.io.Serializable;



public class MaxClass implements Serializable{
	private Integer smaxclass;
	private Integer maxclass;

	public Integer getSmaxclass() {
		return smaxclass;
	}

	public void setSmaxclass(Integer smaxclass) {
		this.smaxclass = smaxclass;
	}

	public Integer getMaxclass() {
		return maxclass;
	}

	public void setMaxclass(Integer maxclass) {
		this.maxclass = maxclass;
	}

	@Override
	public String toString() {
		return "MaxClass [smaxclass=" + smaxclass + ", maxclass=" + maxclass
				+ "]";
	}

}
