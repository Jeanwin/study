package com.zonekey.study.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.ibatis.transaction.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import sun.util.logging.resources.logging;

import com.zonekey.study.common.AppConstants;
import com.zonekey.study.common.BaseRedis;
import com.zonekey.study.common.CommonUtil;
import com.zonekey.study.common.ContinueFTP;
import com.zonekey.study.common.DateTermUtil;
import com.zonekey.study.common.DateUtils;
import com.zonekey.study.common.HttpSend;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.ReadProperties;
import com.zonekey.study.common.TransResult;
import com.zonekey.study.dao.CommentMapper;
import com.zonekey.study.dao.CurriculumIntroduceMapper;
import com.zonekey.study.dao.MaterialMapper;
import com.zonekey.study.dao.NoteMapper;
import com.zonekey.study.dao.ResourceMapper;
import com.zonekey.study.dao.WisclassroomMapper;
import com.zonekey.study.entity.Comment;
import com.zonekey.study.entity.Curriculum;
import com.zonekey.study.entity.CurriculumDetails;
import com.zonekey.study.entity.Material;
import com.zonekey.study.entity.Note;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.ParentReviewTemplate;
import com.zonekey.study.entity.RecordTemplate;
import com.zonekey.study.entity.ReviewTemplate;
import com.zonekey.study.entity.Wisclassroom;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.vo.FileBean;
import com.zonekey.study.vo.ResourceView;
import com.zonekey.study.vo.Trans;
import com.zonekey.study.vo.Tree;

@Service
@Transactional(readOnly = true)
public class CurriculumDetailsService extends BaseRedis{
	@Resource
	private NoteMapper noteMapper;
	@Resource
	private CurriculumIntroduceMapper curriculumIntroduceMapper;
	@Resource
	private MaterialMapper materialMapper;
	@Resource
	private ResourceMapper reDao;
	@Resource
	private CommentMapper commentMapper;
	@Autowired
	private MyClassRoomService myClassRoomService;
	@Resource
	private ServerService serverService;
	@Resource
	private ResourceService reService;
	@Resource
	private WisclassroomMapper wisclassroomMapper;
	@Autowired
	private SysCodeService syscodeService;
	
	
	
	public Map<String,Object> selectNoteByWhere(Note note) {
		Map<String,Object> map =new HashMap<String, Object>();
		String resourceid=(note.getCurriculumid()==null || note.getCurriculumid()=="")?note.getFloder():note.getCurriculumid();
		note.setResourceid(resourceid);
		note.setUserid(ShiroDbRealm.getCurrentLoginName());
		List<Note> clist= noteMapper.selectNoteByWhere(note);
		 map.put("data", clist);
		 return map;
	}
	
	public int updateNote(Note note) {
		//note.setUserid(ShiroDbRealm.getCurrentLoginName());
		note.setUserid(ShiroDbRealm.getCurrentLoginName());
		return noteMapper.updateNote(note);
	}
	
	public int addNote(Note note) {
		note.setUserid(ShiroDbRealm.getCurrentLoginName());
		String resourceid=(note.getCurriculumid()==null || note.getCurriculumid()=="")?note.getFloder():note.getCurriculumid();
		note.setResourceid(resourceid);
		return noteMapper.addNote(note);
	}
	
	public int deleteNote(Note note) {
		return noteMapper.deleteNote(note.getId()+"");
	}
	/**
	 * 查看作品明细
	 * @param curriculumDetails
	 * @return
	 */
	public Map<String,Object> selectCurriculumDetails(Map<String,Object> map){
		map.put("loginname", ShiroDbRealm.getCurrentLoginName());
		Map<String,Object> retmap=new HashMap<String,Object>();
		
		    //如果是直播
		if(map.get("source").equals("1")){
			if(map.get("workid") != "" && map.get("workid") != null){
				//教研跳到详情页
				CurriculumDetails teachervo=curriculumIntroduceMapper.selectTeacherByClass(map);
				CurriculumDetails subcoursevo=curriculumIntroduceMapper.selectSubjectCourseByStudy(map);
				subcoursevo.setTeacherIntroduce((teachervo== null)?"":teachervo.getTeacherIntroduce());
				subcoursevo.setTeachername((teachervo== null)?"":teachervo.getTeachername());
				subcoursevo.setLoginname((teachervo== null)?"":teachervo.getLoginname());
				subcoursevo.setPictureURL("/data/"+teachervo.getPictureURL());
				retmap.put("data", subcoursevo);
				return retmap;
			}
			//直播列表到详情页
			CurriculumDetails teachervo=curriculumIntroduceMapper.selectTeacherByClass(map);
			CurriculumDetails subjectvo=curriculumIntroduceMapper.selectSubjectByLive(map);
			CurriculumDetails coursevo=curriculumIntroduceMapper.selectCourseByLive(map);
			subjectvo.setCourseIntroduce((coursevo == null) ? "" : coursevo.getCourseIntroduce());
			subjectvo.setTeacherIntroduce((teachervo== null)?"":teachervo.getTeacherIntroduce());
			subjectvo.setTeachername((teachervo== null)?"":teachervo.getTeachername());
			subjectvo.setLoginname((teachervo== null)?"":teachervo.getLoginname());
			subjectvo.setPictureURL("/data/"+teachervo.getPictureURL());
			retmap.put("data", subjectvo);
			return retmap;
			//精品课程
		}else if(map.get("source").equals("2")){
			CurriculumDetails teachervo=curriculumIntroduceMapper.selectTeacherByClass(map);
			CurriculumDetails subcoursevo=curriculumIntroduceMapper.selectSubjectCourseByGood(map);
			subcoursevo.setTeacherIntroduce((teachervo== null)?"":teachervo.getTeacherIntroduce());
			subcoursevo.setTeachername((teachervo== null)?"":teachervo.getTeachername());
			subcoursevo.setLoginname((teachervo== null)?"":teachervo.getLoginname());
			subcoursevo.setPictureURL("/data/"+teachervo.getPictureURL());
			retmap.put("data", subcoursevo);
			return retmap;
			//教研中心
		}else if(map.get("source").equals("3")){
			CurriculumDetails teachervo=curriculumIntroduceMapper.selectTeacherByLoginname(map);
			CurriculumDetails subcoursevo=curriculumIntroduceMapper.selectSubjectCourseByStudy(map);
			subcoursevo.setTeacherIntroduce((teachervo== null)?"":teachervo.getTeacherIntroduce());
			subcoursevo.setTeachername((teachervo== null)?"":teachervo.getTeachername());
			subcoursevo.setLoginname((teachervo== null)?"":teachervo.getLoginname());
			subcoursevo.setPictureURL("/data/"+teachervo.getPictureURL());
			retmap.put("data", subcoursevo);
			return retmap;
		}
		return null;
	}
	/**
	 * 查看评审明细
	 * @param curriculumDetails
	 * @return
	 */
	public Map<String,Object> selectReviewDetails_bak(Map<String,Object> map){
		 Map<String,Object>  returnmap=new HashMap<String,Object>();
		String templateid="";
		String content="";
		String remark="";
		String reviewdate="";
	    //如果是直播
			if(map.get("source").equals("1")){
				//根据 resourceid 、userid查看是否存在模板、评价内容
				Map<String,Object> goodmap=curriculumIntroduceMapper.selectReviewDetailByLive(map);
				//如果存在，返回 模板、评价内容
				if(goodmap != null){
					templateid=goodmap.get("templateid").toString();//模板id
					//TODO
					//通过模板id查到一个模板对象 ReviewTemplate
					reviewdate=goodmap.get("reviewdate").toString();//评审时间
					remark=(goodmap.get("remark") == null)?"":goodmap.get("remark").toString();
					content=(goodmap.get("content") == null)?"":goodmap.get("content").toString();
					returnmap.put("ReviewTemplate", "??????");
					returnmap.put("reviewdate", "reviewdate");
					returnmap.put("remark", "remark");
					returnmap.put("content", "content");
					return returnmap;
				}else{
					//TODO
					//如果不存在，返回所有模板  ReviewTemplateList???
					returnmap.put("ReviewTemplateList", "??????");
					return returnmap;
				}
				
				//精品课程
			}else if(map.get("source").equals("2")){
				//根据 resourceid 、userid查看是否存在模板、评价内容
				Map<String,Object> goodmap=curriculumIntroduceMapper.selectReviewDetailByLive(map);
				//如果存在，返回 模板、评价内容
				if(goodmap != null){
					templateid=goodmap.get("templateid").toString();//模板id
					//TODO
					//通过模板id查到一个模板对象 ReviewTemplate
					reviewdate=goodmap.get("reviewdate").toString();//评审时间
					remark=(goodmap.get("remark") == null)?"":goodmap.get("remark").toString();
					content=(goodmap.get("content") == null)?"":goodmap.get("content").toString();
					returnmap.put("ReviewTemplate", "??????");
					returnmap.put("reviewdate", "reviewdate");
					returnmap.put("remark", "remark");
					returnmap.put("content", "content");
					return returnmap;
				}else{
					//TODO
					//如果不存在，返回所有模板  ReviewTemplateList???
					returnmap.put("ReviewTemplateList", "??????");
					return returnmap;
				}
				
				
				//教研中心
			}else if(map.get("source").equals("3")){
				//根据worksid、userid 查一下 模板和评价内容、活动时间
				Map<String,Object> studymap=curriculumIntroduceMapper.selectReviewDetailByStudy(map);
				templateid=studymap.get("templateid").toString();//模板id
				reviewdate=studymap.get("reviewdate").toString();//评审时间
				if(studymap.get("content") != null){
					//该用户之前评论过，返回对应的模板 和评论内容
					content=studymap.get("content").toString();//评分
					remark=studymap.get("remark").toString();//主观评价
				}else{
					remark=(studymap.get("remark") == null)?"":studymap.get("remark").toString();
				}
			}
		return null;
	}
	/**
	 * 查看评审明细
	 * @param curriculumDetails
	 * @return
	 */
	public Map<String,Object> selectReviewDetails(Map<String,Object> map){
		Map<String,Object>  reviewDetailmap=new HashMap<String, Object>();
		//???
		map.put("userid", ShiroDbRealm.getCurrentLoginName());
//		map.put("userid", "admin");
		 Map<String,Object>  returnmap=new HashMap<String,Object>();
		 List<ReviewTemplate> endReviewTemplateList=new ArrayList<ReviewTemplate>();
		String templateid="";
		String content="";
		String remark="";
		String reviewdate="";
		String assess="";
	   if(map.get("worksid") != "" && map.get("worksid") != null){
				int sumScore=0;
				Map<String,Object> studymap=new HashMap<String,Object>();
				//根据worksid、userid 查一下 模板和评价内容、活动时间(如果不是指定专家，查到是空)
				 studymap=curriculumIntroduceMapper.selectReviewDetailByStudy(map);
				if(studymap == null){
					studymap=curriculumIntroduceMapper.selectReviewDetailFromStudy(map);
				}
				//same begin
				templateid=studymap.get("templateid").toString();//模板id
				//根据模板的id，查模板的名字，下拉框里显示
				List<Map<String,Object>> templatename=new ArrayList<Map<String,Object>>();
				Map<String,Object> templatenamemap=new HashMap<String,Object>();
				templatenamemap.put("id", templateid);
				templatenamemap.put("name", curriculumIntroduceMapper.selectOneReview(templateid).getName());
				 templatename.add(templatenamemap);
				reviewdate=(studymap.get("reviewdate") == null)?"":studymap.get("reviewdate").toString();//评审时间
				assess=curriculumIntroduceMapper.selectOneReview(templateid).getAssess();
				//TODO
				//通过模板id查到子模板中一级模板对象 ReviewTemplate
				List<ReviewTemplate> reviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,"0");
				//通过模板di，一级模板id，查到一个对象
				for(ReviewTemplate reviewTemplate:reviewList){
					List<ReviewTemplate> childreviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,reviewTemplate.getId());
					endReviewTemplateList.addAll(childreviewList);
					reviewDetailmap.put(reviewTemplate.getChildKey(), childreviewList);
					sumScore+=Integer.parseInt(reviewTemplate.getChildValue());
				}
				returnmap.put("ReviewTemplate", reviewDetailmap);
				returnmap.put("reviewdate", reviewdate);
				returnmap.put("templateid", templateid);
				returnmap.put("remark", studymap.get("remark"));
				returnmap.put("assess", assess);//主观评价 1需要 0 不需要
				returnmap.put("score", (studymap.get("score")== null)?"0":studymap.get("score"));//现在评的分
				returnmap.put("sumScore", sumScore);//总共的分数
				
				/*if(studymap.get("content") != null && studymap.get("content").toString().length() >1){
					content=studymap.get("content").toString();
					content = content.substring(1, content.length() - 1);
					String[] strarry = content.split(",");
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					for (int i = 0; i < strarry.length; i++) {
						Map<String, Object> maparray = new HashMap<String, Object>();
						String id = strarry[i].trim()
								.substring(7, strarry[i].trim().lastIndexOf("|") - 1);
						String score = strarry[i].trim().substring(
								strarry[i].trim().lastIndexOf(":") + 2, strarry[i].trim().length() - 2);
						maparray.put("id", id);
						maparray.put("score", score);
						list.add(maparray);
					}
					returnmap.put("content", list);
				}else{
					returnmap.put("content", "");
				}*/
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				if(studymap.get("content") != null ){
					content=studymap.get("content").toString();
					List<Map<String,Object>> listmap = JsonUtil.jsonToObject(content, List.class);
					for(Map<String,Object> contentmap:listmap){
						Map<String, Object> maparray = new HashMap<String, Object>();
						maparray.put("id", contentmap.get("id"));
						maparray.put("score", contentmap.get("score"));
						list.add(maparray);
					}
					returnmap.put("content", list);
				}else{
					returnmap.put("content", "");
				}
				returnmap.put("templatename", templatename);
				
				return returnmap;
				//same end
			}else{
				//如果是直播
					int sumScore=0;
					//根据 resourceid 、userid查看是否存在模板、评价内容
					Map<String,Object> goodmap=curriculumIntroduceMapper.selectReviewDetail(map);
					//如果存在，返回 模板、评价内容
					if(goodmap != null){
						/*templateid=goodmap.get("templateid").toString();//模板id
						remark=(goodmap.get("remark") == null)?"":goodmap.get("remark").toString();
						assess=curriculumIntroduceMapper.selectOneReview(templateid).getAssess();*/
						//copy
						//same begin
						templateid=goodmap.get("templateid").toString();//模板id
						//根据模板的id，查模板的名字，下拉框里显示
						List<Map<String,Object>> templatename=new ArrayList<Map<String,Object>>();
						Map<String,Object> templatenamemap=new HashMap<String,Object>();
						templatenamemap.put("id", templateid);
						templatenamemap.put("name", curriculumIntroduceMapper.selectOneReview(templateid).getName());
						 templatename.add(templatenamemap);
						reviewdate=(goodmap.get("reviewdate") == null)?"":goodmap.get("reviewdate").toString();//评审时间
						assess=curriculumIntroduceMapper.selectOneReview(templateid).getAssess();
						//TODO
						//通过模板id查到子模板中一级模板对象 ReviewTemplate
						List<ReviewTemplate> reviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,"0");
						//通过模板di，一级模板id，查到一个对象
						for(ReviewTemplate reviewTemplate:reviewList){
							List<ReviewTemplate> childreviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,reviewTemplate.getId());
							endReviewTemplateList.addAll(childreviewList);
							reviewDetailmap.put(reviewTemplate.getChildKey(), childreviewList);
							sumScore+=Integer.parseInt(reviewTemplate.getChildValue());
						}
						returnmap.put("ReviewTemplate", reviewDetailmap);
						returnmap.put("reviewdate", reviewdate);
						returnmap.put("templateid", templateid);
						returnmap.put("remark", goodmap.get("remark"));
						returnmap.put("assess", assess);//主观评价 1需要 0 不需要
						returnmap.put("score", goodmap.get("score"));//现在评的分
						returnmap.put("sumScore", sumScore);//总共的分数
						content=goodmap.get("content").toString();
						/*content = content.substring(1, content.length() - 1);
						String[] strarry = content.split(",");
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < strarry.length; i++) {
							Map<String, Object> maparray = new HashMap<String, Object>();
							String id = strarry[i].trim()
									.substring(7, strarry[i].trim().lastIndexOf("|") - 1);
							String score = strarry[i].trim().substring(
									strarry[i].trim().lastIndexOf(":") + 2, strarry[i].trim().length() - 2);
							maparray.put("id", id);
							maparray.put("score", score);
							list.add(maparray);
						}*/
						List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
						if(content != null && content.length()>1){
							List<Map<String,Object>> listmap = JsonUtil.jsonToObject(content, List.class);
							for(Map<String,Object> contentmap:listmap){
								Map<String, Object> maparray = new HashMap<String, Object>();
								maparray.put("id", contentmap.get("id"));
								maparray.put("score", contentmap.get("score"));
								list.add(maparray);
							}
							returnmap.put("content", list);
						}else{
							returnmap.put("content", "");
						}
						returnmap.put("templatename", templatename);
						
						return returnmap;
						//same end
					}else{
						//TODO
						//如果不存在，返回任一模板  
						/*ParentReviewTemplate parentReviewTemplate=curriculumIntroduceMapper.selectOneReview("");
						templateid=parentReviewTemplate.getId();//模板id
						assess=parentReviewTemplate.getAssess();*/
						
						//查到所有的评审模板-下拉框
						returnmap.put("ReviewTemplate", selectReviewOption(null));
						return returnmap;
					}
					/*//TODO
					//通过模板id查到子模板中一级模板对象 ReviewTemplate
					List<ReviewTemplate> reviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,"0");
					//通过模板di，一级模板id，查到一个对象
					for(ReviewTemplate reviewTemplate:reviewList){
						List<ReviewTemplate> childreviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,reviewTemplate.getId());
						endReviewTemplateList.addAll(childreviewList);
					}
					
					reviewdate=goodmap.get("reviewdate").toString();//评审时间
					
					content=(goodmap.get("content") == null)?"":goodmap.get("content").toString();
					returnmap.put("ReviewTemplate", endReviewTemplateList);
					returnmap.put("reviewdate", reviewdate);
					returnmap.put("templateid", templateid);
					returnmap.put("remark", remark);
					returnmap.put("assess", assess);//主观评价 1需要 0 不需要
					returnmap.put("content", content);
					return returnmap;*/
					//教研中心
				} 
	}
	/**
	 * 查看听课明细
	 * @param curriculumDetails
	 * @return
	 */
	public Map<String,Object> selectRecordDetails(Map<String,Object> map){
		Map<String,Object> retmap=new HashMap<String,Object>();
		String templateid="";
		String content="";
		//先根据 resourceid|worksid 、userid 去查一下是否存在听课记录
		map.put("userid", ShiroDbRealm.getCurrentLoginName());
		Map<String,Object> recordwmap=curriculumIntroduceMapper.selectRecordDetail(map);
		//如果不存在，查到当前启用的听课表id
		if(recordwmap == null){
			if(curriculumIntroduceMapper.selectNowLecture() == null){
				return null;
			}
			templateid=curriculumIntroduceMapper.selectNowLecture().get("id").toString();
		}else{
			//如果存在返回，听课表id
			templateid=recordwmap.get("templateid").toString();
			content=recordwmap.get("content").toString();
		}
		//
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		/*if(content != null && content.length()>1){
			content = content.substring(1, content.length() - 1);
			String[] strarry = content.split("</><><>,");
			
			for (int i = 0; i < strarry.length; i++) {
				Map<String, Object> maparray = new HashMap<String, Object>();
				String id = strarry[i].trim()
						.substring(7, strarry[i].trim().lastIndexOf("'|'score':'"));
				String score = strarry[i].trim().substring(
						strarry[i].trim().lastIndexOf("'|'score':'") + 11, strarry[i].trim().length() - 2);
				if(i == strarry.length-1){
					score=score.substring(0, score.length()-7);
				}
				maparray.put("id", id);
				maparray.put("score", score);
				list.add(maparray);
			}
		}*/
		
		if(content != null && content.length()>1){
			content = content.replace("\n", "<br>");
			List<Map<String,Object>> listmap = JsonUtil.jsonToObject(content, List.class);
			for(Map<String,Object> contentmap:listmap){
				Map<String, Object> maparray = new HashMap<String, Object>();
				maparray.put("id", contentmap.get("id"));
				maparray.put("score", contentmap.get("score"));
				list.add(maparray);
			}
		}
		//根据听课表模板id，查看子听课表
		List<RecordTemplate>  RecordTemplateList=curriculumIntroduceMapper.selectLectureTemplate(templateid);
		retmap.put("template", RecordTemplateList);
		retmap.put("content", list);
		retmap.put("templateid", templateid);
		
		return retmap;
	}
	
	/**
	 * 保存评审明细
	 * (页面上传过来的参数转化为map；content前台传每个值，后台保存一个json)
	 * @param curriculumDetails
	 * @return
	 */
	public int savetReviewDetails(Map<String,Object> map){
		//直播、精品过来参数为resourceid，教学中心过来为worksid
		String resourceid="";
		if(map.get("floder") != null && map.get("floder") != ""){
			 resourceid=(map.get("curriculumid")==null || map.get("curriculumid")=="")?map.get("floder").toString():map.get("curriculumid").toString();
		}
		String worksid=(map.get("worksid")==null)?"":map.get("worksid").toString();
		String userid=ShiroDbRealm.getCurrentLoginName();
		//
		String score=map.get("score").toString();//总分值
		String templateid=map.get("templateid").toString();//模板id
//		String reviewdate=map.get("reviewdate").toString();//评审时间
		String reviewdate=DateTermUtil.getNowTime();
		String remark=map.get("remark").toString();//评价
		String content=map.get("content").toString();//评审内容，json参数????
		map.put("userid", ShiroDbRealm.getCurrentLoginName());
		map.put("reviewdate", reviewdate);
		
		map.put("content", content);
		map.put("resourceid", resourceid);
		map.put("isover", "1");
		//先根据 resourceid|worksid 、userid 去查一下是否存在
		Map<String,Object> reviewmap=curriculumIntroduceMapper.selectReviewDetail(map);
		//如果存在、修改
		if(reviewmap != null){
			return curriculumIntroduceMapper.updateReviewDetail(map);
		}else{
			//模板使用数加1
			curriculumIntroduceMapper.updateReviewUseTimes(templateid);
			//如果不存在，新增
			return curriculumIntroduceMapper.addReviewDetail(map);
		}
	}
	
	/**
	 * 保存听课明细
	 * (页面上传过来的参数转化为map；content前台传每个值，后台保存一个json)
	 * @param curriculumDetails
	 * @return
	 */
	public int savetRecordDetails(Map<String,Object> map){
		//直播、精品过来参数为resourceid，教学中心过来为worksid
		String resourceid=(map.get("curriculumid")==null || map.get("curriculumid")=="")?map.get("floder").toString():map.get("curriculumid").toString();
		String worksid=(map.get("worksid")==null)?"":map.get("worksid").toString();
		String content=(map.get("content")==null)?"":map.get("content").toString();//评审内容，json参数?
//		String userid=ShiroDbRealm.getCurrentLoginName();
//		String userid="admin";
		map.put("userid", ShiroDbRealm.getCurrentLoginName());
		map.put("content", content);
		map.put("resourceid", resourceid);
		//
//		String templateid=map.get("templateid").toString();//模板id
		//先根据 resourceid|worksid 、userid 去查一下是否存在
		Map<String,Object> reviewmap=curriculumIntroduceMapper.selectRecordDetail(map);
		//如果存在、修改
		if(reviewmap != null){
			return curriculumIntroduceMapper.updateRecordDetails(map);
		}else{
			//如果不存在，新增
			curriculumIntroduceMapper.updateRecordUseTimes(map.get("recordtemplateid").toString());
			return curriculumIntroduceMapper.addRecordDetails(map);
		}
	}
	
	/**
	 * 展示所有评审指标下拉框
	 * @param map
	 * @return
	 */
	public Map<String,Object>  selectReviewOption(Map<String,Object> map){
		//如果是直播、精品过来显示所有模板
		//如果是活动过来，根据作品id，查到活动id，再查到模板id
		Map<String,Object> retmap=new HashMap<String,Object>();
		List<ParentReviewTemplate> list=curriculumIntroduceMapper.selectAllReview();
		retmap.put("data", list);
		return retmap;
	}
	
	/**
	 * 评审指标下拉框触发事件联动
	 * @param map
	 * @return
	 */
	public Map<String,Object>  selectReviewOptiondetail (Map<String,Object> map){
		Map<String,Object>  reviewDetailmap=new HashMap<String, Object>();
		//???
//		map.put("userid", ShiroDbRealm.getCurrentLoginName());
		map.put("userid", ShiroDbRealm.getCurrentLoginName());
		 Map<String,Object>  returnmap=new HashMap<String,Object>();
		 List<ReviewTemplate> endReviewTemplateList=new ArrayList<ReviewTemplate>();
		 int sumScore=0;
		String templateid="";
		String content="";
		String remark="";
		String reviewdate="";
		String assess="";
		Map<String,Object> studymap=new HashMap<String,Object>();
		templateid=map.get("templateid").toString();//模板id
		ParentReviewTemplate parentReviewTemplate=curriculumIntroduceMapper.selectOneReview(templateid);
		//same begin
//		templateid=map.get("templateid").toString();//模板id
		//根据模板的id，查模板的名字，下拉框里显示
		List<Map<String,Object>> templatename=new ArrayList<Map<String,Object>>();
		Map<String,Object> templatenamemap=new HashMap<String,Object>();
		templatenamemap.put("id", templateid);
		templatenamemap.put("name", curriculumIntroduceMapper.selectOneReview(templateid).getName());
		 templatename.add(templatenamemap);
//		reviewdate=(studymap.get("reviewdate") == null)?"":studymap.get("reviewdate").toString();//评审时间
		assess=parentReviewTemplate.getAssess();
		//TODO
		//通过模板id查到子模板中一级模板对象 ReviewTemplate
		List<ReviewTemplate> reviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,"0");
		//通过模板di，一级模板id，查到一个对象
		for(ReviewTemplate reviewTemplate:reviewList){
			List<ReviewTemplate> childreviewList=curriculumIntroduceMapper.selectReviewChildren(templateid,reviewTemplate.getId());
			endReviewTemplateList.addAll(childreviewList);
			reviewDetailmap.put(reviewTemplate.getChildKey(), childreviewList);
			sumScore+=Integer.parseInt(reviewTemplate.getChildValue());
		}
		returnmap.put("ReviewTemplate", reviewDetailmap);
//		returnmap.put("reviewdate", reviewdate);
		returnmap.put("templateid", templateid);
//		returnmap.put("remark", studymap.get("remark"));
		returnmap.put("assess", assess);//主观评价 1需要 0 不需要
		returnmap.put("score", "0");//现在评的分
		returnmap.put("sumScore", sumScore);//总共的分数
		/*content=studymap.get("content").toString();
		content = content.substring(1, content.length() - 1);
		String[] strarry = content.split(",");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < strarry.length; i++) {
			Map<String, Object> maparray = new HashMap<String, Object>();
			String id = strarry[i].trim()
					.substring(7, strarry[i].trim().lastIndexOf("|") - 1);
			String score = strarry[i].trim().substring(
					strarry[i].trim().lastIndexOf(":") + 2, strarry[i].trim().length() - 2);
			maparray.put("id", id);
			maparray.put("score", score);
			list.add(maparray);
		}
		returnmap.put("content", list);*/
		returnmap.put("templatename", templatename);
		
		return returnmap;
		//same end
	}
	
	/**
	 * 根据不同条件查素材
	 * @return
	 */
	public Map<String,Object> selectMaterialList(HttpServletRequest req,Map<String,Object> map){
		Map<String,Object> returnmap=new HashMap<String,Object>();
		Material material = new Material();
		String worksid="";
		if(map.get("worksid")!=null && map.get("worksid")!= ""){
			worksid=map.get("worksid").toString();
		}else if(map.get("floder")!=null && map.get("floder")!=""){
			worksid=map.get("floder").toString();
		}else if(map.get("curriculumid")!=null && map.get("curriculumid")!= ""){
			worksid=map.get("curriculumid").toString();
		}
		material.setWorksid(worksid);
		List<Material> materiallist= materialMapper.selectMaterialByWhere(material);
		for(Material m:materiallist){
			m.setNametype(CommonUtil.getnametype(m.getName()));
			if(m.getNametype().equals("PDF") || m.getNametype().equals("Word") || m.getNametype().equals("PowerPoint") || m.getNametype().equals("Excel")){
				String name = reService.transSwf(req,m.getFileurl());
//				name="0b62ea2f-4706-488c-a488-a7de72a3b9e4.swf";
				m.setTransferurl(name);
			}
		}
		returnmap.put("data", materiallist);
		return returnmap;
	}
	/**
	 * 删除素材
	 * @return
	 */
	@Transactional(readOnly = false)
	public int deleteMaterial(Material material){
		return materialMapper.deleteMaterial(material.getId());
	}
	/**
	 * 新增素材
	 * @return
	 */
	@Transactional(readOnly = false)
	public int addMaterial(Material material){
		return materialMapper.addMaterial(material);
	}
	
	/**
	 * 修改素材
	 * @return
	 */
	@Transactional(readOnly = false)
	public int updateMaterial(Material material){
		return materialMapper.updateMaterial(material);
	}
	
	/**
	 * 允许下载素材
	 * @return
	 */
	@Transactional(readOnly = false)
	public int allowDownloadMaterial(Material material){
		material=materialMapper.selectMaterialById(material.getId());
		return materialMapper.allowDownloadMaterial(material);
	}
	/**
	 * 导入素材
	 * 
	 * @param req
	 * @param parentid
	 * @param curriculumid
	 * @return
	 */
	@Transactional(readOnly = false)
	public int importMaterial(Map<String, Object> map) {
		String worksid="";
		if(map.get("worksid")!=null && map.get("worksid")!= ""){
			worksid=map.get("worksid").toString();
		}else if(map.get("floder")!=null && map.get("floder")!=""){
			worksid=map.get("floder").toString();
		}else if(map.get("curriculumid")!=null && map.get("curriculumid")!=""){
			worksid=map.get("curriculumid").toString();
		}
		if (map.get("resource_uuid") != null) {
			ResourceView resourceView=reDao.findReByUuid(map.get("resource_uuid").toString());
			Map<String, Object> insertmap = new HashMap<String, Object>();
//			insertmap.put("list", map.get("readyresourcidlist"));//name,type,id
			insertmap.put("flag", "0");
			insertmap.put("worksid", worksid);
//			insertmap.put("resourceid", (map.get("resourceid")== null)?"" :map.get("resourceid").toString() );
			insertmap.put("resource_uuid", resourceView.getResource_uuid());
			insertmap.put("name", resourceView.getName());
			insertmap.put("type", map.get("type"));
			materialMapper.addMaterialOne(insertmap);
			return 1;
		}
		return 0;
	}
	/**
	 * 上传素材
	 * 
	 * @param req
	 * @param parentid
	 * @param curriculumid
	 * @return
	 */
	@Transactional(readOnly = false)
	public void uploadMaterial(MultipartHttpServletRequest req, Material material) {
		ContinueFTP ftp = new ContinueFTP();
		List<FileBean> list= new ArrayList<FileBean>();
		try {
			String ip=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.host");
			String host=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.port");
			String username=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.username");
			String password=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.password");
			 boolean conflag=ftp.connect(ip,Integer.parseInt(host),username, password);
			 if(conflag){
				 list =  ftp.upload(null, req);
				 if(list != null && list.size()>0){
					 System.out.println("上传成功");
				 }else{
					 System.out.println("上传失败");
					 return;
				 }
			 }
				//return flag;
				
		} catch (Exception e) {
			e.printStackTrace();
			return;
		} finally {
		}
		
		String worksid="";
		if (material.getWorksidMaterial().length()>0 && !"null".equals(material.getWorksidMaterial())) {
			worksid = material.getWorksidMaterial();
		}
		if (material.getFloderMaterial().length()>0 && !"null".equals(material.getFloderMaterial())) {
			worksid = material.getFloderMaterial();
		}
		if (material.getCurriculumidMaterial().length() >0 && !"null".equals(material.getCurriculumidMaterial())) {
			worksid = material.getCurriculumidMaterial();
		}

//		List<FileBean> list = CommonUtil.upload(null, req);
		/*if (list != null && list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("parentid", "1");//???
			map.put("isfolder", "1");
			map.put("source", "upload");
			map.put("createuser", ShiroDbRealm.getCurrentLoginName());
			reDao.add(map);
			// return 1;
		}*/
		List<FileBean> files = new ArrayList<FileBean>();
		List<Integer> ids = new ArrayList<Integer>();
		if (list != null && list.size() > 0) {
			// Map<String, Object> map = new HashMap<String, Object>();
			
			List<Integer> videoIds = new ArrayList<Integer>();
			List<Trans> transList = new ArrayList<Trans>();
			for (FileBean fileBean : list) {
				this.setName("1", fileBean);
				files.add(fileBean);
			}
			for (FileBean fileBean : files) {
				ResourceView view = new ResourceView();
				view.setFileurl(fileBean.getFileurl());
				view.setName(fileBean.getName());
				view.setNametype(fileBean.getNametype());
				view.setResource_uuid(fileBean.getResource_uuid());
				view.setSize(fileBean.getSize());
				view.setParentid("1");
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
					trans.setReal_path(AppConstants.upload_PATH+view.getFileurl());
					transList.add(trans);
					System.out.println(trans.getTrans_path()+"-----------------------------------"+trans.getRes_path());
				}
			}
			// 转码
			new transThread(transList).start();
		}
		if (list != null && list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("list", list);
			map.put("name", list.get(0).getName());
			map.put("flag", "0");
			map.put("worksid", worksid);
			map.put("resource_uuid", list.get(0).getResource_uuid());
			map.put("type", material.getTypeMaterial().toString());
			map.put("createuser", ShiroDbRealm.getCurrentLoginName());
			materialMapper.addMaterialOne(map);
		}
	}

	/**
	 * 查看评论
	 * @return
	 */
	public Map<String,Object> getComment(PageBean pageBean) {
		Map<String,Object> mapData =null;
		if(pageBean!=null){
			mapData = new HashMap<String,Object>();
			mapData.put("data",commentMapper.getCommentByPage(pageBean));
			mapData.put("total", commentMapper.queryCommentNumsByPage(pageBean));
		}
		return mapData;
	}
	/**
	 * 
	 */
	@Transactional(readOnly = false)
	public int addComment(Comment entity){
		//entity.setCreateuser(ShiroDbRealm.getCurrentLoginName());
		return commentMapper.addComment(entity);
	}
	public List<Tree> getTrees() {
		return reDao.getTrees();
	}
	/**
	 * 查询直播流、转码、分发 
	 * @param req
	 */
	public String selectLiveFlow(String curriculumid){
 		String livecache = null;//排序后的rtmp流
		String result=null;//rtsp流,被缓存
		
		//根据教室id，查录播机ip
		Map<String,Object> curriculummap=myClassRoomService.selectIpByCurriculumid(curriculumid);
		if(curriculummap == null || curriculummap.get("ip")== null || curriculummap.get("mac") ==null){
			return null;
		}
		Date classtime=DateTermUtil.datetimeParse(curriculummap.get("date").toString()+" "+curriculummap.get("endtime").toString()+":00");
		Date now=DateTermUtil.datetimeParse(DateTermUtil.getNowTime());
		if(DateUtils.isStartBeforeEndTime(classtime, now)){
			System.out.println("课程已经结束，没有视频");
			return "0";
		}
		Curriculum curriculum=new Curriculum();
		curriculum.setAreaid(curriculummap.get("areaid").toString());
		curriculum.setLivemodel(curriculummap.get("livemodel").toString());
		curriculum.setDate(curriculummap.get("date").toString());
		curriculum.setEndtime(curriculummap.get("endtime").toString());
//			String ip=myClassRoomService.selectIpByArea(curriculum.getAreaid()).get("ip").toString();
			String ip=curriculummap.get("ip").toString();
			//调用接口 拼url
			String ipPort = serverService.getWebServer("1");
			String rtmpPort = serverService.getWebServer("2");
			String handoutIpPort = serverService.getWebServer("3");
			//?????
			String typeid=(String) curriculummap.get("typeid");
			String url ="http://"+ipPort+"/deviceService/getRtspUrlFromZddm?mac="+curriculummap.get("mac").toString();
			if(typeid!=null&&"2".equals(typeid)){
				 url += "&deviceType=centralcontroller";
			}else{
				 url += "&deviceType=recording";
			}
			//缓存中查rtsp
			result = getCache("respliveflow_"+curriculumid);
			if( result != null && result != ""){
				System.out.println("从缓存中获取rtsp直播流："+result);
			}else{
				//调用设备 查询rtsp地址
				 result = HttpSend.get(url);
				if(result == null || result == ""){
					System.out.println("无法获取到rtsp流："+result+",mac为："+curriculummap.get("mac").toString()+",IP为："+curriculummap.get("ip"));
					return null;
				}else{
					setCache("respliveflow_"+curriculumid, result);
					System.out.println("把rtsp放到缓存里");
				}
			}
			//转json
			Map<String,Object> resultMap = JsonUtil.jsonToObject(result, Map.class);
			//拼一个json格式参数
			List<Map<String,Object>> maplist=  new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> livemodelmap=syscodeService.getCode("livemodel");
			String filmval="";String voideval="";String allval="";
			for(Map<String,Object> m:livemodelmap){
				if("电影".equals(m.get("name"))){
					 filmval=m.get("value").toString();
				}else if("资源".equals(m.get("name"))){
					 voideval=m.get("value").toString();
				}else{
					 allval=m.get("value").toString();
				}
			}
			System.out.println("filmval"+filmval+",voideval:"+voideval+",allval："+allval);
			String mac=curriculummap.get("mac").toString();
			String[] filmArry=new String[1];
			filmArry[0]="film";
			String[] resourceArry=new String[4];
			resourceArry[0]="students";
			resourceArry[1]="full";
			resourceArry[2]="teacher";
			resourceArry[3]="vga";
			String[] filmAndResArry=new String[5];
			filmAndResArry[0]="film";
			filmAndResArry[1]="students";
			filmAndResArry[2]="full";
			filmAndResArry[3]="teacher";
			filmAndResArry[4]="vga";
			//如果是电影模式
			if(curriculum.getLivemodel().equals(filmval)){
				for(int i=0;i<filmArry.length;i++){
					maplist.add(getRtmpParam(resultMap.get(filmArry[i]),curriculumid,mac,filmArry[i]));
				}
				//如果是资源模式
			}else if(curriculum.getLivemodel().equals(voideval)){
				for(int i=0;i<resourceArry.length;i++){
					maplist.add(getRtmpParam(resultMap.get(resourceArry[i]),curriculumid,mac,resourceArry[i]));
				}
				//如果是电影加资源
			}else if(curriculum.getLivemodel().equals(allval)){
				for(int i=0;i<filmAndResArry.length;i++){
					maplist.add(getRtmpParam(resultMap.get(filmAndResArry[i]),curriculumid,mac,filmAndResArry[i]));
				}
			}
			//
			String urlall = "http://"+rtmpPort+"/transcode/transinfo";
			String resultall = HttpSend.post(urlall,JsonUtil.toJson(maplist));
			System.out.println("获取到的rtmp流:"+resultall);
			//转json
			
			livecache=sortFilmStr(resultall);
			if(livecache == "" || livecache== null){
				return "";
			}
			if(handoutIpPort == null || handoutIpPort.contains("null")){
				return livecache;
			}
			//分发
			return handoutrtmp(handoutIpPort,ip,curriculum.getDate()+" "+curriculum.getEndtime(), resultall,livecache);
	}
	/**
	 * @param rtspFlow
	 * @param curriculumid
	 * @param mac
	 * @return
	 */
	public Map<String,Object> getRtmpParam(Object rtspFlow,String curriculumid,String mac,String flowtype){
		if(rtspFlow == null || rtspFlow==""){
			return null;
		}
		Map<String,Object> data = new HashMap<String, Object>();
		data.put("rtmp_repeater", rtspFlow);
		data.put("uid", curriculumid+"_"+flowtype);
		data.put("grade", "1");
		data.put("endtime", "2070-01-01 01:01:01");
		data.put("group_id", mac);
//		data.put("is_trans", 1);//1：表示转码
//		data.put("stream_type", "rtmp");//key不存在 :表示rtsp流； 存在并且key为rtmp表示 rtmp流
		return data;
	}
	/**
	 * 分发
	 * @param groupid
	 * @param endtime
	 * @param rtmps
	 * @return
	 */
	public String handoutrtmp(String ipport,String groupid,String endtime,String rtmps,String rtmpcache){
			String handouturl ="http://"+ipport+"//vds/web";
			Map<String,Object> handoutdata = new HashMap<String, Object>();
			//拼一个json格式参数
			handoutdata.put("group_id", groupid);
			handoutdata.put("endtime", endtime);
			Map<String,Object> jsonMap = JsonUtil.jsonToObject(rtmps, Map.class);
			handoutdata.put("rtmps", jsonMap.get("content"));
			String handputresultall = HttpSend.post(handouturl,JsonUtil.toJson(handoutdata));
			System.out.println("分发接口参数："+JsonUtil.toJson(handoutdata));
			System.out.println("获取到的分发流:"+handputresultall);
			String handoutlivecache=handoutsortFilmStr(handputresultall);
			return handoutlivecache;
	}
	/**
	 * 查询直播流 
	 * @param req
	 */
	public String selectLiveFlow_bak(String curriculumid){
 		String livecache = null;//排序后的rtmp流
		String result=null;//rtsp流,被缓存
		
		//根据教室id，查录播机ip
		Map<String,Object> curriculummap=myClassRoomService.selectIpByCurriculumid(curriculumid);
		if(curriculummap == null || curriculummap.get("ip")== null || curriculummap.get("mac") ==null){
			return null;
		}
		Date classtime=DateTermUtil.datetimeParse(curriculummap.get("date").toString()+" "+curriculummap.get("endtime").toString()+":00");
		Date now=DateTermUtil.datetimeParse(DateTermUtil.getNowTime());
		if(DateUtils.isStartBeforeEndTime(classtime, now)){
			System.out.println("课程已经结束，没有视频");
			return "0";
		}
		Curriculum curriculum=new Curriculum();
		curriculum.setAreaid(curriculummap.get("areaid").toString());
		curriculum.setLivemodel(curriculummap.get("livemodel").toString());
		curriculum.setDate(curriculummap.get("date").toString());
		curriculum.setEndtime(curriculummap.get("endtime").toString());
//			String ip=myClassRoomService.selectIpByArea(curriculum.getAreaid()).get("ip").toString();
			String ip=curriculummap.get("ip").toString();
			//调用接口 拼url
			String ipPort = serverService.getWebServer("1");
			String handoutIpPort = serverService.getWebServer("3");
//			String url = "http://"+ipPort+"/deviceService/sendToRecord?IP="+ip+"&RecordCmd=QueryRtspUrls";
			//?????
			String typeid=(String) curriculummap.get("typeid");
			String url ="http://"+ipPort+"/deviceService/getRtspUrlFromZddm?mac="+curriculummap.get("mac").toString();
			if(typeid!=null&&"2".equals(typeid)){
				 url += "&deviceType=centralcontroller";
			}else{
				 url += "&deviceType=recording";
			}
			//缓存中查rtsp
			result = getCache("respliveflow_"+curriculumid);
			if( result != null && result != ""){
				System.out.println("从缓存中获取rtsp直播流："+result);
			}else{
				//调用设备 查询rtsp地址
				 result = HttpSend.get(url);
				if(result == null || result == ""){
					System.out.println("无法获取到rtsp流："+result+",mac为："+curriculummap.get("mac").toString()+",IP为："+ipPort);
					return null;
				}else{
					setCache("respliveflow_"+curriculumid, result);
					System.out.println("把rtsp放到缓存里");
				}
			}
			//转json
			Map<String,Object> resultMap = JsonUtil.jsonToObject(result, Map.class);
			/*if(resultMap.get("film") == null || resultMap.get("film") == ""){
				return null;
			}*/
			//拼一个json格式参数
			Map<String,Object> parammap=new HashMap<String,Object>();
			List<Map<String,Object>> maplist=  new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> livemodelmap=syscodeService.getCode("livemodel");
			String filmval="";String voideval="";String allval="";
			for(Map<String,Object> m:livemodelmap){
				if("电影".equals(m.get("name"))){
					 filmval=m.get("value").toString();
				}else if("资源".equals(m.get("name"))){
					 voideval=m.get("value").toString();
				}else{
					 allval=m.get("value").toString();
				}
			}
			System.out.println("filmval"+filmval+",voideval:"+voideval+",allval："+allval);
			//如果是电影模式
			if(curriculum.getLivemodel().equals(filmval)){
				if(resultMap.get("film") != null && resultMap.get("film") != ""){
				Map<String,Object> data = new HashMap<String, Object>();
				data.put("rtmp_repeater", resultMap.get("film"));
				data.put("uid", curriculumid+"_film");
				data.put("grade", "1");
				//data.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				data.put("endtime", "2070-01-01 01:01:01");
				data.put("group_id", ip);
				maplist.add(data);
				}
				//如果是资源模式
			}else if(curriculum.getLivemodel().equals(voideval)){
				if(resultMap.get("students") != null && resultMap.get("students") != ""){
				Map<String,Object> studata = new HashMap<String, Object>();
				studata.put("rtmp_repeater", resultMap.get("students"));
				studata.put("uid", curriculumid+"_students");
				studata.put("grade", "1");
				//studata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				studata.put("endtime", "2070-01-01 01:01:01");
				studata.put("group_id", ip);
				maplist.add(studata);
				}
				//
				if(resultMap.get("full") != null && resultMap.get("full") != ""){
				Map<String,Object> fulldata = new HashMap<String, Object>();
				fulldata.put("rtmp_repeater", resultMap.get("full"));
				fulldata.put("uid", curriculumid+"_full");
				fulldata.put("grade", "1");
				//fulldata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				fulldata.put("endtime", "2070-01-01 01:01:01");
				fulldata.put("group_id", ip);
				maplist.add(fulldata);
				}
				//
				if(resultMap.get("teacher") != null && resultMap.get("teacher") != ""){
				Map<String,Object> teacherdata = new HashMap<String, Object>();
				teacherdata.put("rtmp_repeater", resultMap.get("teacher"));
				teacherdata.put("uid", curriculumid+"_teacher");
				teacherdata.put("grade", "1");
				//teacherdata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				teacherdata.put("endtime", "2070-01-01 01:01:01");
				teacherdata.put("group_id", ip);
				maplist.add(teacherdata);
				}
				//
				if(resultMap.get("vga") != null && resultMap.get("vga") != ""){
				Map<String,Object> vgadata = new HashMap<String, Object>();
				vgadata.put("rtmp_repeater", resultMap.get("vga"));
				vgadata.put("uid", curriculumid+"_vga");
				vgadata.put("grade", "1");
				//vgadata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				vgadata.put("endtime", "2070-01-01 01:01:01");
				vgadata.put("group_id", ip);
				maplist.add(vgadata);
				}
				//如果是电影加资源
			}else if(curriculum.getLivemodel().equals(allval)){
				if(resultMap.get("film") != null && resultMap.get("film") != ""){
					Map<String,Object> data = new HashMap<String, Object>();
					data.put("rtmp_repeater", resultMap.get("film"));
					data.put("uid", curriculumid+"_film");
					data.put("grade", "1");
					//data.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
					data.put("endtime", "2070-01-01 01:01:01");
					data.put("group_id", ip);
					maplist.add(data);
				}
				
				//
				if(resultMap.get("students") != null && resultMap.get("students") != ""){
				Map<String,Object> studata = new HashMap<String, Object>();
				studata.put("rtmp_repeater", resultMap.get("students"));
				studata.put("uid", curriculumid+"_students");
				studata.put("grade", "1");
				//studata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				studata.put("endtime", "2070-01-01 01:01:01");
				studata.put("group_id", ip);
				maplist.add(studata);
				}
				//
				if(resultMap.get("full") != null && resultMap.get("full") != ""){
				Map<String,Object> fulldata = new HashMap<String, Object>();
				fulldata.put("rtmp_repeater", resultMap.get("full"));
				fulldata.put("uid", curriculumid+"_full");
				fulldata.put("grade", "1");
				//fulldata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				fulldata.put("endtime", "2070-01-01 01:01:01");
				fulldata.put("group_id", ip);
				maplist.add(fulldata);
				}
				//
				if(resultMap.get("teacher") != null && resultMap.get("teacher") != ""){
				Map<String,Object> teacherdata = new HashMap<String, Object>();
				teacherdata.put("rtmp_repeater", resultMap.get("teacher"));
				teacherdata.put("uid", curriculumid+"_teacher");
				teacherdata.put("grade", "1");
				//teacherdata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				//fulldata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				teacherdata.put("endtime", "2070-01-01 01:01:01");
				teacherdata.put("group_id", ip);
				maplist.add(teacherdata);
				}
				//
				if(resultMap.get("vga") != null && resultMap.get("vga") != ""){
				Map<String,Object> vgadata = new HashMap<String, Object>();
				vgadata.put("rtmp_repeater", resultMap.get("vga"));
				vgadata.put("uid", curriculumid+"_vga");
				vgadata.put("grade", "1");
				//vgadata.put("endtime", curriculum.getDate()+" "+curriculum.getEndtime());
				vgadata.put("endtime", "2070-01-01 01:01:01");
				vgadata.put("group_id", ip);
				maplist.add(vgadata);
				}
			}
			parammap.put("RecordParm", JsonUtil.toJson(maplist));
			//以后写成活的参数
			String rtmppPort = serverService.getWebServer("2");
			if(rtmppPort != null || rtmppPort !=""){
				parammap.put("IP", rtmppPort.split(":")[0]);//转码ip
				parammap.put("PORT", rtmppPort.split(":")[1]);
			}
			
			//
			String urlall = "http://"+ipPort+"/deviceService/startTranscodeLiving";
			String resultall = HttpSend.post(urlall,parammap);
			System.out.println("获取到的rtmp流:"+resultall);
			//转json
			
			livecache=sortFilmStr(resultall);
			if(livecache == "" || livecache== null){
				return "";
			}
			//分发
			Map<String,Object> jsonMap = JsonUtil.jsonToObject(resultall, Map.class);
			return handoutrtmp(handoutIpPort,ip,curriculum.getDate()+" "+curriculum.getEndtime(), resultall,livecache);
	}
	public String handoutsortFilmStr(String flowstr) {
		List<String> flowlist = new ArrayList<String>();
		Map<Integer, Object> flowmap=new HashMap<Integer, Object>();
		JSONObject jsonstr=JsonUtil.string2json(flowstr);
		if(jsonstr == null ){
			return "";
		}
		try{
		List<Map<String,String>> maplist= JsonUtil.jsonToObject(jsonstr.getString("content"), List.class);
		for(Map<String,String> map:maplist){
			if(map.get("uid").indexOf("_film") >0){
				flowmap.put(0, map.get("rtmp_distributer"));
			}else if(map.get("uid").indexOf("_teacher")>0){
				flowmap.put(1, map.get("rtmp_distributer"));
			}else if(map.get("uid").indexOf("_vga")>0){
				flowmap.put(2, map.get("rtmp_distributer"));
			}else if(map.get("uid").indexOf("_students")>0){
				flowmap.put(3, map.get("rtmp_distributer"));
			}else if(map.get("uid").indexOf("_full")>0){
				flowmap.put(4, map.get("rtmp_distributer"));
			}else {
				flowmap.put(5, map.get("rtmp_distributer"));
			}
		}
		for(int i=0;i<=flowmap.size();i++){
			if(flowmap.get(i) != null){
				flowlist.add(flowmap.get(i).toString());
			}
		}
		System.out.println("排序后的分发直播流："+JsonUtil.toJson(flowlist));
		String resultdtr=JsonUtil.toJson(flowlist);
		resultdtr=resultdtr.replace("\"", "'");
		return resultdtr;
		}catch(JSONException e){
			return "";
		}
		
	}
	public String sortFilmStr(String flowstr) {
		List<String> flowlist = new ArrayList<String>();
		Map<Integer, Object> flowmap=new HashMap<Integer, Object>();
		JSONObject jsonstr=JsonUtil.string2json(flowstr);
		if(jsonstr == null ){
			return "";
		}
		try{
		List<Map<String,String>> maplist= JsonUtil.jsonToObject(jsonstr.getString("content"), List.class);
		for(Map<String,String> map:maplist){
			if(map.get("uid").indexOf("_film") >0){
				flowmap.put(0, map.get("rtmp_transcoder"));
			}else if(map.get("uid").indexOf("_teacher")>0){
				flowmap.put(1, map.get("rtmp_transcoder"));
			}else if(map.get("uid").indexOf("_vga")>0){
				flowmap.put(2, map.get("rtmp_transcoder"));
			}else if(map.get("uid").indexOf("_students")>0){
				flowmap.put(3, map.get("rtmp_transcoder"));
			}else if(map.get("uid").indexOf("_full")>0){
				flowmap.put(4, map.get("rtmp_transcoder"));
			}else {
				flowmap.put(5, map.get("rtmp_transcoder"));
			}
		}
		for(int i=0;i<=flowmap.size();i++){
			if(flowmap.get(i) != null){
				flowlist.add(flowmap.get(i).toString());
			}
		}
		System.out.println("排序后的直播流："+JsonUtil.toJson(flowlist));
		String resultdtr=JsonUtil.toJson(flowlist);
		resultdtr=resultdtr.replace("\"", "'");
		return resultdtr;
		}catch(JSONException e){
			return "";
		}
		
	}
	public Map<String,Object> queryCommentNums(String resourceid, String typedata) {
		// TODO Auto-generated method stub
		Map<String,Object> map = commentMapper.queryCommentNums(resourceid,typedata);
		return map;
	}
	/**
	 * 根据floder查一条资源信息
	 * @param floder
	 * @return
	 */
	public String selectResourceByFloder (String floder){
		Map<String,Object> map =new HashMap<String,Object>();
		List<ResourceView> resList=reDao.selectResourceByFloder(floder);
		int i=0;
		for(ResourceView res:resList){
			if("0".equals(res.getTransFlag())){
				String path1=AppConstants.upload_PATH+res.getResourcePath().substring(1,res.getResourcePath().length())+res.getFloder()+"/"+res.getName();
				String path2="/data"+res.getTransPath()+res.getFloder()+"/"+res.getName();
				Map<String,Object> result = CommonUtil.getSubPath(path1, path2);
				map.put("video"+i, result.get("result"));
				map.put("video"+i+"times", result.get("times"));
			}else{
				String path3=AppConstants.upload_PATH+res.getResourcePath().substring(1,res.getResourcePath().length())+res.getFloder()+"/"+res.getName();
				List<String> result = new ArrayList<String>();
				result.add("/data"+res.getTransPath()+res.getFloder()+"/"+res.getName());
				try {
					map.put("video"+i, result);
					map.put("video"+i+"times", CommonUtil.getSeconds(path3));
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			i++;
		}
		String resultdtr=JsonUtil.toJson(map);
//		resultdtr=resultdtr.replace("\"", "'");
		return resultdtr;
	}
	/**
	 * study_resource
	 * @param res
	 * @return
	 */
	public String getVideos(ResourceView res){
		Map<String,Object> map =new HashMap<String,Object>();
		if("1".equals(res.getTransFlag())){
			String path1=AppConstants.upload_PATH+res.getFileurl();
			String path2="/data/"+res.getTransPath();
			Map<String,Object> result = CommonUtil.getSubPath(path1, path2);
			map.put("video0", result.get("result"));
			map.put("video0"+"times", result.get("times"));
		}else{
			String path3=AppConstants.upload_PATH+res.getFileurl();
			List<String> result = new ArrayList<String>();
			result.add("/data/"+res.getFileurl());
			try {
				map.put("video0", result);
				map.put("video0"+"times", CommonUtil.getSeconds(path3));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		String resultdtr=JsonUtil.toJson(map);
		return resultdtr;
	}
	/**
	 * 根据worksid查一条资源信息
	 * @param floder
	 * @return
	 */
	public String selectResourceByWorksid (String worksid,String source){
		Map<String,Object> parammap=new HashMap<String,Object>();
		Map<String,Object> map =new HashMap<String,Object>();
		parammap.put("workid", worksid);
	//	1、根据worksid，到study_works 中查到resourceid
		String resourceid=curriculumIntroduceMapper.selectSubjectCourseByStudy(parammap).getResourceid();
	//	2、判断source
	//	如果source=upload  一个视频窗口，resourceid=study_resource.id 拼transPath即可
		map.put("resourceid", resourceid);
		if(source.equals("upload")){
			//edit2015-11-18
			ResourceView res=reDao.findReById(resourceid);
			return getVideos(res);
		}else if(source.equals("collect")){
//			如果source=collect，条件storeid=resourceid，查到storetype
			ResourceView res=reDao.findReById(resourceid);
			if(res.getStoretype().equals("1")){
//				如果storetype=1，代表收藏别人在个人空间上传的作品，resourceid=study_resource.id一个视频窗口，拼transPath即可
				return getVideos(res);
			}else if(res.getStoretype().equals("2")){
//				如果storetype=2，代表收藏别人在录播机上的作品，resourceid=zonekey_resource.floder多个视频窗口，拼2段transPath+floder即可
				ResourceView zoneres=reDao.findReByid(resourceid);
				return getVideos(zoneres);
			}else if(res.getStoretype().equals("3")){
//				如果storetype=3，代表收藏别人在智慧上的作品，resourceid=study_wisclassroom_resource.id一个视频窗口，拼study_wisclassroom_resource.transPath即可
				Map<String,Object> wismapObject=wisclassroomMapper.selectByResourceId(resourceid);
				Map<String,Object> wismap =new HashMap<String,Object>();
				if("0".equals(wismapObject.get("transFlag"))){
					String path1=AppConstants.upload_PATH+wismapObject.get("fileurl");
					String path2="/data/"+wismapObject.get("transPath");
					Map<String,Object> result = CommonUtil.getSubPath(path1, path2);
					map.put("video0", result.get("result"));
					map.put("video0"+"times", result.get("times"));
				}else{
					String path3=AppConstants.upload_PATH+wismapObject.get("fileurl");
					List<String> result = new ArrayList<String>();
					result.add("/data/"+wismapObject.get("transPath"));
					try {
						map.put("video0", result);
						map.put("video0"+"times", CommonUtil.getSeconds(path3));
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				String resultdtr=JsonUtil.toJson(map);
				return resultdtr;
			}
		}
		return "";
	}
	
	public void hitCount(String name,String value){
		if(name.equals("curriculumid")||name.equals("floder")){
			commentMapper.hitCount(name,value);
		}
	}
	/**
	 * 查看评论的星星数和人数
	 */
	public Map<String,Object> selectCommentCount(HttpServletRequest req){
		Map<String,Object> resmap =new HashMap<String,Object>();
		Map<String,Object> map = JsonUtil.jsonToObject(req, Map.class);
		String type=map.get("type").toString();
		String resourceid="";
		if(map.get("worksid")==null || map.get("worksid") == ""){
			 resourceid=(map.get("curriculumid")==null || map.get("curriculumid")=="")?map.get("floder").toString():map.get("curriculumid").toString();
		}else{
			resourceid=map.get("worksid").toString();
		}
		String sumperson=commentMapper.queryCommentNums(resourceid, type).get("nums").toString();
		String totalscore="";
		if(sumperson.equals("0")){
			totalscore="1";
		}else{
			totalscore=commentMapper.queryComment(resourceid, type).get("totalscore").toString();
			totalscore=Math.ceil((Double.parseDouble(totalscore)/Double.parseDouble(sumperson)))+"";
		}
		resmap.put("sumperson", sumperson);
		resmap.put("totalscore", totalscore);
		return resmap;
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
	private class transThread extends Thread {
		private List<Trans> trans;

		public transThread(List<Trans> trans) {
			this.trans = trans;
		}

		@Override
		public void run() {
			for (Trans tr : trans) {
				File f = new File(tr.getReal_path());
				if(!f.exists()){
					System.out.println("文件不存在，不转码");
					//continue;
				}
					String result = CommonUtil.transCode(serverService.getServer(AppConstants.TYPE_SERVER_TRANS,AppConstants.TRANS_URL),tr);
					if (result != null && !"".equals(result)) {
						TransResult re = JsonUtil.jsonToObject(result, TransResult.class);
						if ("0".equals(re.getResponse_code())) {
							ResourceView view = new ResourceView();
							view.setId(tr.getFile_id());
							String tp = tr.getTrans_path();
							view.setTransPath(tp.substring(tp.indexOf("trans/",0),tp.length())+tr.getRes_name()+".mp4");
							//view.setTransFlag("1");
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

}
