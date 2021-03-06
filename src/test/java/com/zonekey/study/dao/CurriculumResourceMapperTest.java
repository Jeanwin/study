package com.zonekey.study.dao;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CurriculumResourceMapperTest extends SpringTxTestCase{
	@Autowired
	private CurriculumResourceMapper crMapper;
	@Autowired
	private DeptMapper dMapper;

	@Test
	public void test() {
		Map<String, Object> keywords = new HashMap<String, Object>();
		keywords.put("loginname", "admin");
		//
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("limit", 2);
		page.put("offset", 0);
		PageBean pb = new PageBean();
		pb.setPage(page);
		pb.setKeywords(keywords);
		System.out.println(crMapper);
		crMapper.countUnicast(keywords);
		crMapper.getUnicast(pb);
		System.out.println(crMapper.getUnicastDetail("f100fd1c-c793-42c0-ab93-295843f4d266"));
		
		System.out.println(dMapper.getSecondDept(keywords));
		System.out.println(dMapper.getTeacherBySecondDept(keywords));
	}

}
