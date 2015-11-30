package com.zonekey.study.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.vo.WorksView;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class WorksMapperTest extends SpringTxTestCase {

	@Autowired
	private WorksMapper wMapper;

	@Test
	public void testAll() {
	    wMapper.findWorksByActiveId(15);
	    Map<String,Object> keywords = new HashMap<String,Object>();
        Map<String,Object> page = new HashMap<String,Object>();
        keywords.put("activeId", 15);
        keywords.put("name", "a");
        page.put("limit", 0);
        page.put("offset", 10);
        PageBean pb = new PageBean();
        pb.setKeywords(keywords);
        pb.setPage(page);
	    wMapper.findWorksByPage(pb);
	    wMapper.findCount(pb);
	    WorksView view = new WorksView();
	    view.setActiveid(15);
	    view.setName("我的作品");
	    view.setResourceid("12134");
	    view.setUserid("admin");
	    view.setSubject("语文");
	    view.setGrade("高级");
	    view.setType("1");
	    wMapper.insertWorks(view);
	    wMapper.updateWorks(view);
	    List<Integer> worksIds = new ArrayList<Integer>();
	    worksIds.add(1);
		wMapper.delWorks(worksIds);
		wMapper.findWorksWithReview(pb);
		wMapper.findWrCount(pb);
		wMapper.checkIsSigned(15);
	}
}
