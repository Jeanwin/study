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



public class EchartsPieHandler implements EchartsHandler {
	private String template = "echartsPie.ftl";
	
	public EchartsPieHandler() {
		
	}
	
	public EchartsPieHandler(String template) {
		if (template != null) {
			this.template = template;
		}
	}
	
	public String handler(Report report,FreeMarkerConfigurer freeMarkerConfigurer) throws Exception {
		Template template = freeMarkerConfigurer.getConfiguration().getTemplate(this.template);
		Map<String,Object> context = new HashMap<String, Object>();
		context.put("id", report.getId());
		// 饼图标题
		context.put("functitle",report.getFunctitle());
		
		//图例标题
		List<String> legend = new ArrayList<String>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		int total = 0;
		int reviewed = 0;
		for (Map<String,Object> map : report.getList()) {
			for (Entry<String, Object> entry: map.entrySet()) {
				Map<String,Object> m = new HashMap<String, Object>();
				m.put("name", entry.getKey());
				m.put("value", entry.getValue());
				legend.add(entry.getKey());
				data.add(m);
				//总计
				total += Integer.valueOf(entry.getValue().toString());
				//reviewed
				if("已评审".equals(entry.getKey().toString())){
					reviewed = Integer.valueOf(entry.getValue().toString());
				}
			}
		}
		context.put("total", total);
		context.put("reviewed", reviewed);
		context.put("legend", JsonUtil.toJson(legend));//图例
		context.put("data", JsonUtil.toJson(data));//数据
		StringWriter stringWriter = new StringWriter();
		template.process(context, stringWriter);

		return stringWriter.toString();
	}
}
