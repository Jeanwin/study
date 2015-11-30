package com.zonekey.study.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.Message;
import com.zonekey.study.entity.MsgStatus;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.SysUser;
import com.zonekey.study.service.MessageService;
import com.zonekey.study.vo.SendMessageView;

/**
 * 消息控制器
 * 
 * @author JeanwinHuang@live.com
 * 
 */
@Controller
@RequestMapping("/message")
public class MessageController {
	private static final Logger LOG = LoggerFactory.getLogger(SysUserController.class);
	@Resource
	private MessageService msgService;

	/**
	 * 查询收件箱
	 * 
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/queryInbox", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String, Object> queryInbox(HttpServletRequest req) {
		PageBean pageBean = JsonUtil.jsonToPage(req);
		LOG.info("pageBean");
		Page<MsgStatus> dataPage = msgService.queryInbox(pageBean);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("total", dataPage.getTotalElements());
		dataMap.put("data", dataPage.getContent());
		return dataMap;
	}

	/**
	 * 查询发件箱
	 * 
	 * @param pageBean
	 * @return
	 */
	@RequestMapping(value = "/queryOutbox", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String, Object> queryOutbox(HttpServletRequest req) {
		PageBean pageBean = JsonUtil.jsonToPage(req);
		Page<Message> dataPage = msgService.queryOutbox(pageBean);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("total", dataPage.getTotalElements());
		dataMap.put("data", dataPage.getContent());
		return dataMap;
	}

	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/sendMessages", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg sendMessages(HttpServletRequest req) {
		SendMessageView sendMessageView = JsonUtil.jsonToObject(req, SendMessageView.class);
		Message message = new Message();
		message.setTitle(sendMessageView.getTitle());
		message.setContent(sendMessageView.getContent());
		if(sendMessageView.getTypeid() != null && !"".equals(sendMessageView.getTypeid())){
			message.setTypeid(sendMessageView.getTypeid());
		}
		List<String> recieverIds = sendMessageView.getRecieverIds();
		List<MsgStatus> msgStatusList = new ArrayList<MsgStatus>();
		for (String recieverid : recieverIds) {
			MsgStatus msgStatus = new MsgStatus();
			msgStatus.setRecieverid(recieverid);
			if (Integer.valueOf(sendMessageView.getReplyid()) != null && !"".equals(Integer.valueOf(sendMessageView.getReplyid()))) {
				msgStatus.setReplyid(sendMessageView.getReplyid());
			}
			msgStatusList.add(msgStatus);
		}
		message.setMsgStatus(msgStatusList);
		JsonMsg msg = new JsonMsg();
		msg.setOperation("发送消息");
		int code = msgService.sendMessages(message,sendMessageView);
		int count = 0;
		if(sendMessageView.getIsEmail() != null){
			if("Y".equals(sendMessageView.getIsEmail())){
				count = msgService.sendEmialMessage(message,sendMessageView,req);
			}
		}
		if (code == -3) {
			LOG.info("您没有选中联系人");
			msg.setId("1");
			msg.setContent("没有发送消息");
		} else if (code == -2) {
			msg.setId("2");
			msg.setContent("标题不能超过50个字");
		} else if (code == -1) {
			msg.setId("3");
			msg.setContent("内容不能超过1000个字");
		} else if (code == -4 || code == 0) {
			msg.setId("4");
			msg.setContent("保存消息失败");
		} else if (code == 1 ) {
			msg.setId("5");
			if("N".equals(sendMessageView.getIsEmail())){
				count = sendMessageView.getRecieverIds().size();
				msg.setContent("您发送了"+count+"条消息");
			}else{
				msg.setContent("您发送了"+count+"封邮件");
			}
		} else {
			msg.setId("6");
			msg.setContent("消息发送失败");
		}
		return msg;
	}

	/**
	 * 收件箱删除
	 * 
	 * @param msgs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/inboxDel", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg inboxDel(HttpServletRequest req) {
		Map<String, List<Integer>> data = JsonUtil.jsonToObject(req, Map.class);
		List<Integer> msgids = data.get("data");
		Integer messgeids = data.get("data").get(0);
		System.out.println(messgeids);
		JsonMsg msg = new JsonMsg();
		msg.setOperation("删除消息");
		int code = msgService.deleteInbox(msgids);
		if (code == -1) {
			msg.setId("1");
			msg.setContent("您没有选中要删除的消息");
		} else if (code == 1) {
			msg.setId("2");
			msg.setContent("消息删除成功");
		} else {
			msg.setId("3");
			msg.setContent("消息删除失败");
		}
		return msg;
	}

	/**
	 * 删除发件箱
	 * 
	 * @param messages
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/outboxDel", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg outboxDel(HttpServletRequest req) {
		Map<String, List<Integer>> data = JsonUtil.jsonToObject(req, Map.class);
		List<Integer> messageIds = data.get("data");
		JsonMsg msg = new JsonMsg();
		msg.setOperation("发件箱删除");
		int code = msgService.deleteOutbox(messageIds);
		if (code == -1) {
			msg.setId("1");
			msg.setContent("您没有选中要删除的消息");
		} else if (code == 1) {
			msg.setId("2");
			msg.setContent("删除消息成功");
		} else {
			msg.setId("3");
			msg.setContent("删除消息失败");
		}
		return msg;
	}
	/**
	 * 标记消息为已阅读
	 * @param msgStatus
	 * @return
	 */
    @RequestMapping(value = "/viewMessage", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg viewMessage(@RequestBody MsgStatus msgStatus) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("将消息标记为已阅读");
		int code = msgService.viewMessage(msgStatus);
		if (code == 1) {
			msg.setId("1");
			msg.setContent("标记成功");
		} else {
			msg.setId("2");
			msg.setContent("标记失败");
		}
		return msg;
	}
}
