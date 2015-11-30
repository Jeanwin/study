package com.zonekey.study.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isMAC(String mac) {
		String str = "^[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}[A-F\\d]{2}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(mac);
		return m.matches();
	}

	public static boolean isIP(String ip) {
		String str = "^\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(ip);
		return m.matches();
	}

	/**
	 * 验证用户密码："^[a-zA-Z0-9]{6,16}$"正确格式为：必须由非空的大小写字母、数字或下划线组成，长度6至16位。
	 * 
	 * @param password
	 * @return
	 */
	// 验证用户密码："^[a-zA-Z0-9]{6,16}$"正确格式为：以字母开头，长度在6~18之间，只能包含字符、数字和下划线。
    public static boolean isPassword(String password){
        String str = "^[a-zA-Z0-9]{6,16}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

	/**
	 * 手机号验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isMobile(String str) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
		m = p.matcher(str);
		b = m.matches();
		return b;
	}

	/**
	 * 电话号码验证
	 * 
	 * @param str
	 * @return 验证通过返回true
	 */
	public static boolean isPhone(String str) {
		Pattern p1 = null, p2 = null;
		Matcher m = null;
		boolean b = false;
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$"); // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$"); // 验证没有区号的
		if (str.length() > 9) {
			m = p1.matcher(str);
			b = m.matches();
		} else {
			m = p2.matcher(str);
			b = m.matches();
		}
		return b;
	}
}
