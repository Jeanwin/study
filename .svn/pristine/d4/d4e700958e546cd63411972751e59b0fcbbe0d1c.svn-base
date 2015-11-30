package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.Dept;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.vo.SysUserView;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class SysUserServiceTest extends SpringTxTestCase {

	@Autowired
	private SysUserService userService;
	@Autowired
	private ShiroDbRealm realm;

	/*
	 * @Autowired private MultipartHttpServletRequest req;
	 */

	@Test
	public void test() {
		//userService.getUserById(1);
	}

	@Test
	public void test2() {
		System.out.println(userService.getByLoginname("admin"));
	}

	@Test
	public void test3() {
		List<Dept> list = new ArrayList<Dept>();
		Dept dept1 = new Dept();
		Dept dept2 = new Dept();
		//dept1.setId(1);
		//dept2.setId(2);
		list.add(dept1);
		list.add(dept2);
		//userService.getUsersByDeptIds(list);
	}

	@Test
	public void test4() {
		SysUserView user = new SysUserView();
		//user.setId(1);
		user.setOldPassword("123");
		user.setNewPassword("admin123");
		user.setRepPassword("admin123");
		userService.modifyPassword(user);
	}

	@Test
	public void test5() {
		//SysUser user = userService.getUserById(1);
		//user.setPhone("18679791349");
		//user.setEmail("1984719960@qq.com");
		//String modifyuser = ShiroDbRealm.getCurrentLoginName();
		//user.setModifyuser(modifyuser);
		//userService.modifyPersonalInfo(user, null);
	}

	@Test
	public void test6() {
		userService.getByLoginname("admin");
	}

	@Test
	public void test7() {
		List<Dept> depts = new ArrayList<Dept>();
		Dept dept = new Dept();
		Dept dept2 = new Dept();
		//dept.setId(1);
		//dept2.setId(2);
		depts.add(dept);
		depts.add(dept2);
		//List<SysUser> users = userService.getUsersByDeptIds(depts);
	}
    @Test 
    public void testGetUserByDeptPage(){
    	PageBean pageBean = new PageBean();
    	Map<String,Object> keywords = new HashMap<String,Object>();
    	Map<String,Object> page = new HashMap<String,Object>();
    	keywords.put("deptid", "1");
    	keywords.put("onlybyname", "0");
    	keywords.put("name", "a");
    	page.put("limit", 10);
    	page.put("offset", 0);
    	pageBean.setKeywords(keywords);
    	pageBean.setPage(page);
    	System.out.println(JsonUtil.toJson(userService.findUsersByDeptPage(pageBean)));
    }
}
