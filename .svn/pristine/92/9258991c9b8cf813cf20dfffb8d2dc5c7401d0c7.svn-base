package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.zonekey.study.common.SpringTxTestCase;
import com.zonekey.study.vo.ReviewUserView;

@ContextConfiguration(locations={"/applicationContext.xml"})
public class ReviewUserServiceTest extends SpringTxTestCase{
	@Resource
    ReviewUserService ruService;	
	@Test
	public void testAll(){
		System.out.println(ruService.ruList(1));
		List<ReviewUserView> users = new ArrayList<ReviewUserView>();
		ReviewUserView user = new ReviewUserView();
		ReviewUserView user2 = new ReviewUserView();
		ReviewUserView user3 = new ReviewUserView();
		user.setActiveId(15);
		user.setUserid("admin");
		user2.setActiveId(2);
		user2.setUserid("123456");
		user3.setActiveId(2);
		user3.setUserid("admin");
		users.add(user);
		users.add(user2);
		users.add(user3);
		System.out.println(ruService.addReviewUsers(users));
		System.out.println(ruService.delReviewUsers(users));
	}
}
