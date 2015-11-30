package com.zonekey.study.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.Pagebar;
import com.zonekey.study.service.WorksService;
import com.zonekey.study.vo.Tree;
import com.zonekey.study.vo.WorksView;

@Controller
public class WorksController {
	@Resource
	private WorksService worksService;

	/**
	 * 活动报名》》没有上传图片
	 * 
	 * @return
	 */

	@RequestMapping(value = "/private/signUpHasNoPicture", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg signUpHasNoPicture(@RequestBody WorksView view) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("活动报名,没有图片上传");
		int result = worksService.addWorks(view);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("恭喜您，报名成功");
		} else if (result == -6) {
			msg.setId("2");
			msg.setContent("对不起，报名还未开始");
		} else if (result == -5) {
			msg.setId("3");
			msg.setContent("报名已结束,评审未开始");
		} else if (result == -4) {
			msg.setId("4");
			msg.setContent("报名已结束,正在评审中");
		} else if (result == -3) {
			msg.setId("5");
			msg.setContent("对不起,活动已经结束");
		} else if (result == -2) {
			msg.setId("6");
			msg.setContent("对不起，您只能添加一件作品");
		} else {
			msg.setId("7");
			msg.setContent("报名失败，请重试");
		}
		return msg;
	}

	/**
	 * 活动报名》》上传图片
	 * 
	 * @return
	 */
	@RequestMapping(value = "/private/signUpHasPicture", method = RequestMethod.POST, produces = MediaTypes.TEXT_PLAIN_UTF_8)
	public void signUpHasPicture(MultipartHttpServletRequest req, HttpServletResponse res) {
		JsonMsg msg = new JsonMsg();
		WorksView view = new WorksView();
		view.setActiveid(Integer.valueOf(req.getParameter("activeid")));
		view.setResourceid(req.getParameter("resourceid"));
		view.setName(req.getParameter("name"));
		view.setSubject(req.getParameter("subject"));
		view.setGrade(req.getParameter("grade"));
		view.setType(req.getParameter("type"));
		view.setDescription(req.getParameter("description"));
		// System.out.println(req.getFileNames());
		msg.setOperation("活动报名,上传图片");
		int result = worksService.newWorks(req, view);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("恭喜您，报名成功");
		} else if (result == -6) {
			msg.setId("2");
			msg.setContent("对不起，报名还未开始");
		} else if (result == -5) {
			msg.setId("3");
			msg.setContent("报名已结束,评审未开始");
		} else if (result == -4) {
			msg.setId("4");
			msg.setContent("报名已结束,正在评审中");
		} else if (result == -3) {
			msg.setId("5");
			msg.setContent("对不起,活动已经结束");
		} else if (result == -2) {
			msg.setId("6");
			msg.setContent("对不起，您只能添加一件作品");
		} else if (result == -1) {
			msg.setId("7");
			msg.setContent("图片上传失败，请重试");
		} else {
			msg.setId("8");
			msg.setContent("报名失败，请重试");
		}
		try {
			res.getWriter().write(JsonUtil.toJson(msg));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 活动作品展示
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/gateway/worksList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String, Object> worksList(HttpServletRequest req) {
		PageBean pageBean = JsonUtil.jsonToPage(req);
		return worksService.worksList(pageBean);
	}

	/**
	 * 批量删除作品
	 * 
	 * @param worksIds
	 * @return
	 */
	@RequestMapping(value = "/private/deleteWorks", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg deleteWorks(@RequestBody List<Integer> worksIds) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("删除作品");
		int result = worksService.deleteWorks(worksIds);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("您删除了" + result + "件作品");
		} else {
			msg.setId("2");
			msg.setContent("删除失败，请重试");
		}
		return msg;
	}

	/**
	 * 查询作品列表及其评审情况
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/private/getReviewList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String, Object> getWorksWithDetail(HttpServletRequest req) {
		PageBean pageBean = JsonUtil.jsonToPage(req);
		return worksService.getWorksWithDetail(pageBean);
	}

	/**
	 * 查询作品列表及其评审情况
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/ceshi", produces = MediaTypes.JSON_UTF_8)
	public String ceshi(Pagebar page, Model model) {
		page.setTotalCount(130);
		model.addAttribute("page", page);
		Tree tree = new Tree();
		tree.setTitle("一级标签1");
		tree.setIsfolder("0");
		Tree tree3 = new Tree();
		tree3.setTitle("一级标签2");
		tree3.setIsfolder("0");

		Tree tree1 = new Tree();
		tree1.setTitle("二级标签1");
		tree1.setIsfolder("1");
		Tree tree2 = new Tree();
		tree2.setTitle("二级标签2");
		tree2.setIsfolder("1");
		List<Tree> treeList = new ArrayList<Tree>();
		treeList.add(tree1);
		treeList.add(tree2);
		tree.setNodes(treeList);

		List<Tree> t = new ArrayList<Tree>();
		t.add(tree);
		t.add(tree3);
		model.addAttribute("trees", JsonUtil.toJson(t));
		return "portal/Myjsp";
	}

}
