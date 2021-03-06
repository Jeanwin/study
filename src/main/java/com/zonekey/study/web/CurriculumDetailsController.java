package com.zonekey.study.web;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.DateUtils;
import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.Comment;
import com.zonekey.study.entity.Material;
import com.zonekey.study.entity.Note;
import com.zonekey.study.service.CurriculumDetailsService;
import com.zonekey.study.service.ResourceService;
import com.zonekey.study.vo.Tree;

@Controller
@RequestMapping(value = "/rest/curriculumDetails")
public class CurriculumDetailsController {
	@Resource
	private CurriculumDetailsService curriculumDetailsService;
	@Resource
	private ResourceService reService;
	/**
	 * 跳转页面
	 * @param req
	 * @param res
	 * @return
	 */
/*	@RequestMapping(value = "/courseDetail", method = RequestMethod.GET)
	public String courseDetail(String curriculumid,String workid,String floder,HttpServletResponse res,Model model) {
//		String type=req.getParameter("type");
//		String workid=req.getParameter("workid");
//		String curriculumid=req.getParameter("curriculumid");
		res.setHeader("content-type", "text/html;charset=UTF-8");
		
		res.setCharacterEncoding("UTF-8");
		if(curriculumid !=null){
			Map<String,Object> map=curriculumDetailsService.selectLiveFlow(curriculumid);
			model.addAttribute("map", map);
		}else if(workid != null){
			model.addAttribute("typedata", "3");
			model.addAttribute("workid", workid);
		}
		return "portal/courseDetail";
	}*/
	/**
	 * @Title:selectNoteByWhere
	 * @Description: 根据不同条件查笔记
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectNoteByWhere1",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectNoteByWhere1(HttpServletRequest req) {
		Note note=JsonUtil.jsonToObject(req, Note.class);
		return curriculumDetailsService.selectNoteByWhere(note);
	}
	/**
	 * @Title:updateNote
	 * @Description: 修改笔记
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "updateNote",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg updateNote(HttpServletRequest req) {
		JsonMsg msg = new JsonMsg();
		int flag = 0;
		Note note=JsonUtil.jsonToObject(req, Note.class);
		flag= curriculumDetailsService.updateNote(note);
		if (flag >= 0) {

			msg.setId("1");
			msg.setOperation("编辑成功");
		} else {
			msg.setId("0");
			msg.setOperation("编辑失败");
		}

		return msg;
	}
	/**
	 * @Title:addNote
	 * @Description: 新增笔记
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "addNote",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg addNote(HttpServletRequest req) {
		JsonMsg msg = new JsonMsg();
		int flag = 0;
		Note note=JsonUtil.jsonToObject(req, Note.class);
		flag= curriculumDetailsService.addNote(note);
		if (flag >= 0) {

			msg.setId("1");
			msg.setOperation("编辑成功");
		} else {
			msg.setId("0");
			msg.setOperation("编辑失败");
		}

		return msg;
	}
	
	/**
	 * @Title:deleteNote
	 * @Description: 删除笔记
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "deleteNote",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg deleteNote(HttpServletRequest req) {
		JsonMsg msg = new JsonMsg();
		int flag = 0;
		Note note=JsonUtil.jsonToObject(req, Note.class);
		flag= curriculumDetailsService.deleteNote(note);
		if (flag >= 0) {

			msg.setId("1");
			msg.setOperation("删除成功");
		} else {
			msg.setId("0");
			msg.setOperation("删除失败");
		}

		return msg;
	}
	
	/**
	 * @Title:selectCurriculumDetails
	 * @Description: 查看作品明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectCurriculumDetails1",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectCurriculumDetails1(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectCurriculumDetails(map);
	}
	/**
	 * @Title:selectReviewDetails
	 * @Description: 查看评审明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectReviewDetails1",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectReviewDetails1(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectReviewDetails(map);
	}
	/**
	 * @Title:savetReviewDetails
	 * @Description: 保存评审明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "savetReviewDetails",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int savetReviewDetails(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.savetReviewDetails(map);
	}
	/**
	 * @Title:selectRecordDetails
	 * @Description: 查看听课明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectRecordDetails1",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectRecordDetails1(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectRecordDetails(map);
	}
	/**
	 * @Title:savetRecordDetails
	 * @Description: 保存听课明细
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "savetRecordDetails",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int savetRecordDetails(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.savetRecordDetails(map);
	}
	/**
	 * @Title:selectReviewOption
	 * @Description: 展示所有评审指标下拉框
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectReviewOption1",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectReviewOption1(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectReviewOption(map);
	}
	/**
	 * @Title:selectReviewOptiondetail
	 * @Description: 评审指标下拉框触发事件联动
	 * @author niuxl
	 * @param res
	 */
	@RequestMapping(value = "selectReviewOptiondetail1",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectReviewOptiondetail1(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectReviewOptiondetail(map);
	}
	/**
	 * 查看所有素材
	 * 
	 */
	/*@RequestMapping(value = "selectMaterialList1",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectMaterialList1(HttpServletRequest req) {
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.selectMaterialList(map);
	}*/
	/**
	 * 刪除素材
	 * 
	 */
	@RequestMapping(value = "deleteMaterial",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int deleteMaterial(HttpServletRequest req) {
		Material material = JsonUtil.jsonToObject(req, Material.class);
		return curriculumDetailsService.deleteMaterial(material);
	}
	
	/**
	 * 新增素材
	 * 
	 */
	@RequestMapping(value = "addMaterial",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int addMaterial(HttpServletRequest req) {
		Material material = JsonUtil.jsonToObject(req, Material.class);
		return curriculumDetailsService.addMaterial(material);
	}
	
	/**
	 * 修改素材
	 * 
	 */
	@RequestMapping(value = "updateMaterial",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int updateMaterial(HttpServletRequest req) {
		Material material = JsonUtil.jsonToObject(req, Material.class);
		return curriculumDetailsService.updateMaterial(material);
	}
	/**
	 * 修改素材
	 * 
	 */
	@RequestMapping(value = "allowDownloadMaterial",method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int allowDownloadMaterial(HttpServletRequest req) {
		Material material = JsonUtil.jsonToObject(req, Material.class);
		return curriculumDetailsService.allowDownloadMaterial(material);
	}
	/**
	 * @Title:importMaterial
	 * @Description: 导入素材
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	@RequestMapping(value = "importMaterial", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	int importMaterial(HttpServletRequest req) {
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		return curriculumDetailsService.importMaterial(map);
	}
	/**
	 * 上传素材
	 * 
	 * @param req
	 */
	@RequestMapping(value = "uploadMaterial", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
		void uploadMaterial(MultipartHttpServletRequest req, Material material) {
//		String parentid = resource.getParentid();
//		String curriculumid = resource.getId();
		 curriculumDetailsService.uploadMaterial(req, material);
	}
	/**
	 * 新增评论
	 */
	@RequestMapping(value = "addComment", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
		int addComment(HttpServletRequest req){
		Comment comment = JsonUtil.jsonToObject(req, Comment.class);
		comment.setCreatedate(DateUtils.getFormat("yyyy-MM-dd HH:mm"));
		return curriculumDetailsService.addComment(comment);
	}
	
	@RequestMapping(value = "queryCommentNums", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody Map<String,Object> queryCommentNums(String resourceid,String typedata){
		return curriculumDetailsService.queryCommentNums(resourceid,typedata);
	}
	/**
	 * 获取资源树-所有
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getTrees", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<Tree> getTrees(HttpServletRequest req) {
		return curriculumDetailsService.getTrees();
	}
	
	/**
	 * 查看评论的星星数和人数
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "selectCommentCount1", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	Map<String,Object> selectCommentCount1(HttpServletRequest req) {
		return curriculumDetailsService.selectCommentCount(req);
	}
	/**
	 * 
	 * @param response
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "readOnline", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public void transSwf(HttpServletResponse res, HttpServletRequest req,Model model) throws FileNotFoundException {
		String path = req.getParameter("path");
		String name = reService.transSwf(req,path);
		/*HttpSession session = req.getSession();
		session.setAttribute("fileName", name);*/
		model.addAttribute("fileName", name);
    
	}
}
