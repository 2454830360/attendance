package com.system.attendance.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 用于拦截判断用户是否登录
 */
//@WebFilter(urlPatterns = "/*")
public class LoginFilter implements Filter{

    private static final Logger LOG = LoggerFactory.getLogger(LoginFilter.class);

    //标识符：表示用户还每登录
    String noLogin = "false";

    //不需要登录就能访问的路径，如登录界面
    String[] includeURLS = new String[]{"/login/user","/login/admin"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest requests = (HttpServletRequest) request;
        HttpServletResponse responses = (HttpServletResponse) response;
        HttpSession session = requests.getSession(false);
        String uri = requests.getRequestURI();

        LOG.info("filter拦截的路径："+uri);
        boolean needFilter = isNeedFilter(uri);

        if(needFilter){
            //不需要过滤
            chain.doFilter(request,response);
        }else{
            //需要过滤，判断用户是否输入过账号密码
            if(session!=null && session.getAttribute("admin")!=null){
                //session有admin即为登录过
                LOG.info("已登录");
                chain.doFilter(request,response);
            }else{
                String requestType = ((HttpServletRequest) request).getHeader("X-Requested-With");
                //判断是不是ajax请求
                if(requestType!=null && "XMLHttpRequest".equals(requestType)){
                    //该请求是ajax异步http请求
                    LOG.info("ajax请求用户未登录！");
                    responses.getWriter().write(this.noLogin);
                }else{
                    //该请求时传统的同步http请求
                    LOG.info("传统请求用户未登录！");
                    responses.getWriter().write(this.noLogin);
                }
                return;
            }
        }
    }

    //是否需要过滤
    private boolean isNeedFilter(String uri) {
        for(String inUrl: includeURLS){
            if(inUrl.equals(uri)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {

    }
}
