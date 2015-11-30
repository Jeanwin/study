package com.zonekey.study.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.DateUtils;
import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.entity.Comment;
import com.zonekey.study.entity.Pagebar;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class CommentServiceTest extends SpringTxTestCase{
	@Resource
	CommentService reService;
	@Resource
	MyClassRoomService myService;
	@Test
	public void getAllComments(){
		Pagebar page = new Pagebar();
		String type = null;
		try {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void addCommentTest(){
		Comment comment =  new Comment();
		comment.setId(88);
		comment.setResourceid("2");
		comment.setType("2");
		comment.setContent("ceshicontent");
		comment.setCreatedate(DateUtils.getToday());
		comment.setCreateuser("TestZhou");
		comment.setScore("2342");
		comment.setParentid("3");
		
		
	}

}
