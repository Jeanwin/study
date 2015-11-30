package com.zonekey.study.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springside.modules.web.MediaTypes;

import com.zonekey.study.common.JsonMsg;
import com.zonekey.study.common.JsonUtil;
import com.zonekey.study.common.RegexUtils;
import com.zonekey.study.entity.PageBean;
import com.zonekey.study.entity.SysUser;
import com.zonekey.study.service.SysUserService;
import com.zonekey.study.service.auth.ShiroDbRealm;
import com.zonekey.study.vo.SysUserView;

/**
 *  
 * 
 * @className:SysUserController.java
 * @classDescription:
 * @author:JeanwinHuang@live.com
 * @createTime:2015年3月20日
 */
@Controller
@RequestMapping(value = "/user")
public class SysUserController {

	private static final Logger LOG = LoggerFactory.getLogger(SysUserController.class);

	@Autowired
	private SysUserService userService;
	@Resource
	private ShiroDbRealm realm;

	/**
	 * 修改用戶个人信息
	 * 
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/modifyPersonalInfo", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg modifyPersonalInfo(SysUser user, MultipartHttpServletRequest req) {
		return userService.modifyPersonalInfo(user, req);
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/modifyUser", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg modifyPersonalInfo(@RequestBody SysUser user) {
		JsonMsg msg = new JsonMsg();
		msg.setOperation("修改用户信息，无图上传");
		if (user.getId() == null || "".equals(user.getId())) {
			msg.setId("1");
			msg.setContent("用户id为空");
		} else if (userService.getByLoginname(user.getLoginname()) == null) {
			msg.setId("2");
			msg.setContent("未找到该用户信息");
		} else if (user.getPhone() != null && !"".equals(user.getPhone()) && !RegexUtils.isPhone(user.getPhone()) && !RegexUtils.isMobile(user.getPhone())) {
			msg.setId("3");
			msg.setContent("电话号码格式不正确");
		} else if (user.getEmail() != null && !"".equals(user.getEmail()) && !RegexUtils.isEmail(user.getEmail())) {
			msg.setId("4");
			msg.setContent("邮箱格式不正确");
		} else {
			int code = userService.updateUser(user);
			if (code > 0) {
				msg.setId("5");
				msg.setContent("用户信息修改成功");
			} else {
				msg.setId("6");
				msg.setContent("用户信息修改失败，请重试");
			}
		}
		return msg;
	}

	/**
	 * 修改用户密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg modifyPassword(@RequestBody SysUserView user) {
		LOG.info("start 修改密码");
		return userService.modifyPassword(user);
	}

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findAllUsers", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<SysUser> findAllUsers(HttpServletRequest req) {
		SysUser user = JsonUtil.jsonToObject(req, SysUser.class);
		return userService.getAllUsers(user);
	}

	/**
	 * 根据部门查找用户
	 * 
	 * @param deptid
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value = "/findUsersByDeptIds", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<SysUser> findByDeptId(HttpServletRequest req) {
		Map<String, List<Map<String, String>>> deptsMap = (Map<String, List<Map<String, String>>>) JsonUtil.jsonToObject(req, Map.class);
		List<Map<String, String>> depts = (List<Map<String, String>>) deptsMap.get("depts");
		return userService.getUsersByDeptIds(depts);
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getUser", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	SysUser getUser() {
		return realm.getCurrentUser();
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllUser", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	List<Map<String, Object>> getAllUser(HttpServletRequest req) {
		Map<String, Object> map = JsonUtil.jsonToObject(req, Map.class);
		return userService.getAllUser(map);
	}

	/**
	 * 验证手机号是否重复
	 * 
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validatePhone", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg validatePhone(HttpServletRequest req) {
		Map<String, String> phoneMap = JsonUtil.jsonToObject(req, Map.class);
		String phone = phoneMap.get("phone");
		System.out.println(phone);
		return userService.validatePhone(phone);
	}

	/**
	 * 验证邮箱是否重复
	 * 
	 * @param phone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/validateEmail", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg validateEmail(HttpServletRequest req) {
		Map<String, String> emailMap = JsonUtil.jsonToObject(req, Map.class);
		String email = emailMap.get("email");
		System.out.println(email);
		return userService.validateEmail(email);
	}

	/**
	 * ajax验证密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/validatePassword", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody
	JsonMsg validatePassword(@RequestBody SysUser user) {
		return userService.validatePassword(user);
	}
    /**
     * 新建活动》选择专家》根据部门id查询用户
     * @param req
     * @return
     */
	@RequestMapping(value = "/getByDept", method = RequestMethod.POST, produces = MediaTypes.JSON_UTF_8)
	public @ResponseBody Map<String, Object> findUsersByDeptIdPage(HttpServletRequest req) {
       PageBean pb = JsonUtil.jsonToPage(req);
       return userService.findUsersByDeptPage(pb);
	}

}
