package com.zonekey.study.common;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
/**
 * 用于远程访问数据
 * @author gly
 *
 */
public class HttpSend {
	private static String send(HttpResponse response){
		String result = "";
		BufferedReader rd = null;
		try {
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				rd = new BufferedReader(
						new InputStreamReader(response.getEntity()
								.getContent(), "utf-8"));
				String line = "";
				while ((line = rd.readLine()) != null) {
					result += line;
				}
				return result;
			}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				try {
					if (rd != null) {
						rd.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		return null;
	}
	/**
	 * post方式远程获取数据
	 * @param url
	 * @param param
	 * @return
	 */
	public static String post(String url,Object param) {
		HttpPost post = new HttpPost(url);
		post.setHeader("Accept-Encoding", "utf-8");
		post.setHeader("Accept-Language", "zh-CN");
		post.setHeader("Accept",
				"application/json, application/xml, text/html, text/*, image/*, */*");
		try {
			String json = null;
			if(param instanceof String){
				json = (String) param;
			}else{
				json = JsonUtil.toJson(param);
			}
			post.setEntity(new StringEntity(json));
			HttpResponse response = new DefaultHttpClient().execute(post);
			return send(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * get方式远程获取数据
	 * @param url
	 * @param param
	 * @return
	 */
	public static String get(String url) {
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse response = new DefaultHttpClient().execute(get);
			return send(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * put方式远程获取数据
	 * @param url
	 * @param param
	 * @return
	 */
	public static String put(String url,Object param) {
		HttpPut put = new HttpPut(url);
		try {
			put.setEntity(new StringEntity(JsonUtil.toJson(param)));
			HttpResponse response = new DefaultHttpClient().execute(put);
			return send(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map<String,String>  login(HttpServletRequest req, HttpServletResponse res,String username,String password) throws Exception{
		String url = ReadProperties.getByName("login.ip")+"/login";
		Map<String, String> datas=new HashMap<String, String>();
		Map<String, String> cookies=new HashMap<String, String>();
		
		Connection con=Jsoup.connect(url).timeout(120000);//获取连接
		con.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");//配置模拟浏览器
		Response rs ;
		rs = con.execute();
		cookies = rs.cookies();
		Document doc=Jsoup.parse(rs.body());//转换为Dom树
		List<Element> et= doc.select("form");//获取form表单，可以通过查看页面源码代码得知
		for(Element e:et.get(0).getAllElements()){
		   if(e.attr("name").equals("username")){
			   e.attr("value", username);//设置用户名
		   }
		   if(e.attr("name").equals("password")){
			   e.attr("value",password); //设置用户密码
		   }
		   if(e.attr("name").length()>0){//排除空值表单属性
			   datas.put(e.attr("name"), e.attr("value"));  
		   }
		 }
		 //设置cookie和post上面的map数据
		 Response login = null;
		login = con.data(datas).cookies(cookies).method(Method.POST).execute();
		
		url = ReadProperties.getByName("common.ip")+req.getContextPath()+"/user/getUser";
		con=Jsoup.connect(url).cookies(login.cookies()).ignoreContentType(true).method(Method.GET);//获取连接
		rs  = con.execute();
		for (Entry<String, String> entry: rs.cookies().entrySet()) {
			Cookie cookie = new Cookie(entry.getKey(), entry.getValue());
			cookie.setPath(req.getContextPath()+"/");
			res.addCookie(cookie);
		}
		 return JsonUtil.jsonToObject(rs.body(), Map.class);
		}
}
