package com.example.recallbackend.lnterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.recallbackend.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tzih
 * @date 2022.09.13
 */
@Component
@Slf4j
public class MyHanlderInterceptor implements HandlerInterceptor {

//    @Resource
//    private HttpServletRequest myRequest;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        String token = request.getHeader("Authorization");

        if (token == null) {
            log.info("没有token");
            return false;
        }

        DecodedJWT decode = JwtUtils.decode(token);

        if (decode != null) {
            log.info("token校验通过");
            return true;
        }
        else {
            log.info("token校验未通过");
            return false;
        }

//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
