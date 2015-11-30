package com.zonekey.study.vo;

public class CutImgReciever {
    private String file_path;
    private String filename;
    private Integer cut_time;
    private Integer count;
    
	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getCut_time() {
		return cut_time;
	}

	public void setCut_time(Integer cut_time) {
		this.cut_time = cut_time;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "CutImgReciever [file_path=" + file_path + ", filename=" + filename + ", cut_time=" + cut_time + ", count=" + count + "]";
	}
    
    
}
