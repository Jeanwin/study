package com.zonekey.study.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.vo.ReviewUserView;

@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ReviewUserMapperTest extends SpringTxTestCase {

	@Autowired
	private ReviewUserMapper ruMapper;

	@Test
	public void testAll() {
		List<ReviewUserView> users = new ArrayList<ReviewUserView>();
		ReviewUserView user1 = new ReviewUserView();
		ReviewUserView user2 = new ReviewUserView();
		user1.setActiveId(15);
		user1.setUserid("admin");
		user2.setActiveId(15);
		user2.setUserid("123456");
        users.add(user1);
        users.add(user2);
	    ruMapper.findUserList(15);
	    ruMapper.insertUsers(users);
	    ruMapper.deleteUsers(users);
	}   
}
