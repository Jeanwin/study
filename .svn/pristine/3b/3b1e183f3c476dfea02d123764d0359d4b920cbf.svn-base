package com.zonekey.study.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.DateTermUtil;
import com.zonekey.study.common.HttpSend;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.Comment;
import com.zonekey.study.entity.Curriculum;
import com.zonekey.study.entity.Pagebar;
import com.zonekey.study.entity.Wisclassroom;
import com.zonekey.study.service.CurriculumDetailsService;
import com.zonekey.study.service.MyClassRoomService;
import com.zonekey.study.service.ResourceService;
import com.zonekey.study.service.ServerService;
import com.zonekey.study.service.SysCodeService;

@Controller
@RequestMapping(value = "/rest/wisclassroom")
public class WisclassroomController {
	private static final Logger LOG = LoggerFactory.getLogger(MyClassRoomController.class);
	@Autowired
	private MyClassRoomService myClassRoomService;
	@Resource
	private ResourceService reService;
	@Resource
	private SysCodeService syscodeService;
	@Resource
	private ServerService serverService;
	@Resource
	private CurriculumDetailsService curriculumDetailsService;
	/**
	 * 根据mac查询今天教室的课前准备资源(智慧教室下载使用)
	 */
	@RequestMapping(value = "findResourceBeforeClassByMac" ,method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> findResourceBeforeClassByMac(HttpServletRequest req){
		Map<String,Object> map=new HashMap<String, Object>();
		String mac=req.getParameter("mac");
		String nowdate=DateTermUtil.getNowDate();//今天的日期
		map.put("date", nowdate);
		map.put("mac", mac);
	return myClassRoomService.findResourceBeforeClassByMac(map,req);
	}
	/**
	 * 上传资源 
	 * @param req
	 */
	@RequestMapping(value = "uploadwisclassroom", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int uploadwisclassroom(MultipartHttpServletRequest req,Wisclassroom wisclassroom){
		return myClassRoomService.uploadwisclassroom(req,wisclassroom);
	}
	/**
	 * 上传资源
	 * @param req
	 */
	@RequestMapping(value = "goupload", method = RequestMethod.GET)
	public String goupload(){
		return "upload";
	}
	/**
	 * 查询直播列表 
	 * @param req
	 */
	@RequestMapping(value = "selectLiveCurriculum", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectLiveCurriculum(HttpServletRequest req){
		return myClassRoomService.selectLiveCurriculum();
	}
	/**
	 * 測試直播流
	 * @param req
	 */
	@RequestMapping(value = "liveFlow", method = RequestMethod.GET)
	public String liveFlow(){
		return "liveFlow";
	}
	/**
	 * 查询直播流 
	 * @param req
	 */
/*	@RequestMapping(value = "selectLiveFlow", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectLiveFlow(HttpServletRequest req){
//		Curriculum curriculum=JsonUtil.jsonToObject(req, Curriculum.class);
		Curriculum curriculum=new Curriculum();
		curriculum.setAreaid("83dc9244d63e465b8553a1024bcf0da5");
		curriculum.setLivemodel("33");
		curriculum.setDate("2015-05-21");
		curriculum.setEndtime("14:45");
		//根据教室id，查录播机ip
			String ip=myClassRoomService.selectIpByArea(curriculum.getAreaid()).get("ip").toString();
			//调用接口 拼url
			String ipPort = serverService.getWebServer("1");
			ipPort="192.168.12.122:8080";//临时
//			String url = "http://"+ipPort+"/deviceService/sendToRecord?IP="+ip+"&RecordCmd=QueryRtspUrls";
			//?????
			String url = "http://"+ipPort+"/deviceService/getRtspUrlFromZddm?mac="+ip+"&deviceType=recording";
			//调用设备 查询rtsp地址
			String result = HttpSend.get(url);
			//转json
			Map<String,Object> resultMap = JsonUtil.jsonToObject(result, Map.class);
			if(resultMap.get("film") == null){
				return null;
			}
			//拼一个json格式参数
			Map<String,Object> parammap=new HashMap<String,Object>();
			List<Map<String,Object>> maplist=  new ArrayList<Map<String,Object>>();
			//如果是电影模式
			if(curriculum.getLivemodel().equals("33")){
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("rtmp_repeater", resultMap.get("film"));
				data.put("uid", UUID.randomUUID().toString());
				data.put("grade", "1");
				data.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				data.put("group_id", ip);
				maplist.add(data);
				//如果是资源模式
			}else if(curriculum.getLivemodel().equals("34")){
				Map<String,Object> studata = new HashMap<String, Object>();
				studata.put("rtmp_repeater", resultMap.get("students"));
				studata.put("uid", UUID.randomUUID().toString());
				studata.put("grade", "1");
				studata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				studata.put("group_id", ip);
				maplist.add(studata);
				//
				Map<String,Object> fulldata = new HashMap<String, Object>();
				fulldata.put("rtmp_repeater", resultMap.get("full"));
				fulldata.put("uid", UUID.randomUUID().toString());
				fulldata.put("grade", "1");
				fulldata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				fulldata.put("group_id", ip);
				maplist.add(fulldata);
				//
				Map<String,Object> teacherdata = new HashMap<String, Object>();
				teacherdata.put("rtmp_repeater", resultMap.get("teacher"));
				teacherdata.put("uid", UUID.randomUUID().toString());
				teacherdata.put("grade", "1");
				teacherdata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				teacherdata.put("group_id", ip);
				maplist.add(teacherdata);
				//
				Map<String,Object> vgadata = new HashMap<String, Object>();
				vgadata.put("rtmp_repeater", resultMap.get("vga"));
				vgadata.put("uid", UUID.randomUUID().toString());
				vgadata.put("grade", "1");
				vgadata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				vgadata.put("group_id", ip);
				maplist.add(vgadata);
				//如果是电影加资源
			}else if(curriculum.getLivemodel().equals("35")){
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("rtmp_repeater", resultMap.get("film"));
				data.put("uid", UUID.randomUUID().toString());
				data.put("grade", "1");
				data.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				data.put("group_id", ip);
				maplist.add(data);
				//
				Map<String,Object> studata = new HashMap<String, Object>();
				studata.put("rtmp_repeater", resultMap.get("students"));
				studata.put("uid", UUID.randomUUID().toString());
				studata.put("grade", "1");
				studata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				studata.put("group_id", ip);
				maplist.add(studata);
				//
				Map<String,Object> fulldata = new HashMap<String, Object>();
				fulldata.put("rtmp_repeater", resultMap.get("full"));
				fulldata.put("uid", UUID.randomUUID().toString());
				fulldata.put("grade", "1");
				fulldata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				fulldata.put("group_id", ip);
				maplist.add(fulldata);
				//
				Map<String,Object> teacherdata = new HashMap<String, Object>();
				teacherdata.put("rtmp_repeater", resultMap.get("teacher"));
				teacherdata.put("uid", UUID.randomUUID().toString());
				teacherdata.put("grade", "1");
				teacherdata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				teacherdata.put("group_id", ip);
				maplist.add(teacherdata);
				//
				Map<String,Object> vgadata = new HashMap<String, Object>();
				vgadata.put("rtmp_repeater", resultMap.get("vga"));
				vgadata.put("uid", UUID.randomUUID().toString());
				vgadata.put("grade", "1");
				vgadata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				vgadata.put("group_id", ip);
				maplist.add(vgadata);
			}
			parammap.put("RecordParm", JsonUtil.toJson(maplist));
			//以后写成活的参数
			parammap.put("IP", "192.168.12.217");//转码ip
			parammap.put("PORT", "50601");
			//
			String urlall = "http://"+ipPort+"/deviceService/getRtmpUrls";
			String resultall = HttpSend.post(urlall,parammap);
			//转json
			Map<String,Object> flowMap = JsonUtil.jsonToObject(resultall, Map.class);
		return flowMap;
	}*/
	/**
	 * 获取目录树-所有
	 * 
	 * @param req
	 * @return
	 */
/*	@RequestMapping(value = "getComment", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody List<Comment> getComment(HttpServletRequest req) {
//		Comment comment = JsonUtil.jsonToObject(req, Comment.class);
		Comment comment = new Comment();
		comment.setResourceid("123");
		comment.setType("1");
		return curriculumDetailsService.getComment(comment);
	}*/
}
