package com.zonekey.study.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *  
 * 
 * @className:MsgStatus.java
 * @classDescription:消息状态bean
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月19日
 */
public class MsgStatus implements Serializable {

	private static final long serialVersionUID = 8881610760303455948L;
	private int id;
	private String uuid;
	private int msgid;
	private String senderid;
	private String recieverid;
	private String readstatus;
	private String sendstatus;
	private String senderdel;
	private String recieverdel;
	private int replyid;
	private Date readtime;
	private Date sendtime;
	private String createdate;
	private String createuser;
	private String modifydate;
	private String modifyuser;

	// 消息的主体
	private Message message;
	// 消息的接收者
	private SysUser reciever;
	// 消息的发送者
	private SysUser sender;
	private String sendtimestring;
	private String readtimestring; 
	private String modifydatestring;

	public String getReadtimestring() {
		return readtimestring;
	}

	public void setReadtimestring(String readtimestring) {
		this.readtimestring = readtimestring;
	}

	public String getSendtimestring() {
		return sendtimestring;
	}

	public void setSendtimestring(String sendtimestring) {
		this.sendtimestring = sendtimestring;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public SysUser getReciever() {
		return reciever;
	}

	public void setReciever(SysUser reciever) {
		this.reciever = reciever;
	}

	public SysUser getSender() {
		return sender;
	}

	public void setSender(SysUser sender) {
		this.sender = sender;
	}

	public MsgStatus() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getRecieverid() {
		return recieverid;
	}

	public void setRecieverid(String recieverid) {
		this.recieverid = recieverid;
	}

	public String getSenderdel() {
		return senderdel;
	}

	public void setSenderdel(String senderdel) {
		this.senderdel = senderdel;
	}

	public String getRecieverdel() {
		return recieverdel;
	}

	public int getReplyid() {
		return replyid;
	}

	public void setReplyid(int replyid) {
		this.replyid = replyid;
	}

	public void setRecieverdel(String recieverdel) {
		this.recieverdel = recieverdel;
	}

	public Date getReadtime() {
		return readtime;
	}

	public void setReadtime(Date readtime) {
		this.readtime = readtime;
	}

	public String getModifydatestring() {
		return modifydatestring;
	}

	public void setModifydatestring(String modifydatestring) {
		this.modifydatestring = modifydatestring;
	}

	public Date getSendtime() {
		return sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

	public String getModifydate() {
		return modifydate;
	}

	public void setModifydate(String modifydate) {
		this.modifydate = modifydate;
	}

	public String getModifyuser() {
		return modifyuser;
	}

	public void setModifyuser(String modifyuser) {
		this.modifyuser = modifyuser;
	}

	public String getReadstatus() {
		return readstatus;
	}

	public void setReadstatus(String readstatus) {
		this.readstatus = readstatus;
	}

	public String getSendstatus() {
		return sendstatus;
	}

	public void setSendstatus(String sendstatus) {
		this.sendstatus = sendstatus;
	}

	@Override
	public String toString() {
		return "MsgStatus [id=" + id + ", uuid=" + uuid + ", msgid=" + msgid + ", senderid=" + senderid + ", recieverid=" + recieverid + ", readstatus=" + readstatus + ", sendstatus=" + sendstatus
				+ ", senderdel=" + senderdel + ", recieverdel=" + recieverdel + ", readtime=" + readtime + ", sendtime=" + sendtime + ", createdate=" + createdate + ", createuser=" + createuser
				+ ", modifydate=" + modifydate + ", modifyuser=" + modifyuser + ", message=" + message + ", reciever=" + reciever + ", sender=" + sender + "]";
	}

}
