package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.Wisclassroom;

@MyBatisRepository
public interface WisclassroomMapper extends BaseMapper<Wisclassroom, String>{
	/**
	 * 根据文件夹名字查一下是否存在这个文件夹 
	 * @param map
	 * @return
	 */
	public Map<String,Object> selectFloderByName(@Param("name")String name);
	/**
	 * 根据id查询
	 * @param map
	 * @return
	 */
	public Map<String,Object> selectById(@Param("id")String id);
	/**
	 * 根据id查询
	 * @param map
	 * @return
	 */
	public Map<String,Object> selectByResourceId(@Param("id")String id);
	
	
	
	/**
	 * 根据文件夹名字查一下是否存在这个文件夹 
	 * @param map
	 * @return
	 */
	public Map<String,Object> selectFloderByUuid(@Param("uuid")String uuid);
	/**
	 * 新增文件夹
	 * @param map
	 * @return
	 */
	public int insertintowisclass(Map<String,Object> map);
	/**
	 * 新增文件
	 * @param map
	 * @return
	 */
	public int insertFileIntowisclass(Map<String,Object> map);
	/**
	 * 新增文件返回主键
	 * @param map
	 * @return
	 */
	public int insertFileIntowisclassreturnP(Wisclassroom wisclassroom);
	
	/**
	 * 查找智慧教室文件夹
	 * @param map
	 * @return
	 */
	public List<Wisclassroom> selectFloderById(@Param("curriculumid")String curriculumid,@Param("createuser")String createuser);
	/**
	 * 查找智慧教室文件
	 * @param map
	 * @return
	 */
	public List<Wisclassroom> selectFilesById(Map<String,Object> map);
	/**
	 * 轉碼后更新
	 * @param wisclassroom
	 * @return
	 */
	public int wistransUpdate(Wisclassroom wisclassroom);
	
	/**
	 * 设置可见性
	 * @param resourcevisable
	 * @param id
	 * @return
	 */
	public int setafterclassvisibility(@Param("resourcevisable")String resourcevisable,@Param("id")String id);
}
