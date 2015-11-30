package com.zonekey.study.mobile.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zonekey.study.dao.SysCodeMapper;
import com.zonekey.study.entity.Pagebar;
import com.zonekey.study.mobile.service.MobileIndexService;
import com.zonekey.study.service.ActiveService;
import com.zonekey.study.service.CurriculumDetailsService;
import com.zonekey.study.service.ResourceService;
import com.zonekey.study.service.SysCodeService;
import com.zonekey.study.service.SysUserService;
@Controller
@RequestMapping(value = "/mobile")
public class MobileIndexController {
	@Autowired
	private MobileIndexService mobileIndexService;
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
	
	
	
	
	/**
	 * 获取直播课表(全部和按条件均可，以后加上分页参数)
	 */
	@RequestMapping(value = "/liveList")
	public @ResponseBody
	Map<String, Object> liveList(String deptid,String loginname,String name,Pagebar page) {
		Map<String,Object> keywords = new HashMap<String,Object>();
	/*	try {
			if(name != null){
				name = new String(name.getBytes("iso8859-1"),"utf-8");
				name = new String(name.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}*/
		keywords.put("name", name);
		int offset=0;
		if(page.getPage()==0){
			offset = 0;
		}else{
			offset = (page.getPage()-1)*page.getPageSize();
		}	
		
		page.setOffset(offset);
		page.setKeywords(keywords);
		
		try {
			return mobileIndexService.findLiveCurriculum(deptid,loginname, page);
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
	@RequestMapping(value="execList")
	@ResponseBody
	public Map<String,Object> getPublish(String deptid,String loginname,String name,Pagebar page){
		System.out.println(name);
		Map<String,Object> keywords = new HashMap<String,Object>();
		/*try {
			if(name != null){
				name = new String(name.getBytes("iso8859-1"),"utf-8");
				name = new String(name.getBytes("iso8859-1"),"utf-8");
			}
			System.out.println(name);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}*/
		keywords.put("name", name);
		int offset=0;
		if(page.getPage()==0){
			offset = 0;
		}else{
			offset = (page.getPage()-1)*page.getPageSize();
		}	
		
		page.setOffset(offset);
		page.setKeywords(keywords);
		try {
			return mobileIndexService.findPublish(deptid,loginname,page);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("isSuccess", "0");
			return map;
		}
	}
	
	
	/**
	 * H5 首页显示
	 * @param type
	 * @param page
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="index")
	public String index(String deptid,String loginname,String name,Pagebar page,Model model,HttpServletRequest req, HttpServletResponse res) throws Exception{
		model.addAttribute("zhibo",mobileIndexService.findLiveCurriculum(deptid,loginname, page));
		//model.addAttribute("dianbo",mobileIndexService.findPublish(deptid,loginname,page));
		return "/mobile/index";
	}
	
	@RequestMapping(value="hliveList")
	public String hliveList(String deptid,String loginname,String name,Pagebar page,Model model,HttpServletRequest req, HttpServletResponse res) throws Exception{
		//model.addAttribute("zhibo",mobileIndexService.findLiveCurriculum(deptid,loginname, page));
		//model.addAttribute("dianbo",mobileIndexService.findPublish(deptid,loginname,page));
		
		Map<String,Object> keywords = new HashMap<String,Object>();
		/*	try {
				if(name != null){
					name = new String(name.getBytes("iso8859-1"),"utf-8");
					name = new String(name.getBytes("iso8859-1"),"utf-8");
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}*/
			keywords.put("name", name);
			int offset=0;
			if(page.getPage()==0){
				offset = 0;
			}else{
				offset = (page.getPage()-1)*page.getPageSize();
			}	
			
			page.setOffset(offset);
			page.setKeywords(keywords);
			
			try {
				model.addAttribute("result", mobileIndexService.findLiveCurriculum(deptid,loginname, page));
				
			} catch (Exception e) {
				e.printStackTrace();
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("isSuccess", "0");
				model.addAttribute("exception",map);
			}
			model.addAttribute("subject",activeService.findDeptOrUser(deptid,"1"));
		return "/mobile/liveList";
	}
	
	
}
