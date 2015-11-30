package com.zonekey.study.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.Works;
import com.zonekey.study.vo.WorksView;

@MyBatisRepository
public interface WorksMapper extends BaseMapper<Works, Integer> {
	// 根据活动id查询所有作品
	public List<WorksView> findWorksByActiveId(@Param("activeId") int activeId);

	// 分页查询作品,传入活动id
	public List<WorksView> findWorksByPage(PageBean pageBean);

	public long findCount(PageBean pageBean);

	// 添加作品
	public int insertWorks(WorksView view);

	// 修该作品
	public int updateWorks(WorksView view);

	// 删除作品
	public int delWorks(@Param("worksList") List<Integer> worksIds);

	// 查询活动作品评审情况
	public List<WorksView> findWorksWithReview(PageBean pageBean);

	public long findWrCount(PageBean pageBean);
	//查询用户是否报名
    public int checkIsSigned(@Param("activeid") int activeid);
}
