package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.Dept;
import com.zonekey.study.vo.Tree;

@MyBatisRepository
public interface DeptMapper extends BaseMapper<Dept, String> {
	public List<Dept> findDeptsWithUserNumbers();

	public String findDeptByCode(String code);

	public List<Dept> getDeptList(String loginname);

	// 教研中心
	public List<Map<String, Object>> getDepts();

	// 查询机构树
	public Tree getDeptTree();

	public List<Map<String, Object>> getSecondDept(Map<String, Object> keywords);

	public List<Map<String, Object>> getTeacherBySecondDept(Map<String, Object> keywords);

}
