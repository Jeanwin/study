package com.zonekey.study.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.MyworksService;
import com.zonekey.study.service.SysCodeService;


@RestController
@RequestMapping(value = "/myworks")
public class MyWorksController {
	@Resource
	private MyworksService myworksService;
	@Autowired
	private SysCodeService sysCodeService;
	/**
	 * 获取活动列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "getMyworks", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> getMyworks(HttpServletRequest req) {
		PageBean bean = JsonUtil.jsonToPage(req);
		
		Map<String,Object> map = myworksService.getMyworks(bean); 
		try {
			map.put("codeMap",sysCodeService.getSubject("JYActivityType"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

}
