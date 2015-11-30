package com.zonekey.study.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zonekey.study.dao.base.BaseMapper;
import com.zonekey.study.dao.base.MyBatisRepository;
import com.zonekey.study.entity.ReviewUser;
import com.zonekey.study.vo.ReviewUserView;

@MyBatisRepository
public interface ReviewUserMapper extends BaseMapper<ReviewUser, Integer> {
	// 根据活动id查询所有专家
	public List<ReviewUserView> findUserList(@Param("activeId") int activeid);

	// 批量插入数据
	public int insertUsers(@Param("userList") List<ReviewUserView> userList);

	// 添加专家验证
	public int checkExist(@Param("acid") int activeid, @Param("userid") String userid);

	// 删除专家
	public int deleteUsers(@Param("usersDel") List<ReviewUserView> usersDel);

	// 根据活动id删除专家
	public int deleteByActiveid(@Param("actid") int activeid);

	// 删除专家验证
	public int deleteCheck(@Param("aid") int activeid, @Param("uid") String userid);

	public int checkDel(@Param("userId") String userId, @Param("activeId") Integer activeId);

	public int delByActiveAndUser(@Param("aeid") Integer activeId, @Param("users") List<String> users);
}
