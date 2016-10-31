package com.ss.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.changjiudai.util.PropertiesUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ss.bean.ServerBean;

/**
 * 1. check if the email is the one, GET request
 * ?email=cat#123.com
 * 
 * 2. send out the server info to remote, response
                 {
                    "exist": "Y"/"N",
                    "server": "192.167.1.1",
                    "port": "13121",
                    "password": "222222"
                }
 * 
 */
@WebServlet(name = "ShadowSocksServlet", urlPatterns={"/checkEmail"})
public class ShadowSocksServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private static String SERVER_SUFFIX = ".server";
	private static String PORT_SUFFIX = ".port";
	private static String PASSWORD_SUFFIX = ".password";
	
	private static Logger logger = Logger.getLogger(ShadowSocksServlet.class);
	
	private String checkExistAndReturnKey(String email) {
		HashMap<String, String> map = PropertiesUtil.getAll();
		Set<Entry<String, String>> entry = map.entrySet();
		for(Entry<String, String> en : entry){
//			logger.info("========" + en.getValue() +":" + en.getKey());
			if("Y".equalsIgnoreCase(en.getValue()) && email.startsWith(en.getKey())){
				logger.info("get the target is :" + en.getKey() + "=" + en.getValue());
				return en.getKey();
			}
		}
		return null;
	}
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		logger.info("/checkEmail request param :" + email);
		ServerBean result = new ServerBean();
		result.setExist("N");
		
		String ssEnableStr = PropertiesUtil.getValue("ss.enable");
		String[] ssEnable = ssEnableStr.split(",");
		for(String ss : ssEnable){
			String key = checkExistAndReturnKey(ss);
			if(null != checkExistAndReturnKey(ss)){
				result.setExist("Y");
				result.setServer(PropertiesUtil.getValue(key + SERVER_SUFFIX));
				result.setPort(PropertiesUtil.getValue(key + PORT_SUFFIX));
				result.setPassword(PropertiesUtil.getValue(key + PASSWORD_SUFFIX));
				break;
			}
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
		    out = response.getWriter();
		    String jsonStr = mapper.writeValueAsString(result);
		    logger.info(jsonStr);
		    out.write(jsonStr);
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (out != null) {
		        out.close();
		    }
		}
	}

}
