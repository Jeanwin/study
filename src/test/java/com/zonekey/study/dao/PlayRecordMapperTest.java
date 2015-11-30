package com.zonekey.study.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.PlayRecord;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class PlayRecordMapperTest extends SpringTxTestCase {
	@Resource
	private PlayRecordMapper prMapper;

	@Test
	public void test() {
		PlayRecord pr = new PlayRecord();
		pr.setLoginname("admin");
		pr.setPlaytime(100);
        pr.setFlag("0");
        pr.setType("2");
        pr.setResourceid(1);
		prMapper.add(pr);
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);
		ids.add(2);
		ids.add(3);
		prMapper.delPlayRecord(ids);
		
		prMapper.listTwo();
		prMapper.count();
		
		PageBean pb = new PageBean();
		Map<String,Object> page = new HashMap<String,Object>();
		Map<String,Object> keywords = new HashMap<String,Object>();
		page.put("limit", 10);
		page.put("offset", 0);
		pb.setPage(page);
		pb.setKeywords(keywords);
		prMapper.list(pb);
		
		prMapper.check(1);
		prMapper.updateTime(1, 123);
	}

}
