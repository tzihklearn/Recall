package com.example.recallbackend.lnterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.recallbackend.utils.JwtUtils.JwtUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tzih
 * @date 2022.09.13
 */
@Component
public class MyHanlderInterceptor implements HandlerInterceptor {

    @Resource
    private HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader("Authorization");

        DecodedJWT decode = JwtUtils.decode(token);

        if (decode != null) {
            request.setAttribute("jwt", decode.getClaim("userId"));
            return true;
        }
        else {
            return false;
        }

//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
