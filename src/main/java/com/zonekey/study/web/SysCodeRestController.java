/*****************************
* Copyright (c) 2014 by Zonekey Co. Ltd.  All rights reserved.
****************************/
package com.zonekey.study.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.service.SysCodeService;


/**
 * @Title: @{#} SysCodeRestController.java
 * @Description: <p>SysCode的Restful API的Controller.</p>
 * @author <a href="mailto:cuiwx@zonekey.com.cn">cuiwx</a>
 * @date 2014年9月20日 下午7:37:47
 * @version v 1.0
 */
@RestController
@RequestMapping(value = "/syscode")
public class SysCodeRestController {
	private static final Logger LOG = LoggerFactory.getLogger(SysCodeRestController.class);
	
	@Autowired
	private SysCodeService syscodeService;

	@Autowired
	private Validator validator;
	//跟踪机编号
	@RequestMapping(value = "/code", method = RequestMethod.POST)
	public List<Map<String,Object>> code(HttpServletRequest req) {
		Map<String,String> map = JsonUtil.jsonToObject(req, Map.class);
		if(map==null|| map.get("value")==null)
			return null;
		return syscodeService.getCode(map.get("value"));
	}
	/**
	 * 根据父id查找
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET,produces=MediaTypes.JSON_UTF_8)
	public List<Map<String,Object>> getCodeByParentId(@PathVariable("id") String id){
		return syscodeService.getCodeByParentId(id);
	}
}
