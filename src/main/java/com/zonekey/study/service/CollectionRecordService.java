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
import com.zonekey.study.dao.CollectionRecordMapper;
import com.zonekey.study.entity.CollectionRecord;
import com.zonekey.study.entity.PageBean;

@Service
@Transactional(readOnly = true)
public class CollectionRecordService {

	private static final Logger LOG = LoggerFactory.getLogger(PlayRecordService.class);
	@Resource
	private CollectionRecordMapper crMapper;

	/**
	 * 收藏点播、直播
	 * 
	 * @param cr
	 * @return
	 */
	@SuppressWarnings("unused")
	@Transactional(readOnly = false)
	public JsonMsg addCollection(CollectionRecord cr) {
		LOG.info("CollectionRecord:" + cr.toString());
		JsonMsg msg = new JsonMsg();
		msg.setOperation("点播/直播收藏");
		if (cr != null) {
			int r = crMapper.add(cr);
			if (r > 0) {
				msg.setId("1");
				msg.setContent("收藏成功");
			} else {
				msg.setId("0");
				msg.setContent("收藏失败");
			}
		} else {
			msg.setId("-1");
			msg.setContent("未接收到参数");
		}
		return msg;
	}

	/**
	 * 取消收藏
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional(readOnly = false)
	public JsonMsg removeCollections(List<Integer> ids) {
		LOG.info("ids:" + ids.toString());
		JsonMsg msg = new JsonMsg();
		msg.setOperation("删除收藏");
		if (ids != null && ids.size() > 0) {
			int r = crMapper.delAll(ids);
			if (r == ids.size()) {
				msg.setId("1");
				msg.setContent("删除成功");
			} else {
				msg.setId("0");
				msg.setContent("删除失败，请重试");
			}
		} else {
			msg.setId("-1");
			msg.setContent("请选中收藏记录");
		}
		return msg;
	}

	/**
	 * 收藏列表
	 * 
	 * @param pb
	 * @return
	 */
	public Map<String, Object> pageCollections(PageBean pb) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("data", crMapper.list(pb));
		data.put("total", crMapper.count(pb.getKeywords()));
		return data;
	}
}
