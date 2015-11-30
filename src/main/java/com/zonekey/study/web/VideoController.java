package com.zonekey.study.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.service.VideoService;
import com.zonekey.study.vo.CutImgReciever;
import com.zonekey.study.vo.MergeResult;
import com.zonekey.study.vo.MergeVideoReciever;

@Controller
@RequestMapping(value = "/video")
public class VideoController {
	@Resource
	private VideoService videoService;

	/**
	 * 截图
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cutImg", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg cutImg(HttpServletRequest req) {
		CutImgReciever img = JsonUtil.jsonToObject(req, CutImgReciever.class);
		return videoService.cutImg(img);
	}

	/**
	 * 合成视频
	 * 
	 * @param activeid
	 * @return
	 */
	@RequestMapping(value = "/mergeVideos", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg mergeVideos(@RequestBody MergeVideoReciever video) {
		return videoService.mergeVideo(video);
	}
    /**
     * 
     * @param fileid
     * @return
     */
	@RequestMapping(value = "/getMergeStatus", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody MergeResult getMergeResult(@RequestBody String fileid){
		return videoService.getMergeStatus(fileid);
	}
}
