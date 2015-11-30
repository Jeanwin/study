package com.zonekey.study.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.MyrecordsService;


@RestController
@RequestMapping(value = "/myrecords")
public class MyRecordsController {
	@Resource
	private MyrecordsService myrecordsService;

	/**
	 * 听课列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "getMyrecords", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getMyworks(HttpServletRequest req) {
		PageBean bean = JsonUtil.jsonToPage(req);
		return myrecordsService.getMyrecords(bean);
	}

}
