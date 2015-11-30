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
import com.zonekey.study.service.ReviewDetailService;
import com.zonekey.study.vo.AssignExpertView;
import com.zonekey.study.vo.ReviewDetailView;

/**
 *  
 * 
 * @className:ReviewDetailontroller.java
 * @classDescription:
 * @author:JeanwinHuang@live.com
 * @createTime:2015年5月27日
 */
@Controller
@RequestMapping(value = "/private")
public class ReviewDetailController {

	private static final Logger LOG = LoggerFactory.getLogger(ReviewDetailController.class);

	@Autowired
	private ReviewDetailService rdService;
    /**
     * 分配专家 
     * @param worksIds
     * @param specialist
     * @param activeid
     * @return
     */
	@RequestMapping(value = "/assignSpecialist", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg assignSpecialist(@RequestBody AssignExpertView view) {
		System.out.println(view.toString());
		JsonMsg msg = new JsonMsg();
		msg.setOperation("分配评审专家");
		int result = rdService.assingnSpecialist(view.getWorksIds(), view.getSpecialist(), view.getActiveId());
		if (result > 0) {
			msg.setId("1");
			msg.setContent("专家分配成功！");
		} else {
			msg.setId("2");
			msg.setContent("专家分配失败，请重试");
			LOG.info("专家分配失败");
		}
		return msg;
	}
	/**
	 * 查询作品评审详情
	 * @param worksid
	 * @return
	 */
	@RequestMapping(value = "/reviewDetail", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody List<ReviewDetailView> findDetailByWorkId(@RequestParam("worksid") int worksid){
		return rdService.findDetailByWorkId(worksid);
	}
	/**
	 * 根据用户及作品id查询评审状态
	 * @param worksid
	 * @return
	 */
	@RequestMapping(value = "/getReviewByUser", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody ReviewDetailView getReviewByWorkIdAndUser(@RequestParam("workid") int workid){
		return rdService.findReviewByUser(workid);
	}
}
