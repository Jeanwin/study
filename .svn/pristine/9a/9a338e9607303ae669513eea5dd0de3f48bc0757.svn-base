package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.ProcessInfo;
import com.zonekey.study.vo.ProcessView;

@MyBatisRepository
public interface ProcessMapper extends BaseMapper<ProcessInfo, Integer> {
	public List<Map<String,Object>> processList(@Param("resourceid") int resourceid);

	public int insertAll(@Param("processes") List<ProcessInfo> process);

	public int insert(ProcessInfo process);

	public int update(ProcessInfo process);

	public int delete(@Param("list")List<Integer> process);

	public int check(ProcessInfo process);
	
}
