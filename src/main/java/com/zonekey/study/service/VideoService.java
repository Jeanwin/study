package com.zonekey.study.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zonekey.study.common.AppConstants;
import com.zonekey.study.common.CommonUtil;
import com.zonekey.study.common.DateUtils;
import com.zonekey.study.common.HttpSend;
import com.zonekey.study.common.IdUtils;
import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.ReadProperties;
import com.zonekey.study.dao.ResourceMapper;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.vo.CutImgReciever;
import com.zonekey.study.vo.CutImgResult;
import com.zonekey.study.vo.MergeResult;
import com.zonekey.study.vo.MergeVideoReciever;
import com.zonekey.study.vo.ResourceView;
import com.zonekey.study.vo.ViewPoint;

@Service
public class VideoService {
	private static final Logger LOG = LoggerFactory.getLogger(VideoService.class);
    @Resource
    private ResourceMapper rMapper;
    @Resource
    private ServerService serverService;
	/**
     * 截图服务
     * @param img
     * @return
     */
	public JsonMsg cutImg(CutImgReciever img){
		long startTime = System.currentTimeMillis();
		JsonMsg msg = new JsonMsg();
		msg.setOperation("视频截图");
		if(img != null){
			String file_path_all = AppConstants.upload_PATH + img.getFile_path().replace(ReadProperties.getIp(), "");
	    	String file_path = file_path_all.substring(0, file_path_all.lastIndexOf("/"));
	    	String filename = file_path_all.substring(file_path_all.lastIndexOf("/")+1,file_path_all.length());
	    	img.setFile_path(file_path);
	    	img.setFilename(filename);
			String result = HttpSend.post(serverService.getServer(AppConstants.TYPE_SERVER_VIDEO_PROCESS,AppConstants.CUT_IMG_URL),img);
			if(result != null && !"".equals(result)){
				CutImgResult r = JsonUtil.jsonToObject(result, CutImgResult.class);
				if("0".equals(r.getResponse_code())){
					msg.setId("1");
					msg.setContent(file_path.replace(AppConstants.upload_PATH, ReadProperties.getIp())+ File.separator + "temp" + File.separator + filename + "_" + img.getCut_time() + ".jpg");
				    //data/trans/2015/20150706/temp/5fe7cf66-0c35-40b8-9313-fcb7ebd7b155_60.jpg 
				} else {
					msg.setId("-2");
					msg.setContent("截图失败");
				}
			}else{
				msg.setId("-3");
				msg.setContent("截图请求未返回数据");
			}
		} else {
			msg.setId("-1");
			msg.setContent("未获取到请求参数");
		}
		long endTime = System.currentTimeMillis();
		LOG.info("截图时间："+(endTime - startTime) + "ms");
        return msg;
	}
	/**
	 * 合成视频
	 * @return
	 */
	public JsonMsg mergeVideo(MergeVideoReciever video){
		long startTime = System.currentTimeMillis();
		JsonMsg msg = new JsonMsg();
		msg.setOperation("合成视频");
	    if(video != null){
	    	String uuid = IdUtils.uuid2();
	    	Integer video_id = rMapper.getPrimaryKey();
	    	String video_path = AppConstants.upload_PATH + "video" + File.separator + DateUtils.getFormat("yyyy") + File.separator + DateUtils.getFormat("yyyyMMdd");
	    	String name = video.getVideoname()+".mp4";
	    	String videoname = uuid+".mp4";
	    	//指定file_path
	    	//String file_path_all = AppConstants.upload_PATH + video.getFile_path().replace(ReadProperties.getIp(), "");
	    	//String file_path = file_path_all.substring(0, file_path_all.lastIndexOf("/")+1);
	    	//String filename = file_path_all.substring(file_path_all.lastIndexOf("/")+1,file_path_all.length());
	    	video.setVideo_id(video_id);
	    	video.setVideo_path(video_path);
	    	video.setVideoname(videoname);
	    	List<ViewPoint> n = new ArrayList<ViewPoint>();
	    	for (ViewPoint v : video.getViewpoint()) {
	    		String file_path_all = AppConstants.upload_PATH + v.getFile_path().replace(ReadProperties.getIp(), "");
		    	String file_path = file_path_all.substring(0, file_path_all.lastIndexOf("/")+1);
		    	String filename = file_path_all.substring(file_path_all.lastIndexOf("/")+1,file_path_all.length());
				v.setFile_path(file_path);
				v.setFilename(filename);
				n.add(v);
			}
	    	video.setViewpoint(n);
	    	//"file_path":"/home/mfsdate/trans/2015/20150706/",
	    	//前台指定filename
	    	//指定合成路径及文件名
	    	//String video_path = AppConstants.upload_PATH + "video" + File.separator + DateUtils.getFormat("yyyy") + File.separator + DateUtils.getFormat("yyyyMMdd") 
	    	//		+ File.separator + this.uniqueName("1",video.getVideoname() + ".mp4");
	    	//video.setFile_path(file_path);
	    	//video.setFilename(filename);
	    	//“video_path”:”/home/mfsdate/video/2015/20150710/ae565485.mp4”		
	    	String result = HttpSend.post(serverService.getServer(AppConstants.TYPE_SERVER_VIDEO_PROCESS,AppConstants.MERGE_VIDEO_URL), video);
	    	if(result != null && !"".equals(result)){
	    		//结果一样
	    		CutImgResult r = JsonUtil.jsonToObject(result, CutImgResult.class);
	    		if("0".equals(r.getResponse_code())){
					msg.setId(video_id.toString());
				    //data/trans/2015/20150706/temp/5fe7cf66-0c35-40b8-9313-fcb7ebd7b155_60.jpg 
					ResourceView view = new ResourceView();
					view.setId(video_id.toString());
					view.setName(this.uniqueName("1",name));
					view.setNametype("Videos");
					view.setFileurl(video_path.replace(AppConstants.upload_PATH,"")+File.separator+videoname);
					view.setIsfolder("1");
					view.setResource_uuid(uuid);
					view.setParentid("1");
					view.setSource("video_process");
					view.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					view.setTransPath(video_path.replace(AppConstants.upload_PATH,"")+File.separator+videoname);
					view.setTransFlag("1");
					//System.out.println("view:"+view);
					rMapper.addByPrimary(view);
					msg.setContent(ReadProperties.getIp()+view.getTransPath());
				} else {
					msg.setId("-2");
					msg.setContent("视频合成失败");
				}
	    	}else{
	    		msg.setId("-3");
				msg.setContent("合成视频请求未返回数据");
	    	}
	    }else{
	    	msg.setId("-1");
	    	msg.setContent("未获取到请求参数");
	    }
	    long endTime = System.currentTimeMillis();
		LOG.info("合成视频时间："+(endTime - startTime) + "ms");
		return msg;
	}
	/**
	 * 
	 * @param fileid
	 * @return
	 */
	public MergeResult getMergeStatus(String fileid){
		return CommonUtil.mergeResult(serverService.getServer(AppConstants.TYPE_SERVER_VIDEO_PROCESS, AppConstants.MERGE_VIDEO_URL),fileid);
	}
	
	/*************************************************************************************************************/
	/**
	 * 设置文件名
	 * 
	 * @param map
	 * @return
	 */
	public String uniqueName(String parentid, String filename) {
		Map<String, Object> check = new HashMap<String, Object>();
		check.put("name", filename);
		check.put("parentid", parentid);
		long count = rMapper.comCount(check);
		if (count == 0) {
			return filename;
		} else {
			/**
			 * 如果是文件
			 */
			if (filename.lastIndexOf(".") != -1) {
				String prefix;
				String sufix;
				prefix = filename.substring(0, filename.lastIndexOf("."));
				sufix = filename.substring(filename.lastIndexOf("."));
				int index = 0;
				do {
					index = index + 1;
				} while (checkDocumentRepeat(check, parentid, index, prefix, sufix) > 0);
				return prefix + "(" + index + ")" + sufix;
			}
			/**
			 * 如果是文件夹
			 */
			else {
				int index = 0;
				do {
					index = index + 1;
				} while (checkRepeat(check, parentid,filename,index) > 0);

				return filename + "(" + index + ")";
			}

		}
	}
	public long checkDocumentRepeat(Map<String, Object> check, String parentid,int index, String prefix, String sufix) {
		check.put("name", prefix + "(" + index + ")" + sufix);
		check.put("parentid", parentid);
		long result = rMapper.comCount(check);
		return result;
	}
	public long checkRepeat(Map<String, Object> check, String parentid,String filename,int index) {
		check.put("name", filename + "(" + index + ")");
		check.put("parentid", parentid);
		long result = rMapper.comCount(check);
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(ReadProperties.getIp());
		System.out.println("http://study.zonekey.com/data/video/2015/34354564.mp4".replace(ReadProperties.getIp(),""));
		String url = "http://study.zonekey.com/data/video/2015/34354564.mp4".replace(ReadProperties.getIp(),"");
		System.out.println(url.substring(0, url.lastIndexOf("/")));
		System.out.println(url.substring(url.lastIndexOf("/")+1,url.length()));
		System.out.println(File.separator);
	}
}
