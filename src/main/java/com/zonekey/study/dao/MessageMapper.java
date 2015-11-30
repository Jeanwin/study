package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.Message;
import com.zonekey.study.entity.PageBean;

/**
 *  
 * 
 * @className:MessageMapper.java
 * @classDescription:
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月19日
 */
@MyBatisRepository
public interface MessageMapper extends BaseMapper<Message, Integer> {
	/**
	 * 实现方法 int insert(T entity); int update(T entity); int del(T entity);
	 * findOne()
	 */
	public int deleteVirtual(@Param("list") List<Map<String, Object>> list, @Param("modifyuser") String modifyuser);

	/**
	 * 彻底删除发件箱
	 * 
	 * @param msg
	 * @return
	 */
	public int deleteMessage(@Param("id") int msgid);
	/**
	 * 逻辑删除
	 * @param msgid
	 * @param modifyuser
	 * @return
	 */
	public int removeMessage(Message message);
	
	public List<Message> findOutbox(PageBean pageBean);

	public int countOutbox(@Param("senderid") String senderid);
	// public int countRecord(@Param("msgid") int msgid);
}
