package com.zonekey.study.service;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.PlayRecord;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class PlayRecordServiceTest extends SpringTxTestCase {
	
	@Autowired
	private PlayRecordService prService;

	@Test
	public void test() {
		System.out.println(prService.listTwo());
		PlayRecord pr = new PlayRecord("1", "admin", 1, 1233, "0");
		prService.playRecord(pr);
		
		PageBean pb = new PageBean();
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("offset", 0);
		page.put("limit", 2);
		Map<String, Object> keywords = new HashMap<String, Object>();
		pb.setPage(page);
		pb.setKeywords(keywords);
		prService.pageList(pb);
	}

}
