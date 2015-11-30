package com.zonekey.study.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springside.modules.mapper.JsonMapper;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.zonekey.study.entity.PageBean;

public class JsonUtil {
	private static JsonMapper jsonMapper = JsonMapper.nonDefaultMapper();

	// jsonMapper.configure(DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
	// true);

	public static String toJson(Object object) {
		String json = jsonMapper.toJson(object);
		return json;
	}

	public static <T> T jsonToObject(Object json, Class<T> clazz) {
		//jsonMapper.getMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		if (json instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) json;
			try {
				String str = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8")).readLine();
				return jsonMapper.fromJson(str, clazz);
			} catch (Exception e) {
				return null;
			}
		}
		return jsonMapper.fromJson(String.valueOf(json), clazz);
	}

	/**
	 * 分页查找
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static PageBean jsonToPage(HttpServletRequest req) {
		PageBean pageBean = new PageBean();
		try {
			String str = new BufferedReader(new InputStreamReader(req.getInputStream(), "utf-8")).readLine();
			Map<String, Object> map = jsonToObject(str, Map.class);

			if (map.get("keywords") != null && !"".equals(map.get("keywords"))) {
				pageBean = jsonMapper.fromJson(str, PageBean.class);
			}
			Map<String, Object> page = (Map<String, Object>) map.get("page");
			int limit = Integer.parseInt(page.get("pageSize").toString());
			int offset = (Integer.parseInt(page.get("pageIndex").toString()) - 1) * limit;
			page.put("offset", offset);
			pageBean.setPage(page);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return pageBean;
	}
	public static JSONObject string2json(String str) {
		try {
			JSONObject ret=null;
			ret=JSONObject.fromObject(str);
			if ("null".equals(ret.toString())) return null;
			return ret;
		} catch (Exception e) {
			return null;
		}
	}
}
