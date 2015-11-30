package com.zonekey.study.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.vo.ResourceView;
import com.zonekey.study.vo.Tree;
import com.zonekey.study.vo.UpdateShareView;

public interface ResourceMapper extends BaseMapper<T, Serializable> {

	public int delete(String id);

	public List<Map<String, Object>> floderList(PageBean bean);

	public int modify(Map<String, Object> map);

	public int modifyFloder(Map<String, Object> map);

	public int addFolder(Map<String, Object> map);

	public int add(Map<String, Object> map);

	public int addResource(ResourceView res);

	public int deleteResourceByAuthor(ResourceView res);

	public List<Map<String, Object>> findById(String id);

	public ResourceView findResourceById(String id);

	public int shareResource(@Param("resourceList") List<Map<String, Object>> resourceList, @Param("list") List<Map<String, Object>> list, @Param("loginname") String loginname);

	public List<Tree> getTree();

	public List<Tree> getTrees();

	public List<Tree> getVideoTrees();

	public long count(PageBean bean);

	// 获取通缉目录下重名数
	public long comCount(Map<String, Object> map);

	// 查找最新视频(8 rows)
	public List<Map<String, Object>> getLatestVideos();

	//
	public Map<String, Object> selectbyid(@Param("parentid") String parentid);

	// 查找分享列表
	public List<Map<String, Object>> shareList(PageBean bean);

	// 统计分享列表记录数
	public long countShareList(PageBean bean);

	// 查找我的分享列表
	public List<Map<String, Object>> myShareList(PageBean bean);

	// 统计我的分享列表记录数
	public long countMyShareList(PageBean bean);

	// 删除验证，查询文件个数
	public long deleteCheck(@Param("fileUrl") String fileUrl);

	// 取消分享
	public int cancelShare(@Param("shareList") List<String> shareList);

	// 从我的分享收藏
	public int insertAll(@Param("storelist") List<ResourceView> storeList);

	// 从我的分享中取消收藏
	public int deleteAll(@Param("storeListDel") List<String> storeListDel);

	// 根据父id查找所有文件
	// public Resource getResourcesByParentId(Resource resource);
	// 根据id查找
	public ResourceView findReById(String id);
	
	/**
	 * 根据floder id 去修改点击数
	 * @param view
	 * @return
	 */
	public int updateHitCount(@Param("floderid")String floderid);

	// 更新分享表中的收藏标志及收藏id
	public int updateShare(Map<String,Object> view);
	public ResourceView findReByUuid(@Param("resource_uuid")String resource_uuid);
	public ResourceView findReByid(@Param("id")String id);
	public ResourceView findReByStoreid(@Param("storeid")String storeid);
	
	//添加资源
	public int addResourceReturnP(ResourceView view);
	//根据floder查一条资源信息
	public List<ResourceView> selectResourceByFloder (@Param("floder")String floder);
	//转码后更新
	public int transUpdate(ResourceView view);
	//视频加工
	public int getPrimaryKey();
	public int addByPrimary(ResourceView view);
	//视频合成，根据id删除资源
	public int deleteById(@Param("rid") String id);
}
