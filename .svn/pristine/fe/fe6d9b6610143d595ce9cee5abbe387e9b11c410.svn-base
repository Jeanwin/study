package com.zonekey.study.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.entity.Dept;
import com.zonekey.study.service.DeptService;
import com.zonekey.study.vo.Tree;

@Controller
@RequestMapping("/dept")
public class DeptController {
	@Resource
	private DeptService deptService;

	/**
	 * 查询所有部门及人数
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryDepts", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<Dept> queryDepts() {
		return deptService.findDepts();
	}

	/**
	 * 个人所属机构及对应班级列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/deptList", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<Dept> deptList() {
		return deptService.getDeptList();
	}

	/**
	 * 新建活动，添加评审专家，查询所有部门
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getDepts", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<Map<String, Object>> getDepts() {
		return deptService.getDepts();
	}
	/**
	 * 机构树形菜单
	 * @return
	 */
	@RequestMapping(value = "/getDeptTree", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody Tree getDeptTree(){
		return deptService.getDeptTree();
	}
}
