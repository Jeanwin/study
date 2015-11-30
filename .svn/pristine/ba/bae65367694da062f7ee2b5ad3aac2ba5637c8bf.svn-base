package com.zonekey.study.service.echarts;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.vo.Report;

import freemarker.template.Template;

public class EchartsBarHandler implements EchartsHandler {
	private String template = "echartsBar.ftl";

	public EchartsBarHandler() {

	}

	public EchartsBarHandler(String template) {
		if (template != null) {
			this.template = template;
		}
	}

	public String handler(Report report, FreeMarkerConfigurer freeMarkerConfigurer) throws Exception {

		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(this.template);
		Map<String, Object> context = new HashMap<String, Object>();
		context.put("id", report.getId());
		context.put("functitle", report.getFunctitle());

		// 图例标题
		List xdata = new ArrayList();
		List ydata = new ArrayList();
		for (Map<String, Object> map : report.getList()) {
			for (Entry<String, Object> entry : map.entrySet()) {
				xdata.add(entry.getKey());
				ydata.add(entry.getValue());
			}
		}
		context.put("xdata", JsonUtil.toJson(xdata));// 数据
		context.put("ydata", JsonUtil.toJson(ydata));// 数据
		StringWriter stringWriter = new StringWriter();
		template.process(context, stringWriter);

		return stringWriter.toString();

	}
}
