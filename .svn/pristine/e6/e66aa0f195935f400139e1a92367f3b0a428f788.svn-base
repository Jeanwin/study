package com.zonekey.study.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.service.email.FreeMarkerMailUtil;
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class MailServiceTest extends SpringTxTestCase {
    @Resource
	private FreeMarkerMailUtil mailUtil;
	@Test
	public void testSendTemplateMail() {
		 Map<String,Object> root = new HashMap<String,Object>();
		 root.put("username", "freeMarker");
		 root.put("password", "123");
         mailUtil.sendTemplateMail(root,"huangzw@zonekey.com.cn","测试freemarker","demo.ftl");
	}
}
