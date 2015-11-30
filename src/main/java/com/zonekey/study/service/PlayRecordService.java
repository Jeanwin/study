package com.zonekey.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.dao.PlayRecordMapper;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.PlayRecord;
import com.zonekey.study.vo.PlayRecordView;

@Service
@Transactional(readOnly = true)
public class PlayRecordService {
	private static final Logger LOG = LoggerFactory.getLogger(PlayRecordService.class);

	@Resource
	private PlayRecordMapper prMapper;

	/**
	 * saveOrUpdate点播记录
	 * 
	 * @param pr
	 * @return
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public JsonMsg playRecord(PlayRecord pr) {
		LOG.info("record play:" + pr.toString());
		JsonMsg msg = new JsonMsg();
		msg.setOperation("播放记录");
		if (pr != null) {
			long isExist = prMapper.check(pr.getResourceid());
			if (isExist > 0) {
				int r = prMapper.updateTime(pr.getResourceid(), pr.getPlaytime());
				if (r > 0) {
					msg.setId("1");
					msg.setContent("记录更新成功");
				} else {
					msg.setId("0");
					msg.setContent("记录更新失败");
				}
			} else {
				int re = prMapper.add(pr);
				if (re > 0) {
					msg.setId("1");
					msg.setContent("记录添加成功");
				} else {
					msg.setId("0");
					msg.setContent("记录添加失败");
				}
			}
		} else {
			msg.setId("-1");
			msg.setContent("未获取到数据");
		}
		return msg;
	}

	/**
	 * 点播记录列表
	 * 
	 * @param pb
	 * @return
	 */
	public Map<String, Object> pageList(PageBean pb) {
		LOG.info("pageBean:" + pb.toString());
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("data", prMapper.list(pb));
		dataMap.put("total", prMapper.count(pb.getKeywords()));
		return dataMap;
	}

	/**
	 * 最近2条
	 * 
	 * @return
	 */
	public List<PlayRecordView> listTwo() {
		return prMapper.listTwo();
	}


	public List<Map<String, Object>> guankanList(String visitor, String currenttime){
		String type="1";
		List<Map<String,Object>> guankanlist = prMapper.findguankanlist(type,visitor,currenttime);
		return guankanlist;
	}
}
