package com.zonekey.study.vo;

import java.util.List;

public class MergeVideoReciever {
	private Integer video_id;
	private String videoname;
	private String video_path;
	private List<ViewPoint> viewpoint;

	public Integer getVideo_id() {
		return video_id;
	}

	public void setVideo_id(Integer video_id) {
		this.video_id = video_id;
	}

	public String getVideoname() {
		return videoname;
	}

	public void setVideoname(String videoname) {
		this.videoname = videoname;
	}

	public String getVideo_path() {
		return video_path;
	}

	public void setVideo_path(String video_path) {
		this.video_path = video_path;
	}

	public List<ViewPoint> getViewpoint() {
		return viewpoint;
	}

	public void setViewpoint(List<ViewPoint> viewpoint) {
		this.viewpoint = viewpoint;
	}

	@Override
	public String toString() {
		return "MergeVideoReciever [video_id=" + video_id + ", videoname=" + videoname + ", video_path=" + video_path + ", viewpoint=" + viewpoint + "]";
	}

}
