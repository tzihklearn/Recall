package com.example.recallbackend.lnterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.recallbackend.pojo.CommonResult;
import com.example.recallbackend.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            log.warn("token校验未通过");

            CommonResult<Object> objectCommonResult = CommonResult.token_error();

            try {
                //将 map装换为json ResponseBody底层使用jackson
                String json = new ObjectMapper().writeValueAsString(objectCommonResult);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().println(json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return false;
        }

//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
