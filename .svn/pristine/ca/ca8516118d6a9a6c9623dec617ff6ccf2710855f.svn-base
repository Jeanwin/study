package com.zonekey.study.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ServerMapperTest extends SpringTxTestCase {
	@Resource
	private ServerMapper sMapper;

	@Test
	public void testCRUD() {
        List<String> servers = sMapper.getServers("1");
        System.out.println(servers);
	}

}
