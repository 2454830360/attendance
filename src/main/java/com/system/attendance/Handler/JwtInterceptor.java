package com.system.attendance.Handler;


import com.system.attendance.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("tokens:")){
            LOG.info("找不到token，请先登录");
        }
        //取到token
        String token = authHeader.substring(7);
//        System.out.println("前台token----"+token);
        //验证token
        Claims claims = JWTUtil.checkToken(token);

//        request.setAttribute("userId", claims.getSubject());

        return true;
    }
}
