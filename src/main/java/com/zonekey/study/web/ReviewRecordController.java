package com.zonekey.study.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.ReviewRecordService;


@RestController
@RequestMapping(value = "/reviewRecord")
public class ReviewRecordController {
	@Resource
	private ReviewRecordService reviewRecordService;

	/**
	 * 获取活动列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "getAllReviewRecords", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getAllReviewRecords(HttpServletRequest req) {
		PageBean bean = JsonUtil.jsonToPage(req);
		Map<String, Object> dataMap = reviewRecordService.getAllReviewRecords(bean);
		return dataMap;
	}

}
