package com.zonekey.study.vo;

import com.zonekey.study.entity.Review;

public class ReviewView extends Review {

	private static final long serialVersionUID = -6956009124641461394L;
	private String createtimeString;
	private String modifytimeString;

	public String getCreatetimeString() {
		return createtimeString;
	}

	public void setCreatetimeString(String createtimeString) {
		this.createtimeString = createtimeString;
	}

	public String getModifytimeString() {
		return modifytimeString;
	}

	public void setModifytimeString(String modifytimeString) {
		this.modifytimeString = modifytimeString;
	}

}
