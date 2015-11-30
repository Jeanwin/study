package com.zonekey.study.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.MsgStatus;
import com.zonekey.study.entity.PageBean;

/**
 *  
 * 
 * @className:MsgStatusMapper.java
 * @classDescription:
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月19日
 */
@MyBatisRepository
public interface MsgStatusMapper extends BaseMapper<MsgStatus, Integer> {
	/**
	 * 实现方法 int insert(T entity); int del(T entity); findOne();
	 */
	public List<MsgStatus> findInbox(PageBean pageBean);

	public int countInbox(@Param("recieverid") String recieverid);

	public int sendMessages(List<MsgStatus> msgStatus);

	public int deleteMessages(@Param("list") List<Integer> msgstatus);

	public int countRecord(@Param("msgid") int msgid);

	public int viewMessage(MsgStatus msgStatus);
}
