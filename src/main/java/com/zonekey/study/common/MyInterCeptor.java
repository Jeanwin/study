package com.zonekey.study.common;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.zonekey.study.service.CurriculumDetailsService;

public class MyInterCeptor extends HandlerInterceptorAdapter {

	@Resource
	private CurriculumDetailsService curriculumDetailsService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		Map<String,String[]> map = request.getParameterMap();
		Set<String> key = map.keySet();
		String name = key.iterator().next();
		String[] value = map.get(name);
		curriculumDetailsService.hitCount(name, value[0]);
		return true;
	}

}
