package com.zonekey.study.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.transaction.TransactionException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.zonekey.study.common.AppConstants;
import com.zonekey.study.common.BaseRedis;
import com.zonekey.study.common.CommonUtil;
import com.zonekey.study.common.DateUtils;
import com.zonekey.study.common.IdUtils;
import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.ReadProperties;
import com.zonekey.study.common.TransResult;
import com.zonekey.study.dao.ResourceMapper;
import com.zonekey.study.dao.SysUserMapper;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.vo.FileBean;
import com.zonekey.study.vo.ResourceView;
import com.zonekey.study.vo.Trans;
import com.zonekey.study.vo.Tree;

@Service
@Transactional(readOnly = true)
public class ResourceService extends BaseRedis {
	@Resource
	private ResourceMapper reDao;
	@Resource
	private SysUserMapper sysDao;
	@Resource
	private SysCodeService codeService;
    @Resource
    private ServerService serverService;
	/**
	 * 查找最新视频
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getLatestVideos() {
		return reDao.getLatestVideos();
	}

	public int addFolder(Map<String, Object> map) {
		FileBean fileBean = new FileBean();
		fileBean.setName(map.get("name").toString());
		String parentid = map.get("parentid").toString();
		this.setName(parentid, fileBean);
		/*
		 * long count = reDao.comCount(map); if (count > 0) { String name =
		 * map.get("name") + "(" + count + ")"; map.put("name", name); }
		 */
		map.put("name", fileBean.getName());
		map.put("isfolder", "0");
		map.put("nametype", "Folder");
		map.put("createuser", ShiroDbRealm.getCurrentLoginName());
		return reDao.addFolder(map);
	}

	public int del(List<Map<String, Object>> list) {
		int num = 0;
		String path = AppConstants.upload_PATH;
		for (Map<String, Object> map : list) {
			String id = map.get("id") + "";
			List<Map<String, Object>> l = reDao.findById(id);
			for (Map<String, Object> m : l) {
				if (m.get("fileurl") != null && !"".equals(m.get("fileurl").toString().trim())) {
					if (reDao.deleteCheck(m.get("fileurl").toString()) < 2) {
						CommonUtil.delete(path+m.get("fileurl").toString());
					}
				}
			}
			num += reDao.delete(id);
		}
		return num > 0 ? 1 : 0;
	}

	public Map<String, Object> floderList(PageBean bean) {
		Map<String, Object> mapData = new HashMap<String, Object>();
		long total = reDao.count(bean);
		List<Map<String, Object>> list = reDao.floderList(bean);
		/*
		 * List<Map<String, Object>> resources = new ArrayList<Map<String,
		 * Object>>(); for (Map<String, Object> resource : list) { String
		 * fileSufix; String fileUrl = resource.get("fileurl") == null ? "" :
		 * resource.get("fileurl").toString(); if (fileUrl.lastIndexOf(".") > 0)
		 * { fileSufix = fileUrl.substring(fileUrl.lastIndexOf(".")); } else {
		 * fileSufix = ""; } if (AppConstants.IMG_EXT_LIST.contains(fileSufix))
		 * { resource.put("docType", "Pictrue"); } else if
		 * (AppConstants.VIDEO_EXT_LIST.contains(fileSufix)) {
		 * resource.put("docType", "Videos"); } else if
		 * (fileSufix.equals(".doc") || fileSufix.equals(".docx") ) {
		 * resource.put("docType", "Word"); } else if (fileSufix.equals(".xls")
		 * || fileSufix.equals(".xlsx")) { resource.put("docType", "Excel"); }
		 * else if (fileSufix.equals(".ppt")) { resource.put("docType",
		 * "PowerPoint"); } else if (fileSufix.equals(".pdf")) {
		 * resource.put("docType", "PDF"); } else if (fileSufix.equals(".txt"))
		 * { resource.put("docType", "Documents"); } else if
		 * (fileSufix.equals("")) { resource.put("docType", "Folder"); } else {
		 * resource.put("docType", "Default"); } resources.add(resource); }
		 */
		Page<Map<String, Object>> page = new PageImpl<Map<String, Object>>(list, null, total);
		mapData.put("total", page.getTotalElements());
		mapData.put("data", page.getContent());
		return mapData;
	}

	public int move(Map<String, Object> map) {
		int num = 0;
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		String toId = (String) map.get("id");
		for (Map<String, Object> resource : list) {
			resource.put("parentid", toId);
			num += reDao.modify(resource);
		}
		return num > 0 ? 1 : 0;
	}

	public int modify(Map<String, Object> map) {
		return reDao.modify(map);
	}
    /**
     * 已改用addTeachingResource
     * @param req
     * @param path
     * @param parentid
     * @return
     */
	@Transactional(readOnly = false)
	public int addResource(MultipartHttpServletRequest req, String path, String parentid) {
		if (parentid == null || "".equals(parentid)) {
			parentid = "1";
		}
		List<FileBean> list = CommonUtil.upload(path, req);

		List<FileBean> files = new ArrayList<FileBean>();
		List<Trans> transFile = new ArrayList<Trans>();
		if (list != null && list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();

			for (FileBean fileBean : list) {
				this.setName(parentid, fileBean);
				files.add(fileBean);
			}
			map.put("list", files);
			map.put("parentid", parentid);
			map.put("isfolder", "1");
			map.put("source", "upload");
			map.put("createuser", ShiroDbRealm.getCurrentLoginName());
			int count = reDao.add(map);
			System.out.println("count" + count);
			return count > 0 ? 1 : 0;
		}
		// 是否是视频
		// CommonUtil.transCode(trans)
		Trans trans = new Trans();
		trans.setRes_path("/videos/2015/");
		trans.setTrans_path("/trans/2015/20150619/");
		trans.setRes_name("test.mp4");
		trans.setFile_id("");
		transFile.add(trans);
		new transThread(transFile);
		return 0;
	}

	/**
	 * 设置文件名
	 * 
	 * @param map
	 * @return
	 */
	public void setName(String parentid, FileBean fileBean) {
		Map<String, Object> check = new HashMap<String, Object>();
		check.put("name", fileBean.getName());
		check.put("parentid", parentid);
		long count = reDao.comCount(check);
		if (count == 0) {
			fileBean.setName(fileBean.getName());
		} else {
			/**
			 * 如果是文件
			 */
			if (fileBean.getName().lastIndexOf(".") != -1) {
				String prefix;
				String sufix;
				prefix = fileBean.getName().substring(0, fileBean.getName().lastIndexOf("."));
				sufix = fileBean.getName().substring(fileBean.getName().lastIndexOf("."));
				int index = 0;
				do {
					index = index + 1;
				} while (checkDocumentRepeat(check, parentid, fileBean, index, prefix, sufix) > 0);
				fileBean.setName(prefix + "(" + index + ")" + sufix);
			}
			/**
			 * 如果是文件夹
			 */
			else {
				int index = 0;
				do {
					index = index + 1;
				} while (checkRepeat(check, parentid, fileBean, index) > 0);

				fileBean.setName(fileBean.getName() + "(" + index + ")");
			}

		}
	}

	public long checkDocumentRepeat(Map<String, Object> check, String parentid, FileBean fileBean, int index, String prefix, String sufix) {
		check.put("name", prefix + "(" + index + ")" + sufix);
		check.put("parentid", parentid);
		long result = reDao.comCount(check);
		return result;
	}

	public long checkRepeat(Map<String, Object> check, String parentid, FileBean fileBean, int index) {
		check.put("name", fileBean.getName() + "(" + index + ")");
		check.put("parentid", parentid);
		long result = reDao.comCount(check);
		return result;
	}

	public int shareResource(Map<String, Object> map) {
		int num = 0;
		String flag = (String) map.get("flag");
		List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
		List<Map<String, Object>> resourceList = (List<Map<String, Object>>) map.get("resourceList");
		List<Map<String,Object>> resources = new ArrayList<Map<String,Object>>();
		for (Map<String, Object> map2 : resourceList) {
			map2.put("uuid", IdUtils.uuid2());
			resources.add(map2);
		}
		try {
			if (flag != null && flag.equals("0")) {// 机构
				for (Map<String, Object> dept : list) {
					List<Map<String, Object>> l = sysDao.findByDept(dept);
					if (l != null && l.size() != 0) {
						num += reDao.shareResource(resources, l, ShiroDbRealm.getCurrentLoginName());
					}
				}
			} else {
				num = reDao.shareResource(resources, list, ShiroDbRealm.getCurrentLoginName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return num > 0 ? 1 : 0;
	}

	public List<Tree> getTree() {
		return reDao.getTree();
	}

	public List<Tree> getTrees() {
		return reDao.getTrees();
	}

	public List<Tree> getVideoTrees() {
		return reDao.getVideoTrees();
	}

	public void downLoad(HttpServletResponse res, String path, String filename) {
		path = AppConstants.upload_PATH + path;
		BufferedInputStream br = null;
		OutputStream out = null;
		try {
			br = new BufferedInputStream(new FileInputStream(path));
			byte[] buf = new byte[1024];
			int len = 0;
			// 设置输出的格式
			res.reset();
			res.setContentType("application/octet-stream");
			res.addHeader("Content-Disposition", "attachment; filename=\"" + new String(filename.getBytes("UTF-8"), "UTF-8") + "\"");
			out = res.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询分享给我列表
	 * 
	 * @param bean
	 * @return
	 */
	public Map<String, Object> shareList(PageBean bean) {
		Map<String, Object> mapData = new HashMap<String, Object>();
		long total = reDao.countShareList(bean);
		List<Map<String, Object>> list = reDao.shareList(bean);
		Page<Map<String, Object>> page = new PageImpl<Map<String, Object>>(list, null, total);
		mapData.put("total", page.getTotalElements());
		mapData.put("data", page.getContent());
		return mapData;
	}

	/**
	 * 查询我的分享列表
	 * 
	 * @param bean
	 * @return
	 */
	public Map<String, Object> myShareList(PageBean bean) {
		Map<String, Object> mapData = new HashMap<String, Object>();
		long total = reDao.countMyShareList(bean);
		List<Map<String, Object>> list = reDao.myShareList(bean);
		Page<Map<String, Object>> page = new PageImpl<Map<String, Object>>(list, null, total);
		mapData.put("total", page.getTotalElements());
		mapData.put("data", page.getContent());
		return mapData;
	}

	/**
	 * 取消分享
	 * 
	 * @param shareListDel
	 * @return
	 */
	@Transactional(readOnly = false)
	public int cancelShare(List<String> shareListDel) {
		if (shareListDel != null && shareListDel.size() > 0) {
			return reDao.cancelShare(shareListDel);
		} else {
			return 0;
		}
	}

	/**
	 * 查询文件夹及文件夹下面的所有资源
	 * 
	 * @param resource
	 * @return
	 */
	/*
	 * @Transactional(readOnly = false) public com.zonekey.study.entity.Resource
	 * getResourcesByParentId(com.zonekey.study.entity.Resource resource) { if
	 * (resource != null) { if (Integer.valueOf(resource.getId()) != null &&
	 * !"".equals(Integer.valueOf(resource.getId()))) { return
	 * reDao.getResourcesByParentId(resource); } else { return null; } } return
	 * null; }
	 */
	/**
	 * 收藏分享（未使用）
	 * 
	 * @param resources
	 * @return
	 */
	@Transactional(readOnly = false)
	public int storeMyShare(List<ResourceView> resources) {
		int result = 0;
		if (resources != null && resources.size() > 0) {
			result = reDao.insertAll(resources);
		}
		return result;
	}

	/**
	 * 分享》取消收藏
	 * 
	 * @param storeListDel
	 * @return
	 */
	@Transactional(readOnly = false)
	public int unStoreMyShare(List<Map<String, Object>> storeList) {
		int result = 0;
		Map<String, Object> view;
		List<String> storeIdList = new ArrayList<String>();
		if (storeList != null && !"".equals(storeList)) {
			for (Map<String, Object> listDel : storeList) {
				view = new HashMap<String, Object>();
				view.put("id", listDel.get("shareid").toString());
				view.put("storeflag", "0");
				view.put("storeid", listDel.get("storeid").toString());
				reDao.updateShare(view);
				storeIdList.add(listDel.get("storeid").toString());
			}
			result = reDao.deleteAll(storeIdList);
		}
		return result;
	}

	/**
	 * 在线阅读
	 * 
	 * @param req
	 * @param path
	 * @return转换后的名字
	 */

	public String transSwf(HttpServletRequest req, String path) {
		String name = path.substring(path.lastIndexOf(File.separator) + 1, path.lastIndexOf("."));
		path = AppConstants.upload_PATH + path;
		File sourceFile = new File(path);
		File swfFile = new File(AppConstants.TRANSFER_PATH + name + ".swf");
		File pdfFile;
		if(swfFile.exists()){
			return swfFile.getName();
		}
        if(path.lastIndexOf(".pdf") != -1){
        	pdfFile = sourceFile;
        }else{
        	pdfFile = new File(AppConstants.TRANSFER_PATH + name + ".pdf");
        }
		// 转换成pdf文件
        System.out.println("--------------1");
		if (sourceFile.exists()) {
			if (!pdfFile.exists()) {
				OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
				try {
					System.out.println("--------------链接");
					connection.connect();
					System.out.println("--------------1pdf转码");
					DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
					System.out.println("--------------2pdf转码"+sourceFile);
					converter.convert(sourceFile, pdfFile);
					System.out.println("--------------3pdf转码");
					pdfFile.createNewFile();
					System.out.println("--------------4pdf转码");
					connection.disconnect();
					System.out.println("第二步：转换为PDF格式 路径" + pdfFile.getPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		 System.out.println("--------------1");
		// 目标文件
		Runtime r = Runtime.getRuntime();
		// 转换成swf文件
		//File swfFile = new File(AppConstants.TRANSFER_PATH + name + ".swf");
		if (!swfFile.exists()) {
			if (pdfFile.exists()) {
				try {
					Process p = r.exec("pdf2swf " + pdfFile.getPath() + " -o " + swfFile.getPath() + " -T 9");
					p.waitFor();
					swfFile.createNewFile();
					if (pdfFile.exists()) {
						pdfFile.delete();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println(path + "---------------------------------------------------------");
		System.out.println(swfFile.getPath());
		System.out.println(pdfFile.getPath() + "**********************************************************");
		return swfFile.getName();
	}

	/**
	 * 收藏资源 》》通用
	 * 
	 * @param storeList
	 * @return
	 */
	@Transactional(readOnly = false)
	public int storeResource(List<Map<String, Object>> storeList) {
		int count = 0;
		List<ResourceView> storeResources = new ArrayList<ResourceView>();

		String collect = "collect";
		List<Map<String, Object>> sources = codeService.getCode("sourse");
		for (Map<String, Object> map : sources) {
			if ("收藏".equals(map.get("name").toString())) {
				collect = map.get("value").toString();
			}
		}
		if (storeList != null && storeList.size() > 0) {
			Map<String, Object> view;
			for (Map<String, Object> storeResourceView : storeList) {
				if (storeResourceView.get("source") != null && !"".equals(storeResourceView.get("source").toString())) {
					/**************************** 处理重名 ****************************/
					FileBean fileBean = new FileBean();
					fileBean.setName(storeResourceView.get("name").toString());
					// 收藏的资源放在根文件夹下
					String parentid = "1";
					this.setName(parentid, fileBean);
					/**************************************************************/
					// 来自个人空间的资源
					if ("1".equals(storeResourceView.get("source").toString())) {
						String storeid = IdUtils.uuid2();
						ResourceView resource = reDao.findReById(storeResourceView.get("resourceid").toString());
						if(resource != null){
							resource.setParentid("1");
							resource.setName(fileBean.getName());
							resource.setResource_uuid(IdUtils.uuid2());
							resource.setSource(collect);
							resource.setStoreid(storeid);
							resource.setStoretype("1");
							resource.setAuthor(storeResourceView.get("author").toString());
							storeResources.add(resource);
							// 更新收藏表中的收藏标志
							view = new HashMap<String, Object>();
							view.put("id", storeResourceView.get("shareid").toString());
							view.put("storeflag", "1");
							view.put("storeid", storeid);
							reDao.updateShare(view);
						}else{
							System.out.println("不存在该资源");
						}
					}
					// 收藏录录播机资源
					else if ("2".equals(storeResourceView.get("source").toString())) {

					}
					// 收藏智慧教室资源
					else if ("3".equals(storeResourceView.get("source").toString())) {

					} else {
						System.out.println("unknow resource");
					}
				}
			}
			if(storeResources.size() > 0){
				count = reDao.insertAll(storeResources);
			}
		}
		return count;
	}

	/**
	 * 点击数加1
	 * 
	 * @param floderid
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateHitCount(String floderid) {
		int result = reDao.updateHitCount(floderid);
		return result;
	}

	/**
	 * 教研中心上传
	 * 
	 * @param req
	 * @param path
	 * @param parentid
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Integer> addTeachingResource(MultipartHttpServletRequest req, String path, String parentid) {
		if (parentid == null || "".equals(parentid)) {
			parentid = "1";
		}
		List<FileBean> list = CommonUtil.upload(path, req);
		List<FileBean> files = new ArrayList<FileBean>();
		if (list != null && list.size() > 0) {
			// Map<String, Object> map = new HashMap<String, Object>();
			List<Integer> ids = new ArrayList<Integer>();
			List<Integer> videoIds = new ArrayList<Integer>();
			List<Trans> transList = new ArrayList<Trans>();
			for (FileBean fileBean : list) {
				this.setName(parentid, fileBean);
				files.add(fileBean);
			}
			for (FileBean fileBean : files) {
				ResourceView view = new ResourceView();
				view.setFileurl(fileBean.getFileurl());
				view.setName(fileBean.getName());
				view.setNametype(fileBean.getNametype());
				view.setResource_uuid(fileBean.getResource_uuid());
				view.setSize(fileBean.getSize());
				view.setParentid(parentid);
				view.setIsfolder("1");
				view.setSource("upload");
				view.setCreateuser(ShiroDbRealm.getCurrentLoginName());
				reDao.addResourceReturnP(view);
				ids.add(Integer.valueOf(view.getId()));
				// 判断是否是视频文件
				if ("Videos".equals(fileBean.getNametype())) {
					videoIds.add(Integer.valueOf(view.getId()));
					// 转码
					Trans trans = new Trans();
					String name = view.getFileurl().substring(view.getFileurl().lastIndexOf(File.separator)+1);
					String tranpath = "trans" + File.separator + DateUtils.getFormat("yyyy") + File.separator + DateUtils.getFormat("yyyyMMdd") + File.separator;
					trans.setRes_path(AppConstants.upload_PATH+view.getFileurl().substring(0,view.getFileurl().lastIndexOf(File.separator)));
					trans.setTrans_path(AppConstants.upload_PATH + tranpath);
					trans.setRes_name(name);
					trans.setFile_id(view.getId());
					trans.setDur_time(300);
					trans.setFlag_hd(1);
					trans.setFlag_meg(1);
					trans.setGrade(1);
					transList.add(trans);
					System.out.println(trans.getTrans_path()+"-----------------------------------"+trans.getRes_path());
				}
			}
			// 转码
			new transThread(transList).start();
			return ids;
		}
		return null;
	}

	private class transThread extends Thread {
		private List<Trans> trans;

		public transThread(List<Trans> trans) {
			this.trans = trans;
		}

		@Override
		public void run() {
			for (Trans tr : trans) {
				String result = CommonUtil.transCode(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),tr);
				if (result != null && !"".equals(result)) {
					TransResult re = JsonUtil.jsonToObject(result, TransResult.class);
					if ("0".equals(re.getResponse_code())) {
						ResourceView view = new ResourceView();
						view.setId(tr.getFile_id());
						String tp = tr.getTrans_path();
						view.setTransPath(tp.substring(tp.indexOf("trans/",0),tp.length())+tr.getRes_name()+".mp4");
						String re1 = CommonUtil.tranResult(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),tr);
						if(re1 != null && !"".equals(re1)){
							TransResult re2 = JsonUtil.jsonToObject(re1, TransResult.class);
							view.setTransFlag(re2.getContent().get(0).get("status").toString());
						}else{
							view.setTransFlag("2");
						}
						int r = reDao.transUpdate(view);
						if (r == 0) {
							throw new TransactionException("transcode update fail");
						}
					}
				}
			}
		}
	}
	/**
	 * 视频预览转码
	 */
	@Transactional(readOnly = false)
    public JsonMsg previewTrans(ResourceView view){
    	JsonMsg msg = new JsonMsg();
    	msg.setOperation("视频预览转码");
    	if(view != null && view.getFileurl() != null && !"".equals(view.getFileurl())){
    		Trans trans = new Trans();
    		view.setFileurl(view.getFileurl().replace(ReadProperties.getIp(),"").replace("\\", File.separator));
			String name = view.getFileurl().substring(view.getFileurl().lastIndexOf(File.separator)+1);
			String tranpath = "trans" + File.separator + DateUtils.getFormat("yyyy") + File.separator + DateUtils.getFormat("yyyyMMdd") + File.separator;
			System.out.println(view.getFileurl());
			System.out.println(name);
			System.out.println(tranpath);
			//video/2015/20150717/aa9cf7ef-c35f-437d-a9fa-d1dfefeb9e9f.mp4
			//video/2015/20150717/aa9cf7ef-c35f-437d-a9fa-d1dfefeb9e9f.mp4
			//trans\2015\20150717\
			trans.setRes_path(AppConstants.upload_PATH+view.getFileurl().substring(0,view.getFileurl().lastIndexOf(File.separator)));
			trans.setTrans_path(AppConstants.upload_PATH + tranpath);
			trans.setRes_name(name);
			trans.setFile_id(view.getId());
			trans.setDur_time(300);
			trans.setFlag_hd(1);
			trans.setFlag_meg(1);
			trans.setGrade(1);
			System.out.println(trans.getTrans_path()+"-----------------------------------"+trans.getRes_path());
			//查询转码
    		String re1 = CommonUtil.tranResult(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),trans);
    		if(re1 != null && !"".equals(re1)){
    			TransResult re = JsonUtil.jsonToObject(re1, TransResult.class);
    			ResourceView viewc = reDao.findReByid(view.getId());
    			if("0".equals(re.getResponse_code())){
    				//0表示转码未完成，1表示转码完成 2表示转码失败 3 表示转码排队
        			if("0".equals(re.getContent().get(0).get("status").toString()) || "3".equals(re.getContent().get(0).get("status").toString())){
        				msg.setId("-2");
        				msg.setContent("正在转码，请稍后...");
        			}else if("2".equals(viewc.getTransFlag()) ||"2".equals(re.getContent().get(0).get("status").toString())){
        				msg.setId("-3");
        				msg.setContent("不好意思，转码失败了，请重试！");
        				//重新开启转码
        				this.transService(trans);
        			}else if("1".equals(re.getContent().get(0).get("status").toString())){
        				String tp = trans.getTrans_path();
        				tp = ReadProperties.getIp()+tp.substring(tp.indexOf("trans",0),tp.length())+trans.getRes_name()+".mp4";
        				ResourceView viewn = new ResourceView();
        				viewn.setId(view.getId());
        				//viewn.setTransPath(transPath);
        				viewn.setTransFlag("1");
        				reDao.transUpdate(viewn);
        				//返回转码后的路径
        				msg.setId("1");
        				msg.setContent(tp);
        			}else{
        				//默认未转码
        				this.transService(trans);
        				msg.setId("-4");
        				msg.setContent("已开启转码，请稍后...");
        			}
    			}else{
    				//未开启转码
    				this.transService(trans);
    				msg.setId("-4");
    				msg.setContent("已开启转码，请稍后...");
    			}
    		}
    	} else {
    		msg.setId("-1");
    		msg.setContent("对不起，文件不存在！");
    	}
    	return msg;
    }
    //转码服务
    public String transService(Trans tr){
    	String result = CommonUtil.transCode(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),tr);
		if (result != null && !"".equals(result)) {
			TransResult re = JsonUtil.jsonToObject(result, TransResult.class);
			if ("0".equals(re.getResponse_code())) {
				ResourceView view = new ResourceView();
				view.setId(tr.getFile_id());
				String tp = tr.getTrans_path();
				view.setTransPath(tp.substring(tp.indexOf("trans/",0),tp.length())+tr.getRes_name()+".mp4");
				String re1 = CommonUtil.tranResult(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),tr);
				if(re1 != null && !"".equals(re1)){
					TransResult re2 = JsonUtil.jsonToObject(re1, TransResult.class);
					view.setTransFlag(re2.getContent().get(0).get("status").toString());
				}else{
					view.setTransFlag("2");
				}
			    //view.setTransFlag("1");
				int r = reDao.transUpdate(view);
				if (r == 0) {
					throw new TransactionException("transcode update fail");
				}else{
					return ReadProperties.getIp()+view.getTransPath();
				}
			}else{
				return null;
			}
		}else{
			return null;
		}
    }
    /**
     * 合成视频失败是删除资源
     * @param resourceId
     * @return
     */
    public JsonMsg deleteById(String resourceId){
        JsonMsg msg = new JsonMsg();
        msg.setOperation("合成视频失败，删除资源");
    	if(resourceId != null && !"".equals(resourceId)){
    		int flag = reDao.deleteById(resourceId);
    		if(flag > 0){
    			msg.setId("1");
    			msg.setContent("已删除资源");
    		}else{
    			msg.setId("12");
    			msg.setContent("删除资源失败");
    		}
    	}else{
    		msg.setId("-1");
    	    msg.setContent("未接收到请求参数：资源id");
    	}
    	return msg;
    }
}
