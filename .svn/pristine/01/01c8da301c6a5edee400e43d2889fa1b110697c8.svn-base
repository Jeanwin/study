package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.Comment;
import com.zonekey.study.entity.Material;
import com.zonekey.study.vo.Tree;

@MyBatisRepository
public interface MaterialMapper extends BaseMapper<Material, String> {
	//查找
	public List<Material> selectMaterialByWhere(Material material);
	//查找
	public Material selectMaterialById(String id);
	
	//修改
	public int updateMaterial(Material material);
	//允许下载素材
	public int allowDownloadMaterial(Material material);
	
	//新增
	public int addMaterial(Material material);
	//刪除
	public int deleteMaterial(String id);
	public int addMaterial(Map<String, Object> map);
	public int addMaterialOne(Map<String, Object> map);
	
}
