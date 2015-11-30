package com.zonekey.study.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.entity.Comment;
import com.zonekey.study.entity.PageBean;

public interface CommentMapper extends BaseMapper<T, Serializable> {
	/*//获取评论
	public List<Map<String, Object>> getAllComments(@Param("resourceid") String resourceid,@Param("type") String type);*/
	
	public int addComment(Comment entity);
	
	/*//查看评论
	public List<Comment> getComment(@Param("resourceid") String resourceid,@Param("type") String type);*/

	public Map<String,Object> queryCommentNums(@Param("resourceid") String resourceid,@Param("type") String type);
	public Map<String,Object> queryComment(@Param("resourceid") String resourceid,@Param("type") String type);
	

	public void hitCount(@Param("name") String name, @Param("value")String value);

	public List<Comment> getCommentByPage(PageBean pageBean);

	public long queryCommentNumsByPage(PageBean pageBean);
		
}
