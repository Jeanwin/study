package com.zonekey.study.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.vo.ResourceView;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class DeptServiceTest extends SpringTxTestCase{
	@Resource
	private ResourceService reService;
	@Test
	public void testAll(){
		ResourceView view = new ResourceView();
		view.setId("10");
		view.setFileurl("/data/video/2015/20151023/49cc6ef8-a483-47e6-926c-d1a0d469256e.mp4");
		reService.previewTrans(view);
	}
}
