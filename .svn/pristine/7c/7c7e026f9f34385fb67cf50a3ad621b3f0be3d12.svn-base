package com.zonekey.study.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.ProcessInfo;
import com.zonekey.study.service.ProcessService;
import com.zonekey.study.vo.DotInfoView;
import com.zonekey.study.vo.ProcessView;

@RestController
@RequestMapping(value = "/process")
public class ProcessController {
	private static final Logger LOG = LoggerFactory.getLogger(ProcessController.class);
	@Resource
	private ProcessService pService;

	/**
	 * 查询打点信息列表
	 * 
	 * @param resourceid
	 * @return
	 */
	@RequestMapping(value = "/processList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<Map<String, Object>> searchProcessList(@RequestBody String resourceid) {
		int rid = Integer.parseInt(resourceid);
		return pService.findProcessList(rid);
	}

	/**
	 * 打点保存
	 * 
	 * @param process
	 * @return
	 */
	@RequestMapping(value = "/addDotInfo", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg addDotInfo(HttpServletRequest req,@RequestBody DotInfoView dotInfo) {
		//DotInfoView dotInfo = JsonUtil.jsonToObject(req, DotInfoView.class);
		JsonMsg msg = new JsonMsg();
		msg.setOperation("打点保存");
		int result = 0;
		if (dotInfo != null) {
			if (dotInfo != null && dotInfo.getContent().size() > 0) {
				for (int i = 0; i < dotInfo.getContent().size(); i++) {
					ProcessInfo info = new ProcessInfo();
					info.setResourceid(dotInfo.getResourceid());
					info.setVideoLength(dotInfo.getVideoLength());
					info.setDottime(dotInfo.getContent().get(i).get("dotTime").toString());
					info.setContent(dotInfo.getContent().get(i).get("description").toString());
					if (pService.addProcess(info) > 0) {
						result++;
					}
				}
			}

		}
		if (result > 0) {
			msg.setId("1");
			msg.setContent("您保存了" + result + "条打点信息");
		} else {
			msg.setId("2");
			msg.setContent("保存失败啦，请重试");
		}
		return msg;
	}

	/**
	 * 删除打点信息
	 * 
	 * @param processList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delDotInfo", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg delDotInfo(HttpServletRequest req) {
		List<Integer> processList = (List<Integer>) JsonUtil.jsonToObject(req, List.class);
		JsonMsg msg = new JsonMsg();
		msg.setOperation("删除打点");
		int result = pService.delProcess(processList);
		if (result == processList.size()) {
			msg.setId("1");
			msg.setContent("您删除了" + result + "条打点信息");
		} else if (result == 0) {
			msg.setId("2");
			msg.setContent("删除失败");
		} else {
			msg.setId("3");
			msg.setContent("删除失败啦，请重试");
			LOG.info("打点信息删除失败");
		}
		return msg;
	}
}
