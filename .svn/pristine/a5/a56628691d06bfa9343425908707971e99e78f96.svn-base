package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.CollectionRecord;
import com.zonekey.study.entity.PageBean;

@MyBatisRepository
public interface CollectionRecordMapper extends BaseMapper<CollectionRecord, Integer> {

	public int add(CollectionRecord cr);

	public int delCollection(Integer id);

	public int delAll(@Param("ids")List<Integer> ids);

	public List<CollectionRecord> list(PageBean pb);

	public long count(Map<String, Object> keywords);

}
