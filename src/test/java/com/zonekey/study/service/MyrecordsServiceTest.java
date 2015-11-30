package com.zonekey.study.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class MyrecordsServiceTest extends SpringTxTestCase{
	@Resource
	MyrecordsService reService;
	@Test
	public void getAllActives(){
		
		PageBean bean = new PageBean();
		Map page = new HashMap<String, Object>();
		page.put("offset", 0);
		page.put("pageSize",10);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userid",1);
		bean.setPage(page);
		bean.setKeywords(map);
		
		
	}
}
