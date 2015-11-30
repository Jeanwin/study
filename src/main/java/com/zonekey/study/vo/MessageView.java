package com.zonekey.study.vo;

import com.zonekey.study.entity.Message;
import com.zonekey.study.entity.SysUser;

/**
 *  
 * 
 * @className:MessageView.java
 * @classDescription:
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月20日
 */
public class MessageView extends Message {

    private static final long serialVersionUID = 7036744285871788337L;
    // 消息类型名称
    private String typeName;
    // 消息的创建者
    private SysUser creater;

    public String getTypeName() {
	return typeName;
    }

    public void setTypeName(String typeName) {
	this.typeName = typeName;
    }

    public SysUser getCreater() {
	return creater;
    }

    public void setCreater(SysUser creater) {
	this.creater = creater;
    }

}
