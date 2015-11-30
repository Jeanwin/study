package com.zonekey.study.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.IdUtils;
import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.Message;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class MessageMapperTest extends SpringTxTestCase {
	@Autowired
	private MessageMapper messageMapper;

	@Test
	public void test() {
		Message msg = messageMapper.findOne(1);
		//messageMapper.countOutbox(1);
		// messageMapper.countRecord(1);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		PageBean pb = new PageBean();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("createuser", 1);
		map.put("id", 12);
		list.add(map);
		pb.setKeywords(map);
		//messageMapper.deleteVirtual(list, 1);

		// System.out.println(
		// messageMapper.findOutbox(pb).get(0).getMsgStatus().get(0).getReciever().getDept());
		//messageMapper.removeMessage(1, 1);
		System.out.println(msg.getContent());
		msg.setTitle("ceshiguo");
		messageMapper.update(msg);
		System.out.println(msg.getTitle());
		Message msg2 = new Message();
		msg2.setUuid(IdUtils.uuid2());
		msg2.setTitle("heheheehehheh");
		msg2.setContent("yayyayyayayyyay");
		messageMapper.insert(msg2);
		messageMapper.del(msg);
	}
}
