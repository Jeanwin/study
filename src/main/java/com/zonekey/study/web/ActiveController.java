package com.zonekey.study.web;

import java.io.IOException;
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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.Pagebar;
import com.zonekey.study.service.ActiveService;
import com.zonekey.study.service.SysCodeService;
import com.zonekey.study.service.echarts.EchartsHandleFactory;
import com.zonekey.study.vo.ActiveView;
import com.zonekey.study.vo.Report;

@Controller
public class ActiveController {
	@Resource
	private ActiveService activeService;
	@Resource
	private EchartsHandleFactory echartsHandleFactory;
	@Autowired
	private SysCodeService sysCodeService;

	/**
	 * 新建活动》》没有上传图片
	 * 
	 * @return
	 */
	@RequestMapping(value = "/private/newActiveHasNoPicture", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg newActiveHasNoPicture(@RequestBody ActiveView view) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("新建活动,没有图片上传");
		System.out.println(view);
		int result = activeService.addActive(view);
		System.out.println("result:" + result);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("您新建了活动:" + view.getName());
		} else {
			msg.setId("2");
			msg.setContent("新建活动失败，请重试");
		}
		return msg;
	}

	/**
	 * 新建活动》》上传图片
	 * 
	 * @return
	 */
	@RequestMapping(value = "/private/newActiveHasPicture", method = RequestMethod.POST, produces = MediaTypes.TEXT_PLAIN_UTF_8)
	public void newActiveHasPicture(MultipartHttpServletRequest req, HttpServletResponse res) {
		ActiveView view = new ActiveView();
		view.setName(req.getParameter("name"));
		view.setType(req.getParameter("type"));
		view.setRegbegintime(req.getParameter("regbegintime"));
		view.setRegendtime(req.getParameter("regendtime"));
		view.setConbegintime(req.getParameter("conbegintime"));
		view.setConendtime(req.getParameter("conendtime"));
		view.setDescription(req.getParameter("description"));
		view.setContemplate(req.getParameter("contemplate"));
		view.setModel(req.getParameter("model"));
		List<String> users = new ArrayList<String>();
		String[] userArr = req.getParameter("reviewUsers").split(",");
		for (String string : userArr) {
			users.add(string);
		}
		view.setReviewUsers(users);
		System.out.println("view:" + JsonUtil.toJson(view));
		System.out.println("data:" + req.getParameter("reviewUsers"));
		JsonMsg msg = new JsonMsg();
		msg.setOperation("新建活动,上传图片");
		int result = activeService.newActive(req, view);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("您新建了活动:" + view.getName());
		} else if (result == -1) {
			msg.setId("2");
			msg.setContent("图片上传失败，请重试");
		} else {
			msg.setId("3");
			msg.setContent("新建活动失败，请重试");
		}
		try {
			res.getWriter().write(JsonUtil.toJson(msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 管理活动，更新活动》没有图片上传
	 * 
	 * @param view
	 * @return
	 */
	@RequestMapping(value = "/private/updateActiveHasNoPicture", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg updateActiveHasNoPicture(@RequestBody ActiveView view) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("修改活动,没有图片上传");
		int result = activeService.managerActive(view);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("活动信息已更新");
		} else {
			msg.setId("2");
			msg.setContent("更新失败，请重试");
		}
		return msg;
	}

	/**
	 * 更新活动》》上传图片
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/private/updateActiveHasPicture", method = RequestMethod.POST, produces = MediaTypes.TEXT_PLAIN_UTF_8)
	public void updateActiveHasPicture(MultipartHttpServletRequest req, HttpServletResponse res) throws IOException {
		ActiveView view = new ActiveView();
		view.setId(Integer.valueOf(req.getParameter("id")));
		view.setName(req.getParameter("name"));
		view.setType(req.getParameter("type"));
		view.setRegbegintime(req.getParameter("regbegintime"));
		view.setRegendtime(req.getParameter("regendtime"));
		view.setConbegintime(req.getParameter("conbegintime"));
		view.setConendtime(req.getParameter("conendtime"));
		view.setDescription(req.getParameter("description"));
		view.setContemplate(req.getParameter("contemplate"));
		view.setModel(req.getParameter("model"));
		List<String> users = new ArrayList<String>();
		String[] userArr = req.getParameter("reviewUsers").split(",");
		for (String string : userArr) {
			users.add(string);
		}
		view.setReviewUsers(users);
		System.out.println("view:" + JsonUtil.toJson(view));
		System.out.println("data:" + req.getParameter("reviewUsers"));
		JsonMsg msg = new JsonMsg();
		msg.setOperation("更新活动,上传图片");
		int result = activeService.updateActivePicture(req, view);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("活动信息已更新");
		} else if (result == -1) {
			msg.setId("2");
			msg.setContent("图片上传失败，请重试");
		} else {
			msg.setId("3");
			msg.setContent("更新失败，请重试");
		}
		res.setCharacterEncoding("UTF-8");
		res.getWriter().write(JsonUtil.toJson(msg));
	}

	/**
	 * 查询柱状统计图数据
	 * 
	 * @param activeid
	 * @return
	 */
	@RequestMapping(value = "/private/getSignerDept", method = RequestMethod.POST, produces = MediaTypes.TEXT_PLAIN_UTF_8)
	public void getSignerDept(@RequestParam("activeid") int activeid, HttpServletResponse res) {
		// int activeid =Integer.valueOf(req.getParameter("activeid"));
		Report report = new Report();
		report.setId("1");
		report.setFunctitle("报名情况");
		List<Map<String, Object>> data = activeService.findSingerDept(activeid);
		report.setList(data);
		try {
			String html = echartsHandleFactory.handler("2", report);
			res.getWriter().write(html);
			// model.addAttribute("echart", html);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询饼图数据
	 * 
	 * @param activeid
	 * @return
	 */
	@RequestMapping(value = "/private/getPieData", method = RequestMethod.POST, produces = MediaTypes.TEXT_PLAIN_UTF_8)
	public void findPieData(@RequestParam("activeid") int activeid, HttpServletResponse res) {
		Report report = new Report();
		report.setId("2");
		report.setFunctitle("评审进度");
		List<Map<String, Object>> data = activeService.findPieData(activeid);
		report.setList(data);
		try {
			String html = echartsHandleFactory.handler("1", report);
			// map.addAttribute("echart", html);
			// res.getWriter().write(html);
			res.getWriter().write(html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return "/echarts";
	}

	/**
	 * 根据活动id查询各个专家的评审情况
	 * 
	 * @param activeid
	 * @return
	 */
	@RequestMapping(value = "/private/getSpecialDetail", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<Map<String, Object>> findReviewResultBySpecialist(@RequestParam("activeid") int activeid) {
		return activeService.findReviewResultBySpecialist(activeid);
	}

	/**
	 * 获取活动列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gateway/getAllActives")
	public String getAllActives(HttpServletRequest req, Pagebar page, Model model) {
		String value = (String) req.getParameter("value");
		Map<String, Object> list = subject();
		long totalcount = activeService.countAllActives(value);
		Map<String, Object> keywords = new HashMap<String, Object>();
		keywords.put("value", value);
		page.setKeywords(keywords);
		model.addAttribute("activeList", activeService.getAllActives(page).get("data"));
		model.addAttribute("sysCodeList", list);
		page.setTotalCount(totalcount);
		model.addAttribute("page", page);
		model.addAttribute("activeType", value);
		return "portal/ActiveList";
	}

	/**
	 * 获取活动
	 */
	@RequestMapping(value = "/gateway/activeList")
	public @ResponseBody
	Map<String, Object> findActiveList(String type, String name, Pagebar page) {
		try {
			if(name != null){
				name = new String(name.getBytes("iso8859-1"),"utf-8");
				name = new String(name.getBytes("iso8859-1"),"utf-8");
			}
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			return activeService.findActiveList(type, name, page);
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", "0");
			return map;
		}
	}

	/**
	 * 活动字典
	 * 
	 * @return
	 */
	@RequestMapping(value = "/gateway/activeType")
	@ResponseBody
	public Map<String, Object> subject() {
		try {
			return sysCodeService.getSubject("JYActivityType");
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("isSuccess", "0");
			return map;
		}
	}

	/**
	 * demo
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/private/getBarChart", method = RequestMethod.GET)
	public String getBarChart(ModelMap model) {
		Report report = new Report();
		report.setId("12");
		report.setFunctitle("test标题");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("数学", 50);
		map.put("语文", 340);
		map.put("政治", 150);
		map.put("物理", 340);
		map.put("化学", 40);
		list.add(map);
		report.setList(list);
		try {
			String html = echartsHandleFactory.handler("1", report);
			model.addAttribute("echart", html);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/echarts";
	}

	/**
	 * 根据活动id查询活动详情
	 * 
	 * @param activeid
	 * @return
	 */
	@RequestMapping(value = "/gateway/getActiveById", method = RequestMethod.POST)
	public @ResponseBody
	ActiveView getById(@RequestParam("activeid") int activeid) {
		return activeService.findActiveById(activeid);
	}

	/**
	 * 根据作品id查询活动详情
	 * 
	 * @param activeid
	 * @return
	 */
	@RequestMapping(value = "/gateway/getActiveByWorkId", method = RequestMethod.POST)
	public @ResponseBody
	ActiveView getActiveByWorkId(@RequestParam("workId") int workid) {
		return activeService.getActiveByWorkId(workid);
	}
}
