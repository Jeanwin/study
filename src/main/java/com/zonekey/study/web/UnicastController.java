package com.zonekey.study.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.CurriculumResourceService;

@Controller
@RequestMapping(value = "/mobile")
public class UnicastController {

	@Autowired
	private CurriculumResourceService crService;

	/**
	 * 点播列表
	 * 
	 * @param pb
	 * @return
	 */
	@RequestMapping(value = "/unicastList", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> getUnicast(@RequestBody PageBean pb) {
		return crService.unicastList(pb);
	}

	/**
	 * 进入点播
	 * 
	 * @param curriculumid
	 * @return
	 */
	@RequestMapping(value = "/enterUnicast", method = RequestMethod.POST)
	public @ResponseBody
	List<Map<String, Object>> enterUnicast(@RequestParam("curriculumid") String curriculumid) {
		return crService.enterUnicast(curriculumid);
	}

}
