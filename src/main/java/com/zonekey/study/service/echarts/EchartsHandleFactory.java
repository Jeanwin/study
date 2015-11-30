package com.zonekey.study.service.echarts;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.zonekey.study.common.AppConstants;
import com.zonekey.study.vo.Report;

@Component
public class EchartsHandleFactory {
	private static Map<String, EchartsHandler> map = new HashMap<String, EchartsHandler>();
	@Resource
	private FreeMarkerConfigurer freeMarkerConfigurer;
	static {
		//普通饼状图
		map.put(AppConstants.ECHARTS_PIE, new EchartsPieHandler());
		//柱状图
		map.put(AppConstants.ECHARTS_BAR, new EchartsBarHandler());
		//折线图
		map.put(AppConstants.ECHARTS_LINE, new EchartsLineHandler());
	}

	public String handler(String type,Report report) throws Exception {
		return map.get(type).handler(report,freeMarkerConfigurer);
	}
}
