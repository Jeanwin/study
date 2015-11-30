package com.zonekey.study.web;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.AppConstants;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.dao.CommentMapper;
import com.zonekey.study.dao.SysCodeMapper;
import com.zonekey.study.entity.Note;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.Pagebar;
import com.zonekey.study.service.ActiveService;
import com.zonekey.study.service.CurriculumDetailsService;
import com.zonekey.study.service.MyClassRoomService;
import com.zonekey.study.service.ResourceService;
import com.zonekey.study.service.SysCodeService;
import com.zonekey.study.service.SysUserService;
@Controller
@RequestMapping(value = "/gateway")
public class IndexController {
	@Autowired
	private MyClassRoomService myClassRoomService;
	@Resource
	private CurriculumDetailsService curriculumDetailsService;
	@Autowired
	private SysCodeService sysCodeService;
	@Autowired
	private SysCodeMapper syscodeMapper;
	@Resource
	private SysUserService userService;
	@Resource
	private ResourceService reService;
	@Resource
	private ActiveService activeService;
	@Resource
	private CommentMapper commentMapper;
	/**
	 * 热门课程
	 */
	@RequestMapping(value = "/index")
	public String index(String deptid,String loginname,Pagebar page,Model model,HttpServletRequest req, HttpServletResponse res) {
		try {
			page.setPageSize(8);
			model.addAttribute("hotList", myClassRoomService.findHotList(deptid, page));
			model.addAttribute("liveList", myClassRoomService.findLiveCurriculum(deptid,loginname, page));
			model.addAttribute("execList", myClassRoomService.findPublish(deptid,loginname,page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/portal/index";
	}
	
	/**
	 * 热门课程
	 */
	@RequestMapping(value = "/hotList")
	public @ResponseBody
	Map<String, Object> findHotList(String type,Pagebar page) {
		try {
			return myClassRoomService.findHotList(type, page);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isSuccess", "0");
			return map;
		}
	}
	
	/**
	 * 获取直播课表(全部和按条件均可，以后加上分页参数)
	 */
	@RequestMapping(value = "/liveList")
	public @ResponseBody
	Map<String, Object> findLiveCurriculum(String deptid,String loginname,String name,Pagebar page) {
		Map<String,Object> keywords = new HashMap<String,Object>();
		try {
			if(name != null){
				name = new String(name.getBytes("iso8859-1"),"utf-8");
				name = new String(name.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		keywords.put("name", name);
		page.setKeywords(keywords);
		try {
			return myClassRoomService.findLiveCurriculum(deptid,loginname, page);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isSuccess", "0");
			return map;
		}
	}
	
	/**
	 * 获取直播课表(全部和按条件均可，以后加上分页参数)
	 *//*
	@RequestMapping(value = "/live")
	public String findLive(String type,Pagebar page,Model model) {
		try {
			Map<String,Object> map = myClassRoomService.findLiveCurriculum(type, page);
			model.addAttribute("liveList", map);
			page.setTotalCount(Long.valueOf(map.get("total").toString()));
			model.addAttribute("page",page);
			model.addAttribute("type",type);
			model.addAttribute("subject", syscodeMapper.getCode("Subject"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/portal/liveList";
	}*/
	/**
	 * 精品课程列表
	 * @param type 课程类型
	 * @param page
	 * @return
	 */
	@RequestMapping(value="execList")
	@ResponseBody
	public Map<String,Object> getPublish(String deptid,String loginname,String name,Pagebar page){
		System.out.println(name);
		Map<String,Object> keywords = new HashMap<String,Object>();
		try {
			if(name != null){
				name = new String(name.getBytes("iso8859-1"),"utf-8");
				name = new String(name.getBytes("iso8859-1"),"utf-8");
			}
			System.out.println(name);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		keywords.put("name", name);
		page.setKeywords(keywords);
		try {
			return myClassRoomService.findPublish(deptid,loginname,page);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isSuccess", "0");
			return map;
		}
	}

	/**
	 * 精品课程列表
	 * @param type 课程类型
	 * @param page
	 * @return
	 */
	@RequestMapping(value="exec")
	public String getExec(HttpServletRequest req,Pagebar page,Model model){
		try {
			String deptid = req.getParameter("deptid");
			String loginname = req.getParameter("loginname");
			Map<String,Object> map = myClassRoomService.findPublish(deptid,loginname,page);
			page.setTotalCount(Long.valueOf(map.get("total").toString()));
			model.addAttribute("page",page);
			model.addAttribute("execList",map);
			model.addAttribute("type",deptid);
			model.addAttribute("subject", syscodeMapper.getCode("Subject"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "/portal/exceList";
	}
	/**
	 * 精品课程列表
	 * @param type 课程类型
	 * @param page
	 * @return
	 */
	@RequestMapping(value="info")
	public String info(String type,Pagebar page){
		return "/portal/info";
	}
	/**
	 * 学科字典
	 * @return 直播type=1  点播type不传值
	 */
	@RequestMapping(value="subject")
	@ResponseBody
	public Map<String,Object> subject(String deptid,String type){
		try {
			return activeService.findDeptOrUser(deptid,type);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isSuccess", "0");
			return map;
		}
	} 
	
	@ResponseBody
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public Map<String,Object> detail(HttpServletRequest req){
		Map<String,Object> parammap = JsonUtil.jsonToObject(req, Map.class);
		Map<String,Object> result = new HashMap<String,Object>();
		Map<String,Object> map = new HashMap<String,Object>();
		String lieflow = null;
		String typedata = null;
//		String resourceid = "";
		Map<String,Object> resourcemap = null;
		if(parammap.get("curriculumid") !=null){
			lieflow = curriculumDetailsService.selectLiveFlow(parammap.get("curriculumid").toString());
			commentMapper.hitCount("curriculumid",parammap.get("curriculumid").toString());
			typedata = "1";
//			resourceid =parammap.get("curriculumid").toString();
		}else if(parammap.get("workid") != null){
			lieflow=curriculumDetailsService.selectResourceByWorksid(parammap.get("workid").toString(),parammap.get("source").toString());
			typedata = "3";
//			resourceid = (String) resourcemap.get("resourceid");
		}else if(parammap.get("floder") != null){
			lieflow=curriculumDetailsService.selectResourceByFloder(parammap.get("floder").toString());
			commentMapper.hitCount("floder",parammap.get("floder").toString());
//			resourceid =parammap.get("floder").toString();
			typedata = "2";
		}
		result.put("isSuccess", "1");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		//精品或活动
		if(resourcemap!=null ){
			resourcemap.put("map", lieflow);
			resourcemap.put("typedata", typedata);
			resourcemap.put("workid", parammap.get("workid"));
			resourcemap.put("floder", parammap.get("floder"));
//			resourcemap.put("resourceid", resourceid);
			resourcemap.put("tab", parammap.get("tab"));
			list.add(resourcemap);
			//直播
		}else if(lieflow != null&& lieflow.length()>0){
			map.put("map", lieflow);
			map.put("typedata", typedata);
			map.put("curriculumid", parammap.get("curriculumid"));
			map.put("workid", parammap.get("workid"));
//			map.put("resourceid", resourceid);
			map.put("tab", parammap.get("tab"));
			list.add(map);
		}else{
			result.put("isSuccess", "0");
		}
		result.put("data", list);
		return result;
	}
	/**
	 * 获取所有评论
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getComment", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody Map<String,Object> getComment(HttpServletRequest req) {
		PageBean pageBean = JsonUtil.jsonToPage(req);
		Map<String,Object> dataMap = curriculumDetailsService.getComment(pageBean);
		return  dataMap;
	}
	/**
	 * @Title:selectNoteByWhere
	 * @Description: 根据不同条件查笔记
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectNoteByWhere",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectNoteByWhere(HttpServletRequest req) {
		Note note=JsonUtil.jsonToObject(req, Note.class);
		return curriculumDetailsService.selectNoteByWhere(note);
	}
	/**
	 * @Title:selectCurriculumDetails
	 * @Description: 查看作品明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectCurriculumDetails",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectCurriculumDetails(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectCurriculumDetails(map);
	}
	/**
	 * @Title:selectReviewDetails
	 * @Description: 查看评审明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectReviewDetails",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectReviewDetails(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectReviewDetails(map);
	}
	/**
	 * @Title:selectRecordDetails
	 * @Description: 查看听课明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectRecordDetails",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectRecordDetails(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectRecordDetails(map);
	}
	/**
	 * 查看所有素材
	 * 
	 */
	@RequestMapping(value = "selectMaterialList",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectMaterialList(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectMaterialList(req,map);
	}
	/**
	 * 查看评论的星星数和人数
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "selectCommentCount", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectCommentCount(HttpServletRequest req) {
		return curriculumDetailsService.selectCommentCount(req);
	}
	/**
	 * @Title:selectReviewOption
	 * @Description: 展示所有评审指标下拉框
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectReviewOption",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectReviewOption(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectReviewOption(map);
	}
	/**
	 * @Title:selectReviewOptiondetail
	 * @Description: 评审指标下拉框触发事件联动
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectReviewOptiondetail",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectReviewOptiondetail(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectReviewOptiondetail(map);
	}
	/**
	 * 
	 * @param response
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "down", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public void download(HttpServletResponse res, HttpServletRequest req) throws FileNotFoundException {
		String path = req.getParameter("path");
		String name = req.getParameter("name");
		reService.downLoad(res, path, name);
	}
}
