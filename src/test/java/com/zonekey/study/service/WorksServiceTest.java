package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.vo.WorksView;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class WorksServiceTest extends SpringTxTestCase{
	@Resource
    WorksService worksService;
	@Test
	public void testAll(){
	    WorksView view = new WorksView();
		view.setActiveid(15);
		view.setDescription("我的作品，要拿一等奖");
		view.setGrade("终极");
		view.setName("三个代表与马克思思想的结合");
		view.setPicture("picture/my999.jpg");
		view.setSubject("政治");
		view.setType("1");
		view.setResourceid("23244");
		view.setUserid("admin");
		System.out.println(JsonUtil.toJson(worksService.addWorks(view)));
		List<Integer> worksIds = new ArrayList<Integer>();
		System.out.println(worksService.deleteWorks(worksIds));
		PageBean pageBean = new PageBean();
    	Map<String,Object> keywords = new HashMap<String,Object>();
    	Map<String,Object> page = new HashMap<String,Object>();
    	keywords.put("activeId", 15);
    	page.put("limit", 10);
    	page.put("offset", 0);
    	pageBean.setKeywords(keywords);
    	pageBean.setPage(page);
		//System.out.println(JsonUtil.toJson(worksService.getWorksWithDetail(pageBean)));
		System.out.println(JsonUtil.toJson("worklist:"+worksService.worksList(pageBean)));
	}
}
