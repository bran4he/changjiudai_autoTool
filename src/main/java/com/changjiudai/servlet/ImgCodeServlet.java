package com.changjiudai.servlet;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.CookieStore;

import com.changjiudai.trans.Cagent;
import com.changjiudai.trans.LoginAction;

@WebServlet(name = "getImgCode", urlPatterns={"/getCode", "/login", "/exportXlsx"})
public class ImgCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("request.getServletPath(): " + request.getServletPath());
		
		if("/getCode".equals(request.getServletPath())){
			try {
				
				Cagent agent = new Cagent();
				agent.getImgCodeUrl();
				
				response.getWriter().write(agent.getImgCodePath());
				
				HttpSession session = request.getSession();
				System.out.println("session get img: " + session);
				
				//override
				session.setAttribute("agent", agent);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if("/login".equals(request.getServletPath())){
			HttpSession session = request.getSession();
			System.out.println("session login: " + session);
			Cagent agent = (Cagent) session.getAttribute("agent");
			
			if(agent != null){
				
				String imgCode = request.getParameter("imgCode");
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
				System.out.println("login get imgCode: " + imgCode);
				
				try {
					boolean result = agent.loginAndSign(username, password, imgCode);
					if(result){
						response.getWriter().write("login true");
					}else{
						response.getWriter().write("login false");
					}
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}else{
				System.out.println("agent in session is null!!");
			}
			
			
		}else if("/exportXlsx".equals(request.getServletPath())){
			HttpSession session = request.getSession();
			System.out.println("session login: " + session);
			Cagent agent = (Cagent) session.getAttribute("agent");
			
			if(agent != null){
				
				boolean result;
				try {
					result = agent.exportReport();
					if(result){
						response.getWriter().write(agent.getReportPath());
					}else{
						response.getWriter().write("export false");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}else{
				System.out.println("agent in session is null!!");
			}
			
			
		}else{
			try {
				response.getWriter().write("other request...");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	

}
