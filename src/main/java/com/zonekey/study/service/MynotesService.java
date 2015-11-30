package com.zonekey.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.common.BaseRedis;
import com.zonekey.study.dao.MynotesMapper;
import com.zonekey.study.entity.PageBean;

@Service
@Transactional(readOnly = true)
public class MynotesService extends BaseRedis {
	@Resource
	private MynotesMapper mynotesDao;
	
	public Map<String,Object> getMynotes(PageBean bean){
		Map<String,Object> mapData = new HashMap<String, Object>();
		long total = mynotesDao.count(bean);
		List<Map<String,Object>> list =mynotesDao.getMynotes(bean);
		Page<Map<String,Object>> page = new PageImpl<Map<String,Object>>(list,null,total);
		mapData.put("total",page.getTotalElements());
		mapData.put("data", page.getContent());
		return mapData;
	}
}
