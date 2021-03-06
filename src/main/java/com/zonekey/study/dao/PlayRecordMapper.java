package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.PlayRecord;
import com.zonekey.study.vo.PlayRecordView;

@MyBatisRepository
public interface PlayRecordMapper extends BaseMapper<PlayRecord, Integer> {

	public int add(PlayRecord pr);

	public int delPlayRecord(@Param("playids") List<Integer> ids);

	public List<PlayRecordView> list(PageBean pb);

	public long count(Map<String, Object> keywords);

	public List<PlayRecordView> listTwo();

	public long check(@Param("rid") Integer resourceid);

	public int updateTime(@Param("reid") Integer resourceid, @Param("playtime") long playtime);


	public List<Map<String, Object>> findguankanlist(@Param("type")String type,@Param("visitor") String visitor,
			@Param("currenttime") String currenttime);
}
