/*****************************
 * Copyright (c) 2014 by Zonekey Co. Ltd.  All rights reserved.
 ****************************/
package com.zonekey.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.dao.SysCodeMapper;

/**
 * @Title: @{#} SysCodeService.java
 * @Description: <p>
 *               SysCode实体业务类
 *               </p>
 * @author <a href="mailto:cuiwx@zonekey.com.cn">cuiwx</a>
 * @date 2014年9月20日 下午7:37:47
 * @version v 1.0
 */
@Component
@Transactional(readOnly = true)
public class SysCodeService {
	@Autowired
	private SysCodeMapper syscodeMapper;

	public List<Map<String, Object>> getCode(String value) {
		return syscodeMapper.getCode(value);
	}
	public Map<String,Object> getSubject(String value) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> codes =  getCode(value);
		map.put("data", codes);
		if(codes !=null){
			map.put("isSuccess", "1");
		}else{
			map.put("isSuccess", "0");
		}
		return map;
	}
    /**
     * 根据父id查询
     * @param parentId
     * @return
     */
	public List<Map<String,Object>> getCodeByParentId(String parentId){
		if(parentId!= null && !"".equals(parentId)){
			return syscodeMapper.getCodeByParentId(parentId);
		}else{
			return null;
		}
	}
}
