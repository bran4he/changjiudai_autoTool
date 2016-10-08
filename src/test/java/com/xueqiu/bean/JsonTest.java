package com.xueqiu.bean;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest {

	
	public static void main(String[] args) throws Exception {
		InputStream is = JsonTest.class.getResourceAsStream("test.json");
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line = br.readLine()) != null){
			sb.append(line);
		}
		System.out.println("the json is :\n" + sb.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		ScriptBean bean = mapper.readValue(sb.toString(), ScriptBean.class);
		
		System.out.println(bean);
		String url = bean.getStatuses()[0].getUser().getProfile_image_url();
		System.out.println(url);
		String[] urls = url.split(",");
		for(String u: urls){
			System.out.println(u);
		}
		
	}
	
	public static void simpleTest() throws Exception {
		InputStream is = JsonTest.class.getResourceAsStream("test.json");
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String line = null;
		StringBuffer sb = new StringBuffer();
		while((line = br.readLine()) != null){
			sb.append(line);
		}
		System.out.println("the json is :\n" + sb.toString());
		
		ObjectMapper mapper = new ObjectMapper();
		Bean2 bean = mapper.readValue(sb.toString(), Bean2.class);
		
		System.out.println(bean);
		System.out.println(bean.getCount());
	}
	
	
	
}
