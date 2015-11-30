package com.zonekey.study.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/mobile")
public class MobilePageController {

	@RequestMapping(value = "/unicast", method = RequestMethod.GET)
	public String toUnicast() {
		return "mobile/execList";
	}
    
	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	public String toPersonalCenter() {
		return "mobile/personalCenter";
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String toIndex() {
		return "mobile/index";
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String toModifyPassword() {
		return "mobile/modifyPassword";
	}
	
	@RequestMapping(value = "/toCollection", method = RequestMethod.GET)
	public String toCollection() {
		return "mobile/collection";
	}
	
	@RequestMapping(value = "/toCourse", method = RequestMethod.GET)
	public String toCourseDetail() {
		return "mobile/courseDetail";
	}
}
