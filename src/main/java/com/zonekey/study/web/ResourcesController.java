package com.zonekey.study.web;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.ResourceService;
import com.zonekey.study.vo.ResourceView;
import com.zonekey.study.vo.StoreResourceView;
import com.zonekey.study.vo.Tree;
import com.zonekey.study.vo.UpdateShareView;

@RestController
@RequestMapping(value = "/resource")
public class ResourcesController {
	@Resource
	private ResourceService reService;

	/**
	 * 获取目录树
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public List<Tree> getTree(HttpServletRequest req) {
		return reService.getTree();
	}

	/**
	 * 获取目录树-所有
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "trees", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public List<Tree> getTrees(HttpServletRequest req) {
		return reService.getTrees();
	}

	/**
	 * 获取视频树-所有
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "videoTrees", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public List<Tree> getVideoTrees(HttpServletRequest req) {
		return reService.getVideoTrees();
	}

	/**
	 * 新建目录
	 * 
	 * @param req
	 * @return 1-添加成功
	 */
	@RequestMapping(value = "addFloder", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public int addFloder(HttpServletRequest req) {
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		return reService.addFolder(map);
	}

	/**
	 * 删除目录
	 * 
	 * @param req
	 * @return 1-删除成功
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public int delFloder(HttpServletRequest req) {
		List<Map<String, Object>> list = JsonUtil.jsonToObject(req, List.class);
		return reService.del(list);
	}

	/**
	 * 资源展示
	 * 
	 * @param req
	 * @return 资源信息
	 */
	@RequestMapping(value = "resourceList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> floderList(HttpServletRequest req) {
		PageBean bean = JsonUtil.jsonToPage(req);
		return reService.floderList(bean);
	}

	/**
	 * 上传资源
	 * 
	 * @param req
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public int upload(MultipartHttpServletRequest req, ResourceView resource) {
		String parentid = resource.getParentid();
		List<Integer> result = reService.addTeachingResource(req, null, parentid);
		if (result != null) {
			return result.size();
		} else {
			return 0;
		}
	}

	/**
	 * 移动文件或文件夹
	 * 
	 * @param req
	 * @return 1-成功
	 */
	@RequestMapping(value = "move", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public int move(HttpServletRequest req) {
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		return reService.move(map);
	}

	/**
	 * 修改资源信息
	 * 
	 * @param req
	 * @return 1-成功
	 */
	@RequestMapping(value = "modify", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public int modifyResource(HttpServletRequest req) {
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		return reService.modify(map);
	}

	/**
	 * 分享收藏
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "shareResource", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public int shareResource(HttpServletRequest req) {
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		return reService.shareResource(map);
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

	/**
	 * 
	 * @param response
	 * @throws FileNotFoundException
	 */
	@RequestMapping(value = "readOnline", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public void transSwf(HttpServletResponse res, HttpServletRequest req) {
		String path = req.getParameter("path");
		try {
			String name = reService.transSwf(req, path);
			HttpSession session = req.getSession();
			session.setAttribute("fileName", name);
			res.sendRedirect(req.getContextPath() + "/flexpaper/readFile.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 最新视频
	 * 
	 * @return
	 */
	@RequestMapping(value = "getLatestVideos", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public List<Map<String, Object>> getLatestVideos() {
		return reService.getLatestVideos();
	}

	/**
	 * 查询分享给我列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "shareList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> shareList(HttpServletRequest req) {
		PageBean bean = JsonUtil.jsonToPage(req);
		return reService.shareList(bean);
	}

	/**
	 * 查询我的分享列表
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "myShareList", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public Map<String, Object> myShareList(HttpServletRequest req) {
		PageBean bean = JsonUtil.jsonToPage(req);
		return reService.myShareList(bean);
	}

	@RequestMapping(value = "/cancelShare", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg cancelShare(HttpServletRequest req) {
		@SuppressWarnings("unchecked")
		List<String> shareListDel = (List<String>) JsonUtil.jsonToObject(req, List.class);
		// System.out.println("delteid"+shareListDel.get(0));
		JsonMsg msg = new JsonMsg();
		msg.setOperation("取消分享");
		int result = reService.cancelShare(shareListDel);
		if (result > 0) {
			msg.setId("1");
			msg.setContent("您取消了" + result + "条分享");
		} else {
			msg.setId("2");
			msg.setContent("取消失败啦，请重试");
		}
		return msg;
	}

	/**
	 * 从分享中取消收藏
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/cancelStore", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg cancelStoreMyShare(HttpServletRequest req) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> storeListDel = (List<Map<String, Object>>) JsonUtil.jsonToObject(req, List.class);
		// System.out.println("delteid"+shareListDel.get(0));
		JsonMsg msg = new JsonMsg();
		msg.setOperation("取消收藏");
		int result = reService.unStoreMyShare(storeListDel);
		if (result == storeListDel.size()) {
			msg.setId("1");
			msg.setContent("您取消了" + result + "条收藏");
		} else {
			msg.setId("2");
			msg.setContent("取消失败啦，请重试");
		}
		return msg;
	}

	/**
	 * 收藏资源通用
	 * 
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/storeResource", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg storeResource(HttpServletRequest req) {
		List<Map<String, Object>> storeList = (List<Map<String, Object>>) JsonUtil.jsonToObject(req, List.class);
		System.out.println(storeList.getClass());
		JsonMsg msg = new JsonMsg();
		msg.setOperation("收藏资源");
		int result = reService.storeResource(storeList);
		if (result == storeList.size()) {
			msg.setId("1");
			msg.setContent("您收藏了" + result + "条资源");
		} else {
			msg.setId("2");
			msg.setContent("收藏失败啦，请重试");
		}
		return msg;
	}

	/**
	 * 已经不用啦
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/storeMyShare", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg storeMyShare(HttpServletRequest req) {
		@SuppressWarnings("unchecked")
		List<ResourceView> storeList = (List<ResourceView>) JsonUtil.jsonToObject(req, List.class);
		// System.out.println("delteid"+shareListDel.get(0));
		JsonMsg msg = new JsonMsg();
		msg.setOperation("收藏我的分享");
		int result = reService.storeMyShare(storeList);
		if (result == storeList.size()) {
			msg.setId("1");
			msg.setContent("您收藏了" + result + "条资源");
		} else {
			msg.setId("2");
			msg.setContent("收藏失败啦，请重试");
		}
		return msg;
	}

	public int updateHitCount(String floderid) {
		return reService.updateHitCount(floderid);
	}
    /**
     * 预览转码
     * @param view
     * @return
     */
	@RequestMapping(value = "/previewTrans", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg previewTrans(@RequestBody ResourceView view) {
		return reService.previewTrans(view);
	}
	/**
	 * 
	 * @param resourceId
	 * @return
	 */
	@RequestMapping(value = "/deleteByRsourceId", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody JsonMsg deleteById(@RequestBody String resourceId){
		return reService.deleteById(resourceId);
	}
}
