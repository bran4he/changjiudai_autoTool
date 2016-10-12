package com.changjiudai.util;

import org.apache.http.Header;

import com.changjiudai.trans.LoginAction;

public class CommonUtil {

	public static void logHeaders(Header[] headers) {
		System.out.println("----response headers----");
		for (Header header : headers) {
			System.out.println(header.getName() + "\t" + header.getValue());
		}
		System.out.println("----response headers----");
	}
	
	public static boolean checkLogin(String responseStr) {
		//验证码错误
		//用户名密码错误
		if(responseStr.indexOf("验证码错误") != -1){
			System.out.println("fail login with wrong img code!");
			return false;
		}else if(responseStr.indexOf("用户名密码错误") != -1){
			System.out.println("fail login with wrong username or password!");
			return false;
		}
		System.out.println("login successfully!");
		return true;
	}
	
	public static String getProjectPath() {
		String temp = CommonUtil.class.getResource("/").getPath(); 
		String path = temp.substring(0, temp.indexOf("WEB-INF"));
		System.out.println("path:" + path);
		//   path:/D:/dev/apache-tomcat-7.0.70/wtpwebapps/photo-capture/
		return path;
	}
}
