package com.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.model.Logger;
import com.model.User;

public class HandlerInterceptor1 implements HandlerInterceptor{

	protected Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		//
	}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		User user=(User) session.getAttribute("user");
		if(user!=null){
			return true;
		}
		logger.info("用户未登录!");
		/*request.getRequestDispatcher("login.jsp").forward(request, response);*/
		response.sendRedirect("/aihudongUser-web/index/test");
		return false;
	}

	

	
	
}
