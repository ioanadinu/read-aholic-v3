package com.app.readaholicv3.config;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

public class SecurityHttpInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest requestServlet, HttpServletResponse responseServlet, Object handler) throws Exception
    {
        System.out.println("MINIMAL: INTERCEPTOR PREHANDLE CALLED");
        System.out.println(requestServlet.getRequestURI());
        System.out.println(Collections.list(requestServlet.getHeaderNames()));
//        System.out.println(requestServlet.getParts());
        System.out.println(requestServlet.getQueryString());
        return true;
    }

//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
//    {
//        System.out.println("MINIMAL: INTERCEPTOR POSTHANDLE CALLED");
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) throws Exception
//    {
//        System.out.println("MINIMAL: INTERCEPTOR AFTERCOMPLETION CALLED");
//    }
}
