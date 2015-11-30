package com.zonekey.study.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SysUserMapperTest extends SpringTxTestCase {

	@Autowired
	private SysUserMapper userMapper;

	@Test
	public void testAll() {
		System.out.println(userMapper.findByLoginname("admin"));
	}
}
