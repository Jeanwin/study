package com.zonekey.study.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.dao.ReviewUserMapper;
import com.zonekey.study.entity.Dept;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.vo.ReviewUserView;

@Service
@Transactional(readOnly = true)
public class ReviewUserService {
	@Resource
	private ReviewUserMapper ruMapper;

	/**
	 * 根据活动id查询专家列表
	 * 
	 * @param activeId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ReviewUserView> ruList(int activeId) {
		if (Integer.valueOf(activeId) != null && !"".equals(Integer.valueOf(activeId))) {
			return ruMapper.findUserList(activeId);
		} else {
			return null;
		}
	}

	/**
	 * 添加专家
	 * 
	 * @param users
	 * @return
	 */
	@Transactional(readOnly = false)
	public int addReviewUsers(List<ReviewUserView> users) {
		if (users != null && users.size() > 0) {
			List<ReviewUserView> usersAdd = new ArrayList<ReviewUserView>();
			List<ReviewUserView> usersNotAdd = new ArrayList<ReviewUserView>();
			for (ReviewUserView user : users) {
				if (ruMapper.checkExist(user.getActiveId(), user.getUserid()) == 0) {
					usersAdd.add(user);
				} else {
					usersNotAdd.add(user);
				}
			}
			return ruMapper.insertUsers(usersAdd);
		} else {
			return 0;
		}
	}

	/**
	 * 删除专家
	 * 
	 * @param users
	 * @return
	 */
	@Transactional(readOnly = false)
   	public int delReviewUsers(List<ReviewUserView> users) {
   		if (users != null && users.size() > 0) {
   			List<ReviewUserView> usersDel = new ArrayList<ReviewUserView>();
			List<ReviewUserView> usersNotDel = new ArrayList<ReviewUserView>();
   			for (ReviewUserView user : users) {
				if(ruMapper.deleteCheck(user.getActiveId(),user.getUserid()) >0 ){
					usersDel.add(user);
				}else{
					usersNotDel.add(user);
				}
			}
   			return ruMapper.deleteUsers(users);
   		} else {
   			return 0;
   		}
   	}
	/**
	 * 
	 * @param userId
	 * @param activeId
	 * @return
	 */
	@Transactional(readOnly = true)
	public JsonMsg checkDel(String userId, Integer activeId) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("删除专家验证");
		if(userId != null && !"".equals(userId) && activeId != null && !"".equals(activeId)){
			int flag = ruMapper.checkDel(userId,activeId);
			System.out.println("flag;"+flag);
		    if (flag == 0) {
				msg.setId("1");
				msg.setContent("验证通过");
			}else{
				msg.setId("-2");
				msg.setContent("验证未通过");
			}
		}else{
			msg.setId("-1");
			msg.setContent("未接收到请求参数");
		}
		return msg;
	}
}
