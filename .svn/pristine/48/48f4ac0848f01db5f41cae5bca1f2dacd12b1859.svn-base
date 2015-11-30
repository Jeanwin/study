package com.zonekey.study.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CurriculumResourceServiceTest extends SpringTxTestCase {

	@Autowired
	private CurriculumResourceService crService;

	@Test
	public void test() {
		Map<String, Object> keywords = new HashMap<String, Object>();
		//keywords.put("loginname", "admin");
		//
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("limit", 6);
		page.put("offset", 0);
		PageBean pb = new PageBean();
		pb.setPage(page);
		pb.setKeywords(keywords);
		
		System.out.println(crService.unicastList(pb));
	}
}
