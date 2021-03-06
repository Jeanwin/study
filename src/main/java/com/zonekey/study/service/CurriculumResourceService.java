package com.zonekey.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.dao.CurriculumResourceMapper;
import com.zonekey.study.dao.DeptMapper;
import com.zonekey.study.entity.PageBean;

@Service
@Transactional(readOnly = false)
public class CurriculumResourceService {
	@Autowired
	private CurriculumResourceMapper crMapper;

	@Autowired
	private DeptMapper dMapper;

	/**
	 * 点播列表
	 * 
	 * @return
	 */
	public Map<String, Object> unicastList(PageBean pb) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", crMapper.getUnicast(pb));
		dataMap.put("count", crMapper.countUnicast(pb.getKeywords()));
		dataMap.put("depts", dMapper.getSecondDept(pb.getKeywords()));
		dataMap.put("teachers", dMapper.getTeacherBySecondDept(pb.getKeywords()));
		return dataMap;
	}

	/**
	 * 进入点播
	 * 
	 * @param curriculumid
	 * @return
	 */
	public List<Map<String, Object>> enterUnicast(String curriculumid) {
		return crMapper.getUnicastDetail(curriculumid);
	}
}
