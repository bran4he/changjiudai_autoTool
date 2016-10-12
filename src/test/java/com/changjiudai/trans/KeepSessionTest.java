package com.changjiudai.trans;

import static org.junit.Assert.fail;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;

public class KeepSessionTest {

	private static CookieStore cookieStore = null;
	
	@Before
	public void setUp() throws Exception {
		System.out.println("start setUp............");
		cookieStore = LoginAction.ClientLoginReturnCookieStore();
	}

	@Test
	public void testKeepSeesionWithCookie() throws Exception {
		System.out.println("==============sign daily");
		
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		System.out.println("httpclient to sign: ========" + httpclient);
		HttpPost httpPost = new HttpPost("http://www.changjiudai.com/index.php?user&q=code/credit/registertime");
		CloseableHttpResponse response3 = httpclient.execute(httpPost);
		try {
			System.out.println("sign get status: " + response3.getStatusLine());
			HttpEntity entity1 = response3.getEntity();
			
			LoginAction.logHeaders(response3.getAllHeaders());
			
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
			httpclient.close();
			
		}
	}

}
