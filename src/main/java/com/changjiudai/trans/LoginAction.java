package com.changjiudai.trans;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class LoginAction {

	public static void main(String[] args) throws Exception {
		// CloseableHttpClientTest();
		ClientFormLogin();
	}

	public static void CloseableHttpClientTest() throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://www.baidu.com");
		CloseableHttpResponse response1 = httpclient.execute(httpGet);
		// The underlying HTTP connection is still held by the response object
		// to allow the response content to be streamed directly from the
		// network socket.
		// In order to ensure correct deallocation of system resources
		// the user MUST call CloseableHttpResponse#close() from a finally
		// clause.
		// Please note that if response content is not fully consumed the
		// underlying
		// connection cannot be safely re-used and will be shut down and
		// discarded
		// by the connection manager.
		try {
			System.out.println(response1.getStatusLine());
			HttpEntity entity1 = response1.getEntity();
			Header[] headers = response1.getAllHeaders();
			for (Header header : headers) {
				System.out.println(header.getName() + "\t" + header.getValue());
			}
			// do something useful with the response body
			// and ensure it is fully consumed
			InputStream ins = entity1.getContent();
			BufferedInputStream bis = new BufferedInputStream(ins);
			StringBuffer sb = new StringBuffer();
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = bis.read(buff)) != -1) {
				sb.append(new String(buff, 0, len));
			}
			System.out.println(sb.toString());
			EntityUtils.consume(entity1);
		} finally {
			response1.close();
		}

		// HttpPost httpPost = new HttpPost("http://targethost/login");
		// List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		// nvps.add(new BasicNameValuePair("username", "vip"));
		// nvps.add(new BasicNameValuePair("password", "secret"));
		// httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		// CloseableHttpResponse response2 = httpclient.execute(httpPost);
		//
		// try {
		// System.out.println(response2.getStatusLine());
		// HttpEntity entity2 = response2.getEntity();
		// // do something useful with the response body
		// // and ensure it is fully consumed
		// EntityUtils.consume(entity2);
		// } finally {
		// response2.close();
		// }
	}

	
	public static String getImgCodeUrl() throws Exception {
		
		String fileName = "";
		
		List<Cookie> cookielst = new ArrayList<Cookie>();
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		try {
			System.out.println("==============get img code start");
			HttpGet httpget = new HttpGet("http://www.changjiudai.com//plugins/index.php?q=imgcode&height=25");
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();

				HttpEntity entity1 = response1.getEntity();
				Header[] headers = response1.getAllHeaders();
				
				logHeaders(headers);
				
				InputStream ins = entity1.getContent();
				
//				LoginAction l = new LoginAction();
//				System.out.println(LoginAction.class.getResource("").getPath());
//				System.out.println(LoginAction.class.getResource("/").getPath());
				
				String temp = LoginAction.class.getResource("/").getPath();
				
				String path = temp.substring(0, temp.indexOf("WEB-INF"));
				System.out.println("path:" + path);
//				System.out.println(l.getClass().getResource("").getPath());
//				System.out.println(l.getClass().getResource("").getFile().toString());
//				System.out.println(l.getClass().getResource("../").getFile().toString());
				
				
				BufferedInputStream bis = new BufferedInputStream(ins);
				fileName = "changjiudai_" + System.currentTimeMillis() + ".gif";
				FileOutputStream fileOut = new FileOutputStream(path + fileName);
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = bis.read(buff)) != -1) {
					fileOut.write(buff, 0, len);
				}
				fileOut.close();
				bis.close();
				
				System.out.println("get img code status: " + response1.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());
					}
				}
			} finally {
				response1.close();
			}
			
		} finally {
//			httpclient.close();
		}
		
		return fileName;
	}
	
	public static CookieStore ClientLoginReturnCookieStore() throws Exception {

		List<Cookie> cookielst = new ArrayList<Cookie>();
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		System.out.println("httpclient to login: ========" + httpclient);
		
		try {
			
			System.out.println("==============get img code start");
			HttpGet httpget = new HttpGet("http://www.changjiudai.com//plugins/index.php?q=imgcode&height=25");
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();

				HttpEntity entity1 = response1.getEntity();
				Header[] headers = response1.getAllHeaders();
				
				logHeaders(headers);
				
				// do something useful with the response body
				// and ensure it is fully consumed
				InputStream ins = entity1.getContent();
				BufferedInputStream bis = new BufferedInputStream(ins);				
				FileOutputStream fileOut = new FileOutputStream("changjiudai_" + System.currentTimeMillis() + ".gif");
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = bis.read(buff)) != -1) {
					fileOut.write(buff, 0, len);
				}
				fileOut.close();
				bis.close();
				
				System.out.println("get img code status: " + response1.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());
					}
				}
			} finally {
				response1.close();
			}

			
			Scanner scan = new Scanner(System.in);
			String code = scan.nextLine();
			
			System.out.println("==============login start");
			
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("http://www.changjiudai.com/index.php?user&q=action/login"))
					.addParameter("keywords", "")
					.addParameter("password", "")
					.addParameter("valicode", code)
					.build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			try {
				HttpEntity entity = response2.getEntity();

				InputStream ins = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(ins);
				StringBuffer sb = new StringBuffer();
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = bis.read(buff)) != -1) {
					sb.append(new String(buff, 0, len));
				}
				
				if(!checkLogin(sb.toString())) {
					return null;
				}
				
				logHeaders(response2.getAllHeaders());
				
				System.out.println("login get status: " + response2.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Post login cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				cookielst = cookieStore.getCookies();
				
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());	//cookies.get(i).toString()
					}
				}
				
			} finally {
				response2.close();
			}
			
		} finally {
			httpclient.close();
		}
		return cookieStore;
	}
	
	
	public static List<Cookie> ClientFormLogin() throws Exception {

		List<Cookie> cookielst = new ArrayList<Cookie>();
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		try {
			
			System.out.println("==============get img code start");
			HttpGet httpget = new HttpGet("http://www.changjiudai.com//plugins/index.php?q=imgcode&height=25");
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();

				HttpEntity entity1 = response1.getEntity();
				Header[] headers = response1.getAllHeaders();
				
				logHeaders(headers);
				
				// do something useful with the response body
				// and ensure it is fully consumed
				InputStream ins = entity1.getContent();
				BufferedInputStream bis = new BufferedInputStream(ins);				
				FileOutputStream fileOut = new FileOutputStream("changjiudai_" + System.currentTimeMillis() + ".gif");
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = bis.read(buff)) != -1) {
					fileOut.write(buff, 0, len);
				}
				fileOut.close();
				bis.close();
				
				System.out.println("get img code status: " + response1.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());
					}
				}
			} finally {
				response1.close();
			}

			
			Scanner scan = new Scanner(System.in);
			String code = scan.nextLine();
			
			System.out.println("==============login start");
			
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("http://www.changjiudai.com/index.php?user&q=action/login"))
					.addParameter("keywords", "")
					.addParameter("password", "")
					.addParameter("valicode", code)
					.build();
			CloseableHttpResponse response2 = httpclient.execute(login);
			try {
				HttpEntity entity = response2.getEntity();

				InputStream ins = entity.getContent();
				BufferedInputStream bis = new BufferedInputStream(ins);
				StringBuffer sb = new StringBuffer();
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = bis.read(buff)) != -1) {
					sb.append(new String(buff, 0, len));
				}
				
				if(!checkLogin(sb.toString())) {
					return null;
				}
				
				logHeaders(response2.getAllHeaders());
				
				System.out.println("login get status: " + response2.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Post login cookies:");
				List<Cookie> cookies = cookieStore.getCookies();
				cookielst = cookieStore.getCookies();
				
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());	//cookies.get(i).toString()
					}
				}
				

				
			} finally {
				response2.close();
			}
			
			
			System.out.println("==============sign daily");
			HttpPost httpPost = new HttpPost("http://www.changjiudai.com/index.php?user&q=code/credit/registertime");
			CloseableHttpResponse response3 = httpclient.execute(httpPost);
			try {
				System.out.println("sign get status: " + response3.getStatusLine());
				HttpEntity entity1 = response3.getEntity();
				
				logHeaders(response3.getAllHeaders());
				
				InputStream ins = entity1.getContent();
				BufferedInputStream bis = new BufferedInputStream(ins);
				StringBuffer sb = new StringBuffer();
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = bis.read(buff)) != -1) {
					sb.append(new String(buff, 0, len));
				}
				
				System.out.println("sign response: \t" + sb.toString());
				
				EntityUtils.consume(entity1);
			} finally {
				response3.close();
			}
			
		} finally {
			httpclient.close();
		}
		
		return cookielst;
	}
	
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
}
