package com.zonekey.study.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class MyClassRoomServiceTest extends SpringTxTestCase {
	@Resource
	private MyClassRoomService myClassRoomService;

	@Test
	public void testInsertintowisclass() {
		Map<String, Object> addfile = new HashMap<String, Object>();
		addfile.put("parentid", "1");
		// myClassRoomService.insertintowisclass(addfile);
		PageBean pageBean = new PageBean();
		Map<String, Object> keywords = new HashMap<String, Object>();
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("limit", 10);
		page.put("offset", 0);
		pageBean.setKeywords(keywords);
		pageBean.setPage(page);
	}
}
