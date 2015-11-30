package com.zonekey.study.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.MsgStatus;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class MsgStatusMapperTest extends SpringTxTestCase {
	@Autowired
	private MsgStatusMapper msgStatusMapper;

	@Test
	public void test() {
		msgStatusMapper.countRecord(1);
		MsgStatus msgStatus = msgStatusMapper.findOne(1);
		//msgStatusMapper.countInbox(1);
		PageBean pageBean = new PageBean();
		Map< String, Object> map = new HashMap<String,Object>();
		map.put("recieverid", 2);
		pageBean.setKeywords(map);
		System.out.println(msgStatusMapper.findInbox(pageBean));
		List<MsgStatus> list = new ArrayList<MsgStatus>();
        list.add(msgStatus);
		msgStatusMapper.sendMessages(list);
		//msgStatusMapper.deleteMessage(list);
	}

}
