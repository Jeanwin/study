package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.SysUser;
import com.zonekey.study.vo.SysUserView;

/**
 *  
 * 
 * @className:SysUserMapper.java
 * @classDescription:
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月19日
 */
@MyBatisRepository
public interface SysUserMapper extends BaseMapper<SysUser, String> {
	public int updateLoginDateAndIp(@Param("loginip") String loginip, @Param("loginname") String loginname);

	public int modifyPassword(SysUser user);

	public List<SysUser> findUsers(SysUser user);

	public List<Map<String, Object>> findByDept(Map<String, Object> map);

	public SysUser findByLoginname(@Param("loginname") String loginname);

	public SysUserView getUserByNameAndPass(@Param("loginname") String loginname, @Param("password") String password);

	public List<SysUser> getByDeptIds(@Param("map") List<Map<String, String>> depts);

	public int addEmailCode(@Param("loginname") String loginname, @Param("validateCode") String validateCode);

	public List<Map<String, Object>> getAllUser(@Param("loginname") String loginname);

	/*
	 * 验证方法
	 */
	public int validatePhone(@Param("phone") String phone);

	public int validateEmail(@Param("email") String email);

	/**
	 * 根据用户登录名查询用户邮箱号和真实姓名
	 * 
	 * @param users
	 * @return
	 */
	public List<Map<String, Object>> findEmais(@Param("users") List<String> users);

	// 添加专家,根据部门id查询用户
	public List<SysUser> getByDeptId(PageBean pageBean);

	// 添加专家，查询用户数量
	public long findUserCountByDeptId(PageBean pageBean);

	// 查询当前用户是否有系统管理员权限
	public int hasAdminPermission();

	//
	public List<String> getUserRole();

	// 查询二级部门
	public Map<String, Object> getDeptName(@Param("parentDeptId") String parentDeptId);
}
