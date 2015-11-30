package com.zonekey.study.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.transaction.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.zonekey.study.common.ContinueFTP;
import com.zonekey.study.common.BaseRedis;
import com.zonekey.study.common.HttpSend;
import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.AppConstants;
import com.zonekey.study.common.CommonUtil;
import com.zonekey.study.common.DateTermUtil;
import com.zonekey.study.common.DateUtils;
import com.zonekey.study.common.IdUtils;
import com.zonekey.study.common.ReadProperties;
import com.zonekey.study.common.TransResult;
import com.zonekey.study.common.WisclassUpload;
import com.zonekey.study.dao.MyClassRoomMapper;
import com.zonekey.study.dao.ResourceMapper;
import com.zonekey.study.dao.ServerMapper;
import com.zonekey.study.dao.WisclassroomMapper;
import com.zonekey.study.entity.ClassInfo;
import com.zonekey.study.entity.Curriculum;
import com.zonekey.study.entity.CurriculumMaterials;
import com.zonekey.study.entity.CurriculumParamForWeek;
import com.zonekey.study.entity.Curriculumbase;
import com.zonekey.study.entity.MaxClass;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.Pagebar;
import com.zonekey.study.entity.SysUser;
import com.zonekey.study.entity.Term;
import com.zonekey.study.entity.Wisclassroom;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.vo.FileBean;
import com.zonekey.study.vo.ResourceView;
import com.zonekey.study.vo.Trans;

@Component
@Transactional(readOnly = true)
public class MyClassRoomService extends BaseRedis {
	@Autowired
	private MyClassRoomMapper myClassRoomMapper;
	@Resource
	private ResourceMapper reDao;
	@Resource
	private ServerMapper serverMapper;
	@Autowired
	private WisclassroomMapper wisclassroomMapper;
	@Autowired
	private SysUserService userService;
	@Autowired
	private ServerService serverService;
	/**
	 * @Title:findMyWeekCurriculumList
	 * @Description: 学生查找我的课表
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> stufindMyWeekCurriculumList(Curriculum curriculum) {
		CurriculumParamForWeek cuparam = new CurriculumParamForWeek();
		Map<String, Object> map = new HashMap<String, Object>();
		MaxClass maxclass = new MaxClass();
		String week = "0";
		String date = DateTermUtil.getNowDate();
		// 查看当前学期
		Term nowterm = myClassRoomMapper.findNowTerm();
		String endday = DateTermUtil.getEndTermByBegin(DateTermUtil.dateParse(nowterm.getStartday()), nowterm.getWeeks());
		// 判断当前时间是当前学期的第几周
		if (nowterm != null && curriculum.getWeeks() == null) {
			// 在当前学期范围内，
			if (DateTermUtil.isOrNotTerm(nowterm.getStartday(), endday, date) == true) {
				// 1、如果在然后确定第几周
				week = DateTermUtil.getWeeksByDateAndTerm(DateTermUtil.dateParse(date), nowterm) + "";
			} else {
				// 2、当前日期不在当前学期范围内时，2.1在学期开始之前，显示第一周课表；
				if (DateUtils.isStartBeforeEndTime(DateTermUtil.dateParse(date), DateTermUtil.dateParse(nowterm.getStartday()))) {
					week = "1";
				} else {
					// 2.2在学期结束之后的，显示最后一周课表
					week = nowterm.getWeeks();
				}
			}
		} else {
			week = curriculum.getWeeks();
		}
		/*
		 * String key0="xitong"+"_"+week; if(getCache(key0) != null){
		 * System.out.println("从缓存中获取："+getCache(key0)); return getCache(key0);
		 * }
		 */
		curriculum.setWeeks(week);
		cuparam.setWeeks(week.toString());
		cuparam.setAreaid(curriculum.getAreaid());
		cuparam.setDeptid(curriculum.getDeptid());
		cuparam.setLive(curriculum.getLive());
		cuparam.setRecord(curriculum.getRecord());
		cuparam.setIsresource(curriculum.getIsresource());
		cuparam.setMonDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "1"));
		cuparam.setTuesDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "2"));
		cuparam.setWednesDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "3"));
		cuparam.setThursDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "4"));
		cuparam.setFriDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "5"));
		cuparam.setSaturDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "6"));
		cuparam.setSunDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "7"));

		// 查找最大课节
		// 根据学期开始时间、第几周、星期几，查到当前周的起止时间
		String weekstr = DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "1");
		String weekend = DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "7");
		// 根据起止时间查到一共有多少种方案
		Curriculumbase curriculumbase = new Curriculumbase();
		curriculumbase.setDatebegin(weekstr);
		curriculumbase.setDateend(weekend);
		List<CurriculumParamForWeek> weeklistCurriculum = new ArrayList<CurriculumParamForWeek>();
		List<Curriculumbase> curriculumbaselist = myClassRoomMapper.findMyTypesByweek(curriculumbase);

		if (curriculumbaselist.get(0) == null || curriculumbaselist.size() == 0) {
			maxclass.setSmaxclass(3);
			maxclass.setMaxclass(6);
			map.put("classmaxnum", maxclass);
			map.put("data", "");
			return map;
		}
		if (curriculumbaselist.size() > 0) {
			for (int i = 0; i < curriculumbaselist.size(); i++) {
				cuparam.setClassbatch(curriculumbaselist.get(i).getClassbatch());
				// 查找课表集合数据42条或者49条
				weeklistCurriculum = myClassRoomMapper.stufindMyWeekCurriculumList(cuparam);
				if (weeklistCurriculum != null && weeklistCurriculum.size() > 0) {
					maxclass.setSmaxclass(curriculumbaselist.get(i).getSmaxclass());
					maxclass.setMaxclass(curriculumbaselist.get(i).getMaxclass());
					map.put("classmaxnum", maxclass);
					map.put("data", weeklistCurriculum);
					// System.out.println(weeklistCurriculum);

					break;
				}

			}

		}

		// 循环查到最大节次
		// 根据 学期、第几周、教室id 查找周课表
		// List<Curriculum> listCurriculum =
		// curriculumMapper.findWeekCurriculum(curriculum);
		if (weeklistCurriculum == null || weeklistCurriculum.size() == 0) {
			maxclass.setSmaxclass(3);
			maxclass.setMaxclass(6);
			map.put("classmaxnum", maxclass);
			map.put("data", "");
		}
		/*
		 * String key="xitong"+"_"+week; System.out.println("设置缓存key:"+key);
		 * System.out.println(weeklistCurriculum); setCache(key, map);
		 * System.out.println("设置缓存成功");
		 */
		return map;
	}

	/**
	 * @Title:findMyWeekCurriculumList
	 * @Description: 教师查找我的课表
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> findMyWeekCurriculumList(Curriculum curriculum) {
		CurriculumParamForWeek cuparam = new CurriculumParamForWeek();
		Map<String, Object> map = new HashMap<String, Object>();
		MaxClass maxclass = new MaxClass();
		String week = "0";
		String date = DateTermUtil.getNowDate();
		// 查看当前学期
		Term nowterm = myClassRoomMapper.findNowTerm();
		String endday = DateTermUtil.getEndTermByBegin(DateTermUtil.dateParse(nowterm.getStartday()), nowterm.getWeeks());
		// 判断当前时间是当前学期的第几周
		if (nowterm != null && curriculum.getWeeks() == null) {
			// 在当前学期范围内，
			if (DateTermUtil.isOrNotTerm(nowterm.getStartday(), endday, date) == true) {
				// 1、如果在然后确定第几周
				week = DateTermUtil.getWeeksByDateAndTerm(DateTermUtil.dateParse(date), nowterm) + "";
			} else {
				// 2、当前日期不在当前学期范围内时，2.1在学期开始之前，显示第一周课表；
				if (DateUtils.isStartBeforeEndTime(DateTermUtil.dateParse(date), DateTermUtil.dateParse(nowterm.getStartday()))) {
					week = "1";
				} else {
					// 2.2在学期结束之后的，显示最后一周课表
					week = nowterm.getWeeks();
				}
			}
		} else {
			week = curriculum.getWeeks();
		}
		/*
		 * String key0="xitong"+"_"+week; if(getCache(key0) != null){
		 * System.out.println("从缓存中获取："+getCache(key0)); return getCache(key0);
		 * }
		 */
		curriculum.setWeeks(week);
		cuparam.setWeeks(week.toString());
		cuparam.setAreaid(curriculum.getAreaid());
		cuparam.setUserno(ShiroDbRealm.getCurrentLoginName());
		// cuparam.setUserno("xitong");
		// ShiroDbRealm.getCurrentLoginName()
		cuparam.setLive(curriculum.getLive());
		cuparam.setRecord(curriculum.getRecord());
		cuparam.setIsresource(curriculum.getIsresource());
		cuparam.setMonDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "1"));
		cuparam.setTuesDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "2"));
		cuparam.setWednesDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "3"));
		cuparam.setThursDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "4"));
		cuparam.setFriDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "5"));
		cuparam.setSaturDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "6"));
		cuparam.setSunDate(DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "7"));

		// 查找最大课节
		// 根据学期开始时间、第几周、星期几，查到当前周的起止时间
		String weekstr = DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "1");
		String weekend = DateTermUtil.getDateByTermAndWeeks(nowterm.getStartday(), week, "7");
		// 根据起止时间查到一共有多少种方案
		Curriculumbase curriculumbase = new Curriculumbase();
		curriculumbase.setDatebegin(weekstr);
		curriculumbase.setDateend(weekend);
		List<CurriculumParamForWeek> weeklistCurriculum = new ArrayList<CurriculumParamForWeek>();
		List<Curriculumbase> curriculumbaselist = myClassRoomMapper.findMyTypesByweek(curriculumbase);

		if (curriculumbaselist.get(0) == null || curriculumbaselist.size() == 0) {
			maxclass.setSmaxclass(3);
			maxclass.setMaxclass(6);
			map.put("classmaxnum", maxclass);
			map.put("data", "");
			return map;
		}
		if (curriculumbaselist.size() > 0) {
			for (int i = 0; i < curriculumbaselist.size(); i++) {
				cuparam.setClassbatch(curriculumbaselist.get(i).getClassbatch());
				// 查找课表集合数据42条或者49条
				weeklistCurriculum = myClassRoomMapper.findMyWeekCurriculumList(cuparam);
				if (weeklistCurriculum != null && weeklistCurriculum.size() > 0) {
					maxclass.setSmaxclass(curriculumbaselist.get(i).getSmaxclass());
					maxclass.setMaxclass(curriculumbaselist.get(i).getMaxclass());
					map.put("classmaxnum", maxclass);
					map.put("data", weeklistCurriculum);
					// System.out.println(weeklistCurriculum);

					break;
				}

			}

		}

		// 循环查到最大节次
		// 根据 学期、第几周、教室id 查找周课表
		// List<Curriculum> listCurriculum =
		// curriculumMapper.findWeekCurriculum(curriculum);
		if (weeklistCurriculum == null || weeklistCurriculum.size() == 0) {
			maxclass.setSmaxclass(3);
			maxclass.setMaxclass(6);
			map.put("classmaxnum", maxclass);
			map.put("data", "");
		}
		/*
		 * String key="xitong"+"_"+week; System.out.println("设置缓存key:"+key);
		 * System.out.println(weeklistCurriculum); setCache(key, map);
		 * System.out.println("设置缓存成功");
		 */
		return map;
	}

	/**
	 * 查看所有周,不分页
	 */
	public List<LinkedHashMap<String, Object>> findAllWeeksForShearch() {
		List<LinkedHashMap<String, Object>> list = new ArrayList<LinkedHashMap<String, Object>>();
		Term term = myClassRoomMapper.findAllWeeksForShearch();

		for (int i = 0; i < Integer.parseInt(term.getWeeks()); i++) {
			LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("id", (i + 1) + "");
			map.put("value", "第" + (i + 1) + "周");
			list.add(map);
		}
		// list.add(map);
		return list;
	}

	/**
	 * @Title:findTermtips
	 * @Description: 学期提示
	 * @author niuxl
	 * @date 2014年10月31日 下午7:46:41
	 * @param term
	 * @return
	 */
	@Transactional(readOnly = false)
	public Map<String, Object> findTermtips() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 查看当前学期
			Term nowterm = myClassRoomMapper.findNowTerm();
			String endday = DateTermUtil.getEndTermByBegin(DateTermUtil.dateParse(nowterm.getStartday()), nowterm.getWeeks());
			if (nowterm != null) {
				// 如果学期结束时间<当前时间，提示学期已经结束
				if (Integer.parseInt(endday.replaceAll("-", "")) < Integer.parseInt(DateTermUtil.getNowDate().replaceAll("-", ""))) {
					map.put("id", 0);
					map.put("date", DateTermUtil.getNowDate());
					map.put("desc", "学期已经结束");
					return map;
				}
				// 当前时间，date类型
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
				Date date = null;
				date = format.parse(DateTermUtil.getNowDate());// 将字符串转换为日期
				// 星期几
				String weekdate = DateTermUtil.getWeekDayByDate(date);
				// 根据学期和当前时间查到是第几周
				int week = DateTermUtil.getWeeksByDate(nowterm, date);
				map.put("id", 1);
				map.put("termname", nowterm.getTermname());
				map.put("week", week + "");
				map.put("date", DateTermUtil.getNowDate());
				map.put("weekdate", weekdate);
			} else {
				map.put("id", 0);
				map.put("desc", "学期已经结束");
			}

		} catch (Exception e) {

		}
		return map;
	}

	/**
	 * @Title:findCurriculumDetailList
	 * @Description: 查找课堂内容
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> findCurriculumDetailList(CurriculumMaterials curriculumMaterials) {
		myClassRoomMapper.findCurriculumDetailList(curriculumMaterials);
		return null;
	}

	/**
	 * @Title:showResourceBeforeClass
	 * @Description: 查找课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> showResourceBeforeClass(CurriculumMaterials curriculumMaterials) {
		Map<String, Object> map = new HashMap<String, Object>();
		curriculumMaterials.setCreateuser(ShiroDbRealm.getCurrentLoginName());
		List<CurriculumMaterials> list = myClassRoomMapper.showResourceBeforeClass(curriculumMaterials);
		for (CurriculumMaterials c : list) {
			c.setReadyresourcesize(CommonUtil.getFormatSize(Double.valueOf(c.getReadyresourcesize())));
		}
		map.put("total", "");
		map.put("data", list);
		return map;
	}

	/**
	 * @Title:stushowResourceBeforeClass
	 * @Description: 学生查找课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> stushowResourceBeforeClass(CurriculumMaterials curriculumMaterials) {
		Map<String, Object> map = new HashMap<String, Object>();
		curriculumMaterials.setCreateuser(ShiroDbRealm.getCurrentLoginName());
		List<CurriculumMaterials> list = myClassRoomMapper.showResourceBeforeClass(curriculumMaterials);
		List<CurriculumMaterials> returnlist = new ArrayList<CurriculumMaterials>();
		for (CurriculumMaterials c : list) {
			c.setReadyresourcesize(CommonUtil.getFormatSize(Double.valueOf(c.getReadyresourcesize())));
			if (c.getResourcevisable().equals("1")) {
				returnlist.add(c);
			}
		}
		map.put("total", "");
		map.put("data", returnlist);
		return map;
	}

	/**
	 * @Title:selectResourceBeforeClass
	 * @Description: 查找课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> selectResourceBeforeClass(Map<String, Object> parammap) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CurriculumMaterials> list = myClassRoomMapper.selectResourceBeforeClass(parammap);
		map.put("total", "");
		map.put("data", list);
		return map;
	}

	/**
	 * @Title:showResourceBeforeClass
	 * @Description: 查找课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public List<CurriculumMaterials> initshowResourceBeforeClass(CurriculumMaterials curriculumMaterials) {
		return myClassRoomMapper.showResourceBeforeClass(curriculumMaterials);
	}

	/**
	 * @Title:showAllResourceBeforeClass
	 * @Description: 展示全部课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> showAllResourceBeforeClass(CurriculumMaterials curriculumMaterials) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CurriculumMaterials> list = myClassRoomMapper.showAllResourceBeforeClass(curriculumMaterials);
		map.put("total", "");
		map.put("data", list);
		return map;
	}

	/**
	 * @Title:updateCurriculum
	 * @Description: 编辑课堂内容（主题、学科、阶段、简介）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int updateCurriculum(CurriculumMaterials curriculumMaterials) {
		return myClassRoomMapper.updateCurriculum(curriculumMaterials);
	}

	/**
	 * @Title:selectCurriculumMaterials
	 * @Description: 查找课前准备资料，永远不为null
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public CurriculumMaterials selectCurriculumMaterials(CurriculumMaterials curriculumMaterials) {
		CurriculumMaterials c = myClassRoomMapper.selectCurriculumMaterials(curriculumMaterials.getCurriculumid());
		/*if (c != null && c.getImageurl() == null) {
			c.setImageurl("/curriculum/defaultimage.jpg");
			return c;
		} else if (c == null) {
			CurriculumMaterials newc = new CurriculumMaterials();
			newc.setImageurl("/curriculum/defaultimage.jpg");
			return newc;
		}*/
		if (c != null && c.getImageurl() != null) {
			c.setImageurl("/data"+"/"+c.getImageurl());
			return c;
		}
		return c;
	}

	/**
	 * @Title:selectCMaterials
	 * @Description: 查找课前准备资料(为其他方法调用使用)
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public CurriculumMaterials selectCMaterials(CurriculumMaterials curriculumMaterials) {
		return myClassRoomMapper.selectCurriculumMaterials(curriculumMaterials.getCurriculumid());
	}

	public int insertCurriculumMaterials(CurriculumMaterials curriculumMaterials) {
		curriculumMaterials.setUuid(UUID.randomUUID().toString());
		return myClassRoomMapper.insertCurriculumMaterials(curriculumMaterials);
	}

	/**
	 * @Title:setupVisibilityClassReady
	 * @Description: 设置课前准备资料的学生可见性 操作（zonekey_curriculum_ready_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int setupVisibilityClassReady(CurriculumMaterials curriculumMaterials) {
		return myClassRoomMapper.setupVisibilityClassReady(curriculumMaterials);
	}

	/**
	 * @Title:moveClassReady
	 * @Description: 教师移除课前准备资料（zonekey_curriculum_ready_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int moveClassReady(CurriculumMaterials curriculumMaterials) {
		return myClassRoomMapper.moveClassReady(curriculumMaterials);

	}
	
	/**
	 * @Title:setcollectAfterResource
	 * @Description: 智慧教室收藏课后生成资料（zonekey_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int setcollectAfterResource(ResourceView res) {
		int flag=0;
		res.setCreateuser(ShiroDbRealm.getCurrentLoginName());
		if (res.getResourcecollection().equals("1")) {
				//查出文件夹下面所有文件
				Map<String, Object> parammap = new HashMap<String, Object>();
				parammap.put("parentid", res.getStoreid());
				parammap.put("curriculumid", res.getCurriculumid());
				parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
				List<Wisclassroom> wlist = wisclassroomMapper.selectFilesById(parammap);
				//@jeanwin
				List<ResourceView> storeList = new ArrayList<ResourceView>();
				SysUser user = userService.getByLoginname(ShiroDbRealm.getCurrentLoginName());
				for(Wisclassroom childWis:wlist){
					if (!"1".equals(user.getUsertype())) {
						if(!"1".equals(childWis.getResourcevisable())){
							continue;
						}
					}
					// 根据floder去查一条资源记录
//					Map<String, Object> resmap = wisclassroomMapper.selectById(res.getStoreid());
					ResourceView vediores = new ResourceView();
					vediores.setParentid("1");
					vediores.setResource_uuid(IdUtils.uuid2());
					vediores.setName(childWis.getName());
					vediores.setNametype(CommonUtil.getnametype(childWis.getName()));
					vediores.setFileurl(childWis.getFileurl());
					vediores.setSource("collect");
					vediores.setIsfolder("1");
					vediores.setSize(Integer.parseInt(childWis.getSize()));
					vediores.setSubject("");
					vediores.setGrade("");
					vediores.setStoreid(childWis.getId());
					vediores.setStoretype(res.getStoretype());
					// vediores.setAuthor((resmap.get("createuser").toString()));
					vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					//flag=reDao.addResource(vediores);
					/***********************************************************************/
					storeList.add(vediores);
					/***********************************************************************/
				}
				flag=reDao.insertAll(storeList);
				
			}else{
				//查出文件夹下面所有文件
				Map<String, Object> parammap = new HashMap<String, Object>();
				parammap.put("parentid", res.getStoreid());
				parammap.put("curriculumid", res.getCurriculumid());
				parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
				List<Wisclassroom> wlist = wisclassroomMapper.selectFilesById(parammap);
				for(Wisclassroom childWis:wlist){
					// 根据floder去查一条资源记录
//					Map<String, Object> resmap = wisclassroomMapper.selectById(res.getStoreid());
					ResourceView vediores = new ResourceView();
					vediores.setStoreid(childWis.getId());
					vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					flag=reDao.deleteResourceByAuthor(vediores);
				}

		
	}
		return flag;
	}
	
	/**
	 * @Title:setOnecollectAfterResource
	 * @Description: 智慧教室收藏课前准备资料（单一文件）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param map（name，fileurl，size，id，storetype，resourcecollection）
	 */
	public int setOnecollectAfterResource(Map<String, String> map) {
		List<ResourceView> storeList = new ArrayList<ResourceView>();
		int flag=0;
			if (map.get("resourcecollection").toString().equals("1")) {
					ResourceView vediores = new ResourceView();
					vediores.setParentid("1");
					vediores.setResource_uuid(IdUtils.uuid2());
					vediores.setName(map.get("name"));
					vediores.setNametype(CommonUtil.getnametype(map.get("name")));
					vediores.setFileurl(map.get("fileurl").substring(6, map.get("fileurl").length()));
					vediores.setSource("collect");
					vediores.setIsfolder("1");
					vediores.setSize(Long.valueOf(map.get("size")));
					vediores.setSubject("");
					vediores.setGrade("");
					vediores.setStoreid(map.get("id"));
					vediores.setStoretype("3");
					// vediores.setAuthor((resmap.get("createuser").toString()));
					vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					vediores.setStoreuser(ShiroDbRealm.getCurrentLoginName());
					//flag=reDao.addResource(vediores);
					/***********************************************************************/
					storeList.add(vediores);
					/***********************************************************************/
				flag=reDao.insertAll(storeList);
				
			}else{
					ResourceView vediores = new ResourceView();
					vediores.setStoreid(map.get("id"));
					vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					flag=reDao.deleteResourceByAuthor(vediores);
			}
		return flag;
	}
	/**
	 * 学生收藏录播机单一资料
	 * @param resourcecollection  name  subject grade id
	 * @return
	 */
	//TODO
	public int collectOneVideo(ResourceView resource){
		int flag=0;
		if(resource.getResourcecollection().equals("1")){
			List<ResourceView> storeList = new ArrayList<ResourceView>();
			ResourceView vediores = new ResourceView();
			vediores.setIsfolder("1");
			vediores.setParentid("1");
			vediores.setResource_uuid(IdUtils.uuid2());
			vediores.setName(resource.getName());
			vediores.setNametype(CommonUtil.getnametype(resource.getName()));
			//如果是视频存transurl，其他存image/2015/20150706,暂时不存，用时关联查询
			vediores.setFileurl(resource.getResourcePath().substring(1)+resource.getFloder()+File.separatorChar+resource.getName());
			vediores.setSource("collect");
			vediores.setSubject(resource.getSubject());
			vediores.setGrade(resource.getGrade());
			vediores.setStoreid(resource.getId());
			vediores.setStoretype("2");
			vediores.setStoreuser(ShiroDbRealm.getCurrentLoginName());
			vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
			vediores.setTransPath(resource.getTransPath().substring(1)+resource.getFloder()+File.separatorChar+resource.getName());
			if(resource.getTransFlag().equals("0")){
				vediores.setTransFlag("1");
			}else if(resource.getTransFlag().equals("1")){
				vediores.setTransFlag("0");
			}
			storeList.add(vediores);
			flag=reDao.insertAll(storeList);
			return 1;
		}else{
			ResourceView vediores = new ResourceView();
			vediores.setStoreid(resource.getId());
			vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
			flag=reDao.deleteResourceByAuthor(vediores);
		}
		return flag;
		
	}
	/**
	 * @Title:collectPrepareDate
	 * @Description: 学生收藏资料（课前、录播机文件及、智慧教室文件夹）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int collectPrepareDate(ResourceView res) {
		res.setCreateuser(ShiroDbRealm.getCurrentLoginName());
		// 根据Createuser和资源storeid查是否有记录，没有新增，有删除
		int resourceViewcount = myClassRoomMapper.selectCollectCountResource(res);
		if (resourceViewcount == 0) {
			//@jeanwin
			List<ResourceView> storeList = new ArrayList<ResourceView>();
			if (res.getStoretype().equals("1")) {
				// 根据id去查一条资源记录
				ResourceView resvo = reDao.findResourceById(res.getStoreid());
				// add
				resvo.setStoreid(res.getStoreid());
				resvo.setStoretype(res.getStoretype());
				/******************************@Jeanwin*************************************/
				resvo.setSource("collect");
				/******************************@Jeanwin*************************************/
				resvo.setAuthor(resvo.getCreateuser());
				resvo.setStoreuser(ShiroDbRealm.getCurrentLoginName());
				resvo.setCreateuser(ShiroDbRealm.getCurrentLoginName());
//				reDao.addResource(resvo);
				storeList.add(resvo);
			} else if (res.getStoretype().equals("2")) {
				// 根据floder去查一条资源记录
//				Map<String, Object> resmap = myClassRoomMapper.findResourceByFloder(res.getStoreid());
				List<ResourceView> resList=myClassRoomMapper.findResourceByFloder(res.getStoreid());
				SysUser user = userService.getByLoginname(ShiroDbRealm.getCurrentLoginName());
				for(ResourceView resource:resList){
					if (!"1".equals(user.getUsertype())) {
						if(!"1".equals(resource.getResourcevisable())){
							continue;
						}
					}
					ResourceView vediores = new ResourceView();
					vediores.setIsfolder("1");
					vediores.setParentid("1");
					vediores.setResource_uuid(IdUtils.uuid2());
					vediores.setName(resource.getName());
					vediores.setNametype(CommonUtil.getnametype(resource.getName()));
					vediores.setFileurl(resource.getResourcePath().substring(1)+resource.getFloder()+File.separatorChar+resource.getName());
					vediores.setSource("collect");
					vediores.setSubject(resource.getSubject());
					vediores.setGrade(resource.getGrade());
					vediores.setStoreid(resource.getId());
					vediores.setStoretype(res.getStoretype());
//					vediores.setAuthor(resource.getCreateuser());
					vediores.setStoreuser(ShiroDbRealm.getCurrentLoginName());
					vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					vediores.setTransPath(resource.getTransPath().substring(1)+resource.getFloder()+File.separatorChar+resource.getName());
					vediores.setTransFlag(resource.getTransFlag());
//					reDao.addResource(vediores);
					storeList.add(vediores);
				}
				
			}else if (res.getStoretype().equals("3")) {
				//查出文件夹下面所有文件
				Map<String, Object> parammap = new HashMap<String, Object>();
				parammap.put("parentid", res.getStoreid());
				parammap.put("curriculumid", res.getCurriculumid());
				parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
				List<Wisclassroom> wlist = wisclassroomMapper.selectFilesById(parammap);
				for(Wisclassroom childWis:wlist){
					// 根据floder去查一条资源记录
//					Map<String, Object> resmap = wisclassroomMapper.selectById(res.getStoreid());
					ResourceView vediores = new ResourceView();
					vediores.setParentid("1");
					vediores.setResource_uuid(IdUtils.uuid2());
					vediores.setName(childWis.getName());
					vediores.setNametype(CommonUtil.getnametype(childWis.getName()));
					vediores.setFileurl(childWis.getFileurl());
					vediores.setSource("collect");
					vediores.setIsfolder("1");
					vediores.setSize(Integer.parseInt(childWis.getSize()));
					vediores.setSubject("");
					vediores.setGrade("");
					vediores.setStoreid(childWis.getId());
					vediores.setStoretype(res.getStoretype());
					// vediores.setAuthor((resmap.get("createuser").toString()));
					vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					vediores.setStoreuser(ShiroDbRealm.getCurrentLoginName());
					storeList.add(vediores);
//					reDao.addResource(vediores);
				}
				
			}
			reDao.insertAll(storeList);
		} else {
			if (res.getStoretype().equals("1")) {
				ResourceView vediores = new ResourceView();
				vediores.setStoreid(res.getStoreid());
				vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
				reDao.deleteResourceByAuthor(vediores);
			}else if (res.getStoretype().equals("2")) {
				List<ResourceView> resList=myClassRoomMapper.findResourceByFloder(res.getStoreid());
				for(ResourceView resource:resList){
				ResourceView vediores = new ResourceView();
				vediores.setStoreid(resource.getId());
				vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
				reDao.deleteResourceByAuthor(vediores);
				}
				
			}else if (res.getStoretype().equals("3")) {
				//查出文件夹下面所有文件
				Map<String, Object> parammap = new HashMap<String, Object>();
				parammap.put("parentid", res.getStoreid());
				parammap.put("curriculumid", res.getCurriculumid());
				parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
				List<Wisclassroom> wlist = wisclassroomMapper.selectFilesById(parammap);
				for(Wisclassroom childWis:wlist){
					// 根据floder去查一条资源记录
//					Map<String, Object> resmap = wisclassroomMapper.selectById(res.getStoreid());
					ResourceView vediores = new ResourceView();
					vediores.setStoreid(childWis.getId());
					vediores.setCreateuser(ShiroDbRealm.getCurrentLoginName());
					reDao.deleteResourceByAuthor(vediores);
				}
			}
		}
		return 0;
	}

	/**
	 * @Title:collectPrepareDate
	 * @Description: 学生收藏课前准备资料（zonekey_resource_collection）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public int collectPrepareDate_bak(CurriculumMaterials curriculumMaterials) {
		curriculumMaterials.setUuid(UUID.randomUUID().toString());
		curriculumMaterials.setUserid(ShiroDbRealm.getCurrentLoginName());
		// 根据userid和资源id查是否有记录，没有新增，有修改
		CurriculumMaterials collection = myClassRoomMapper.selectCollectPrepareDate(curriculumMaterials);
		if (collection == null) {
			// add
			return myClassRoomMapper.collectPrepareDate(curriculumMaterials);
		} else {
			// update
			return myClassRoomMapper.deletecollectPrepareDate(curriculumMaterials);
		}
	}

	/**
	 * 课前上传资源
	 * 
	 * @param req
	 * @param parentid
	 * @param curriculumid
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<Integer> addResource(MultipartHttpServletRequest req, String parentid, String curriculumid) {
		ContinueFTP ftp = new ContinueFTP();
		List<FileBean> list= new ArrayList<FileBean>();
		try {
			String ip=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.host");
			String host=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.port");
			String username=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.username");
			String password=ReadProperties.loadProperties("ftp.properties").getProperty("data.ftp.password");
			 boolean conflag=ftp.connect(ip,Integer.parseInt(host),username, password);
//			 ftp.connect("192.168.12.220",21,"ftpde", "123456");
			 if(conflag){
				 list =  ftp.upload(null, req);
					//return flag;
					System.out.println("上传成功");
			 }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
//		List<FileBean> list = CommonUtil.upload(null, req);
		/*if (list != null && list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("parentid", parentid);
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
			map.put("list", list);
			map.put("uuid", IdUtils.uuid2());
			map.put("curriculumid", curriculumid);
			map.put("createuser", ShiroDbRealm.getCurrentLoginName());
			myClassRoomMapper.addResourceBeforeClass(map);
			return ids;
		}
		return null;
	}

	/**
	 * 根据文件夹名字查一下是否存在这个文件夹
	 * 
	 * @param map
	 * @return
	 */
	@Transactional(readOnly = false)
	public int insertintowisclass(Map<String, Object> map) {
		return wisclassroomMapper.insertintowisclass(map);
	}
	/**
	 * 智慧教室上传资源
	 * （只负责保存文件）
	 * @param req
	 * @param parentid
	 * @param curriculumid
	 * @return
	 */
	@Transactional(readOnly = false)
	public int uploadwisclassroom(MultipartHttpServletRequest req, Wisclassroom wisclassroom) {
		System.out.println("智慧教室上传成功后，进入uploadwisclassroom，准备操作数据库！");
		System.out.println("文件路径："+wisclassroom.getFileurl());
		String parentid = "";
 		String resource_uuid = IdUtils.uuid2();
//		ContinueFTP ftp = new ContinueFTP();
		/*List<FileBean> list= new ArrayList<FileBean>();
		try {
			 ftp.connect("192.168.12.220",21,"ftpde", "123456");
				 list =  ftp.upload(null, req);
				//return flag;
				System.out.println("上传成功");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}*/
		// 根据教室mac查询教室id
		Map<String, Object> cmap = myClassRoomMapper.selectCurriculumIdByMac(wisclassroom);
		if(cmap == null){
			System.out.println("mac:"+wisclassroom.getMac()+",日期："+wisclassroom.getDate()+",节次："+wisclassroom.getClassnum()+"没有查到对应的课。");
			return 0;
		}
		// 根据文件夹名字查询是否存在
		Map<String, Object> filemap = wisclassroomMapper.selectFloderByName(wisclassroom.getFilePath().split("\\\\")[0]);
		// 如果文件夹不存在
		if (filemap == null) {
			// 新增文件夹记录进行创建
			Map<String, Object> addfile = new HashMap<String, Object>();
			addfile.put("parentid", "1");
			addfile.put("resource_uuid", resource_uuid);
			addfile.put("name", wisclassroom.getFilePath().split("\\\\")[0]);
			addfile.put("isfolder", "0");
			addfile.put("createuser", ShiroDbRealm.getCurrentLoginName());
			wisclassroomMapper.insertintowisclass(addfile);
			// 根据uuid查询刚才新增的id
			parentid = wisclassroomMapper.selectFloderByUuid(resource_uuid).get("id").toString();
		} else {
			// 如果文件夹存在，查询到id
			parentid = filemap.get("id").toString();
		}
//		if (list != null && list.size() > 0) {
			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("fileurl", wisclassroom.getFileurl().toString());
			map.put("name", wisclassroom.getFilename().toString());
			map.put("nametype", CommonUtil.getnametype(wisclassroom.getFilename().toString()));
			map.put("size", wisclassroom.getSize().toString());
			map.put("parentid", parentid);
			map.put("isfolder", "1");
			map.put("areaid", cmap.get("areaid").toString());
			map.put("userid", cmap.get("userid").toString());
			map.put("curriculumid", cmap.get("id").toString());
			map.put("createuser", ShiroDbRealm.getCurrentLoginName());*/
			List<Trans> transList = new ArrayList<Trans>();
			Wisclassroom wis=new Wisclassroom ();
			wis.setFileurl(wisclassroom.getFileurl().toString());
			wis.setName(wisclassroom.getFilename().toString());
			wis.setNametype(CommonUtil.getnametype(wisclassroom.getFilename().toString()));
			wis.setSize(wisclassroom.getSize().toString());
			wis.setParentid(parentid);
			wis.setIsfolder("1");
			wis.setAreaid(cmap.get("areaid").toString());
			wis.setUserid(cmap.get("userid").toString());
			wis.setCurriculumid(cmap.get("id").toString());
			wis.setCreateuser(ShiroDbRealm.getCurrentLoginName());
			wisclassroomMapper.insertFileIntowisclassreturnP(wis);
			//2015-7-6如果上传的是视频，需要转码+++
			// 判断是否是视频文件
			if ("Videos".equals(CommonUtil.getnametype(wisclassroom.getFilename().toString())) ){
//				videoIds.add(Integer.valueOf(wis.getId()));
				// 转码
				Trans trans = new Trans();
				String name = wis.getFileurl().substring(wis.getFileurl().lastIndexOf(File.separator)+1);
				String tranpath = "trans" + File.separator + DateUtils.getFormat("yyyy") + File.separator + DateUtils.getFormat("yyyyMMdd") + File.separator;
				trans.setRes_path(AppConstants.upload_PATH+wis.getFileurl().substring(0,wis.getFileurl().lastIndexOf(File.separator)));
				trans.setTrans_path(AppConstants.upload_PATH + tranpath);
				trans.setRes_name(name);
				trans.setFile_id(wis.getId());
				trans.setDur_time(300);
				trans.setFlag_hd(1);
				trans.setFlag_meg(1);
				trans.setGrade(1);
				trans.setReal_path(AppConstants.upload_PATH+wis.getFileurl());
				transList.add(trans);
				System.out.println(trans.getTrans_path()+"-----------------------------------"+trans.getRes_path());
				// 转码
				new wistransThread(transList).start();
			}
			
			return 1;
//		}
	}
	/**
	 * 智慧教室上传资源
	 * (之前的普通上传方式)
	 * @param req
	 * @param parentid
	 * @param curriculumid
	 * @return
	 */
	@Transactional(readOnly = false)
	public int uploadwisclassroom_bak(MultipartHttpServletRequest req, Wisclassroom wisclassroom) {
		String parentid = "";
		String resource_uuid = IdUtils.uuid2();
		List<FileBean> list = WisclassUpload.upload(null, req);
		// 根据教室mac查询教室id
		Map<String, Object> cmap = myClassRoomMapper.selectCurriculumIdByMac(wisclassroom);
		// 根据文件夹名字查询是否存在
		Map<String, Object> filemap = wisclassroomMapper.selectFloderByName(wisclassroom.getFilePath().split("\\\\\\\\")[0]);
		// 如果文件夹不存在
		if (filemap == null) {
			// 新增文件夹记录进行创建
			Map<String, Object> addfile = new HashMap<String, Object>();
			addfile.put("parentid", "1");
			addfile.put("resource_uuid", resource_uuid);
			addfile.put("name", wisclassroom.getFilePath().split("\\\\\\\\")[0]);
			addfile.put("isfolder", "0");
			addfile.put("createuser", ShiroDbRealm.getCurrentLoginName());
			wisclassroomMapper.insertintowisclass(addfile);
			// 根据uuid查询刚才新增的id
			parentid = wisclassroomMapper.selectFloderByUuid(resource_uuid).get("id").toString();
		} else {
			// 如果文件夹存在，查询到id
			parentid = filemap.get("id").toString();
		}
		if (list != null && list.size() > 0) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("parentid", parentid);
			// map.put("name", wisclassroom.getFilename());
			map.put("isfolder", "1");
			map.put("areaid", cmap.get("areaid").toString());
			map.put("userid", cmap.get("userid").toString());
			map.put("curriculumid", cmap.get("id").toString());
			// map.put("fileurl", wisclassroom.getFilePath());
			// map.put("size", wisclassroom.getSize());
			map.put("createuser", ShiroDbRealm.getCurrentLoginName());
			wisclassroomMapper.insertFileIntowisclass(map);
			return 1;
		}
		return 0;
	}

	/**
	 * 课前导入资源
	 * 
	 * @param req
	 * @param parentid
	 * @param curriculumid
	 * @return
	 */
	@Transactional(readOnly = false)
	public int importDateForCurriculum(Map<String, Object> map) {
		if (map.get("readyresourcuuidlist") != null) {
			Map<String, Object> insertmap = new HashMap<String, Object>();
			insertmap.put("list", map.get("readyresourcuuidlist"));
			insertmap.put("uuid", IdUtils.uuid2());
			insertmap.put("curriculumid", map.get("curriculumid"));
			insertmap.put("createuser", ShiroDbRealm.getCurrentLoginName());
			myClassRoomMapper.addResourceBeforeClass(insertmap);
			return 1;
		}
		return 0;
	}

	/**
	 * 上传课堂图片
	 * 
	 * @param req
	 * @param parentid
	 * @param curriculumid
	 * @return
	 */
	@Transactional(readOnly = false)
	public int uploadImage(MultipartHttpServletRequest req, CurriculumMaterials curriculumMaterials) {
		int flag = 0;
		List<FileBean> list = CommonUtil.upload(null, req);
		if (list != null && list.size() > 0) {
			CurriculumMaterials initCurriculum = selectCMaterials(curriculumMaterials);
			curriculumMaterials.setImageurl(list.get(0).getFileurl());
			if (initCurriculum == null) {
				flag = insertCurriculumMaterials(curriculumMaterials);
			} else {
				flag = updateCurriculum(curriculumMaterials);
			}
		}
		return flag;
	}

	/**
	 * @Title:showVideoAfterClass
	 * @Description: 录播机生成资料的展示（文件夹）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> showVideoAfterClass(CurriculumMaterials curriculumMaterials) {
		Map<String, Object> map = new HashMap<String, Object>();
		curriculumMaterials.setCreateuser(ShiroDbRealm.getCurrentLoginName());
		List<CurriculumMaterials> list =new ArrayList<CurriculumMaterials>();
		 list = myClassRoomMapper.showVideoAfterClass(curriculumMaterials);
		for (CurriculumMaterials c : list) {
			c.setAfterclassimageurl("/curriculum/defaultimage.jpg");
			List<ResourceView> resList=myClassRoomMapper.findResourceByFloder(c.getFloder());
			int vcount=0;
			int ccount=0;
			for(ResourceView resourceView:resList){
				if(resourceView.getResourcevisable()!= null && resourceView.getResourcevisable().equals("1")){
					vcount++;
				}
				if(resourceView.getResourcecollection() !=null && resourceView.getResourcecollection().equals("1")){
					ccount++;
				}
			}
			if(vcount==resList.size()){
				c.setResourcevisable("1");
			}else{
				c.setResourcevisable("0");
			}
			if(ccount==resList.size()){
				c.setResourcecollection("1");
			}else{
				c.setResourcecollection("0");
			}
		}
		map.put("total", "");
		map.put("data", list);
		return map;
	}
	/**
	 * @Title:showWisclassroomFiles
	 * @Description: 智慧教室资源的展示文件夹内所有文件
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> showWisclassroomFiles(Wisclassroom wisclassroom) {
		Map<String, Object> parammap = new HashMap<String, Object>();
		Map<String, Object> returnmmap = new HashMap<String, Object>();
		parammap.put("parentid", wisclassroom.getParentid());
		parammap.put("curriculumid", wisclassroom.getCurriculumid());
		parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
		List<Wisclassroom> wlist = wisclassroomMapper.selectFilesById(parammap);
		for(Wisclassroom w:wlist){
			String url=w.getFileurl().replace("\\", "/");
			w.setFileurl("/data/"+w.getFileurl().replace("\\", "/"));
		}
		returnmmap.put("data", wlist);
		returnmmap.put("source", "1");
		return returnmmap;
	}
	/**
	 * @Title:stushowWisclassroomFiles
	 * @Description: 智慧教室资源的展示文件夹内所有文件
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> stushowWisclassroomFiles(Wisclassroom wisclassroom) {
		Map<String, Object> parammap = new HashMap<String, Object>();
		Map<String, Object> returnmmap = new HashMap<String, Object>();
		List<Wisclassroom> returnlist = new ArrayList<Wisclassroom>();
		parammap.put("parentid", wisclassroom.getParentid());
		parammap.put("curriculumid", wisclassroom.getCurriculumid());
		parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
		List<Wisclassroom> wlist = wisclassroomMapper.selectFilesById(parammap);
		for(Wisclassroom childWis:wlist){
			childWis.setFileurl("/data/"+childWis.getFileurl().replace("\\", "/"));
			//查看每个文件夹下面的文件是否被收藏了
				if(childWis.getResourcevisable().equals("1")){
					returnlist.add(childWis);
				}
			}
		returnmmap.put("data", returnlist);
		returnmmap.put("source", "1");
		return returnmmap;
	}
	/**
	 * @Title:showVideoAfterClass
	 * @Description: 智慧教室资源的展示文件夹(老师调用)
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> showWisclassroomResource(Wisclassroom wisclassroom) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Wisclassroom> wlist = wisclassroomMapper.selectFloderById(wisclassroom.getCurriculumid(),ShiroDbRealm.getCurrentLoginName());
		for (Wisclassroom w : wlist) {
			w.setAfterclassimageurl("/curriculum/defaultimage.jpg");
			w.setName(w.getName().split("\\\\")[0]);
			w.setCurriculumid(wisclassroom.getCurriculumid());
			//查出文件夹下面所有文件
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("parentid", w.getId());
			parammap.put("curriculumid", w.getCurriculumid());
			parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
			List<Wisclassroom> wfilelist = wisclassroomMapper.selectFilesById(parammap);
			for(Wisclassroom childWis:wfilelist){
				//查看每个文件夹下面的文件是否全部可見，默認為不可見
				int vcount=0;
				int ccount=0;
				for(Wisclassroom resourceView:wfilelist){
					if(resourceView.getResourcevisable()!= null && resourceView.getResourcevisable().equals("1")){
						vcount++;
					}
					if(resourceView.getResourcecollection() !=null && resourceView.getResourcecollection().equals("1")){
						ccount++;
					}
				}
				if(vcount==wfilelist.size()){
					w.setResourcevisable("1");
				}else{
					w.setResourcevisable("0");
				}
				if(ccount==wfilelist.size()){
					w.setResourcecollection("1");
				}else{
					w.setResourcecollection("0");
				}
				
				/*if(childWis.getResourcevisable().equals("1")){
					w.setResourcevisable("1");
					continue;
				}*/
			}
			//
			/*for(Wisclassroom childWis:wfilelist ){
				//查看每个文件夹下面的文件是否被收藏了
					if(childWis.getResourcecollection().equals("1")){
						w.setResourcecollection("1");
						continue;
					}
				}*/
			//这样可以显示出图片，但是暂时不用这个办法
			for(Wisclassroom childWis:wfilelist){
				//显示图片
					if(CommonUtil.getnametype(childWis.getName()).equals("Pictrue")){
						w.setAfterclassimageurl("/data/" + childWis.getFileurl()); 
						continue;
					}
				}
		}
		map.put("data", wlist);
		return map;
	}

	/**
	 * @Title:stushowWisclassroomResource
	 * @Description: 智慧教室资源的展示文件夹(学生调用)
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> stushowWisclassroomResource(Wisclassroom wisclassroom) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Wisclassroom> wlist = wisclassroomMapper.selectFloderById(wisclassroom.getCurriculumid(),ShiroDbRealm.getCurrentLoginName());
		List<Wisclassroom> returnlist = new ArrayList<Wisclassroom>();
		for (Wisclassroom w : wlist) {
			w.setAfterclassimageurl("/curriculum/defaultimage.jpg");
			w.setName(w.getName().split("\\\\")[0]);
			w.setCurriculumid(wisclassroom.getCurriculumid());
			//查出文件夹下面所有文件
			Map<String, Object> parammap = new HashMap<String, Object>();
			parammap.put("parentid", w.getId());
			parammap.put("curriculumid", w.getCurriculumid());
			parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
			List<Wisclassroom> returnFiles = new ArrayList<Wisclassroom>();
			List<Wisclassroom> wfilelist = wisclassroomMapper.selectFilesById(parammap);
			
			//
			int vcount=0;
			int ccount=0;
			for(Wisclassroom resourceView:wfilelist){
				if(resourceView.getResourcevisable() != null && resourceView.getResourcevisable().equals("1")){
					returnFiles.add(resourceView);
					vcount++;
					if(resourceView.getResourcecollection() != null && resourceView.getResourcecollection().equals("1")){
						ccount++;
					}
					
				}
			}
			if(ccount >= returnFiles.size()){
				w.setResourcecollection("1");
			}else{
				w.setResourcecollection("0");
			}
			if (vcount >0) {
				returnlist.add(w);
			}
			//
			/*for(Wisclassroom childWis:wfilelist){
				//查看每个文件夹下面的文件是否被收藏了
					if(childWis.getResourcecollection().equals("1")){
						w.setResourcecollection("1");
						continue;
					}
				}
			for(Wisclassroom childWis:wfilelist){
				//查看每个文件夹下面的文件是否有可见的，如果有，该文件夹为可见，如果没有默认不可见
				if(childWis.getResourcevisable().equals("1")){
					returnlist.add(w);
					break;
				}
			}*/
		}
		map.put("data", returnlist);
		return map;
	}
	/**
	 * @Title:stushowVideoAfterClass
	 * @Description: 学生登陆，查看录播机生成资料的展示
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> stushowVideoAfterClass(CurriculumMaterials curriculumMaterials) {
		Map<String, Object> map = new HashMap<String, Object>();
		curriculumMaterials.setCreateuser(ShiroDbRealm.getCurrentLoginName());
		List<CurriculumMaterials> list = myClassRoomMapper.showVideoAfterClass(curriculumMaterials);
		List<CurriculumMaterials> returnlist = new ArrayList<CurriculumMaterials>();
		List<ResourceView> returnFiles = new ArrayList<ResourceView>();
		for (CurriculumMaterials c : list) {
			c.setAfterclassimageurl("/curriculum/defaultimage.jpg");
			List<ResourceView> resList=myClassRoomMapper.findResourceByFloder(c.getFloder());
			int vcount=0;
			int ccount=0;
			for(ResourceView resourceView:resList){
				if(resourceView.getResourcevisable() != null && resourceView.getResourcevisable().equals("1")){
					returnFiles.add(resourceView);
					vcount++;
					if(resourceView.getResourcecollection() != null && resourceView.getResourcecollection().equals("1")){
						ccount++;
					}
					
				}
			}
			if(ccount >= returnFiles.size()){
				c.setResourcecollection("1");
			}else{
				c.setResourcecollection("0");
			}
			if (vcount >0) {
				returnlist.add(c);
			}
		}
		map.put("total", "");
		map.put("data", returnlist);
		return map;
	}

	/**
	 * @Title:collectGenerateClass（不用）
	 * @Description: 收藏课堂生成资料（zonekey_resource_collection）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public void collectGenerateClass(CurriculumMaterials curriculumMaterials) {
		myClassRoomMapper.collectGenerateClass(curriculumMaterials);

	}

	/**
	 * @Title:setupVisibilityAfterClass
	 * @Description: 老师设置录播机资料的可见性（zonekey_curriculum_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	@Transactional(readOnly = false)
	public int setupVisibilityAfterClass(CurriculumMaterials curriculumMaterials) {
		int flag = 0;
		List<CurriculumMaterials> storeList = new ArrayList<CurriculumMaterials>();
		myClassRoomMapper.deletefromafterresource(curriculumMaterials);
		if(curriculumMaterials.getResourcevisable().equals("1")){
			List<ResourceView> resList=myClassRoomMapper.findResourceByFloder(curriculumMaterials.getFloder());
			for(ResourceView resource:resList){
				CurriculumMaterials cum = new CurriculumMaterials();
				cum.setUuid(IdUtils.uuid2());
				cum.setCurriculumid(resource.getCurriculumid());
				cum.setReadyresourcid(resource.getId());
				cum.setFloder(curriculumMaterials.getFloder());
				cum.setResourcevisable(curriculumMaterials.getResourcevisable());
				storeList.add(cum);
			}
			myClassRoomMapper.insertAll(storeList);
		}
		

		return flag;
	}
	
	/**
	 * @Title:oneafterclassvisibility
	 * @Description: 老师设置录播机资料的可见性（单一文件）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	@Transactional(readOnly = false)
	public int oneafterclassvisibility(CurriculumMaterials curriculumMaterials) {
		int flag = 0;
		if(curriculumMaterials.getResourcevisable().equals("0")){
			//删除
			flag=myClassRoomMapper.deletefromafterresourceById(curriculumMaterials);
		}else{
			//新增
			curriculumMaterials.setUuid(IdUtils.uuid2());
			flag=myClassRoomMapper.insertafterresource(curriculumMaterials);
		}
		return flag;
	}
	
	/**
	 * @Title:onesetafterclassvisibility
	 * @Description: 老师设置智慧教室的可见性（单一）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	@Transactional(readOnly = false)
	public int onesetafterclassvisibility(Wisclassroom wisclassroom) {
		int flag = 0;
		flag=wisclassroomMapper.setafterclassvisibility(wisclassroom.getResourcevisable(),wisclassroom.getId());

		return flag;
	}
	/**
	 * @Title:setafterclassvisibility
	 * @Description: 老师设置智慧教室的可见性（zonekey_curriculum_resource）
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	@Transactional(readOnly = false)
	public int setafterclassvisibility(Wisclassroom wisclassroom) {
		int flag = 0;
		Map<String, Object> parammap = new HashMap<String, Object>();
		parammap.put("parentid", wisclassroom.getId());
		parammap.put("curriculumid", wisclassroom.getCurriculumid());
		parammap.put("createuser", ShiroDbRealm.getCurrentLoginName());
		List<Wisclassroom> wlist = wisclassroomMapper.selectFilesById(parammap);
		for(Wisclassroom childWis:wlist){
			childWis.setResourcevisable(wisclassroom.getResourcevisable());
			flag=wisclassroomMapper.setafterclassvisibility(wisclassroom.getResourcevisable(),childWis.getId());
		}

		return flag;
	}
	@Transactional(readOnly = false)
	public String onDemand_bak(HttpServletRequest req) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		List<Map<String, Object>> list = null;
		// 查询正在上传个数
		int num = 0;
		num = myClassRoomMapper.getUploadCount("0");
		if ("2".equals(map.get("status")) && num > 3) {
			resultData.put("status", "3");
			return JsonUtil.toJson(resultData);
		}
		List<Map<String, Object>> data = myClassRoomMapper.getMp4(map);
		String ipPort = serverMapper.getServerByType(AppConstants.TYPE_SERVER_DEMAND);
		if (ipPort != null) {
			String url = "http://" + ipPort + AppConstants.DEMAND_URL;
			String result = HttpSend.post(url, data);
			Map<String, Object> resultMap = JsonUtil.jsonToObject(result, Map.class);
			if (resultMap != null && "0".equals(resultMap.get("response_code") + "")) {
				list = (List<Map<String, Object>>) resultMap.get("content");
				if (list.size() == 0) {
					myClassRoomMapper.updateStatusByFloder(map, "0");
					resultData.put("status", "0");
				} else {
					for (Map<String, Object> typemap : list) {
						typemap.put("type", "video");
					}
					myClassRoomMapper.updateStatusByFloder(map, "1");
					resultData.put("status", "1");
					resultData.put("data", list);
				}
			} else {
				resultData.put("status", "4");
			}
		} else {
			resultData.put("status", "4");
		}
		resultData.put("source", "2");
		return JsonUtil.toJson(resultData);

	}
	@Transactional(readOnly = false)
	public Map<String, Object> onDemand(HttpServletRequest req) {
		Map<String, Object> remap = new HashMap<String, Object>();
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		List<ResourceView> resList=myClassRoomMapper.findResourceByFloder(map.get("floder").toString());
		for(ResourceView resourceView:resList){
			resourceView.setNametype(CommonUtil.getnametype(resourceView.getName()));
			resourceView.setFileurl("/data"+resourceView.getTransPath()+resourceView.getFloder()+"/"+resourceView.getName());
//			resourceView.setFileurl("http://192.168.12.220/data"+resourceView.getTransPath()+resourceView.getFloder()+"/"+resourceView.getName());
		}
		remap.put("data", resList);
		remap.put("source", "2");
		return remap;
	}
	@Transactional(readOnly = false)
	public Map<String, Object> stuonDemand(HttpServletRequest req) {
		Map<String, Object> remap = new HashMap<String, Object>();
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		List<ResourceView> returnlist = new ArrayList<ResourceView>();
		List<ResourceView> resList=myClassRoomMapper.findResourceByFloder(map.get("floder").toString());
		for(ResourceView resourceView:resList){
			resourceView.setNametype(CommonUtil.getnametype(resourceView.getName()));
			resourceView.setFileurl("/data"+resourceView.getTransPath()+resourceView.getFloder()+"/"+resourceView.getName());
//			resourceView.setFileurl("http://192.168.12.220/data"+resourceView.getTransPath()+resourceView.getFloder()+"/"+resourceView.getName());
			//查看每个文件夹下面的文件是否被收藏了
			if("1".equals(resourceView.getResourcevisable())){
				returnlist.add(resourceView);
			}
		}
		remap.put("data", returnlist);
		remap.put("source", "2");
		return remap;
	}
	/**
	 * @Title:showResourceBeforeClass
	 * @Description: 查找课前准备资料
	 * @author niuxl
	 * @date 2015年3月19日 下午1:18:29
	 * @param res
	 */
	public Map<String, Object> findResourceBeforeClassByMac(Map<String, Object> parammap, HttpServletRequest req) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CurriculumMaterials> list = myClassRoomMapper.findResourceBeforeClassByMac(parammap);
		List<ClassInfo> classlist = myClassRoomMapper.findClssInfoByMac(parammap);

		/*
		 * String ip=req.getLocalAddr(); int host=req.getLocalPort();
		 * System.out.println("ip:"+ip); System.out.println("host:"+host);
		 */
		for (CurriculumMaterials c : list) {
			String name = c.getReadyresourcename();
			String parentid = c.getParentid();
			if (c.getParentid().equals("1")) {

			} else {
				for (int i = 0; i < 1; i++) {
					Map<String, Object> map1 = reDao.selectbyid(parentid);
					name = map1.get("name") + "/" + name;
					if (map1.get("parentid").equals("1")) {
						continue;
					} else {
						parentid = map1.get("parentid").toString();
						i--;
					}
				}

			}
			c.setReadyresourcefileurl(ReadProperties.getByName("common.ip")+ReadProperties.getIp()+ c.getReadyresourcefileurl());
			c.setReadyresourcename(name);
		}
		map.put("data", list);
		map.put("classinfo", classlist);
		return map;
	}

	/**
	 * 查找当前时间的直播课程
	 * 
	 * @return
	 */
	public Map<String, Object> selectLiveCurriculum() {
		Map<String, Object> map = new HashMap<String, Object>();
		String Date = DateTermUtil.getNowDate();
		List<Curriculum> clist = myClassRoomMapper.selectLiveCurriculum(Date);
		map.put("date", clist);
		return map;
	}

	// 根据教室id，查教室下录播机ip
	public Map<String, Object> selectIpByArea(String areaid) {
		return myClassRoomMapper.selectIpByArea(areaid);
	}
	// 根据课表id，查录播机mac，ip等信息
		public Map<String, Object> selectIpByCurriculumid(String curriculumid) {
			return myClassRoomMapper.selectIpByCurriculumid(curriculumid);
		} 
	public Map<String, Object> findHotList(String type,Pagebar page) throws Exception{
		Map<String, Object> map = getCache("hotList");
		if( map != null){
			return map;
		}else{
			map = new HashMap<String, Object>();
		}
		List<Map<String,Object>> listCurriculum = myClassRoomMapper.findHotList(type,page);
		map.put("data", listCurriculum);
		map.put("isSuccess", "1");
		setCache("hotList", map);
		return map;
	}
	/**
	 * @Title:findLiveCurriculum
	 * @Description: 查找直播列表
	 * 
	 * @author niuxl
	 * @date 2014年9月11日 下午2:45:30
	 * @return
	 */
	public Map<String, Object> findLiveCurriculum(String deptid,String loginname,Pagebar page) throws Exception{
		long total = myClassRoomMapper.findLiveCount(deptid,loginname,page);
		List<Map<String,Object>> listCurriculum = myClassRoomMapper.findLiveCurriculum(deptid,loginname,page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("data", listCurriculum);
		map.put("isSuccess", "1");
		return map;
	}
	/**
	 * @jeawin 查询my直播课
	 * @param type
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> findMyLiveCurriculum(PageBean page){
		long total = myClassRoomMapper.findMyLiveCount(page);
		List<Map<String,Object>> listCurriculum = myClassRoomMapper.findMyLiveCurriculum(page);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("data", listCurriculum);
		return map;
	}
	public Map<String, Object> findPublish(String deptid,String loginname,Pagebar page) throws Exception{
		String execName = "execList_"+page.getPage()+"_"+loginname;
		Map<String, Object> map = getCache(execName);
		if( map != null){
			return map;
		}else{
			map = new HashMap<String, Object>();
		}
		List<Map<String,Object>> list = myClassRoomMapper.findPublish(deptid,loginname,page);
		long total = myClassRoomMapper.findPublishCount(deptid,loginname,page);
		map.put("data", list);
		map.put("total", total);
		map.put("isSuccess", "1");
		setCache("execList", map);
		return map;
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
					System.out.println("资源上传完开始转码，文件不存在");
//					continue;
				}else{
					System.out.println("资源上传完开始转码，文件存在");
				}
				String result = CommonUtil.transCode(serverService.getServer(AppConstants.TYPE_SERVER_TRANS,AppConstants.TRANS_URL),tr);
				if (result != null && !"".equals(result)) {
					TransResult re = JsonUtil.jsonToObject(result, TransResult.class);
					if ("0".equals(re.getResponse_code())) {
						ResourceView view = new ResourceView();
						view.setId(tr.getFile_id());
						String tp = tr.getTrans_path();
						view.setTransPath(tp.substring(tp.indexOf("trans/",0),tp.length())+tr.getRes_name()+".mp4");
//						view.setTransFlag("1");
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
	
	private class wistransThread extends Thread {
		private List<Trans> trans;

		public wistransThread(List<Trans> trans) {
			this.trans = trans;
		}

		@Override
		public void run() {
			for (Trans tr : trans) {
				File f = new File(tr.getReal_path());
				if(!f.exists()){
					System.out.println("智慧教室上传完开始转码，文件不存在");
//					continue;
				}else{
					System.out.println("智慧教室上传完开始转码，文件存在");
				}
				String result = CommonUtil.transCode(serverService.getServer(AppConstants.TYPE_SERVER_TRANS,AppConstants.TRANS_URL),tr);
				if (result != null && !"".equals(result)) {
					TransResult re = JsonUtil.jsonToObject(result, TransResult.class);
					if ("0".equals(re.getResponse_code())) {
						Wisclassroom view = new Wisclassroom();
						view.setId(tr.getFile_id());
						String tp = tr.getTrans_path();
						view.setTransPath(tp.substring(tp.indexOf("trans/",0),tp.length())+tr.getRes_name()+".mp4");
//						view.setTransFlag("1");
						String re1 = CommonUtil.tranResult(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),tr);
						if(re1 != null && !"".equals(re1)){
							TransResult re2 = JsonUtil.jsonToObject(re1, TransResult.class);
							view.setTransFlag(re2.getContent().get(0).get("status").toString());
						}else{
							view.setTransFlag("2");
						}
						int r = wisclassroomMapper.wistransUpdate(view);
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
    public JsonMsg previewTrans(Wisclassroom view){
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
			trans.setReal_path(AppConstants.upload_PATH+view.getFileurl());
			System.out.println(trans.getTrans_path()+"-----------------------------------"+trans.getRes_path());
			//查询转码
    		String re1 = CommonUtil.tranResult(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),trans);
    		if(re1 != null && !"".equals(re1)){
    			TransResult re = JsonUtil.jsonToObject(re1, TransResult.class);
//    			ResourceView viewc = reDao.findReByid(view.getId());
    			Map<String, Object> resmap =wisclassroomMapper.selectById(view.getId());
    			if("0".equals(re.getResponse_code())){
    				//0表示转码未完成，1表示转码完成 2表示转码失败 3 表示转码排队
        			if("0".equals(re.getContent().get(0).get("status").toString()) || "3".equals(re.getContent().get(0).get("status").toString())){
        				msg.setId("-2");
        				msg.setContent("正在转码，请稍后...");
        			}else if("2".equals(resmap.get("transFlag").toString()) ||"2".equals(re.getContent().get(0).get("status").toString())){
        				msg.setId("-3");
        				msg.setContent("不好意思，转码失败了，请重试！");
        				//重新开启转码
        				this.transService(trans);
        			}else if("1".equals(re.getContent().get(0).get("status").toString())){
        				String tp = trans.getTrans_path();
        				tp = ReadProperties.getIp()+tp.substring(tp.indexOf("trans",0),tp.length())+trans.getRes_name()+".mp4";
        				Wisclassroom viewn = new Wisclassroom();
        				viewn.setId(view.getId());
        				//viewn.setTransPath(transPath);
        				viewn.setTransFlag("1");
        				wisclassroomMapper.wistransUpdate(viewn);
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
    //智慧教室二次转码服务
    public String transService(Trans tr){
    	File f = new File(tr.getReal_path());
		if(!f.exists()){
			System.out.println("智慧教室点击转码，文件不存在");
//			continue;
		}else{
			System.out.println("智慧教室点击转码，文件存在");
		}
    	String result = CommonUtil.transCode(serverService.getServer(AppConstants.TYPE_SERVER_TRANS, AppConstants.TRANS_URL),tr);
		if (result != null && !"".equals(result)) {
			TransResult re = JsonUtil.jsonToObject(result, TransResult.class);
			if ("0".equals(re.getResponse_code())) {
				Wisclassroom view = new Wisclassroom();
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
				int r = wisclassroomMapper.wistransUpdate(view);
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
}
