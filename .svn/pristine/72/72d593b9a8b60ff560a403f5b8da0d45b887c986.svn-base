package com.zonekey.study.common;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class UploadImg {
	/**
	 * 
	 * @param resourcePath
	 *            项目下相对路径 如:upload/images/
	 * @param req
	 * @param delName
	 *            需要删除的图片
	 * @return
	 */
	public static String uploadImage(String resourcePath, MultipartHttpServletRequest req, String delName) {
		/*
		 * //绝对路径 String absPath =
		 * req.getSession().getServletContext().getRealPath(""); //项目名 String
		 * projectName =
		 * absPath.substring(absPath.lastIndexOf(File.separatorChar));
		 */
		// 图片存储的绝对路径
		String folder = resourcePath;
		if (StringUtils.isEmpty(resourcePath)) {
			resourcePath = AppConstants.upload_PATH;
			folder = "";
		} else {
			folder = resourcePath;
			resourcePath = AppConstants.upload_PATH + resourcePath;
		}
		// 获得第1张图片（根据前台的name名称得到上传的文件）
		MultipartFile imgFile = req.getFile("file");
		// 保存第一张图片
		if (!StringUtils.isEmpty(imgFile.getOriginalFilename())) {
			// 返回文件名带/images路径
			String filName = getFile(imgFile, null, AppConstants.IMG_EXT_LIST);
			// 删除已存在图片,用新图片代替
			if (StringUtils.isNotEmpty(filName) && StringUtils.isNotBlank(filName) && delName != null && !"".equals(delName)) {
				delName = resourcePath + delName;
				System.out.println("delname:" + delName);
				File file = new File(delName);
				if (file.exists()) {
					file.delete();
				}
			}
			// 返回
			return folder + filName;
			//return File.separator + folder + filName;
		}
		return null;
	}

	private static String getFile(MultipartFile imgFile, String path, List<String> fileTypes) {
		// 图片名
		String filename = imgFile.getOriginalFilename(); // user.png
		// 待保存的图片名称
		String fileName = IdUtils.uuid();
		// 扩展名
		// String ext = filename.substring(filename.lastIndexOf("."));
		// 扩展名
		String ext = filename.substring(filename.indexOf("."));
		fileName += ext;
		// 校验文件类型并进行分类存储
		String rePath = "";
		if (path == null) {
			path = AppConstants.upload_PATH;
			rePath = getTypePath(ext);
			path += rePath;
		}
		// File file = creatFolder(path, fileName);
		// 为新生成图片命名
		// String fileName = DateUtils.getFormat("yyyyMMddHHmmss") + ext;

		ext = ext.toLowerCase();
		if (fileTypes.contains(ext)) { // 如果扩展名属于允许上传的类型，则创建文件
			try {
				File file = creatFolder(path, fileName);
				imgFile.transferTo(file); // 保存上传的文件
				// 返回/images/......
				return getTypePath(ext) + file.getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 分类存储路径
	 */
	private static String getTypePath(String ext) {
		String path = DateUtils.getFormat("yyyy") + File.separator + DateUtils.getFormat("yyyyMMdd") + File.separator;
		if (AppConstants.IMG_EXT_LIST.contains(ext)) {
			return AppConstants.TYPE_PATH_LIST.get(0) + path;
		} else if (AppConstants.VIDEO_EXT_LIST.contains(ext)) {
			return AppConstants.TYPE_PATH_LIST.get(1) + path;
		} else if (AppConstants.DOC_EXT_LIST.contains(ext)) {
			return AppConstants.TYPE_PATH_LIST.get(2) + path;
		} else {
			return AppConstants.TYPE_PATH_LIST.get(3) + path;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            绝对路径
	 * @param fileName
	 *            文件名
	 * @return
	 */
	private static File creatFolder(String path, String fileName) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(file, fileName);
		return file;
	}
}
