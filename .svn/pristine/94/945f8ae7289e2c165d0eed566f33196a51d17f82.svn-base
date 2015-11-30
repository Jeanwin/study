package com.zonekey.study.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.zonekey.study.dao.base.BaseMapper;

public interface SysCodeMapper extends BaseMapper<T, Serializable> {
	public List<Map<String, Object>> getCode(String value);

	public List<Map<String, Object>> getCodeByParentId(String parentId);
}
