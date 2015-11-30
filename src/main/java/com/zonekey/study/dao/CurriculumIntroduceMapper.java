package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.CurriculumDetails;
import com.zonekey.study.entity.ParentReviewTemplate;
import com.zonekey.study.entity.RecordTemplate;
import com.zonekey.study.entity.ReviewTemplate;

@MyBatisRepository
public interface CurriculumIntroduceMapper extends BaseMapper<CurriculumDetails, String> {
	//标题（直播）
	public CurriculumDetails selectSubjectByLive(Map<String,Object> map);
	//标题、课程介绍（精品）
	public CurriculumDetails selectSubjectCourseByGood(Map<String,Object> map);
	//标题、课程介绍（教研中心）
	public CurriculumDetails selectSubjectCourseByStudy(Map<String,Object> map);
	
	
	//课程介绍（直播）
	public CurriculumDetails selectCourseByLive(Map<String,Object> map);
	//教师介绍
	public CurriculumDetails selectTeacherByLoginname(Map<String,Object> map);
	//教师介绍
		public CurriculumDetails selectTeacherByClass(Map<String,Object> map);
	
	
	//评审相关(教研中心)
	public Map<String,Object> selectReviewDetailByStudy(Map<String,Object> map);
	public Map<String,Object> selectReviewDetailFromStudy(Map<String,Object> map);
	
	//评审相关（直播、精品）
	public Map<String,Object> selectReviewDetailByLive(Map<String,Object> map);
	
	
	//查看评审明细（直播、精品、教研）
	public Map<String,Object> selectReviewDetail(Map<String,Object> map);
	//修改评审明细（直播、精品、教研）
	public int updateReviewDetail(Map<String,Object> map);
	//新增评审明细（直播、精品、教研）
	public int addReviewDetail(Map<String,Object> map);
	//修改评审模板使用次数
	public int updateReviewUseTimes(String id);
	//修改听课模板使用次数
	public int updateRecordUseTimes(String id);
	
	//修改听课明细（直播、精品、教研）
	public int updateRecordDetails(Map<String,Object> map);
	//新增听课明细（直播、精品、教研）
	public int addRecordDetails(Map<String,Object> map);
	//查看听课明细（直播、精品、教研）
	public Map<String,Object> selectRecordDetail(Map<String,Object> map);
	
	//查看当前使用的听课表
	public Map<String,Object> selectNowLecture();
	
	//根据听课表模板id，查听课模板
	public List<RecordTemplate> selectLectureTemplate(String id);
	public List<ReviewTemplate> selectReviewChildren(@Param("reviewid") String reviewid,@Param("parentid") String parentid);
	
	//查询所有父模板
	public List<ParentReviewTemplate> selectAllReview();
	//查询任一父模板
	public ParentReviewTemplate selectOneReview(@Param("id") String id);
	
	
}
