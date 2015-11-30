package com.zonekey.study.vo;

/**
 * 视频资源转码bean
 * @author gly
 *
 */
public class Trans {
	//文件名
	private String res_name;
	//文件id,用户查询转码状态
	private String file_id;
	//文件路径 
	private String res_path;
	//文件物理全路径 
	private String real_path;
	//文件格式 .mp4
	private String res_type;
	//转码参数选择  0 高级 1 中高清 2 标清
	private int flag_hd;
	//转码后的路径
	private String trans_path="0";
	//分段时间
	private int dur_time;
	//是否合并
	private int flag_meg;
	private int trans_flag;
	//转码优先级
	private int grade;
	
	public String getReal_path() {
		return real_path;
	}
	public void setReal_path(String real_path) {
		this.real_path = real_path;
	}
	public String getRes_name() {
		return res_name;
	}
	public void setRes_name(String res_name) {
		this.res_type = res_name.substring(res_name.lastIndexOf("."));
		this.res_name = res_name.substring(0,res_name.lastIndexOf("."));
	}
	public String getFile_id() {
		return file_id;
	}
	public void setFile_id(String file_id) {
		this.file_id = file_id;
	}
	public String getRes_path() {
		return res_path;
	}
	public void setRes_path(String res_path) {
		this.res_path = res_path;
	}
	public String getRes_type() {
		return res_type;
	}
	public void setRes_type(String res_type) {
		this.res_type = res_type;
	}
	public int getFlag_hd() {
		return flag_hd;
	}
	public void setFlag_hd(int flag_hd) {
		this.flag_hd = flag_hd;
	}
	public String getTrans_path() {
		return trans_path;
	}
	public void setTrans_path(String trans_path) {
		this.trans_path = trans_path;
	}
	public int getDur_time() {
		return dur_time;
	}
	public void setDur_time(int dur_time) {
		this.dur_time = dur_time;
	}
	public int getFlag_meg() {
		return flag_meg;
	}
	public void setFlag_meg(int flag_meg) {
		this.flag_meg = flag_meg;
	}
	public int getTrans_flag() {
		return trans_flag;
	}
	public void setTrans_flag(int trans_flag) {
		this.trans_flag = trans_flag;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
}
