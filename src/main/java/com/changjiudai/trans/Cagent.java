package com.changjiudai.trans;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.changjiudai.util.CommonUtil;

public class Cagent {

	private static final String REQ = "http://www.changjiudai.com/index.php?user&q=code/borrow/gathering&status=0&page=";
	
	private static int totalPage = 0;
	
	private CookieStore cookieStore = null;
	
	private String imgCodePath = "";
	private String reportPath = "";
	
	public void getImgCodeUrl() throws Exception {
		
		this.cookieStore = new BasicCookieStore();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.cookieStore).build();
		try {
			System.out.println("==============get img code start");
			HttpGet httpget = new HttpGet("http://www.changjiudai.com//plugins/index.php?q=imgcode&height=25");
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();

				Header[] headers = response.getAllHeaders();
				
				CommonUtil.logHeaders(headers);
				
				InputStream ins = entity.getContent();
				
				String path = CommonUtil.getProjectPath();
				
				BufferedInputStream bis = new BufferedInputStream(ins);
				this.imgCodePath = "changjiudai_" + System.currentTimeMillis() + ".gif";
				FileOutputStream fileOut = new FileOutputStream(path + this.imgCodePath);
				byte[] buff = new byte[1024];
				int len = -1;
				while ((len = bis.read(buff)) != -1) {
					fileOut.write(buff, 0, len);
				}
				fileOut.close();
				bis.close();
				
				System.out.println("get img code status: " + response.getStatusLine());
				EntityUtils.consume(entity);

				System.out.println("Initial set of cookies:");
				List<Cookie> cookies = this.cookieStore.getCookies();
				if (cookies.isEmpty()) {
					System.out.println("None");
				} else {
					for (int i = 0; i < cookies.size(); i++) {
						System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());
					}
				}
			} finally {
				response.close();
			}
			
		} finally {
			httpclient.close();
		}
		
	}

	
	public boolean login(String username, String password, String code) throws IOException, URISyntaxException{
		System.out.println("==============login start");
		
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.cookieStore).build();
		
		HttpUriRequest login = RequestBuilder.post()
				.setUri(new URI("http://www.changjiudai.com/index.php?user&q=action/login"))
				.addParameter("keywords", username)
				.addParameter("password", password)
				.addParameter("valicode", code)
				.build();
		CloseableHttpResponse response = httpclient.execute(login);
		try {
			HttpEntity entity = response.getEntity();

			InputStream ins = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(ins);
			StringBuffer sb = new StringBuffer();
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = bis.read(buff)) != -1) {
				sb.append(new String(buff, 0, len));
			}
			
			
			CommonUtil.logHeaders(response.getAllHeaders());
			
			System.out.println("login get status: " + response.getStatusLine());
			EntityUtils.consume(entity);

			System.out.println("Post login cookies:");
			List<Cookie> cookies = this.cookieStore.getCookies();
			
			if (cookies.isEmpty()) {
				System.out.println("None");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());	//cookies.get(i).toString()
				}
			}
			
			return CommonUtil.checkLogin(sb.toString());
					
		} finally {
			response.close();
			httpclient.close();
		}
	}
	
	public boolean loginAndSign(String username, String password, String code) throws IOException, URISyntaxException{
		System.out.println("==============login start");
		
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultCookieStore(this.cookieStore).build();
		
		HttpUriRequest login = RequestBuilder.post()
				.setUri(new URI("http://www.changjiudai.com/index.php?user&q=action/login"))
				.addParameter("keywords", username)
				.addParameter("password", password)
				.addParameter("valicode", code)
				.build();
		CloseableHttpResponse response = httpclient.execute(login);
		try {
			HttpEntity entity = response.getEntity();
			
			InputStream ins = entity.getContent();
			BufferedInputStream bis = new BufferedInputStream(ins);
			StringBuffer sb = new StringBuffer();
			byte[] buff = new byte[1024];
			int len = -1;
			while ((len = bis.read(buff)) != -1) {
				sb.append(new String(buff, 0, len));
			}
			
			
			CommonUtil.logHeaders(response.getAllHeaders());
			
			System.out.println("login get status: " + response.getStatusLine());
			EntityUtils.consume(entity);
			
			System.out.println("Post login cookies:");
			List<Cookie> cookies = this.cookieStore.getCookies();
			
			if (cookies.isEmpty()) {
				System.out.println("None");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					System.out.println("- " + cookies.get(i).getName() + "\t" + cookies.get(i).getValue());	//cookies.get(i).toString()
				}
			}
			
			boolean result = CommonUtil.checkLogin(sb.toString());
			
			if(result){
				System.out.println("==============sign daily");
				HttpPost httpPost = new HttpPost("http://www.changjiudai.com/index.php?user&q=code/credit/registertime");
				CloseableHttpResponse response1 = httpclient.execute(httpPost);
				try {
					System.out.println("sign get status: " + response1.getStatusLine());
					HttpEntity entity1 = response1.getEntity();
					
					CommonUtil.logHeaders(response1.getAllHeaders());
					
					BufferedInputStream bisSign = new BufferedInputStream(entity1.getContent());
					StringBuffer sbSign = new StringBuffer();
					byte[] buffSign = new byte[1024];
					int lenSign = -1;
					while ((lenSign = bisSign.read(buffSign)) != -1) {
						sbSign.append(new String(buffSign, 0, lenSign));
					}
					
					System.out.println("sign response: \t" + sbSign.toString());
					
					EntityUtils.consume(entity1);
				} finally {
					response1.close();
				}
			}
			
			return result;
			
		} finally {
			response.close();
			httpclient.close();
		}
	}
	
	public boolean exportReport() throws Exception {
		
		List<String> datelst = new ArrayList<String>();
		List<String> totallst = new ArrayList<String>();
		List<String> capitallst = new ArrayList<String>();
		List<String> interestlst = new ArrayList<String>();
		
		Map<String, String> cookies = new HashMap<String, String>();
		
		List<Cookie> cookielst = this.cookieStore.getCookies();
		
		for (int i = 0; i < cookielst.size(); i++) {
			System.out.println("- " + cookielst.get(i).getName() + "\t" + cookielst.get(i).getValue());	//cookies.get(i).toString()
			cookies.put(cookielst.get(i).getName(), cookielst.get(i).getValue());
		}
		
		Document doc = null;
		
		for(int i=0; i<=totalPage; i++){
			String url = REQ + i;
			
			doc = Jsoup.connect(url).cookies(cookies).timeout(30000).get();
			
			if(totalPage == 0){
				Elements pages = doc.select("div .userPage");
				String pageStr = pages.get(0).text();	//共12页/当前为第1页 首页 上一页 下一页 尾页
				totalPage = Integer.parseInt(pageStr.substring(1, pageStr.indexOf("页")));
				System.out.println("get total pages :" + totalPage);
			}
			
			Elements tables = doc.select(".tableInfo");
			
			for(Element table : tables){
				String date = table.select("td").get(1).attr("title"); //date 日期
				String total = table.select("td").get(4).text();	//total 共收入
				String capital = table.select("td").get(5).text();	//capital 本金
				String interest = table.select("td").get(6).text();	//interest 利息
				System.out.println(date +"\t"+ total +"\t"+ capital + "\t" +interest);
				datelst.add(date);
				totallst.add(total);
				capitallst.add(capital);
				interestlst.add(interest);
			}
		}
		return this.generateExcelReport(datelst, totallst, capitallst, interestlst);
	}
	
	private boolean generateExcelReport(List<String> datelst,List<String> totallst, List<String> capitallst, List<String> interestlst) throws Exception{
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		
		Row row = sheet.createRow((short)0);
		Cell cell1 = row.createCell(0);
		cell1.setCellValue("日期");
		Cell cell2 = row.createCell(1);
		cell2.setCellValue("总收入");
		Cell cell3 = row.createCell(2);
		cell3.setCellValue("本金");
		Cell cell4 = row.createCell(3);
		cell4.setCellValue("利息");
		
		for(int i=0; i< datelst.size(); i++){
			row = sheet.createRow((short)(i+1));
			cell1 = row.createCell(0);
			cell1.setCellValue(datelst.get(i));
			cell2 = row.createCell(1);
			cell2.setCellValue(totallst.get(i));
			cell3 = row.createCell(2);
			cell3.setCellValue(capitallst.get(i));
			cell4 = row.createCell(3);
			cell4.setCellValue(interestlst.get(i));
		}
		
		String path = CommonUtil.getProjectPath();
		String fileName = "changjiudai_" + new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss").format(new Date()) + ".xlsx";
	    FileOutputStream fileOut = new FileOutputStream(path + fileName);
	    wb.write(fileOut);
	    fileOut.close();
	    
	    this.reportPath = fileName;
	    return true;
	    
	}
	
	
	public  CookieStore getCookieStore() {
		return cookieStore;
	}

	public  void setCookieStore(CookieStore cookieStore) {
		this.cookieStore = cookieStore;
	}

	public String getImgCodePath() {
		return imgCodePath;
	}

	public void setImgCodePath(String imgCodePath) {
		this.imgCodePath = imgCodePath;
	}


	public String getReportPath() {
		return reportPath;
	}


	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}
	

	
}
