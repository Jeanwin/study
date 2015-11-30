package com.zonekey.study.service.echarts;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.vo.Report;

import freemarker.template.Template;


public class EchartsLineHandler implements EchartsHandler{
	private String template = "echartsLine.ftl";
	public EchartsLineHandler() {
		
	}
	
	public EchartsLineHandler(String template) {
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
		Set<String> legend = new HashSet<String>(report.getList().get(0).keySet());
		
		legend.remove("时间");
		//装载数据
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		//x轴数据展示
		List xData = new ArrayList();
		boolean flag = true;
		for (String xtitle : legend) {
			Map<String,Object> m = new HashMap<String, Object>();
			List l = new ArrayList();
			for(Map<String,Object> map : report.getList()){
				l.add(map.get(xtitle));
				if(flag){
					String time = map.get("时间")+"";
					time = time.substring(0, 10);
					xData.add(time);
				}
			}
			m.put("name", xtitle);
			m.put("type", "line");
			m.put("data", l);
			
			data.add(m);
			flag = false;
		}
		context.put("xData", JsonUtil.toJson(xData));
		context.put("legend", JsonUtil.toJson(legend));//图例
		context.put("data", JsonUtil.toJson(data));//数据
		StringWriter stringWriter = new StringWriter();
		template.process(context, stringWriter);
		return stringWriter.toString();
	}
}
