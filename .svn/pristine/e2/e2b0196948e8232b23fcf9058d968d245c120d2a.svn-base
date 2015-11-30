package com.zonekey.study.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.CollectionRecord;
import com.zonekey.study.entity.PageBean;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class CollectionRecordMapperTest extends SpringTxTestCase {

	@Resource
	private CollectionRecordMapper crMapper;

	@Test
	public void test() {
		CollectionRecord cr = new CollectionRecord();
		cr.setLoginname("admin");
		cr.setResourceid(1);
		crMapper.add(cr);
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		ids.add(4);
		crMapper.delCollection(1);
		crMapper.delAll(ids);
		//
		Map<String, Object> keywords = new HashMap<String, Object>();
		keywords.put("loginname", "admin");
		crMapper.count(keywords);
		//
		Map<String, Object> page = new HashMap<String, Object>();
		page.put("limit", 2);
		page.put("offset", 0);
		PageBean pb = new PageBean();
		pb.setPage(page);
		pb.setKeywords(keywords);
		crMapper.list(pb);
	}

}
