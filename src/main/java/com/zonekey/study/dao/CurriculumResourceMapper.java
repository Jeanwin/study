package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.Resource;
import com.zonekey.study.vo.UnicastResourceView;

@MyBatisRepository
public interface CurriculumResourceMapper extends BaseMapper<Resource, String> {

	// 点播列表
	public List<UnicastResourceView> getUnicast(PageBean pb);

	public long countUnicast(Map<String, Object> keywords);

	// 根据资源id查询
	public List<Map<String, Object>> getUnicastDetail(@Param("curriculumid") String curriculumid);

}
