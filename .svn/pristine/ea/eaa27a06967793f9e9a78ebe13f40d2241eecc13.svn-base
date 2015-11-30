package com.zonekey.study.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.service.ReviewUserService;
import com.zonekey.study.vo.CheckDelUser;
import com.zonekey.study.vo.ReviewUserView;

/**
 *  
 * 
 * @className:ReviewUserController.java
 * @classDescription:
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月20日
 */
@Controller
@RequestMapping(value = "/private")
public class ReviewUserController {

	private static final Logger LOG = LoggerFactory.getLogger(ReviewUserController.class);

	@Autowired
	private ReviewUserService ruService;

	/**
	 * 根据活动id查询评审专家
	 * 
	 * @param activeId
	 * @return
	 */
	@RequestMapping(value = "/reviewUsers", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<ReviewUserView> reviewUsers(@RequestParam("activeId") int activeId) {
		return ruService.ruList(activeId);
	}

	/**
	 * 添加评审专家
	 * 
	 * @param rUsers
	 * @return
	 */
	@RequestMapping(value = "/addReviewers", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg addReviewers(@RequestBody List<ReviewUserView> rUsers) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("添加评审专家");
		int result = ruService.addReviewUsers(rUsers);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("您添加了" + result + "位评审专家");
		} else {
			msg.setId("2");
			msg.setContent("添加专家失败，请重试");
		}
		return msg;
	}

	/**
	 * 删除评审专家
	 * 
	 * @param rUsers
	 * @return
	 */
	@RequestMapping(value = "/delReviewers", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg delReviewers(@RequestBody List<ReviewUserView> rUsers) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("删除评审专家");
		int result = ruService.delReviewUsers(rUsers);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("您删除了" + result + "位评审专家");
		} else {
			msg.setId("2");
			msg.setContent("删除专家失败，请重试");
		}
		return msg;
	}

	/**
	 * 
	 * @param userId
	 * @param activeId
	 * @return
	 */
	@RequestMapping(value = "/delReviewerCheck", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg checkDel(@RequestBody CheckDelUser user) {
		return ruService.checkDel(user.getUserId(), user.getActiveId());
	}
}
