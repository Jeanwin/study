package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.CollectionRecord;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CollectionRecordServiceTest extends SpringTxTestCase {
	@Resource
	private CollectionRecordService crService;

	@Test
	public void test() {
		CollectionRecord cr = new CollectionRecord();
		cr.setLoginname("admin");
		cr.setResourceid(1);
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		ids.add(3);

		JsonMsg msg = crService.addCollection(cr);
		System.out.println(msg);

		msg = crService.removeCollections(ids);
		System.out.println(msg);

		PageBean pb = new PageBean();
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("offset", 0);
		page.put("limit", 2);
		Map<String, Object> keywords = new HashMap<String, Object>();
		pb.setPage(page);
		pb.setKeywords(keywords);
		crService.pageCollections(pb);

	}

}
