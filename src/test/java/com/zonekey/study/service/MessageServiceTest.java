package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.Message;
import com.zonekey.study.entity.MsgStatus;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class MessageServiceTest extends SpringTxTestCase {
	@Resource
	private MessageService msgService;

	@Test
	public void test() {
	    PageBean pageBean = new PageBean();
	    Map<String, Object> map = new HashMap<String, Object>();
	    map.put("recieverid", 1);
	    map.put("senderid", 1);
	    pageBean.setKeywords(map);
        msgService.queryInbox(pageBean);
        msgService.queryOutbox(pageBean);
        Message message = new Message();
        List<MsgStatus> msgStatus = new ArrayList<MsgStatus>();
        MsgStatus msgStatus2 = new MsgStatus();
        msgStatus.add(msgStatus2);
        message.setMsgStatus(msgStatus);
      //  msgService.sendMessages(message);
        List<Message> msgs = new ArrayList<Message>();
        //msgService.deleteOutbox(msgs);
        //msgService.deleteInbox(msgStatus);
        
	}

}
