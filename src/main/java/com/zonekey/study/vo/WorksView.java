package com.zonekey.study.vo;

import com.zonekey.study.entity.Works;

public class WorksView extends Works {
	private static final long serialVersionUID = 7619342063452581620L;
	// 作品作者
	private String authorName;
	// 作者部门名称
	private String deptName;
	// 评审专家列表
	private String specialers;
	// 评审专家登录名
	private String specialLoginName;
	// 评审进度
	private String reviewprogress;
	// 评审百分比
	private String reviewPercent;
	// 最高分
	private float maxscore;
	// 最低分
	private float minscore;
	// 综合得分
	private float avgscore;
	// 专家总人数
	private int specialnum;
	// 未评审人数
	private int notreviewnum;
	//
	private String weeks;
	//
	private String sameclass;
	// 资源来源
	private String resourceSource;
	// 用来保存直播课图片路径
	private String imgSrc;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getReviewprogress() {
		return reviewprogress;
	}

	public void setReviewprogress(String reviewprogress) {
		this.reviewprogress = reviewprogress;
	}

	public String getReviewPercent() {
		return reviewPercent;
	}

	public void setReviewPercent(String reviewPercent) {
		this.reviewPercent = reviewPercent;
	}

	public float getMaxscore() {
		return maxscore;
	}

	public void setMaxscore(float maxscore) {
		this.maxscore = maxscore;
	}

	public float getMinscore() {
		return minscore;
	}

	public void setMinscore(float minscore) {
		this.minscore = minscore;
	}

	public float getAvgscore() {
		return avgscore;
	}

	public void setAvgscore(float avgscore) {
		this.avgscore = avgscore;
	}

	public int getSpecialnum() {
		return specialnum;
	}

	public void setSpecialnum(int specialnum) {
		this.specialnum = specialnum;
	}

	public int getNotreviewnum() {
		return notreviewnum;
	}

	public void setNotreviewnum(int notreviewnum) {
		this.notreviewnum = notreviewnum;
	}

	public String getWeeks() {
		return weeks;
	}

	public String getSpecialers() {
		return specialers;
	}

	public void setSpecialers(String specialers) {
		this.specialers = specialers;
	}

	public String getSpecialLoginName() {
		return specialLoginName;
	}

	public void setSpecialLoginName(String specialLoginName) {
		this.specialLoginName = specialLoginName;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getSameclass() {
		return sameclass;
	}

	public void setSameclass(String sameclass) {
		this.sameclass = sameclass;
	}

	public String getResourceSource() {
		return resourceSource;
	}

	public void setResourceSource(String resourceSource) {
		this.resourceSource = resourceSource;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	@Override
	public String toString() {
		return "WorksView [authorName=" + authorName + ", deptName=" + deptName + ", specialers=" + specialers + ", reviewprogress=" + reviewprogress + ", reviewPercent=" + reviewPercent
				+ ", maxscore=" + maxscore + ", minscore=" + minscore + ", avgscore=" + avgscore + ", specialnum=" + specialnum + ", notreviewnum=" + notreviewnum + "]";
	}

}
