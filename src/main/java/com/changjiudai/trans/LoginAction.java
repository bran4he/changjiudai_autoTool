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
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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

	public static List<Cookie> ClientFormLogin() throws Exception {

		List<Cookie> cookielst = new ArrayList<Cookie>();
		
		BasicCookieStore cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		try {
			HttpGet httpget = new HttpGet("http://www.changjiudai.com//plugins/index.php?q=imgcode&height=25");
			CloseableHttpResponse response1 = httpclient.execute(httpget);
			try {
				HttpEntity entity = response1.getEntity();

				HttpEntity entity1 = response1.getEntity();
				Header[] headers = response1.getAllHeaders();
				for (Header header : headers) {
					System.out.println(header.getName() + "\t" + header.getValue());
				}
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
				
				System.out.println("Login form get: " + response1.getStatusLine());
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
			
			
			HttpUriRequest login = RequestBuilder.post()
					.setUri(new URI("http://www.changjiudai.com//index.php?user&q=action/login"))
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
				System.out.println(sb.toString());
				
				
				System.out.println("Login form get: " + response2.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Post logon cookies:");
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
		
		return cookielst;
	}
}
