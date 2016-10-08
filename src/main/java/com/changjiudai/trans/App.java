package com.changjiudai.trans;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.http.cookie.Cookie;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	private static final String REQ = "http://www.changjiudai.com/index.php?user&q=code/borrow/gathering&status=0&page=";
	
//	static {
//		Properties prop = System.getProperties();
//		prop.setProperty("http.proxyHost", "shtmg.ebaotech.com");
//		prop.setProperty("http.proxyPort", "8080");
//	}
	
	private static int totalPage = 0;
	
	public static void main(String[] args) throws Exception {
		
		List<String> datelst = new ArrayList<String>();
		List<String> totallst = new ArrayList<String>();
		List<String> capitallst = new ArrayList<String>();
		List<String> interestlst = new ArrayList<String>();
		
		Map<String, String> cookies = new HashMap<String, String>();
		
		/*
		PHPSESSID=njt8223cft6mq1v92dhfod2hc5; 
		CNZZDATA5931011=cnzz_eid%3D80950273-1473815458-http%253A%252F%252Fwww.changjiudai.com%252F%26ntime%3D1473815458; 
		Hm_lvt_75e225e1cf5a4bfe98f1a1369df4174d=1473816072; 
		Hm_lpvt_75e225e1cf5a4bfe98f1a1369df4174d=1473817622
		*/
		
//		String cookieStr = TransUtil.getFileFirstLine("cookie.txt");
//		
//		String[] temp = cookieStr.split(";");
//		for(String str : temp){
//			String[] ss = str.split("=");
//			if(null != ss[0] && null != ss[1]){
//				cookies.put(ss[0], ss[1]);
//			}
//		}
		
		List<Cookie> cookielst = LoginAction.ClientFormLogin();
		
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
		generateExcelReport(datelst, totallst, capitallst, interestlst);
	}
	
	public static void generateExcelReport(List<String> datelst,List<String> totallst, List<String> capitallst, List<String> interestlst) throws Exception{
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
		
	    FileOutputStream fileOut = new FileOutputStream("changjiudai_" + System.currentTimeMillis() + ".xlsx");
	    wb.write(fileOut);
	    fileOut.close();
	}
	
}
