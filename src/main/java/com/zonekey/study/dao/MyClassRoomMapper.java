package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.entity.ClassInfo;
import com.zonekey.study.entity.Curriculum;
import com.zonekey.study.entity.CurriculumParamForWeek;
import com.zonekey.study.entity.Curriculumbase;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.Pagebar;
import com.zonekey.study.entity.Term;
import com.zonekey.study.entity.Wisclassroom;
import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.CurriculumMaterials;
import com.zonekey.study.vo.ResourceView;

@MyBatisRepository
public interface MyClassRoomMapper extends BaseMapper<CurriculumMaterials, String> {
	/**
	 * 教师查找周课表
	 * 
	 * @return
	 */
	public List<CurriculumParamForWeek> findMyWeekCurriculumList(CurriculumParamForWeek curriculumParamForWeek);

	/**
	 * 学生查找周课表
	 * 
	 * @return
	 */
	public List<CurriculumParamForWeek> stufindMyWeekCurriculumList(CurriculumParamForWeek curriculumParamForWeek);

	/**
	 * 查找所有周,不翻页
	 * 
	 * @param id
	 * @return
	 */
	public Term findAllWeeksForShearch();

	/**
	 * @Title:findCurriculumDetailList
	 * @Description: 查找课堂内容
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public List<CurriculumMaterials> findCurriculumDetailList(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:showResourceBeforeClass
	 * @Description: 查找课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public List<CurriculumMaterials> showResourceBeforeClass(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:selectResourceBeforeClass
	 * @Description: 查找课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public List<CurriculumMaterials> selectResourceBeforeClass(Map<String, Object> parammap);

	public int addResourceBeforeClass(Map<String, Object> map);

	/**
	 * @Title:showAllResourceBeforeClass
	 * @Description: 展示全部课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public List<CurriculumMaterials> showAllResourceBeforeClass(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:updateCurriculum
	 * @Description: 编辑课堂内容（主题、学科、阶段、简介）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int updateCurriculum(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:selectCurriculumMaterials
	 * @Description: 查找是否有这条记录
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public CurriculumMaterials selectCurriculumMaterials(@Param("curriculumid") String curriculumid);

	public int insertCurriculumMaterials(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:setupVisibilityClassReady
	 * @Description: 设置课前准备资料的学生可见性 操作（zonekey_curriculum_ready_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int setupVisibilityClassReady(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:moveClassReady
	 * @Description: 教师移除课前准备资料（zonekey_curriculum_ready_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int moveClassReady(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:collectPrepareDate
	 * @Description: 学生收藏课前准备资料（zonekey_resource_collection）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int collectPrepareDate(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:selectCollectPrepareDate
	 * @Description: 查找学生收藏课前准备资料（zonekey_resource_collection）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public CurriculumMaterials selectCollectPrepareDate(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:selectCollectPrepareDate
	 * @Description: 从资源表查找课前准备资料是否被收藏（zonekey_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public ResourceView selectCollectResource(ResourceView resourceView);
	public int selectCollectCountResource(ResourceView resourceView);

	/**
	 * @Title:updatecollectPrepareDate
	 * @Description: 学生取消收藏课前准备资料（zonekey_resource_collection）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int deletecollectPrepareDate(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:showVideoAfterClass
	 * @Description: 课堂生成资料的展示(zonekey_curriculum_resource) 调别人接口
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public List<CurriculumMaterials> showVideoAfterClass(CurriculumMaterials curriculumMaterials);
	public int deletefromafterresource(CurriculumMaterials curriculumMaterials);
	public int deletefromafterresourceById(CurriculumMaterials curriculumMaterials);
	
	public int insertAll(@Param("storelist") List<CurriculumMaterials> storeList);
	/**
	 * @Title:collectGenerateClass
	 * @Description: 收藏课堂生成资料（zonekey_resource_collection）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public void collectGenerateClass(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:setupVisibilityAfterClass
	 * @Description: 老师设置课堂生成性资料的可见性（zonekey_curriculum_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int setupVisibilityAfterClass(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:selectfromafterresource
	 * @Description: 查询课后资源表中是否有记录
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public CurriculumMaterials selectfromafterresource(CurriculumMaterials curriculumMaterials);

	/**
	 * @Title:insertafterresource
	 * @Description: 新增课后资源表中是否有记录（zonekey_curriculum_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int insertafterresource(CurriculumMaterials curriculumMaterials);

	/**
	 * 查找当前学期
	 * 
	 * @param id
	 * @return
	 */
	public Term findNowTerm();

	/**
	 * @Title:findMyTypesByweek
	 * @Description: 查找一段时间内的所有方案(为我的课表使用)
	 * @author niuxl
	 * @date 2014年10月16日 下午3:20:45
	 * @param curriculumbase
	 * @return
	 */
	public List<Curriculumbase> findMyTypesByweek(Curriculumbase curriculumbase);

	//
	public List<Map<String, Object>> getMp4(@Param("map") Map<String, Object> map);

	public int getUploadCount(@Param("status") String status);

	public void updateStatusByFloder(@Param("map") Map<String, Object> map, @Param("status") String status);

	public List<CurriculumMaterials> findResourceBeforeClassByMac(@Param("map") Map<String, Object> map);

	/**
	 * 根据mac date查询对应教室今天的所有课程
	 * 
	 * @param map
	 * @return
	 */
	public List<ClassInfo> findClssInfoByMac(@Param("map") Map<String, Object> map);

	// 通过mac查课表id
	public Map<String, Object> selectCurriculumIdByMac(Wisclassroom wisclassroom);

	public List<ResourceView> findResourceByFloder(String floder);

	// 查找当前时间的直播课程
	public List<Curriculum> selectLiveCurriculum(String date);

	// 根据教室id，查教室下录播机ip
	public Map<String, Object> selectIpByArea(String areaid);
	// 根据课表id，查录播机mac，ip等信息
		public Map<String, Object> selectIpByCurriculumid(@Param("curriculumid")String curriculumid);
	

	/**
	 * 查找直播课
	 * 
	 * @param pageBean
	 * @return
	 */
	public List<Map<String,Object>> findLiveCurriculum(@Param("deptid")String deptid,@Param("loginname")String loginname,@Param("page")Pagebar page);

	/**
	 * 查找直播课总数
	 * 
	 * @param pageBean
	 * @return
	 */
	public long findLiveCount(@Param("deptid")String deptid,@Param("loginname")String loginname,@Param("page")Pagebar page);
	public List<Map<String,Object>> findHotList(@Param("type")String type,@Param("page")Pagebar page);
	public List<Map<String,Object>> findPublish(@Param("deptid")String deptid,@Param("loginname")String loginname,@Param("page")Pagebar page);
	public long findPublishCount(@Param("deptid")String deptid,@Param("loginname")String loginname,@Param("page")Pagebar page);
	/**
	 * 
	 * @param page
	 * @return
	 */
	public long findMyLiveCount(PageBean page);
	public List<Map<String, Object>> findMyLiveCurriculum(PageBean page);
}
