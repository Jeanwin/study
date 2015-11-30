package com.zonekey.study.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.ReviewDetail;
import com.zonekey.study.vo.ReviewDetailView;

@MyBatisRepository
public interface ReviewDetailMapper extends BaseMapper<ReviewDetail, Integer> {
	// 批量插入数据(自动分配专家)
	public int insertDetails(@Param("detailList") List<ReviewDetailView> userList);

	// 根据活动id删除专家分配记录
	public int deleteByActiveId(@Param("activeId") int activeId);

	// 根据作品worksid删除分配记录
	public int deleteByWorksId(@Param("worksids") List<Integer> worksids);

	// 根据作品id查询评审专家列表
	public List<ReviewDetailView> findByWorksId(@Param("worksid") int worksid);

	// 批量分配专家
	// public int assignSpecialist();
	// 根据作品id查询作品评审详情
	public List<ReviewDetailView> findDetailByWorkId(@Param("workid") int workid);

	// 查询本次活动的所有评审专家
	public List<Map<String, Object>> findSpecialistByActiveId(@Param("aid") int activeid);

	// 根据专家登录名和活动id查询评审情况
	public int countReview(@Param("acid") int activeid, @Param("userid") String loginname, @Param("isover") String isover);

	public ReviewDetailView findReviewByUser(@Param("woid") int workid);
    //分配专家验证
	public String checkIsOver(@Param("worid")Integer workid,@Param("speid") String speciaer);
    //根据作品id和专家列表删除评审记录
	public void delByWorkIdAndSpe(@Param("wid") Integer workid, @Param("slist") List<String> speRemove);
}
