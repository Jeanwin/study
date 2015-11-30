package com.zonekey.study.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.CollectionRecordService;
import com.zonekey.study.vo.CollectionRecordView;

@Controller
@RequestMapping("/mobile")
public class CollectionRecordController {

	@Autowired
	private CollectionRecordService crService;

	/**
	 * 添加收藏
	 * 
	 * @param cr
	 * @return
	 */
	@RequestMapping(value = "/collection", method = RequestMethod.POST)
	public @ResponseBody
	JsonMsg collectionAdd(@RequestBody CollectionRecordView cr) {
		return crService.addCollection(cr);
	}

	/**
	 * 取消收藏
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/removeCollections", method = RequestMethod.POST)
	public @ResponseBody
	JsonMsg removeCollections(@RequestBody List<Integer> ids) {
		return crService.removeCollections(ids);
	}

	/**
	 * 收藏列表
	 * 
	 * @param pb
	 * @return
	 */
	@RequestMapping(value = "/collectionsList", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> collectionList(@RequestBody PageBean pb) {
		return crService.pageCollections(pb);
	}

}
