package com.zonekey.study.dao;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.Dept;
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class DeptMapperTest extends SpringTxTestCase {
    @Resource
    private DeptMapper deptMapper;
	@Test
	public void test() {
		List<Dept> depts = deptMapper.findDeptsWithUserNumbers();
		System.out.println(depts.get(0).getName()+depts.get(0).getUserAmount());
		System.out.println("value:"+deptMapper.getDepts().get(0).get("value"));
		System.out.println("name:"+deptMapper.getDepts().get(0).get("name"));
	}

}
