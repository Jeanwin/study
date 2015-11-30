package com.zonekey.study.common;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zonekey.study.vo.FileBean;


public class WisclassUpload {
	/******************************************************************上传start*********************************/
	/**
	 * 上传
	 * @param resourcePath 上传相对路径
	 * @param req
	 * @return返回结果为以逻辑名为key,真实文件名为value的map的集合
	 */
	 public static List<FileBean> upload(String resourcePath,MultipartHttpServletRequest req){
		/* if(StringUtils.isEmpty(resourcePath)){
			 resourcePath = AppConstants.upload_PATH;
		 }*/
		 
		 List<FileBean> list = new ArrayList<FileBean>();
		 MultiValueMap<String,MultipartFile> mfiles =  req.getMultiFileMap();
        for (List<MultipartFile> f : mfiles.values()) {
        	FileBean bean = getFile(f.get(0),resourcePath);
        	if(bean!=null){
        		list.add(bean);
        	}
		}
         return list;
   }
   
   private static FileBean getFile(MultipartFile imgFile,String path) {
	   FileBean bean = new FileBean();
	   String filename=imgFile.getOriginalFilename();
	   String fileName=DateUtils.getFormat("yyyyMMddHHmmss");
       try {
    	   //扩展名
    	   String ext = filename.substring(filename.indexOf("."));
    	   fileName +=ext;
    	   //校验文件类型并进行分类存储
    	   String rePath = "";
    	   if(path == null){
    		   path = AppConstants.upload_PATH;
    		   rePath = getTypePath(ext);
    		   path +=rePath;
    	   }
    	   File file = creatFolder(path,fileName);  
    	   imgFile.transferTo(file);
    	   //filename为逻辑名称,file.getName为物理名
    	    bean.setName(filename);
    	    
    	    bean.setFileurl(rePath+file.getName());
    	    bean.setSize(file.length());
    	    bean.setResource_uuid(IdUtils.uuid2());
    	    return bean;
		} catch (Exception e) {
			e.printStackTrace();
		}                
       return null;
   }
   /**
    * 创建文件
    * @param path 绝对路径
    * @param fileName 文件名
    * @return
    */
   private static File creatFolder(String path,String fileName) {  
       File file = new File(path);  
       if(!file.exists()){
       	file.mkdirs();
       }
       file = new File(file,fileName);
       return file;  
  }
   
   /******************************************************************上传end*********************************/
   /**
    * 创建路径
    * @param path 绝对路径
    * @param fileName 文件名
    * @return
    */
   public static void  creatFolder(String path) {  
       File file = new File(path);  
       if(!file.exists()){
       	file.mkdirs();
       }
  }
  public static boolean delete(String path){
	   return FileUtils.deleteQuietly(new File(path));
   }
   /**
    * 分类存储路径
    */
   private static String getTypePath(String ext){
	   String path = DateUtils.getFormat("yyyy")+File.separator+DateUtils.getFormat("yyyyMMdd")+File.separator;
	   if(AppConstants.IMG_EXT_LIST.contains(ext)){
		   return AppConstants.TYPE_PATH_LIST.get(0)+path;
	   }else if(AppConstants.VIDEO_EXT_LIST.contains(ext)){
		   return AppConstants.TYPE_PATH_LIST.get(1)+path;
	   }else if(AppConstants.DOC_EXT_LIST.contains(ext)){
		   return AppConstants.TYPE_PATH_LIST.get(2)+path;
	   }else{
		   return AppConstants.TYPE_PATH_LIST.get(3)+path;
	   }
   }
   public static String getFormatSize(double size) {  
       double kiloByte = size/1024;  
       if(kiloByte < 1) {  
           return size + "B";  
       }  
         
       double megaByte = kiloByte/1024;  
       if(megaByte < 1) {  
           BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
           return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
       }  
         
       double gigaByte = megaByte/1024;  
       if(gigaByte < 1) {  
           BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
           return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
       }  
         
       double teraBytes = gigaByte/1024;  
       if(teraBytes < 1) {  
           BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
           return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
       }  
       BigDecimal result4 = new BigDecimal(teraBytes);  
       return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
   } 
}
