package com.zonekey.study.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.common.IdUtils;
import com.zonekey.study.common.ReadProperties;
import com.zonekey.study.dao.MessageMapper;
import com.zonekey.study.dao.MsgStatusMapper;
import com.zonekey.study.dao.SysUserMapper;
import com.zonekey.study.entity.Message;
import com.zonekey.study.entity.MsgStatus;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.SysUser;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.service.email.FreeMarkerMailUtil;
import com.zonekey.study.vo.SendMessageView;

@Service
@Transactional(readOnly = true)
public class MessageService {
	@Resource
	private MsgStatusMapper msgStatusMapper;
	@Resource
	private MessageMapper messageMapper;
	@Resource
	private SysUserMapper userMapper;
	@Resource
	private FreeMarkerMailUtil mailUtil;

	/**
	 * 查询发件箱
	 * 
	 * @param pageBean
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Message> queryOutbox(PageBean pageBean) {
		if (ShiroDbRealm.getCurrentLoginName() == null || "".equals(ShiroDbRealm.getCurrentLoginName())) {
			return null;
		} else {
			String senderId = ShiroDbRealm.getCurrentLoginName();
			long total = messageMapper.countOutbox(senderId);
			List<Message> data = messageMapper.findOutbox(pageBean);
			Page<Message> page = new PageImpl<Message>(data, null, total);
			return page;
		}
	}

	/**
	 * 查询收件箱
	 * 
	 * @param pageBean
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<MsgStatus> queryInbox(PageBean pageBean) {
		if (ShiroDbRealm.getCurrentLoginName() == null || "".equals(ShiroDbRealm.getCurrentLoginName())) {
			return null;
		} else {
			// System.out.println("ok");
			String recieverId = ShiroDbRealm.getCurrentLoginName();
			long total = msgStatusMapper.countInbox(recieverId);
			Map<String, Object> keywords = new HashMap<String, Object>();
			keywords.put("loginname", recieverId);
			pageBean.setKeywords(keywords);
			List<MsgStatus> data = msgStatusMapper.findInbox(pageBean);
			Page<MsgStatus> page = new PageImpl<MsgStatus>(data, null, total);
			return page;
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param messages
	 * @return
	 */
	@Transactional(readOnly = false)
	public int sendMessages(Message message,SendMessageView sendMessageView) {
		if (message == null || message.getMsgStatus().get(0).getRecieverid() == null || "".equals(message.getMsgStatus().get(0).getRecieverid())) {
			return -3;
		} else if (message.getTitle() != null && !"".equals(message.getTitle()) && message.getTitle().length() > 50) {
			return -2;
		} else if (message.getContent() != null && !"".equals(message.getContent()) && message.getContent().length() > 1000) {
			return -1;
		} else {
			// 插入消息主体
			message.setUuid(IdUtils.uuid2());
			String createuser = ShiroDbRealm.getCurrentLoginName();
			message.setCreateuser(createuser);
			message.setModifyuser(createuser);
			if (message.getTypeid() == null || "".equals(message.getTypeid())) {
				message.setTypeid("1");
			}
			int isSuccess = messageMapper.insert(message);
			int messageId = -1; 
			if(isSuccess>0){
			    messageId = message.getId();
			}else{
				return -4;
			}
			// 插入消息记录
			for (MsgStatus ms : message.getMsgStatus()) {
				ms.setMsgid(messageId);
				ms.setSenderid(createuser);
				ms.setCreateuser(createuser);
				ms.setSendstatus("1");
			}
			int code = msgStatusMapper.sendMessages(message.getMsgStatus());
			if (Integer.valueOf(messageId) != null && !"".equals(Integer.valueOf(messageId)) && code > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * 删除收件箱记录
	 * 
	 * @param msgIds
	 * @return
	 */
	@Transactional(readOnly = false)
	public int deleteInbox(List<Integer> msgIds) {
		if (msgIds == null) {
			return -1;
		} else {
			int code = msgStatusMapper.deleteMessages(msgIds);
			if (code > 0) {
				return 1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * 删除发件箱
	 * 
	 * @param messages
	 * @return
	 */
	@Transactional(readOnly = false)
	public int deleteOutbox(List<Integer> messageIds) {
		int result = 0;
		if (messageIds == null || "".equals(messageIds)) {
			result = -1;
		} else {
			/**
			 * 如果消息记录表中没有相关的发送记录，则删除消息 如果消息记录表中还有相关的发送记录，将删除标记置1
			 */
			String modifyuser = ShiroDbRealm.getCurrentLoginName();
			for (Integer messageId : messageIds) {
				if (msgStatusMapper.countRecord(messageId) > 0) {
					Message message = new Message();
					message.setId(messageId);
					message.setModifyuser(modifyuser);
					int code = messageMapper.removeMessage(message);
					if (code > 0) {
						result = 1;
					} else {
						return 0;
					}
				} else {
					int code = messageMapper.deleteMessage(messageId);
					if (code > 0) {
						result = 1;
					} else {
						return 0;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 添加消息主体内容
	 * 
	 * @param msg
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean addMessage(Message msg) {
		boolean isAdded = false;
		msg.setUuid(IdUtils.uuid2());
		if (messageMapper.insert(msg) > 0) {
			isAdded = true;
		}
		return isAdded;
	}

	/**
	 * 修改消息主体
	 * 
	 * @param msg
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean modifyMessage(Message msg) {
		boolean isModified = false;
		if (messageMapper.update(msg) > 0) {
			isModified = true;
		}
		return isModified;
	}

	/**
	 * 移除消息，并没有删除消息记录
	 * 
	 * @param msg
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean removeMessage(int msgid, String modifyuser) {
		boolean isRemoved = false;
		Message message = new Message();
		message.setId(msgid);
		message.setModifyuser(modifyuser);
		if (messageMapper.removeMessage(message) > 0) {
			isRemoved = true;
		}
		return isRemoved;
	}

	/**
	 * 删除消息记录，当消息状态表里没有相关联的记录时删除消息记录
	 * 
	 * @param msg
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean deleteMessage(Message msg) {
		boolean isDeleted = false;
		if (messageMapper.del(msg) > 0) {
			isDeleted = true;
		}
		return isDeleted;
	}
	/**
	 * 标记消息为已阅读
	 * @param msgStatus
	 * @return
	 */
	@Transactional(readOnly = false)
	public int viewMessage(MsgStatus msgStatus){
		if(Integer.valueOf(msgStatus.getId())==null || "".equals(Integer.valueOf(msgStatus.getId()).toString())){
			return 0;
		}else{
		    String modifyuser = ShiroDbRealm.getCurrentLoginName();
		    msgStatus.setModifyuser(modifyuser);
		    int issuccess = msgStatusMapper.viewMessage(msgStatus);
		    if(issuccess>0){
		    	return 1;
		    }else{
		    	return 0;
		    }
		}
	}
	/**
	 * 发送邮件
	 * @param message
	 * @return
	 */
	public int sendEmialMessage(Message message,SendMessageView sendMessageView,HttpServletRequest req){
		List<Map<String,Object>> recievers = userMapper.findEmais(sendMessageView.getRecieverIds());
		SysUser sender = userMapper.findByLoginname(ShiroDbRealm.getCurrentLoginName());
		int count = 0;
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();
		System.out.println(sendMessageView.getRecieverIds());
		for (Map<String, Object> data : recievers) {
			data.put("sender",sender);
			data.put("message",message);
			data.put("senddate",new SimpleDateFormat("YYYY-MM-dd hh:mm").format(new Date()));
			data.put("appBase",ReadProperties.getByName("common.ip")+req.getContextPath());
			//data.put("appBase","http://study.zonekey.com.cn/study");
			datas.add(data);
		}
		for (Map<String, Object> root : datas) {
			boolean flag = mailUtil.sendTemplateMail(root,root.get("email").toString(),message.getTitle(),"messageEmail.ftl");
			if(flag){
				count = count+1;
			}
		}
		return count;
	}
}
