package com.zonekey.study.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.service.ReviewService;

@Controller
public class ReviewController {
	@Resource
	private ReviewService rService;
	@RequestMapping(value = "/gateway/getReviewOptions",method = RequestMethod.POST,produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody List<Map<String,Object>> getReviewOptions(){
		return rService.getReviewOptions();
	}
}
