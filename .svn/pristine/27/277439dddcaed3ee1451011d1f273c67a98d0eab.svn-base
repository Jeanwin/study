package com.zonekey.study.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.entity.Comment;
import com.zonekey.study.entity.PageBean;

public interface MyrecordsMapper extends BaseMapper<T, Serializable> {
	
	//获取评论
		public List<Map<String, Object>> getMyrecords(PageBean bean);
		
		public long count(PageBean bean);
		
}
