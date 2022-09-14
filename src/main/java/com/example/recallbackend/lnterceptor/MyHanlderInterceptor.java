package com.example.recallbackend.lnterceptor;

import com.example.recallbackend.utils.JwtUtils.JwtUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tzih
 * @date 2022.09.13
 */
public class MyHanlderInterceptor implements HandlerInterceptor {

    @Resource
    private HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("Authorization");

        return JwtUtils.decode(token);
//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
