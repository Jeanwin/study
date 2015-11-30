package com.zonekey.study.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zonekey.study.common.IdUtils;
import com.zonekey.study.common.MD5Utils;
import com.zonekey.study.common.RegexUtils;
import com.zonekey.study.entity.SysUser;
import com.zonekey.study.service.SysUserService;
import com.zonekey.study.service.email.TemplateEmail;

@Controller
@RequestMapping("/code")
public class CheckCodeController {
	@Resource
	private TemplateEmail templateEmail;
	@Resource
	private SysUserService sysUserService;

	/**
	 * 重设密码>>跳转到填写用户名页面
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/retrievedPwd", method = RequestMethod.GET)
	public String resetPwd(HttpServletRequest req) {
		return "retrievedPwd";
	}

	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public String sendMail(String loginname, HttpServletRequest req) {
		String validateCode = IdUtils.uuid2();
		String url = req.getRequestURL().toString();
		url = url.substring(0, url.lastIndexOf("/")) + "/checkEmail?sid=" + validateCode + "&loginname=" + loginname;

		SysUser user = sysUserService.getByLoginname(loginname);
		if (user == null) {
			req.setAttribute("msg", "用户" + loginname + "不存在,请核实");
			return "result";
		}
		String email = user.getEmail();
		if (email == null) {
			req.setAttribute("msg", "您没有设置邮箱，请联系管理员。");
			return "result";
		}
		int update = sysUserService.addEmailCode(loginname, validateCode);
		if (update == 1) {
			boolean flag = templateEmail.sendTemplateMail(loginname, url, email, "密码找回", "email.vm");
			if (flag) {
				req.setAttribute("msg", "重置密码邮件已发送您的" + email + "邮箱，请注意查收");
			} else {
				req.setAttribute("msg", "发送邮件到" + email + "失败，请重试,若多次发送失败可能是邮箱不正确,请联系管理员。");
			}
		} else {
			req.setAttribute("msg", "用户" + loginname + "不存在,请核实");
		}
		return "result";
	}

	@RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
	public String checkEmail(HttpServletRequest req, String sid, String loginname) {
		if (StringUtils.isEmpty(sid) || StringUtils.isEmpty(loginname)) {
			req.setAttribute("msg", "链接不完整,请重新生成");
			return "retrievedPwd";
		}

		SysUser user = sysUserService.getByLoginname(loginname);
		if (user != null) {
			System.out.println(user.getOutDate()+user.getValidateCode());
			Date date = (Date) user.getOutDate();
			if (date != null) {
				long time = date.getTime() + 30 * 60 * 1000;
				if (time >= System.currentTimeMillis()) {
					if (sid.equals(user.getValidateCode())) {
						req.setAttribute("loginname", loginname);
						// 用于判断是否通过邮箱链接进行的密码修改
						req.getSession().setAttribute("checked", "checked");
						return "resetPwd";
					} else {
						req.setAttribute("msg", "验证码错误,请重新找回");
						return "retrievedPwd";
					}
				} else {
					req.setAttribute("msg", "链接已失效或网络问题，请重新找回");
					return "retrievedPwd";
				}
			} else {
				req.setAttribute("msg", "非法操作,请重新找回");
				return "retrievedPwd";
			}
		} else {
			req.setAttribute("msg", "用户" + loginname + "不存在,请核实");
			return "result";
		}
	}

	/**
	 * 重置密码
	 * 
	 * @param req
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String checkEmail(HttpServletRequest req, SysUser user) {
		if (req.getSession().getAttribute("checked") != null && !"".equals(req.getSession().getAttribute("checked"))){
			if (req.getSession().getAttribute("checked").equals("checked") && RegexUtils.isPassword(user.getPassword())) {
				String password = MD5Utils.md5(user.getPassword());
				user.setPassword(password);
				if (StringUtils.isNotEmpty(user.getPassword())) {
					int update = sysUserService.updateUser(user);
					if (update >0 ) {
						req.setAttribute("msg", "密码修改成功,请用新密码<a href='" + req.getContextPath() + "/'>登录</a>");
						return "result";
					} else {
						req.setAttribute("msg", "密码修改失败");
					}
					req.getSession().removeAttribute("checked");
				}
			} else if (req.getSession().getAttribute("checked") != null && !RegexUtils.isPassword(user.getPassword())) {
				req.setAttribute("msg", "密码格式不正确，请重新输入");
			} else {
				req.setAttribute("msg", "非法链接");
			}
		}else{
			req.setAttribute("msg", "验证出错了");
		}
		 
		return "result";
	}

}